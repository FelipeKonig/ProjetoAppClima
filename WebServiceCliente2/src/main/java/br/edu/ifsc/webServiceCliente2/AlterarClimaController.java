package br.edu.ifsc.webServiceCliente2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javafx.application.Platform;
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

public class AlterarClimaController implements Initializable {

	Integer clima_selecionado = null;

	@FXML
	private ChoiceBox<Integer> lista_ids;

	@FXML
	private TextField txt_data;

	@FXML
	private TextField txt_hora;

	@FXML
	private TextField txt_temperatura;

	@FXML
	private TextField txt_umidade;

	@FXML
	private TextField txt_luminosidade;

	@FXML
	private Button btn_confirmar;

	@FXML
	public void confirmar(MouseEvent event) {

		boolean verifica_dados = true;

		if (clima_selecionado != null) {

			try {
				Integer.parseInt(txt_temperatura.getText());
				Integer.parseInt(txt_umidade.getText());
			} catch (Exception e) {
				verifica_dados = false;

				Alert alert = new Alert(AlertType.ERROR, "Digite a temperatura e umidade apenas com numeros");
				alert.setHeaderText("Temperatura e/ou umidade invalido(s)");
				alert.show();
			}

			if (verifica_dados) {
				Clima clima = new Clima(txt_data.getText(), txt_hora.getText(), txt_luminosidade.getText(),
						Integer.parseInt(txt_temperatura.getText()), clima_selecionado,
						Integer.parseInt(txt_umidade.getText()));

				boolean alterar_clima = alterarClima(clima);

				if (alterar_clima) {
					abrirHome();
					Alert alert = new Alert(AlertType.INFORMATION, "Clima alterado");
					alert.show();
				} else {
					Alert alert = new Alert(AlertType.ERROR, "Clima não alterado");
					alert.show();
				}
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING, "Selecione um clima");
			alert.setHeaderText("Clima não selecionado");
			alert.show();
		}
	}

	@FXML
	public void escolherId(MouseEvent event) {

		if (lista_ids.getSelectionModel().getSelectedItem() != null) {
			clima_selecionado = lista_ids.getSelectionModel().getSelectedItem().intValue();
			if (clima_selecionado > 0) {
				Clima clima = HomeController.getLista_clima().get(clima_selecionado);

				atualizarDados(clima);
			}
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		preencherLista();

	}

	private void preencherLista() {
		List<Integer> lista = new ArrayList<Integer>();
		lista.add(0);

		System.out.println("");
		Iterator<Entry<Integer, Clima>> iterator = HomeController.getLista_clima().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Clima> clima = iterator.next();

			lista.add(clima.getValue().getId());

		}
		ObservableList<Integer> observableArrayList = FXCollections.observableArrayList(lista);
		lista_ids.setItems(observableArrayList);
	}

	private void atualizarDados(Clima clima) {

		Platform.runLater(() -> {
			try {
				txt_data.setText(clima.getData());
				txt_hora.setText(clima.getHorario());
				txt_luminosidade.setText(clima.getLuminosidade());
				txt_umidade.setText(String.valueOf(clima.getUmidade()));
				txt_temperatura.setText(String.valueOf(clima.getTemperatura()));

			} catch (Exception e) {
				System.out.println(e);
			}
		});
	}

	private boolean alterarClima(Clima clima) {

		try {
			
			URL url = new URL("http://localhost:8080/clima/altera");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setDoOutput(true);

			String jsonInputString = "{\"id\":\"" + clima.getId() + "\", \"data\":\"" + clima.getData()
					+ "\", \"horario\":\"" + clima.getHorario() + "\", \"temperatura\":\"" + clima.getTemperatura()
					+ "\", \"umidade\":\"" + clima.getUmidade() + "\", \"luminosidade\":\"" + clima.getLuminosidade() + "\"}";

			try {
				OutputStream os = conn.getOutputStream();
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			} catch (Exception e) {
				e.printStackTrace();
			}

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

			Stage stage2 = (Stage) txt_data.getScene().getWindow();
			stage2.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
