package br.edu.ifsc.projetoWebServiceServer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Clima {
	private String data, horario, luminosidade;
	private int temperatura;
	private int id, umidade;

	public Clima(@JsonProperty("id") int id, @JsonProperty("data") String data, @JsonProperty("horario") String horario,
			@JsonProperty("temperatura") int temperatura, @JsonProperty("umidade") int umidade) {
		this.id = id;
		this.data = data;
		this.horario = horario;
		this.temperatura = temperatura;
		this.umidade = umidade;

		String[] horaS = horario.split(":");
		int hora = Integer.parseInt(horaS[0]);
		if (hora < 7) {
			this.luminosidade = "Madrugada";
		} else if (hora >= 7 && hora < 12) {
			this.luminosidade = "Manhã";
		} else if (hora >= 12 && hora < 19) {
			this.luminosidade = "Tarde";
		} else if (hora >= 19) {
			this.luminosidade = "Noite";
		}
	}

	public int getId() {
		return id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getLuminosidade() {
		return luminosidade;
	}

	public void setLuminosidade(String luminosidade) {
		this.luminosidade = luminosidade;
	}

	public float getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}

	public int getUmidade() {
		return umidade;
	}

	public void setUmidade(int umidade) {
		this.umidade = umidade;
	}

	@Override
	public String toString() {
		return "Clima [data=" + data + ", horario=" + horario + "; luminosidade=" + luminosidade + ", temperatura="
				+ temperatura + ", id=" + id + ", umidade=" + umidade + "]";
	}
	
	
}
