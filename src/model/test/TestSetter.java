package model.test;

import model.server.MyClientHandler;
import model.server.MySerialServer;
import model.server.Server;

public class TestSetter {
	

	static Server s; 
	
	public static void runServer(int port) {

		s=new MySerialServer(); // initialize
		try {
			s.open(port, new MyClientHandler());
		}catch (Exception e) {e.printStackTrace();}
	}

	public static void stopServer() {
		s.stop();
	}
	

}
