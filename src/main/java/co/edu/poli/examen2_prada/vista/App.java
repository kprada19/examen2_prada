package co.edu.poli.examen2_prada.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(
            getClass().getResource("/co/edu/poli/examen2_prada/formSeguro.fxml")
        );

        stage.setScene(new Scene(root));
        stage.setTitle("Seguros");
        stage.show();
    }


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch();
	}
}

