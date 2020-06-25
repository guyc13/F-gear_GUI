//package view;
//
//import javafx.beans.property.DoubleProperty;
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.scene.SnapshotParameters;
//import javafx.scene.canvas.Canvas;
//
//import model.server.*;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Observable;
//import java.util.Observer;
//
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
//
//import model.interpeter.MyInterpreter;
//
//
//
//public class Aircraft extends Canvas {
//    Image aircraft;
////    ImageView aircraft;
//    guiController vc;
//    DoubleProperty longitude, latitude,heading;
//    Position currentPosition;
//    double x, y, pixelSize;
//    int [] mapSize;
//
//
//    public Aircraft() {
//        currentPosition=new Position();
//        try {
//            aircraft=new Image(new FileInputStream("./resources/airplan.png"));
//            //aircraft=new ImageView();
//            //aircraft.setImage(new Image(new FileInputStream("./images/c130j.png")));
//        } catch (IOException e) {e.printStackTrace();}
//        longitude=new SimpleDoubleProperty();
//        latitude=new SimpleDoubleProperty();
//        heading=new SimpleDoubleProperty();
//    }
//    public void setVc(guiController vc){
//        this.vc=vc;
//    }
//    public void setAircraft(){
//
//        x=vc.map.initCoordinate.getX();//load to x and y the 0,0 cell coordinate
//        y=vc.map.initCoordinate.getY();//load to x and y the 0,0 cell coordinate
//        
//        System.out.println(x+" , "+y);
//        mapSize=new int[]{vc.map.rows, vc.map.columns};
//        pixelSize=vc.map.pixelSize;
//    }
//    public void position() {
//        double longNew = ((MyInterpreter.SymbolTbl.get("positionX").getValue() - x) + pixelSize) / pixelSize;
//        double latNew = (-(MyInterpreter.SymbolTbl.get("positionY").getValue() - y) + pixelSize) / pixelSize;
////        double longNew = ((longitude.get() - x) + pixelSize) / pixelSize;
////        double latNew = (-(latitude.get() - y) + pixelSize) / pixelSize;
//        
//        if(mapSize!=null) {
//            int row = Math.round((float) (mapSize[0] * longNew / getHeight()));
//            int column = Math.round((float) (mapSize[1] * latNew / getWidth()));
//            currentPosition.setX(row);
//            currentPosition.setY(column);
//            
//        }
//        GraphicsContext gc = getGraphicsContext2D();
//        gc.clearRect(0, 0, getWidth(), getHeight());
//        gc.drawImage(aircraft, longNew, latNew, 25, 25);
////        gc.drawImage(aircraft, longNew-18, latNew-18, 40, 40);
//    }
//
//
//}
