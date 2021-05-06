package br.edu.ifsc.webServiceCliente2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConfiguracaoController implements Initializable {

	public static String funcao = null;

	@FXML
	private Button btn_confirmar;

	@FXML
	private ChoiceBox<String> lista_horarios_interface;

	@FXML
	private TextField txt_id;

	@FXML
	private void confirmar(MouseEvent event) {
		Clima clima = verificaClimaSelecionado();

		if (funcao.contentEquals("deletar")) {

			if (clima != null) {
				boolean del = deletarClima(clima.getId());
				HomeController.getLista_clima().remove(clima.getId());
				abrirHome();

				if (del) {
					Alert alert = new Alert(AlertType.INFORMATION, "Clima deletado");
					alert.show();
				} else {
					Alert alert = new Alert(AlertType.ERROR, "Clima não deletado");
					alert.show();
				}
			}

		} else {

			if (clima != null) {
				HomeController.clima = clima;
				abrirHome();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		preencherLista();

	}

	private void preencherLista() {
		List<String> lista = new ArrayList<String>();
		lista.add("");

		System.out.println("");
		Iterator<Entry<Integer, Clima>> iterator = HomeController.getLista_clima().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Clima> clima = iterator.next();

			String data_hora = clima.getValue().getData() + " | " + clima.getValue().getHorario();

			lista.add(data_hora);

		}
		ObservableList<String> observableArrayList = FXCollections.observableArrayList(lista);
		lista_horarios_interface.setItems(observableArrayList);
	}

	private Clima verificaClimaSelecionado() {

		Clima clima = null;
		String horario_selecionado = lista_horarios_interface.getSelectionModel().getSelectedItem();

		if (horario_selecionado != null) {

			if (!(horario_selecionado.isBlank())) {
				int id = verificarHorario(horario_selecionado);
				clima = HomeController.getLista_clima().get(id);

			} else {
				Alert alert = new Alert(AlertType.WARNING, "ID e horario vazios");
				alert.setHeaderText("Digite um ID ou horario");
				alert.show();
			}

		} else {

			if (txt_id.getText().isBlank()) {
				Alert alert = new Alert(AlertType.WARNING, "ID e horario vazios");
				alert.setHeaderText("Digite um ID ou horario");
				alert.show();
			} else {

				try {
					int id = Integer.parseInt(txt_id.getText());
					if (HomeController.getLista_clima().get(id) != null) {

						clima = HomeController.getLista_clima().get(id);

					} else {
						Alert alert = new Alert(AlertType.ERROR, "Digite um ID valido");
						alert.setHeaderText("ID invalido");
						alert.show();
					}
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.ERROR, "Digite um ID valido");
					alert.setHeaderText("ID invalido");
					alert.show();
				}
			}
		}
		return clima;
	}

	private Integer verificarHorario(String data_hora) {
		System.out.println("");
		Iterator<Entry<Integer, Clima>> iterator = HomeController.getLista_clima().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Clima> clima = iterator.next();

			String data = clima.getValue().getData();
			String hora = clima.getValue().getHorario();

			if (data_hora.contains(data) && data_hora.contains(hora)) {
				return clima.getKey();
			}
		}
		return null;
	}

	private boolean deletarClima(int id) {

		try {
			URL url = new URL("http://localhost:8080/clima/apaga/?id=" + String.valueOf(id));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("DELETE");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);

				if (output.contains("Sucesso")) {
					return true;
				}
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return false;
	}

	private void abrirHome() {

		try {

			Stage stage = new Stage();

			FXMLLoader fxmlLoader = new FXMLLoader(
					App.class.getResource("/br/edu/ifsc/ProjetoWebServiceCliente2/home.fxml"));
			Parent root = (Parent) fxmlLoader.load();

			stage.setScene(new Scene(root));
			stage.show();

			Stage stage2 = (Stage) txt_id.getScene().getWindow();
			stage2.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
