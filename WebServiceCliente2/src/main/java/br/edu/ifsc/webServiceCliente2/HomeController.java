package br.edu.ifsc.webServiceCliente2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.json.*;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomeController implements Initializable {

	public static Clima clima = null;

	private static HashMap<Integer, Clima> lista_clima = new HashMap<Integer, Clima>();

	@FXML
	private Button btn_atualiza;

	@FXML
	private Button btn_selecionar;

	@FXML
	private Button btn_alterar;

	@FXML
	private Button btn_deletar;

	@FXML
	private Label lbl_temperatura;

	@FXML
	private Label lbl_umidade;

	@FXML
	private Label lbl_luminosidade;

	@FXML
	private Label lbl_data;

	@FXML
	private Label lbl_hora;

	@FXML
	private Label lbl_id;

	@FXML
	private void atualizarClimas(MouseEvent event) {
		lista_clima.clear();
		pegarClimas();

		if (!(lista_clima.isEmpty())) {
			NavigableMap<Integer, Clima> map = new TreeMap<Integer, Clima>(lista_clima);
			Clima clima = lista_clima.get(map.lastEntry().getKey());
			atualizarDadosInterface(clima);
		}

	}

	@FXML
	private void alterarClima(MouseEvent event) {
		abrirAlterar();
	}

	@FXML
	private void deletarClima(MouseEvent event) {
		ConfiguracaoController.funcao = "deletar";
		abrirConfig();
	}

	@FXML
	private void selecionarClima(MouseEvent event) {
		ConfiguracaoController.funcao = "selecionar";
		abrirConfig();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (clima != null) {
			buscarClimaServer(clima);
			clima = null;
		} else {
			if (lista_clima.isEmpty()) {
				pegarClimas();
			}
			if (!(lista_clima.isEmpty())) {
				NavigableMap<Integer, Clima> map = new TreeMap<Integer, Clima>(lista_clima);
				Clima clima = lista_clima.get(map.lastEntry().getKey());
				atualizarDadosInterface(clima);
			}
		}

	}

	private void pegarClimas() {
		try {
			URL url = new URL("http://localhost:8080/clima/todos");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader climas = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			addClimas(climas);

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	private void addClimas(BufferedReader climas) {

		JSONTokener tokener = new JSONTokener(climas);
		JSONObject climasJ = new JSONObject(tokener);

		Iterator<String> keys = climasJ.keys();

		System.out.println("Todos os climas:");
		while (keys.hasNext()) {
			String key = keys.next();
			if (climasJ.get(key) instanceof JSONObject) {

				JSONObject obj = (JSONObject) climasJ.get(key);

				Clima clima = new Clima(obj.getString("data"), obj.getString("horario"), obj.getString("luminosidade"),
						obj.getInt("temperatura"), obj.getInt("id"), obj.getInt("umidade"));

				System.out.println(clima);

				lista_clima.put(clima.getId(), clima);
			}
		}
		System.out.println("");
	}

	private void atualizarDadosInterface(Clima clima) {

		Platform.runLater(() -> {
			try {
				lbl_data.setText(clima.getData());
				lbl_hora.setText(clima.getHorario());
				lbl_id.setText(String.valueOf(clima.getId()));
				lbl_luminosidade.setText(clima.getLuminosidade());
				lbl_umidade.setText(String.valueOf(clima.getUmidade() + "%"));
				lbl_temperatura.setText(String.valueOf(clima.getTemperatura() + "ºC"));

			} catch (Exception e) {
				System.out.println(e);
			}
		});
	}

	private void buscarClimaServer(Clima clima) {
		try {
			URL url = new URL("http://localhost:8080/clima/buscaid/" + clima.getId());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader clima_server = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			JSONTokener tokener = new JSONTokener(clima_server);

			JSONObject obj = new JSONObject(tokener);

			Clima clima_encontrado = new Clima(obj.getString("data"), obj.getString("horario"),
					obj.getString("luminosidade"), obj.getInt("temperatura"), obj.getInt("id"), obj.getInt("umidade"));

			Platform.runLater(() -> {
				try {
					lbl_data.setText(clima_encontrado.getData());
					lbl_hora.setText(clima_encontrado.getHorario());
					lbl_id.setText(String.valueOf(clima_encontrado.getId()));
					lbl_luminosidade.setText(clima_encontrado.getLuminosidade());
					lbl_umidade.setText(String.valueOf(clima_encontrado.getUmidade() + "%"));
					lbl_temperatura.setText(String.valueOf(clima_encontrado.getTemperatura() + "ºC"));

				} catch (Exception e) {
					System.out.println(e);
				}
			});

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	private void abrirAlterar() {

		try {
			Stage stage = new Stage();

			FXMLLoader fxmlLoader = new FXMLLoader(
					App.class.getResource("/br/edu/ifsc/ProjetoWebServiceCliente2/alterarClima.fxml"));
			Parent root = (Parent) fxmlLoader.load();

			stage.setScene(new Scene(root));
			stage.show();

			Stage stage2 = (Stage) btn_alterar.getScene().getWindow();
			stage2.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void abrirConfig() {
		try {
			Stage stage = new Stage();

			FXMLLoader fxmlLoader = new FXMLLoader(
					App.class.getResource("/br/edu/ifsc/ProjetoWebServiceCliente2/configuracao.fxml"));
			Parent root = (Parent) fxmlLoader.load();

			stage.setScene(new Scene(root));
			stage.show();

			Stage stage2 = (Stage) btn_alterar.getScene().getWindow();
			stage2.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static HashMap<Integer, Clima> getLista_clima() {
		return lista_clima;
	}

}
