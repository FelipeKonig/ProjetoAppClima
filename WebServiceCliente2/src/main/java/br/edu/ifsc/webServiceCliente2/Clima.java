package br.edu.ifsc.webServiceCliente2;

public class Clima {

	private String data, horario, luminosidade;
	private int temperatura;
	private int id, umidade;

	public Clima(String data, String horario, String luminosidade, int temperatura, int id, int umidade) {
		this.data = data;
		this.horario = horario;
		this.luminosidade = luminosidade;
		this.temperatura = temperatura;
		this.id = id;
		this.umidade = umidade;
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

	public int getTemperatura() {
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

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Clima [data=" + data + ", horario=" + horario + ", luminosidade=" + luminosidade + ", temperatura="
				+ temperatura + ", id=" + id + ", umidade=" + umidade + "]";
	}

}
