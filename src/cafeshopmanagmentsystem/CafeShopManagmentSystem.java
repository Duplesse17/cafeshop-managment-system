package cafeshopmanagmentsystem;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CafeShopManagmentSystem extends Application {
    
    @Override
    public void start(Stage stage)throws Exception{
        Parent root ;
        try{
            root= FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root);

            stage.setTitle("Cafe Shop Managment System");
            Image icon = new Image("address-card.svg");
            stage.getIcons().add(icon);
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();   
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    /**
     * @param args
     */
    public static void main(String args[]){
        launch(args);
    }
}
