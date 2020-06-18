package model;

import model.interpeter.*;
import model.interpeter.assets.DataWriterClient;
import model.server.MyClientHandler;
import model.server.MySerialServer;
import model.server.MyTestClientHandler;
//import model.server_side.MyClientHandler;
//import model.server_side.MySerialServer;
//import model.server_side.Server;
import model.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class MyModel extends Observable {

	Server calcServer; // server to calc best path
	int calcServerPort = 5566;
	PrintWriter outTocalcServer;
	BufferedReader inFromCalcServer;
	String[][] matrix;
	String path;
	String ipForCalcServer;
	int portForCalcServer;

	public MyModel() {
//        this.calcServerPort = calcServerPort;
		calcServer = new MySerialServer();
		calcServer.open(calcServerPort, new MyClientHandler());
//        getAircraftPosition();
		Command openServer = MyInterpreter.commandMap.get("openDataServer");
		String[] args = { "5400", "10" };
		openServer.doCommand(args);
//    
	}

	public void runScript(String[] lines) {
		new Thread(() -> {
			MyInterpreter.interpret(lines);
		}).start();
	}

	public void controlElevatorAileron(double elevator, double aileron) {
		System.out.println("elevator -> " + elevator + " , aileron-> " + aileron);
		ConnectCommand.out.println("set /controls/flight/elevator " + elevator);
		ConnectCommand.out.println("set /controls/flight/aileron " + aileron);
		ConnectCommand.out.flush();
//		DataWriterClient.out.println("set /controls/flight/elevator " + elevator);
//		DataWriterClient.out.println("set /controls/flight/aileron " + aileron);
//		DataWriterClient.out.flush();
	}

	public void controlRudder(double rudder) {
		ConnectCommand.out.println("set /controls/flight/rudder " + rudder);
		ConnectCommand.out.flush();
	}

	public void controlThrottle(double throttle) {
		ConnectCommand.out.println("set /controls/engines/current-engine/throttle " + throttle);
		ConnectCommand.out.flush();
	}

	public void connectToSim(String ip, String port) {
		String[] args = { ip, port };
		Command connect = MyInterpreter.commandMap.get("connect");
		connect.doCommand(args);
	}

	public String connectToCalcServer(String ip, String port, String[][] matrix, String init, String goal) {
		ipForCalcServer = ip;
		portForCalcServer = Integer.parseInt(port);
		this.matrix = matrix;
		return getPathFromCalcServer(init, goal);
	}

	public String getPathFromCalcServer(String init, String goal) {
		try {
			
			

			Socket theServer = new Socket(ipForCalcServer, portForCalcServer);
			System.out.println("connected to calc server");
			outTocalcServer = new PrintWriter(theServer.getOutputStream());
			inFromCalcServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		int i, j;
		System.out.println("sending problem...");
		for (i = 0; i < matrix.length -1 ; i++) {
			// System.out.print("\t");
			for (j = 0; j < matrix[i].length - 1; j++) {
				outTocalcServer.print(matrix[i][j] + ",");
//				 System.out.print(matrix[i][j]+",");
			}
			outTocalcServer.println(matrix[i][j]);
//			 System.out.println(matrix[i][j]);
		}

		outTocalcServer.println("end");// end of matrix sign
		outTocalcServer.println(init);
		outTocalcServer.println(goal);
		outTocalcServer.flush();
		System.out.println("\tend\n\t" + init + "\n\t" + goal);
		System.out.println("\tproblem sent, waiting for solution...");
		try {
			this.path = inFromCalcServer.readLine();
			System.out.println(this.path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("\tsolution received");
		return path;
	}

	public void getAircraftPosition() {
		new Thread(() -> {
			while (true) {
				String[] pos = new String[3];				
				pos[0] = String.valueOf(MyInterpreter.SymbolTbl.get("positionX").getValue());
				pos[1] = String.valueOf(MyInterpreter.SymbolTbl.get("positionY").getValue());
				pos[2] = String.valueOf(MyInterpreter.SymbolTbl.get("heading").getValue());
				
//				pos[0] = String.valueOf(MyInterpreter.SymbolTbl.get("/position/latitude-deg"));
//				pos[1] = String.valueOf(MyInterpreter.SymbolTbl.get("/position/longitude-deg"));
//				pos[2] = String.valueOf(
//						MyInterpreter.SymbolTbl.get("/instrumentation/heading-indicator/indicated-heading-deg"));

				this.setChanged();
				this.notifyObservers(pos);
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {e.printStackTrace();}
			}
		}).start();
	}
}
