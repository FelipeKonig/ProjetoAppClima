package br.edu.ifsc.projetoWebServiceServer;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController 
public class ClimaController {
		
	@RequestMapping(value = "*", method = RequestMethod.GET)
	@ResponseBody
	public String getFallback() {
		return "Um endpont válido deve ser especificado.";
	}
	
	//curl -i -X GET http://127.0.0.1:8080/clima/todos
		@RequestMapping(value = "/clima/todos", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody
		public Map<Integer, Clima> todosClimas() {
			return ClimaBD.getInstance().todosClima();
		}

		// curl -i -X GET http://127.0.0.1:8080/Clima/busca?id=1
		@RequestMapping(value = "/clima/busca", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody
		public Clima buscaClima(@RequestParam(value = "id", defaultValue = "0") Integer id) {
			return ClimaBD.getInstance().buscaClima(id);
		}

//		curl -i -X GET http://127.0.0.1:8080/clima/buscaid/1
		@RequestMapping(value = "/clima/buscaid/{id}", method = RequestMethod.GET, produces = "application/json")
		@ResponseBody
		public Clima Clima(@PathVariable Integer id) {
			return ClimaBD.getInstance().buscaClima(id);
		}

//		curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"id":"3", "nome":"ads", "idade":"3"}' http://localhost:8080/clima/adiciona	
		@RequestMapping(value = "/clima/adiciona", method = RequestMethod.POST, // MESMA IDEIA PARA O PUT
				produces = "application/json", consumes = "application/json")
		@ResponseBody
		public String adcClima(@RequestBody String json) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				Clima clima = objectMapper.readValue(json, Clima.class);
				ClimaBD.getInstance().adcClima(clima);
				return "Sucesso";
			} catch (JsonParseException e) {
				e.printStackTrace();
				return "Erro";
			} catch (JsonMappingException e) {
				e.printStackTrace();
				return "Erro";
			} catch (IOException e) {
				e.printStackTrace();
				return "Erro";
			}
		}
		
		@RequestMapping(value = "/clima/altera", method = RequestMethod.PUT,
				produces = "application/json", consumes = "application/json")
		@ResponseBody
		public String alterarClima(@RequestBody String json) {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				Clima clima = objectMapper.readValue(json, Clima.class);
				ClimaBD.getInstance().atlzClima(clima.getId(), clima);
				return "Sucesso";
			} catch (JsonParseException e) {
				e.printStackTrace();
				return "Erro";
			} catch (JsonMappingException e) {
				e.printStackTrace();
				return "Erro";
			} catch (IOException e) {
				e.printStackTrace();
				return "Erro";
			}
		}

//		curl -i -X DELETE http://127.0.0.1:8080/Clima/apaga?id=1
		@RequestMapping(value = "/clima/apaga", method = RequestMethod.DELETE)
		@ResponseBody
		public String apagaClima(@RequestParam(value = "id", defaultValue = "0") Integer id) {
			ClimaBD.getInstance().delClima(id);
			return "Sucesso";
		}

}
