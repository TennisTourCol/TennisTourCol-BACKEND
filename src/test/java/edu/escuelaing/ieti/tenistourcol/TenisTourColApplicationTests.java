package edu.escuelaing.ieti.tenistourcol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import edu.escuelaing.ieti.tenistourcol.mapper.TournamentMapper;
import edu.escuelaing.ieti.tenistourcol.model.*;
import edu.escuelaing.ieti.tenistourcol.repository.*;
import edu.escuelaing.ieti.tenistourcol.service.MainView;
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
import edu.escuelaing.ieti.tenistourcol.model.ExceptionResponse;
import edu.escuelaing.ieti.tenistourcol.model.Response;
import edu.escuelaing.ieti.tenistourcol.model.SuccessResponse;
import edu.escuelaing.ieti.tenistourcol.model.Tournament;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private RankingRepository rankingRepository;

	private static String idTournament = "";
	private static String idNotification = "";
	private static String idPlayer = "";

	private static Tournament tournament;
	private static Notification notification;
	private static Player player;
	private static Match match;

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
			assertEquals("Se elimino el torneo Torneo prueba", response.getMessage());
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

	@Test
	@Order(11)
	public void T11CreateMatch(){
		try {
			Match match = Match.builder()
					.player1("Toreto")
					.player2("La roca")
					.court("3")
					.round("8")
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(match);
			assertEquals(0, matchRepository.findAll().size());
			MvcResult result = this.mockMvc.perform(post("/match")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			assertEquals(1, matchRepository.findAll().size());
			Match match1 = gson.fromJson(response.getBody(), Match.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(12)
	public void T12MatchByRound() {
		try {
			MvcResult result = this.mockMvc.perform(get("/match/"+"8")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Match[] match = gson.fromJson(response.getBody(), Match[].class);
			assertEquals("8", match[0].getRound());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(13)
	public void T13CreateMatchError() {
		try {
			Match match = Match.builder()
					.player1("Toreto")
					.player2("La roca")
					.round("8")
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(match);
			MvcResult result = this.mockMvc.perform(post("/match")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isBadRequest())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt, ExceptionResponse.class);
			response.toString();
			String mensaje = "El campo court must not be null.";
			ExceptionResponse response1 = new ExceptionResponse();
			response1.setTimestamp(response.getTimestamp());
			response1.setStatus(400);
			response1.setError("Bad Request");
			response1.setMessage(mensaje);
			response.hashCode();
			Match.builder().toString();
			assertEquals(mensaje, response.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(14)
	public void T14MatchByRoundNull() {
		try {
			MvcResult result = this.mockMvc.perform(get("/match/"+"32")
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
	@Order(15)
	public void T15UpdateMatch(){
		try {
			Match match = this.match;
			match.setPlayer1("Brian");
			match.toString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(match);
			assertEquals("Toreto", matchRepository.findById(match.getId()).get().getPlayer1());
			MvcResult result = this.mockMvc.perform(put("/match")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Match match1 = gson.fromJson(response.getBody(), Match.class);
			assertEquals("Brian", match1.getPlayer1());
			assertEquals("Brian", matchRepository.findById(match.getId()).get().getPlayer1());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(16)
	public void T16UpdateMatchDebeFallar(){
		try {
			Match match = this.match;
			match.setId("nbdjhdfksdhj");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(match);
			MvcResult result = this.mockMvc.perform(put("/match")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isNotFound())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(17)
	public void T17ProbarMainView(){
		MvcResult result = null;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = simpleDateFormat.format(new Date());
		try {
			result = this.mockMvc.perform(get("/mainview/"+date)
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			assertEquals("Se encontraron los partidos de hoy", response.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(18)
	public void T18DebeFallarMainView(){
		MvcResult result = null;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		String date = "unnd/ds/mj";
		try {
			result = this.mockMvc.perform(get("/mainview/"+date)
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(19)
	public void T19crearPlayer() {
		List<String> schedule = new ArrayList<>();
		try {
			Player player = Player.builder()
					.id("1")
					.name("player prueba")
					.apodo("prueeba")
					.ciudad("Bogota")
					.description("nada")
					.liga("Bogota")
					.schedule(schedule)
					.mail("prueba@gmail.com")
					.imagen(new Integer(23))
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(player);
			assertEquals(0, playerRepository.findAll().size());
			MvcResult result = this.mockMvc.perform(post("/player")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			assertEquals(1, playerRepository.findAll().size());
			Player player1 = gson.fromJson(response.getBody(), Player.class);
			this.player = player1;
			idPlayer = player1.getId();
			System.out.println(idPlayer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(20)
	public void T20crearTorneo() {
		try {
			Tournament tournament = Tournament.builder()
					.id("prueba")
					.nombre("Torneo prueba 2")
					.responsable("David")
					.direccion("Tv 1 #2-3")
					.ciudad("Bogotá")
					.club("El club de david")
					.grado("1")
					.categoria("12-14")
					.precio(BigInteger.valueOf(10000))
					.hora("10:00")
					.fechaInicio(new Date())
					.fechaFin(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(tournament);
			MvcResult result = this.mockMvc.perform(post("/tournament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			assertEquals(1, tournamentRepository.findAll().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(21)
	public void T21setSchedule() {
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

			MvcResult result = this.mockMvc.perform(post("/player/1/addTournament/prueba")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			System.out.println(response);
			Player player1 = gson.fromJson(response.getBody(), Player.class);
			assertEquals(1, player1.getSchedule().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(22)
	public void T22setScheduleError() {
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

			MvcResult result = this.mockMvc.perform(post("/player/1/addTournament/prueba2")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(23)
	public void T23getSchedule() {
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

			MvcResult result = this.mockMvc.perform(get("/player/1/schedule")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Tournament[] tournaments = gson.fromJson(response.getBody(), Tournament[].class);
			assertEquals(1, tournaments.length);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	@Order(24)
	public void T24getPlayerByIdAndTournament(){
		try {
			MvcResult result = this.mockMvc.perform(get("/player/"+"1"+"/tournament/"+"prueba")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Player player = gson.fromJson(response.getBody(), Player.class);
			assertEquals("1", player.getId());
			assertEquals("prueba", player.getSchedule().get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(25)
	public void T25getPlayerByIdAndTournamentErrorTournament(){
		try {
			MvcResult result = this.mockMvc.perform(get("/player/"+"1"+"/tournament/"+"prueba1")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isNotFound()).andReturn();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(26)
	public void T26getPlayerByIdAndTournamentErrorPlayer(){
		try {
			MvcResult result = this.mockMvc.perform(get("/player/"+"2345"+"/tournament/"+"prueba")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isNotFound()).andReturn();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(28)
	public void T28getScheduleError() {
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			MvcResult result = this.mockMvc.perform(get("/player/2/schedule")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(29)
	public void T29CreateMatch(){
		try {
			Match match = Match.builder()
					.id("1")
					.player1("David")
					.player2("Manuel")
					.court("15")
					.round("16")
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(match);
			assertEquals(1, matchRepository.findAll().size());
			MvcResult result = this.mockMvc.perform(post("/match")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			assertEquals(2, matchRepository.findAll().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//{id}/resultado
	@Test
	@Order(30)
	public void T30setMatchResult() {
		try {
			List<String> resultado = new ArrayList<>();
			resultado.add("6");
			resultado.add("6");
			resultado.add("1");
			resultado.add("0");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(resultado);
			MvcResult result = this.mockMvc.perform(post("/match/1/resultado")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Match match = gson.fromJson(response.getBody(), Match.class);
			assertEquals("1", match.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(31)
	public void T31setMatchResultFAilData() {
		try {
			List<String> resultado = new ArrayList<>();
			resultado.add("6");
			resultado.add("4");
			resultado.add("4");
			resultado.add("6");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(resultado);
			MvcResult result = this.mockMvc.perform(post("/match/1/resultado")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isNotFound())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(32)
	public void T31setMatchResultFAilId() {
		try {
			List<String> resultado = new ArrayList<>();
			resultado.add("6");
			resultado.add("4");
			resultado.add("4");
			resultado.add("6");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(resultado);
			MvcResult result = this.mockMvc.perform(post("/match/2/resultado")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isNotFound())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(33)
	public void T33editTournament(){
		try {
			Tournament tournamentRemplazar = Tournament.builder()
					.id("prueba")
					.nombre("Torneo Cambio")
					.responsable("David")
					.direccion("Tv 1 #2-3")
					.ciudad("Cali")
					.club("El club de Edicion")
					.grado("3")
					.categoria("20-22")
					.precio(BigInteger.valueOf(10000))
					.hora("8:00")
					.fechaInicio(new Date())
					.fechaFin(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(tournamentRemplazar);
			MvcResult result = this.mockMvc.perform(put("/tournament/editTorunament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			System.out.println(response);
			Tournament tournament1 = gson.fromJson(response.getBody(), Tournament.class);
			assertEquals("El club de Edicion", tournament1.getClub());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(34)
	public void T34editTournamentErorr(){
		try {
			Tournament tournamentRemplazar = Tournament.builder()
					.id("prueba")
					.nombre("Torneo Cambio")
					.responsable("David")
					.direccion("Tv 1 #2-3")
					.ciudad("Cali")
					.grado("3")
					.categoria("20-22")
					.precio(BigInteger.valueOf(10000))
					.hora("8:00")
					.fechaInicio(new Date())
					.fechaFin(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(tournamentRemplazar);
			MvcResult result = this.mockMvc.perform(put("/tournament/editTorunament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isBadRequest())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Bad Request", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(35)
	public void T35editTournamentNombre(){
		try {
			Tournament tournamentRemplazar = Tournament.builder()
					.id("prueba")
					.nombre("Torneo Cambio 2")
					.responsable("David")
					.direccion("Tv 1 #2-3")
					.ciudad("Cali")
					.club("El club de Edicion")
					.grado("3")
					.categoria("20-22")
					.precio(BigInteger.valueOf(10000))
					.hora("8:00")
					.fechaInicio(new Date())
					.fechaFin(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(tournamentRemplazar);
			MvcResult result = this.mockMvc.perform(put("/tournament/editTorunament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			System.out.println(response);
			Tournament tournament1 = gson.fromJson(response.getBody(), Tournament.class);
			assertEquals("Torneo Cambio 2", tournament1.getNombre());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(36)
	public void T36editTournamentGrado(){
		try {
			Tournament tournamentRemplazar = Tournament.builder()
					.id("prueba")
					.nombre("Torneo Cambio 2")
					.responsable("David")
					.direccion("Tv 1 #2-3")
					.ciudad("Cali")
					.club("El club de Edicion")
					.grado("1")
					.categoria("20-22")
					.precio(BigInteger.valueOf(10000))
					.hora("8:00")
					.fechaInicio(new Date())
					.fechaFin(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(tournamentRemplazar);
			MvcResult result = this.mockMvc.perform(put("/tournament/editTorunament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			System.out.println(response);
			Tournament tournament1 = gson.fromJson(response.getBody(), Tournament.class);
			assertEquals("1", tournament1.getGrado());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(37)
	public void T37editTournamentDireccion(){
		try {
			Tournament tournamentRemplazar = Tournament.builder()
					.id("prueba")
					.nombre("Torneo Cambio 2")
					.responsable("David")
					.direccion("Calle 221 club bogota")
					.ciudad("Cali")
					.club("El club de Edicion")
					.grado("1")
					.categoria("20-22")
					.precio(BigInteger.valueOf(10000))
					.hora("8:00")
					.fechaInicio(new Date())
					.fechaFin(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(tournamentRemplazar);
			MvcResult result = this.mockMvc.perform(put("/tournament/editTorunament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			System.out.println(response);
			Tournament tournament1 = gson.fromJson(response.getBody(), Tournament.class);
			assertEquals("Calle 221 club bogota", tournament1.getDireccion());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(38)
	public void T38editTournamentClub(){
		try {
			Tournament tournamentRemplazar = Tournament.builder()
					.id("prueba")
					.nombre("Torneo Cambio 2")
					.responsable("David")
					.direccion("Calle 221 club bogota")
					.ciudad("Cali")
					.club("El club de Edicion 2")
					.grado("1")
					.categoria("20-22")
					.precio(BigInteger.valueOf(10000))
					.hora("8:00")
					.fechaInicio(new Date())
					.fechaFin(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(tournamentRemplazar);
			MvcResult result = this.mockMvc.perform(put("/tournament/editTorunament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			System.out.println(response);
			Tournament tournament1 = gson.fromJson(response.getBody(), Tournament.class);
			assertEquals("El club de Edicion 2", tournament1.getClub());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(39)
	public void T39editTournamentCiudad(){
		try {
			Tournament tournamentRemplazar = Tournament.builder()
					.id("prueba")
					.nombre("Torneo Cambio 2")
					.responsable("David")
					.direccion("Calle 221 club bogota")
					.ciudad("Bogota")
					.club("El club de Edicion 2")
					.grado("1")
					.categoria("20-22")
					.precio(BigInteger.valueOf(10000))
					.hora("8:00")
					.fechaInicio(new Date())
					.fechaFin(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(tournamentRemplazar);
			MvcResult result = this.mockMvc.perform(put("/tournament/editTorunament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			System.out.println(response);
			Tournament tournament1 = gson.fromJson(response.getBody(), Tournament.class);
			assertEquals("Bogota", tournament1.getCiudad());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(40)
	public void T49editTournamentFechaInicio(){
		try {
			Tournament tournamentRemplazar = Tournament.builder()
					.id("prueba")
					.nombre("TorneoCambio2")
					.responsable("David")
					.direccion("Calle 221 club bogota")
					.ciudad("Bogota")
					.club("El club de Edicion 2")
					.grado("1")
					.categoria("20-22")
					.precio(BigInteger.valueOf(10000))
					.hora("14:00")
					.fechaInicio(new Date())
					.fechaFin(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(tournamentRemplazar);
			MvcResult result = this.mockMvc.perform(put("/tournament/editTorunament")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			System.out.println(response);
			Tournament tournament1 = gson.fromJson(response.getBody(), Tournament.class);
			assertEquals("14:00", tournament1.getHora());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(41)
	public void T41deleteTournament(){
		try {
			MvcResult result = this.mockMvc.perform(delete("/player/"+"1"+"/tournament/"+"prueba")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Player player = gson.fromJson(response.getBody(), Player.class);
			assertEquals("1", player.getId());
			assertEquals(0, player.getSchedule().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(42)
	public void T42deleteTournamentErrorIdTournament(){
		try {
			MvcResult result = this.mockMvc.perform(delete("/player/"+"1"+"/tournament/"+"prueba21")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isNotFound()).andReturn();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(43)
	public void T43deleteTournamentErrorIdPlayer(){
		try {
			MvcResult result = this.mockMvc.perform(delete("/player/"+"1234"+"/tournament/"+"prueba")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isNotFound()).andReturn();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(44)
	public void T52ActualizarEmailJugador(){
		try{
			MvcResult result = this.mockMvc.perform(get("/player/1")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Player player = gson.fromJson(response.getBody(), Player.class);

			Player tmp = Player.builder()
					.id("1")
					.name("player prueba")
					.mail("cesar@mail.com")
					.apodo("prueba")
					.liga("Bogota")
					.ciudad("Bogota")
					.description("nada")
					.imagen(23)
					.schedule(player.getSchedule())
					.build();
			Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson2.toJson(tmp);
			MvcResult result2 = this.mockMvc.perform(post("/player/updatePlayer")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt2 = result2.getResponse().getContentAsString();
			SuccessResponse response2 = gson2.fromJson(rt2,  SuccessResponse.class);
			System.out.println("Holaaaaaaaaaaaaaa"+response2);
			Player player1 = gson.fromJson(response2.getBody(), Player.class);
			assertEquals("cesar@mail.com", player1.getMail());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(45)
	public void T53ActualizarApodoJugador(){
		try{
			MvcResult result = this.mockMvc.perform(get("/player/1")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Player player = gson.fromJson(response.getBody(), Player.class);

			Player tmp = Player.builder()
					.id("1")
					.name("player prueba")
					.mail("cesar@mail.com")
					.apodo("ElMatador")
					.liga("Bogota")
					.ciudad("Bogota")
					.description("nada")
					.imagen(23)
					.schedule(player.getSchedule())
					.build();
			Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson2.toJson(tmp);
			MvcResult result2 = this.mockMvc.perform(post("/player/updatePlayer")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt2 = result2.getResponse().getContentAsString();
			SuccessResponse response2 = gson2.fromJson(rt2,  SuccessResponse.class);
			System.out.println(response2);
			Player player1 = gson.fromJson(response2.getBody(), Player.class);
			assertEquals("ElMatador", player1.getApodo());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(46)
	public void T54ActualizarLigaJugador(){
		try{
			MvcResult result = this.mockMvc.perform(get("/player/1")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Player player = gson.fromJson(response.getBody(), Player.class);

			Player tmp = Player.builder()
					.id("1")
					.name("player prueba")
					.mail("cesar@mail.com")
					.apodo("ElMatador")
					.liga("Antioquia")
					.ciudad("Bogota")
					.description("nada")
					.imagen(23)
					.schedule(player.getSchedule())
					.build();
			Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson2.toJson(tmp);
			MvcResult result2 = this.mockMvc.perform(post("/player/updatePlayer")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt2 = result2.getResponse().getContentAsString();
			SuccessResponse response2 = gson2.fromJson(rt2,  SuccessResponse.class);
			System.out.println(response2);
			Player player1 = gson.fromJson(response2.getBody(), Player.class);
			assertEquals("Antioquia", player1.getLiga());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(47)
	public void T55ActualizarCiudadJugador(){
		try{
			MvcResult result = this.mockMvc.perform(get("/player/1")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Player player = gson.fromJson(response.getBody(), Player.class);

			Player tmp = Player.builder()
					.id("1")
					.name("player prueba")
					.mail("cesar@mail.com")
					.apodo("ElMatador")
					.liga("Antioquia")
					.ciudad("Envigado")
					.description("nada")
					.imagen(23)
					.schedule(player.getSchedule())
					.build();
			Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson2.toJson(tmp);
			MvcResult result2 = this.mockMvc.perform(post("/player/updatePlayer")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt2 = result2.getResponse().getContentAsString();
			SuccessResponse response2 = gson2.fromJson(rt2,  SuccessResponse.class);
			System.out.println(response2);
			Player player1 = gson.fromJson(response2.getBody(), Player.class);
			assertEquals("Envigado", player1.getCiudad());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(48)
	public void T56ActualizarDescripcionJugador(){
		try{
			MvcResult result = this.mockMvc.perform(get("/player/1")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Player player = gson.fromJson(response.getBody(), Player.class);

			Player tmp = Player.builder()
					.id("1")
					.name("player prueba")
					.mail("cesar@mail.com")
					.apodo("ElMatador")
					.liga("Antioquia")
					.ciudad("Envigado")
					.description("Jugador rapido,con mucho espiritu de competitividad")
					.imagen(23)
					.schedule(player.getSchedule())
					.build();
			Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson2.toJson(tmp);
			MvcResult result2 = this.mockMvc.perform(post("/player/updatePlayer")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt2 = result2.getResponse().getContentAsString();
			SuccessResponse response2 = gson2.fromJson(rt2,  SuccessResponse.class);
			System.out.println(response2);
			Player player1 = gson.fromJson(response2.getBody(), Player.class);
			assertEquals("Jugador rapido,con mucho espiritu de competitividad", player1.getDescription());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(49)
	public void T57ActualizarImagenJugador(){
		try{
			MvcResult result = this.mockMvc.perform(get("/player/1")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Player player = gson.fromJson(response.getBody(), Player.class);

			Player tmp = Player.builder()
					.id("1")
					.name("player prueba")
					.mail("cesar@mail.com")
					.apodo("ElMatador")
					.liga("Antioquia")
					.ciudad("Envigado")
					.description("Jugador rapido,con mucho espiritu de competitividad")
					.imagen(25)
					.schedule(player.getSchedule())
					.build();
			Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson2.toJson(tmp);
			MvcResult result2 = this.mockMvc.perform(post("/player/updatePlayer")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt2 = result2.getResponse().getContentAsString();
			SuccessResponse response2 = gson2.fromJson(rt2,  SuccessResponse.class);
			System.out.println(response2);
			Player player1 = gson.fromJson(response2.getBody(), Player.class);
			assertEquals(Integer.valueOf(25), player1.getImagen());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(50)
	public void T58ActualizarJugadorError(){
		try{
			MvcResult result = this.mockMvc.perform(get("/player/1")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Player player = gson.fromJson(response.getBody(), Player.class);

			Player tmp = Player.builder()
					.id("1")
					.name("player prueba")
					.mail("cesar@mail.com")
					.apodo("ElMatador")
					.liga("Antioquia")
					.ciudad("Envigado")
					.imagen(25)
					.schedule(player.getSchedule())
					.build();
			Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson2.toJson(tmp);
			MvcResult result2 = this.mockMvc.perform(post("/player/updatePlayer")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isBadRequest())
					.andReturn();
			String rt2 = result2.getResponse().getContentAsString();
			ExceptionResponse response2 = gson.fromJson(rt2,  ExceptionResponse.class);
			assertEquals("Bad Request", response2.getError());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(51)
	public void T51CreateNotification(){
		try {
			Notification  notification = Notification.builder()
					.id("1")
					.playerId("1")
					.mailPlayer("david@gmail.com")
					.tournament("Ascenos Verdiery")
					.date(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(notification);
			assertEquals(0,notificationRepository.findAll().size());
			MvcResult result = this.mockMvc.perform(post("/notification")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			assertEquals(1, notificationRepository.findAll().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(52)
	public void T52NotificationByPlayer() {
		try {
			MvcResult result = this.mockMvc.perform(get("/notification/"+"1")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Notification[] notifications = gson.fromJson(response.getBody(), Notification[].class);
			assertEquals("1", notifications[0].getPlayerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(53)
	public void T53NotificationByPlayerDebeFallar() {
		try {
			MvcResult result = this.mockMvc.perform(get("/notification/"+"2")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isNotFound()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Notification[] notifications = gson.fromJson(response.getBody(), Notification[].class);
			assertEquals("1", notifications[0].getPlayerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(54)
	public void T54CreateNotificationDebeFallar(){
		try {
			Notification  notification = Notification.builder()
					.id("1")
					.mailPlayer("david@gmail.com")
					.tournament("Ascenos Verdiery")
					.date(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(notification);
			MvcResult result = this.mockMvc.perform(post("/notification")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isBadRequest())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Bad Request", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(55)
	public void T55CreateNotificationDos(){
		try {
			Notification  notification = Notification.builder()
					.id("2")
					.playerId("2")
					.mailPlayer("juan@gmail.com")
					.tournament("Ascenos Verdiery")
					.date(new Date())
					.build();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson.toJson(notification);
			MvcResult result = this.mockMvc.perform(post("/notification")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			assertEquals(2, notificationRepository.findAll().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(56)
	public void T56NotificacionPorIdPlayerDebeRetornarNull() {
		try {
			MvcResult result = this.mockMvc.perform(get("/notification/"+"23")
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
	@Order(57)
	public void T57CrearRanking(){
		try {
			MvcResult result = this.mockMvc.perform(get("/player/1")
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Player player = gson.fromJson(response.getBody(), Player.class);
			Player player2 = gson.fromJson(response.getBody(), Player.class);
			List<Player> jugadores= new ArrayList<>();
			jugadores.add(player);
			jugadores.add(player2);

			Ranking ranking = Ranking.builder()
					.id("0")
					.nombre("18 Masc S")
					.jugadores(jugadores)
					.build();
			Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			String json = gson2.toJson(ranking);
			assertEquals(0, rankingRepository.findAll().size());
			MvcResult result2 = this.mockMvc.perform(post("/ranking")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)).andExpect(status().isOk())
					.andReturn();
			String rt2 = result2.getResponse().getContentAsString();
			SuccessResponse response2 = gson2.fromJson(rt2,  SuccessResponse.class);
			assertEquals(1, rankingRepository.findAll().size());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(58)
	public void T58ObtenerJugadoresPorRanking() {
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

			MvcResult result = this.mockMvc.perform(get("/ranking/players/0")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Player[] jugadores = gson.fromJson(response.getBody(), Player[].class);
			assertEquals(2, jugadores.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(59)
	public void T59ObtenerJugadoresPorRankingDeFallar() {
		try {

			MvcResult result = this.mockMvc.perform(get("/ranking/players/1")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	@Order(60)
	public void T60btenerRankingPorId() {
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			MvcResult result = this.mockMvc.perform(get("/ranking/0")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Ranking ranking  = gson.fromJson(response.getBody(), Ranking.class);
			assertEquals("18 Masc S", ranking.getNombre());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(61)
	public void T61getTorneoPorNombre(){
		try {
			MvcResult result = this.mockMvc.perform(get("/tournament/name/"+"TorneoCambio2")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
			).andExpect(status().isOk()).andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt,  SuccessResponse.class);
			Tournament tournament = gson.fromJson(response.getBody(), Tournament.class);
			assertEquals("TorneoCambio2", tournament.getNombre());;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(62)
	public void T62getTorneoPorNombreDebeFallar() {
		try {
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

			MvcResult result = this.mockMvc.perform(get("/tournament/name/"+"Torneo Cambio")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(63)
	public void T63FindTournamentbyGrade() {
		try {
			MvcResult result = this.mockMvc.perform(get("/tournament/grade/1")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			SuccessResponse response = gson.fromJson(rt, SuccessResponse.class);
			Tournament[] tournaments = gson.fromJson(response.getBody(), Tournament[].class);
			assertEquals("1", tournaments[0].getGrado());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(64)
	public void T64FindTournamentbyGradeError() {
		try {
			MvcResult result = this.mockMvc.perform(get("/tournament/grade/8")
					//.header("Authorization", token)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
					.andReturn();
			String rt = result.getResponse().getContentAsString();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			ExceptionResponse response = gson.fromJson(rt,  ExceptionResponse.class);
			assertEquals("Not Found", response.getError());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
