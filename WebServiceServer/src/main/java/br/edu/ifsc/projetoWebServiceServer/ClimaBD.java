package br.edu.ifsc.projetoWebServiceServer;

import java.util.HashMap;
import java.util.Map;

public class ClimaBD {
	
	public static ClimaBD instance;
	
	public static synchronized ClimaBD getInstance() {
		if (instance == null)
			instance = new ClimaBD();
		
		return instance;
	}
	
	private Map<Integer, Clima> climaMap = new HashMap<Integer, Clima>();
	
	public synchronized void adcClima(Clima clima) {
		climaMap.put(clima.getId(), clima);
		System.out.println("Clima adicionado:");
		System.out.println(climaMap.get(clima.getId()));
		System.out.println("");
	}

	public synchronized Clima buscaClima(Integer id) {
		System.out.println("Clima buscado:");
		System.out.println(climaMap.get(id));
		System.out.println("");
		return climaMap.get(id);
	}
	
	public synchronized void atlzClima(Integer id, Clima clima) {
		System.out.println("Clima atualizado:");
		climaMap.put(id, clima);
		System.out.println(climaMap.get(clima.getId()));
		System.out.println("");
	}	
	
	public synchronized void delClima(Integer id) {
		System.out.println("Clima deletado");
		System.out.println(climaMap.get(id));
		climaMap.remove(id);
		System.out.println("");
	}
	
	public synchronized Map<Integer, Clima> todosClima(){
		System.out.println("Buscado todos os climas");
		System.out.println("");
		return climaMap;
	}
}
