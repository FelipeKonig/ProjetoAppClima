package br.edu.ifsc.webServiceCliente2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

	private static Scene scene;

	@Override
	public void start(Stage stage) throws IOException {
		scene = new Scene(loadFXML());
		stage.setScene(scene);
		stage.show();
	}

	public static void setRoot() throws IOException {
		scene.setRoot(loadFXML());
	}

	private static Parent loadFXML() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/br/edu/ifsc/ProjetoWebServiceCliente2/home.fxml"));
		return fxmlLoader.load();
	}

}