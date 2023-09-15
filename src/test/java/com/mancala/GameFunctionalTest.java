package com.mancala;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@SpringBootTest
public class GameFunctionalTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldReturnFullGameStructureWithDefinedPlayersNames() throws Exception {
        mockMvc.perform(post("/start")
                                .content("{\"player1Name\":\"Alex\",\"player2Name\":\"Bob\"}")
                                .contentType(APPLICATION_JSON)
                                .header("accept-language", "en"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value("true"))
               .andExpect(jsonPath("$.statusMessage").value("Alex turn"))
               .andExpect(jsonPath("$.player1Name").value("Alex"))
               .andExpect(jsonPath("$.player2Name").value("Bob"))
               .andExpect(jsonPath("$.pits[0].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[1].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[2].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[3].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[4].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[5].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[6].stonesCount").value("0"))
               .andExpect(jsonPath("$.pits[7].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[8].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[9].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[10].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[11].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[12].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[13].stonesCount").value("0"));
    }

    @Test
    public void shouldReturnCurrentGameStructure() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/start")
                                                      .content("{\"player1Name\":\"Alex\",\"player2Name\":\"Bob\"}")
                                                      .contentType(APPLICATION_JSON)
                                                      .header("accept-language", "en")).andReturn();

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
        mockMvc.perform(get("/game")
                                .session(session)
                                .header("accept-language", "en"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value("true"))
               .andExpect(jsonPath("$.statusMessage").value("Alex turn"))
               .andExpect(jsonPath("$.player1Name").value("Alex"))
               .andExpect(jsonPath("$.player2Name").value("Bob"))
               .andExpect(jsonPath("$.pits[0].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[1].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[2].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[3].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[4].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[5].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[6].stonesCount").value("0"))
               .andExpect(jsonPath("$.pits[7].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[8].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[9].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[10].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[11].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[12].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[13].stonesCount").value("0"));
    }

    @Test
    public void shouldReturnGameStructureConsideringActionsMade() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/start")
                                                      .content("{\"player1Name\":\"Alex\",\"player2Name\":\"Bob\"}")
                                                      .contentType(APPLICATION_JSON)
                                                      .header("accept-language", "en")).andReturn();

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();

        makeActionMockRequest(0, session);
        mockMvc.perform(patch("/action")
                                .session(session)
                                .content("{\"selectedPitNumber\":\"1\"}")
                                .contentType(APPLICATION_JSON)
                                .header("accept-language", "en"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value("true"))
               .andExpect(jsonPath("$.statusMessage").value("Bob turn"))
               .andExpect(jsonPath("$.player1Name").value("Alex"))
               .andExpect(jsonPath("$.player2Name").value("Bob"))
               .andExpect(jsonPath("$.pits[0].stonesCount").value("0"))
               .andExpect(jsonPath("$.pits[1].stonesCount").value("0"))
               .andExpect(jsonPath("$.pits[2].stonesCount").value("8"))
               .andExpect(jsonPath("$.pits[3].stonesCount").value("8"))
               .andExpect(jsonPath("$.pits[4].stonesCount").value("8"))
               .andExpect(jsonPath("$.pits[5].stonesCount").value("8"))
               .andExpect(jsonPath("$.pits[6].stonesCount").value("2"))
               .andExpect(jsonPath("$.pits[7].stonesCount").value("7"))
               .andExpect(jsonPath("$.pits[8].stonesCount").value("7"))
               .andExpect(jsonPath("$.pits[9].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[10].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[11].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[12].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[13].stonesCount").value("0"));
    }

    @Test
    public void shouldReturnWinnerMessageWithCorrectScoreAfterFullGameCycle() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/start")
                                                      .content("{\"player1Name\":\"Alex\",\"player2Name\":\"Bob\"}")
                                                      .contentType(APPLICATION_JSON)
                                                      .header("accept-language", "en")).andReturn();

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
        makeActionMockRequest(0, session);
        makeActionMockRequest(3, session);
        makeActionMockRequest(10, session);
        makeActionMockRequest(5, session);
        makeActionMockRequest(12, session);
        makeActionMockRequest(5, session);
        makeActionMockRequest(3, session);
        makeActionMockRequest(9, session);
        makeActionMockRequest(4, session);
        makeActionMockRequest(10, session);
        makeActionMockRequest(9, session);
        makeActionMockRequest(5, session);
        makeActionMockRequest(3, session);
        makeActionMockRequest(11, session);
        makeActionMockRequest(0, session);
        makeActionMockRequest(4, session);
        makeActionMockRequest(3, session);
        makeActionMockRequest(7, session);
        makeActionMockRequest(2, session);
        mockMvc.perform(patch("/action")
                                .session(session)
                                .content("{\"selectedPitNumber\":\"12\"}")
                                .contentType(APPLICATION_JSON)
                                .header("accept-language", "en"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value("true"))
               .andExpect(jsonPath("$.statusMessage").value("Alex is winner"))
               .andExpect(jsonPath("$.pits[6].stonesCount").value("52"))
               .andExpect(jsonPath("$.pits[13].stonesCount").value("20"));

    }

    @Test
    public void shouldReturnErrorWhenTryingToMakeActionInFinishedGame() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/start")
                                                      .content("{\"player1Name\":\"Alex\",\"player2Name\":\"Bob\"}")
                                                      .contentType(APPLICATION_JSON)
                                                      .header("accept-language", "en")).andReturn();

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
        makeActionMockRequest(0, session);
        makeActionMockRequest(3, session);
        makeActionMockRequest(10, session);
        makeActionMockRequest(5, session);
        makeActionMockRequest(12, session);
        makeActionMockRequest(5, session);
        makeActionMockRequest(3, session);
        makeActionMockRequest(9, session);
        makeActionMockRequest(4, session);
        makeActionMockRequest(10, session);
        makeActionMockRequest(9, session);
        makeActionMockRequest(5, session);
        makeActionMockRequest(3, session);
        makeActionMockRequest(11, session);
        makeActionMockRequest(0, session);
        makeActionMockRequest(4, session);
        makeActionMockRequest(3, session);
        makeActionMockRequest(7, session);
        makeActionMockRequest(2, session);
        makeActionMockRequest(12, session);
        mockMvc.perform(patch("/action")
                                .session(session)
                                .content("{\"selectedPitNumber\":\"12\"}")
                                .contentType(APPLICATION_JSON)
                                .header("accept-language", "en"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value("false"))
               .andExpect(jsonPath("$.statusMessage").value("Game is finished, the action can't be made."));

    }

    @Test
    public void shouldReturnErrorWhenTryingToMakeActionOnNotAvailablePit() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/start")
                                                      .content("{\"player1Name\":\"Alex\",\"player2Name\":\"Bob\"}")
                                                      .contentType(APPLICATION_JSON)
                                                      .header("accept-language", "en")).andReturn();

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
        mockMvc.perform(patch("/action")
                                .session(session)
                                .content("{\"selectedPitNumber\":\"12\"}")
                                .contentType(APPLICATION_JSON)
                                .header("accept-language", "en"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value("false"))
               .andExpect(jsonPath("$.statusMessage").value("Player Alex can't choose a pit number 12"));

    }

    @Test
    public void shouldReturnErrorWhenTryingToMakeActionOnEmptyPit() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/start")
                                                      .content("{\"player1Name\":\"Alex\",\"player2Name\":\"Bob\"}")
                                                      .contentType(APPLICATION_JSON)
                                                      .header("accept-language", "en")).andReturn();

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
        makeActionMockRequest(0, session);
        mockMvc.perform(patch("/action")
                                .session(session)
                                .content("{\"selectedPitNumber\":\"0\"}")
                                .contentType(APPLICATION_JSON)
                                .header("accept-language", "en"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value("false"))
               .andExpect(jsonPath("$.statusMessage").value("No stones in pit 0, the action isn't possible."));

    }

    @Test
    public void shouldResetGameStructureToDefaultSettings() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/start")
                                                      .content("{\"player1Name\":\"Alex\",\"player2Name\":\"Bob\"}")
                                                      .contentType(APPLICATION_JSON)
                                                      .header("accept-language", "en")).andReturn();

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
        makeActionMockRequest(0, session);
        mockMvc.perform(delete("/reset")
                                .session(session)
                                .header("accept-language", "en"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.success").value("true"))
               .andExpect(jsonPath("$.statusMessage").value("Player1 turn"))
               .andExpect(jsonPath("$.player1Name").value("Player1"))
               .andExpect(jsonPath("$.player2Name").value("Player2"))
               .andExpect(jsonPath("$.pits[0].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[1].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[2].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[3].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[4].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[5].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[6].stonesCount").value("0"))
               .andExpect(jsonPath("$.pits[7].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[8].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[9].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[10].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[11].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[12].stonesCount").value("6"))
               .andExpect(jsonPath("$.pits[13].stonesCount").value("0"));
    }

    @Test
    public void shouldNotAffectGameSettingByChangesInAnotherGameSession() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/start")
                                                      .content("{\"player1Name\":\"Alex\",\"player2Name\":\"Bob\"}")
                                                      .contentType(APPLICATION_JSON)
                                                      .header("accept-language", "en")).andReturn();

        MockHttpSession session = (MockHttpSession) mvcResult.getRequest().getSession();
        makeActionMockRequest(0, session);
        mockMvc.perform(get("/game")
                                .header("accept-language", "en"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.player1Name").value("Player1"))
               .andExpect(jsonPath("$.player2Name").value("Player2"));
    }

    private void makeActionMockRequest(int selectedPitNumber, MockHttpSession session) throws Exception {
        mockMvc.perform(patch("/action")
                                .session(session)
                                .content(String.format("{\"selectedPitNumber\":\"%d\"}", selectedPitNumber))
                                .contentType(APPLICATION_JSON)
                                .header("accept-language", "en"));
    }
}
