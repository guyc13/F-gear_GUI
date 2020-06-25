package model.interpeter.assets;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.interpeter.MyInterpreter;


public class DataReaderServer {
	
	private static volatile boolean stop  ;
	private int port;
	private int frequency;
	public  ArrayList<String> varNames;
	BufferedReader input;
	
	public DataReaderServer(int port, int frequency) {
		stop = false;
		this.input = null;
		if(port<1 || port >65000)
			System.out.println("Unvalid port!");
		this.port = port;
		if(frequency < 1)
			System.out.println("Unvalid frequency");
		this.frequency = frequency;
		
		
//        try {
//            Scanner s=new Scanner(new BufferedReader(new FileReader("./resources/simulator_vars.txt")));
//            while(s.hasNext()){
//                varNames.add(s.next());
//            }
//            s.close();
//        } catch (FileNotFoundException e) {}
////        addVars();
//        for(String s : varNames) {
//            MyInterpreter.SymbolTbl.put(s, 0.0);}
		
	}
	
	
	
	public void runServer() {
		try {
			ServerSocket server = new ServerSocket(port);
//			server.setSoTimeout(60000);
			try {
				while(!stop) {
					Socket client = server.accept(); //blocking call
					System.out.println("simulator has been conncated");
					input=new BufferedReader(new InputStreamReader(client.getInputStream()));
					String line,name;
					String[] lines;
					double value;
					while ((line = input.readLine())!= null) {
						lines = line.split(",");
						
//						MyInterpreter.pathToValue.put("simX", new SimpleDoubleProperty(Double.parseDouble(lines[0])));
//						MyInterpreter.pathToValue.put("simY", new SimpleDoubleProperty(Double.parseDouble(lines[1])));
//						MyInterpreter.pathToValue.put("simZ", new SimpleDoubleProperty(Double.parseDouble(lines[2])));
						if(lines.length!= MyInterpreter.varList.size()) {
							System.out.println("Connections Problems");
							System.out.println("lines.length = " + lines.length +"and varList.size is " + MyInterpreter.varList.size());
							continue;
						}
						
						for (int i = 0; i < lines.length; i++) {
							name = MyInterpreter.varList.get(i);
							value = Double.parseDouble(lines[i]);
							MyInterpreter.SymbolTbl.get(name).set(value);
							MyInterpreter.pathToValue.put(MyInterpreter.varToPath.get(name), new SimpleDoubleProperty(value));
						}
						
						try {Thread.sleep(1000/frequency);
						} catch (InterruptedException e) {e.printStackTrace();}
						
					}
					input.close();
					client.close();
				}
				
			} catch (SocketTimeoutException e) {e.printStackTrace();}
				
			
			server.close();
		} catch(IOException e){e.printStackTrace();}
	}
	
	
	
	
//	public void runServer() {
//		try {
//			System.out.println("Data server port is : "+ port);
//			ServerSocket server=new ServerSocket(port);
//			server.setSoTimeout(1000);
//				try{
//					while(!stop){
//					Socket client=server.accept();
//					System.out.println("client connected...");
//					MyInterpreter.flag=true;
//					BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
//					String line = in.readLine();
//					while(line != null){
//						String[] paths = line.split(",");
//						MyInterpreter.pathToValue.put("simX",Double.parseDouble(paths[0]));
//						MyInterpreter.pathToValue.put("simY",Double.parseDouble(paths[1]));
//						MyInterpreter.pathToValue.put("simZ",Double.parseDouble(paths[2]));
//
//						line = in.readLine();
//						try {Thread.sleep(1000/frequency);} catch (InterruptedException e) {e.printStackTrace();}
//					}
//					in.close();
//					client.close();
//					
//					}	
//				}catch(SocketTimeoutException e){}
//			server.close();
//		} catch (IOException e) {}
//	}

	public static void close() {
		stop = true;
	}
	
	
//  public void addVars(){
//  varNames.add("/instrumentation/airspeed-indicator/indicated-speed-kt");
//  varNames.add("/instrumentation/altimeter/indicated-altitude-ft");
//  varNames.add("/instrumentation/altimeter/pressure-alt-ft");
//  varNames.add("/instrumentation/attitude-indicator/indicated-pitch-deg");
//  varNames.add("/instrumentation/attitude-indicator/indicated-roll-deg");
//  varNames.add("/instrumentation/attitude-indicator/internal-pitch-deg");
//  varNames.add("/instrumentation/attitude-indicator/internal-roll-deg");
//  varNames.add("/instrumentation/encoder/indicated-altitude-ft");
//  varNames.add("/instrumentation/encoder/pressure-alt-ft");
//  varNames.add("/instrumentation/gps/indicated-altitude-ft");
//  varNames.add("/instrumentation/gps/indicated-ground-speed-kt");
//  varNames.add("/instrumentation/gps/indicated-vertical-speed");
//  varNames.add("/instrumentation/heading-indicator/indicated-heading-deg");
//  varNames.add("/instrumentation/magnetic-compass/indicated-heading-deg");
//  varNames.add("/instrumentation/slip-skid-ball/indicated-slip-skid");
//  varNames.add("/instrumentation/turn-indicator/indicated-turn-rate");
//  varNames.add("/instrumentation/vertical-speed-indicator/indicated-speed-fpm");
//  varNames.add("/controls/flight/aileron");
//  varNames.add("/controls/flight/elevator");
//  varNames.add("/controls/flight/rudder");
//  varNames.add("/controls/flight/flaps");
//  varNames.add("/controls/engines/current-engine/throttle");
//  varNames.add("/engines/engine/rpm");
//  varNames.add("/controls/flight/speedbrake");
//  varNames.add("/position/latitude-deg");
//  varNames.add("/position/longitude-deg");
//
//}
//  
  
  
  
  
  
  
//  public void runServer() {
//      try {
//    	  System.out.println("Creating Data server");
//          ServerSocket server=new ServerSocket(port);
//          Socket client=server.accept(); //blocking call
//          System.out.println("client has been connected...");
//          MyInterpreter.flag=true;
//          input=new BufferedReader(new InputStreamReader(client.getInputStream()));
//          int i;
//          while(!stop) {
//              i=0;
//              String[] inputFromClient = input.readLine().split(",");
//              for(String v: inputFromClient) {
//            	  String path=varNames.get(i);
//            	  //updating the path to value hashmap
//            	  if(MyInterpreter.pathToValue.containsKey(path)) 
//            		  MyInterpreter.pathToValue.put(path,Double.parseDouble(v));
////            	  else System.out.println("Wrong path");
//            		 
//            	  //extrcting the vars and updating the sybmboltbl hashmap
//            	  if (MyInterpreter.pathToVar.containsKey(path))
//            			  {  LinkedList<String> vars = MyInterpreter.pathToVar.get(varNames.get(i));
//                    	  
//                    	  for (String var : vars) {
//                    		  MyInterpreter.SymbolTbl.put(var, Double.parseDouble(v));
//        				}}
//            	  i++;
//            	}
//            	  
//                  
//              }
//              Thread.sleep(frequency);
//          
//          input.close();
//          client.close();
//          server.close();
//      } catch (IOException | InterruptedException e) {}
//
//  }
//  
//	while (!stop) {
//		i = 0;
//		inputFromClient = in.readLine().split(",");
//		for (String s : inputFromClient) {
//			
//			i++;
//		}
//		Thread.sleep(frequency);
}
