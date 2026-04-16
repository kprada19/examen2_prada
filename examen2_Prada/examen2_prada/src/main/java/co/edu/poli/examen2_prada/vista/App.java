package co.edu.poli.examen2_prada.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        TabPane root = (TabPane) FXMLLoader.load(
            getClass().getResource("/co/edu/poli/examen2_prada/formSeguro.fxml"));
        scene = new Scene(root);
        stage.setTitle("Seguros");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
