package viewModel;


import javafx.beans.property.*;
import model.MyModel;
import model.server.Position;

import java.awt.Point;
import java.util.Observable;
import java.util.Observer;


public class ViewModel extends Observable implements Observer {

    MyModel m;
    public DoubleProperty joyStickX, joyStickY, throttle, rudder, longitude, latitude, heading; //value of the joyStick position
    public BooleanProperty path;

    public ViewModel(MyModel m) {
        this.m = m;
        joyStickY=new SimpleDoubleProperty();
        joyStickX=new SimpleDoubleProperty();
        throttle=new SimpleDoubleProperty();
        rudder=new SimpleDoubleProperty();
        latitude=new SimpleDoubleProperty();
        longitude=new SimpleDoubleProperty();
        heading=new SimpleDoubleProperty();
    }
    public void runScriptVm(String script){
        m.runScript(script.split("\n"));
    }
    public void controlElevatorAileronVm(){
        double elevatorVal;
        double aileronVal;
        elevatorVal=-joyStickY.doubleValue()/61;//Double.min(0-(joyStickY.doubleValue()/70), 1);
        aileronVal=joyStickX.doubleValue()/61;//Double.min(joyStickX.doubleValue()/70, 1);
        //System.out.println(elevatorVal+" , "+aileronVal);
        m.controlElevatorAileron(elevatorVal, aileronVal);
    }
    public void controlRudderVm(){
        m.controlRudder(rudder.doubleValue());
    }
    public void controlThrottleVm(){
        m.controlThrottle(throttle.doubleValue());
    }
    public void connectToSimVM(String ip, String port){
        m.connectToSim(ip, port);
    }
    public String connectToCalcServerVm(String ip, String port, double [][] matrix, Point currentPos, Point destPoint){
        String[][] matrixAsString=new String[matrix.length][matrix[0].length];
        String initPointAsString=currentPos.getX()+","+currentPos.getY();  // changed from init.x
        String goalPointAsString=destPoint.getX()+","+destPoint.getY();
        for(int i=0;i<matrix.length;i++)
            for(int j=0;j<matrix[i].length;j++)
                matrixAsString[i][j]=String.valueOf(matrix[i][j]);
       return m.connectToCalcServer(ip, port,matrixAsString,initPointAsString, goalPointAsString);
    }
    public String getPathFromCalcServerVm( Point currentPos, Point destPoint) {
        String initPointAsString = currentPos.getX() + "," + currentPos.getY();
        String goalPointAsString = destPoint.getX() + "," + destPoint.getY();
        return m.getPathFromCalcServer(initPointAsString, goalPointAsString);
    }
    public void getAircraftPosition(){
        m.getAircraftPosition();
    }
    @Override
    public void update(Observable o, Object arg) {
        latitude.setValue(Double.parseDouble(((String[])arg)[0]));
        longitude.setValue(Double.parseDouble(((String[])arg)[1]));
        heading.setValue(Double.parseDouble(((String[])arg)[2]));
        this.setChanged();
        this.notifyObservers();
    }
    
//    public void findPath(double h,double w) {
//
//
//        if (!path.getValue())
//        {
//            m.connectPath(ip.getValue(), Integer.parseInt(port.getValue()));
//        }
//        model.findPath((int) (airplaneY.getValue()/-1), (int) (airplaneX.getValue() +15),Math.abs( (int) (markSceneY.getValue() / h)) ,
//               Math.abs((int) (markSceneX.getValue() / w)), data );
//    }
}
