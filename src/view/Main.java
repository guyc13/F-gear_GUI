package view;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.MyModel;
import model.interpeter.assets.DataWriterClient;
import viewModel.ViewModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("GuiMainWindow.fxml"));
			
			
			MyModel m = new MyModel();
			ViewModel vm = new ViewModel(m);
			m.addObserver(vm);
//			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("GuiMainWindow.fxml"));
//			Scene scene = new Scene(root,640,250);
//			
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();


	        FXMLLoader loader = new FXMLLoader(getClass().getResource("GuiMainWindow.fxml"));
	        Parent root = loader.load();
			
	        guiController ctrl = loader.getController();
	        ctrl.setViewModel(vm);
	        vm.addObserver(ctrl);
	        primaryStage.setTitle("FlightGear Desktop App");
	        primaryStage.setScene(new Scene(root, 828, 290));
	        primaryStage.show();
	        
	        DataWriterClient dw = new DataWriterClient("127.0.0.1", 5402);
	        dw.runClient();
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
