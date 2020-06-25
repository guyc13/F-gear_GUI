//package view;
//
//
//import javafx.geometry.Point2D;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
//import javafx.stage.FileChooser;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//public class Map extends Canvas {
//
//    String path;
//    guiController vc;
//    double heightDelta;
//    double maxHeight, minHeight;
//    double [][] matrix;
//    double pixelSize;
//    Point2D initCoordinate;
//    int columns, rows;
//
//    public void setPath(String path){
//        this.path=path;
////        convertPathToLine();
//    }
//    public void loadCSV(){
//        FileChooser fc=new FileChooser();
//        fc.setTitle("Choose CSV file");
//        fc.setInitialDirectory(new File("./resources"));
//        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("csv", "*.csv"));
//        File chosen= fc.showOpenDialog(null);
//        if(chosen!=null){
//            System.out.println(chosen.getName());
//        }
//        try {
//            Scanner s=new Scanner(new FileReader(chosen)).useDelimiter("\n");
//            this.setMapDisplay(s);
//        } catch (FileNotFoundException e) {}
//    }
//    public void setVc(guiController vc){
//        this.vc=vc;
//    }
//    public void setMapDisplay(Scanner s){
//        ArrayList<String[]> arr=new ArrayList<>();
//        while(s.hasNext()){
//            String[] read=s.next().split(",");
//            arr.add(read);
//        }
//
//        initCoordinate=new Point2D(Double.parseDouble(arr.get(0)[0]),Double.parseDouble(arr.get(0)[1]));
//        pixelSize=Double.parseDouble(arr.get(1)[0]);
//        columns=arr.get(2).length;
//        rows=arr.size()-2;
//        matrix=new double[rows][columns];
//        for(int i=0; i<rows; i++){
//            for (int j=0; j<columns; j++){
//                matrix[i][j]=Double.parseDouble(arr.get(i+2)[j]);
//                if(matrix[i][j]<minHeight) minHeight=matrix[i][j];
//                else if(matrix[i][j]>maxHeight) maxHeight=matrix[i][j];
//            }
//        }
//        heightDelta=maxHeight-minHeight;
//    }
//    public Color getColor(double cellHeight){
//        Color c=Color.hsb(100*cellHeight/heightDelta, 1.0, 0.5);
//        double red=c.getRed(), green=c.getGreen(), blue=c.getBlue();
//        return Color.color(red, green, blue);
//    }
//    public void mapDrawer() {
//        GraphicsContext gc=getGraphicsContext2D();
//        double H=this.getHeight();
//        double W=this.getWidth();
//        double h=H/rows;
//        double w=W/columns;
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                gc.setFill(getColor(this.matrix[i][j]));
////                gc.fillRect(j*w/1.5, i*h/1.2, w, h);
//                gc.fillRect(j*w, i*h, w, h);
////                gc.setStroke(Color.WHITE);
////                gc.setFont(new Font("ariel", 8));
////                gc.strokeText(String.valueOf(matrix[i][j]),j*w/1.5 , i*h/1.2);
//            }
//        }
//    }
//}