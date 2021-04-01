package edu.escuelaing.ieti.tenistourcol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.escuelaing.ieti.tenistourcol.mapper.TournamentMapper;
import edu.escuelaing.ieti.tenistourcol.model.ExceptionResponse;
import edu.escuelaing.ieti.tenistourcol.model.Tournament;
import edu.escuelaing.ieti.tenistourcol.repository.TournamentEntity;
import edu.escuelaing.ieti.tenistourcol.repository.TournamentRepository;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {TenisTourColApplication.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TenisTourColApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TournamentRepository tournamentRepository;

	private static String idTournament = "";

	private static Tournament tournament;

	@Test
	public void T01crearTorneo() {
		try {
			Tournament tournament = Tournament.builder()
					.nombre("Torneo prueba")
					.responsable("Juan")
					.direccion("Tv 1 #2-3")
					.ciudad("Bogotá")
					.club("El club de prueba")
					.grado("A")
					.categoria("20-22")
					.precio(BigInteger.valueOf(10000))
					.hora(new Date())
					.fechaInicio(new Date())
					.fechaFin(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(tournament);
			assertEquals(0, tournamentRepository.findAll().size());
			MvcResult result = this.mockMvc.perform(post("/tournament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			Tournament tournament1 = gson.fromJson(rt,  Tournament.class);
			assertEquals(1, tournamentRepository.findAll().size());
			idTournament = tournament1.getId();
			this.tournament = tournament1;
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

	@Test
	public void T02todosLosTorneos() {
		try {
			MvcResult result = this.mockMvc.perform(get("/tournament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					).andExpect(status().isOk()).andReturn();

			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			Type tournamentListType = new TypeToken<List<Tournament>>(){}.getType();
			List<Tournament> lista = gson.fromJson(rt, tournamentListType);
			assertEquals(1, lista.size());
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

	@Test
	public void T03torneoPorId() {
		try {
			MvcResult result = this.mockMvc.perform(get("/tournament/"+idTournament)
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			this.tournament.toString();
			this.tournament.hashCode();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			Tournament tournament = gson.fromJson(rt, Tournament.class);
			assertEquals(idTournament, tournament.getId());
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

	@Test
	public void T04debeFallarPorFaltaAtributos(){
		try {
			Tournament tournament = Tournament.builder()
					.id("1l")
					.nombre("Torneo prueba")
					.responsable("Juan")
					.direccion("Tv 1 #2-3")
					.ciudad("Bogotá")
					.club("El club de prueba")
					.categoria("20-22")
					.precio(BigInteger.valueOf(10000))
					.hora(new Date())
					.fechaInicio(new Date())
					.fechaFin(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(tournament);
			MvcResult result = this.mockMvc.perform(post("/tournament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isBadRequest())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			response.toString();
			String mensaje = "El campo grado must not be null.";
			ExceptionResponse response1 = new ExceptionResponse();
			response1.setTimestamp(response.getTimestamp());
			response1.setStatus(400);
			response1.setError("Bad Request");
			response1.setMessage(mensaje);
			response.hashCode();
			Tournament.builder().toString();
			assertEquals(mensaje, response.getMessage());
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

	@Test
	public void T05probarMetodosTournament(){
		TournamentEntity tournamentEntity = TournamentMapper.map(tournament);
		tournamentEntity.toString();
	}

	@Test
	public void T06torneoPorIdDebeRetornarNull() {
		try {
			this.mockMvc.perform(get("/tournament/"+"54a46s5dsa")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
