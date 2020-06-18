package model.interpeter;

import java.io.PrintWriter;
import java.util.LinkedList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.interpeter.assets.DataWriterClient;
import shuntingYard.*;

public class AssignCommand implements Command {

	//This command doing assignment, variable to value or variable to path 
	public double doCommand(String[] args) {
		String var = args[0];
		StringBuilder sb = new StringBuilder();
		String path;
		Double value;

		if (!MyInterpreter.SymbolTbl.containsKey(var)) {
			System.out.println(var + "is unvalid input.");
			return -1;
		}


		// bind path
		if (args[1].equals("bind")) {
			if (MyInterpreter.SymbolTbl.containsKey(args[0]) && (MyInterpreter.SymbolTbl.get(args[0]).doubleValue()!=-1))
				return 0;
			
			for (int i = 2; i < args.length - 1; i++) {
				if (args[i].equals("\""))
					continue;
				sb.append(args[i]);
			}
			sb.deleteCharAt(sb.length()-1);
			path = sb.toString();

			
			
			//bind var and path 
			MyInterpreter.SymbolTbl.put(var, new SimpleDoubleProperty(0.0));
			MyInterpreter.pathToValue.put(path, new SimpleDoubleProperty(0.0));
			
			MyInterpreter.SymbolTbl.get(var).bindBidirectional(MyInterpreter.pathToValue.get(path));
			MyInterpreter.varToPath.put(var, path);
			
			
//			
//			// bind between param and path
//			if (!MyInterpreter.pathToVar.containsKey(path))
//				MyInterpreter.pathToVar.put(path, new LinkedList<String>());
//			MyInterpreter.pathToVar.get(path).add(var);
//			MyInterpreter.varToPath.put(var, path);
//
//			// in case the path own value
//			if (MyInterpreter.pathToValue.containsKey(path))
//				MyInterpreter.SymbolTbl.replace(var, MyInterpreter.pathToValue.get(path));
//			else
//				MyInterpreter.pathToValue.put(path, MyInterpreter.SymbolTbl.get(var));

		}

		else {
			for (int i = 1; i < args.length - 1; i++) // create an equation
				sb.append(args[i]);
			value = ShuntingYard.calc(sb.toString());
			MyInterpreter.SymbolTbl.get(var).set(value);
			path = MyInterpreter.varToPath.get(var);
			ConnectCommand.out.println("set " + path + " " + value);
			// in case the variable is already binded
//			if (MyInterpreter.varToPath.containsKey(var)) {
//				path = MyInterpreter.varToPath.get(var);
//				// send update to the simulator
//				DataWriterClient.out.println("set " + path +" "+ value);
////				DataWriterClient.out.println("set " + path.substring(1,path.length()-1) +" "+ value);
//				DataWriterClient.out.flush();
////				ConnectCommand.out.flush();
//				MyInterpreter.pathToValue.replace(path, value);
//				// update all binded variables with new value
//				MyInterpreter.pathToVar.get(path)
//						.forEach((v) -> MyInterpreter.SymbolTbl.replace(v, ShuntingYard.calc(sb.toString())));
//			}
		}

		return 0;
	}

}
