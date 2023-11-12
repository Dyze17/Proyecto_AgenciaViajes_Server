package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AgenciaUQApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(AgenciaUQApp.class.getResource("/Interfaces/Principal.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Viajes UQ");
        stage.show();
    }

    public static void main(String[] args) {
        launch(AgenciaUQApp.class,args);
    }
}