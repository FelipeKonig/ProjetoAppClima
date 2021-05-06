package br.edu.ifsc.ProjetoWebServiceCliente1.WebServiceCliente1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App {
	public static void post(int id) {
		try {

			URL url = new URL("http://localhost:8080/clima/adiciona");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setDoOutput(true);

			Random random = new Random();

			int min = -20;
			int max = 50;
			int temperatura = random.nextInt(max - min) + min;

			min = 0;
			max = 100;
			int umidade = random.nextInt(max - min) + min;

			min = 0;
			max = 24;
			int hora = random.nextInt(max - min) + min;

			min = 0;
			max = 59;
			int minuto = random.nextInt(max - min) + min;

			ZoneId z = ZoneId.of("America/Bahia");
			LocalDate data = LocalDate.now(z);

			String jsonInputString = "{\"id\":\"" + id + "\", \"data\":\"" + data + "\", \"horario\":\"" + hora + ":"
					+ minuto + "\", \"temperatura\":\"" + temperatura + "\", \"umidade\":\"" + umidade + "\"}";

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
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public static void main(String[] args) {

		int id = 1;
		while (true) {
			post(id);
			id++;

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// do things with exception
			}
		}
	}
}
