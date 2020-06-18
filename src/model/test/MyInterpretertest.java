package model.test;

import model.interpeter.MyInterpreter;

public class MyInterpretertest {

	public static  double interpret(String[] lines){
		String sim="./scripts/simulator_vars.txt";
		String test="./scripts/variables_order_mainTrain4.txt";
		double i= MyInterpreter.interpret(lines);
		return i;
	}
}
