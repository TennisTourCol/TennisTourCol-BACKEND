package edu.escuelaing.ieti.tenistourcol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.escuelaing.ieti.tenistourcol.mapper.TournamentMapper;
import edu.escuelaing.ieti.tenistourcol.model.*;
import edu.escuelaing.ieti.tenistourcol.repository.PlayerRepository;
import edu.escuelaing.ieti.tenistourcol.repository.TournamentEntity;
import edu.escuelaing.ieti.tenistourcol.repository.TournamentRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
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

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {TenisTourColApplication.class})
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TenisTourColApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TournamentRepository tournamentRepository;

	@Autowired
	private PlayerRepository playerRepository;

	private static String idTournament = "";
	private static String idPlayer = "";

	private static Tournament tournament;
	private static Player player;

	@Test
	@Order(1)
	public void T01noDebeFuncionarGetTodosLosTorneos() {
		try {
			MvcResult result = this.mockMvc.perform(get("/tournament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isNotFound()).andReturn();

			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			ExceptionResponse response = gson.fromJson(rt, ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(2)
	public void T02crearTorneo() {
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
					.hora("12:00")
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
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			assertEquals(1, tournamentRepository.findAll().size());
			Tournament tournament1 = gson.fromJson(response.getBody(), Tournament.class);
			this.tournament = tournament1;
			idTournament = tournament1.getId();
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

	@Test
	@Order(3)
	public void T03todosLosTorneos() {
		try {
			MvcResult result = this.mockMvc.perform(get("/tournament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					).andExpect(status().isOk()).andReturn();

			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Type tournamentListType = new TypeToken<List<Tournament>>(){}.getType();
			List<Tournament> lista = gson.fromJson(response.getBody(), tournamentListType);
			assertEquals(1, lista.size());
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

	@Test
	@Order(4)
	public void T04torneoPorId() {
		try {
			MvcResult result = this.mockMvc.perform(get("/tournament/"+idTournament)
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			this.tournament.toString();
			this.tournament.hashCode();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Tournament tournament = gson.fromJson(response.getBody(), Tournament.class);
			assertEquals(idTournament, tournament.getId());
		} catch (Exception e) {
            e.printStackTrace();
		}
	}

	@Test
	@Order(5)
	public void T05debeFallarPorFaltaAtributos(){
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
					.hora("12:00")
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
	@Order(6)
	public void T06probarMetodosTournament(){
		TournamentEntity tournamentEntity = TournamentMapper.map(tournament);
		tournamentEntity.toString();
		SuccessResponse successResponse = new SuccessResponse("prueba");
		successResponse.setBody("Prueba2");
		successResponse.toString();
		Response response = new Response();
		response.toString();
	}

	@Test
	@Order(7)
	public void T07torneoPorIdDebeRetornarNull() {
		try {
			MvcResult result = this.mockMvc.perform(get("/tournament/"+"54a46s5dsa")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isNotFound()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(9)
	public void T08eliminarTorneo() {
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(tournament);
			MvcResult result = this.mockMvc.perform(delete("/tournament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json))
					.andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
//			assertEquals("Not Found", response.getError());
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	@Test
	@Order(10)
	public void T10playerPorId() {
		try {
			MvcResult result = this.mockMvc.perform(get("/player/"+idPlayer)
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			this.player.toString();
			this.player.hashCode();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Player player = gson.fromJson(response.getBody(), Player.class);
			assertEquals(idPlayer, player.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 */

}
