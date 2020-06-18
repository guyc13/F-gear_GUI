package model.test;

import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;

import model.interpeter.Command;
import model.interpeter.MyInterpreter;
import model.server.MyClientHandler;
import model.server.MySerialServer;

public class maintestguy {

	public static void main(String[] args) throws UnknownHostException, IOException {
		//server 1 - calc path
		int port_1 = 5566;
//		Socket s1 =new Socket("127.0.0.1",port_1);
		MySerialServer ss = new MySerialServer();
		ss.open(port_1,new MyClientHandler());
		
//       Command openServer =MyInterpreter.commandMap.get("openDataServer");
//       String[] args1 = {"5500", "10"};
//       openServer.doCommand(args1);
		
		
		//server2 - sin
//		int port_2 = 6666;
//		Socket s2 =new Socket("127.0.0.1",port_2);
		
	}

}
