package ch.hearc.boardel.controller;


import ch.hearc.boardel.BoardelApplication;
import ch.hearc.boardel.model.Board;
import ch.hearc.boardel.service.BoardService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=BoardelApplication.class)
@WebMvcTest(BoardController.class)
public class BoardControllerTest {


    @Autowired
    private MockMvc mvc;

    @Mock
    private BoardService boardService;


    @Before
    public void setUp() {
        Board board =  new Board();
        board.setName("test board");
        board.setDescription("test description");
        board.setId(0l);
        Mockito.when(boardService.find(0l))
                .thenReturn(board);
    }

    @Test

    public void HomeController_thenResponseIsCorrect() throws Exception {


        mvc.perform(get("/boards/0")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin",roles = { "ADMIN" })
    public void HomeController_thenResponseIsCorrectAdmin() throws Exception {


        mvc.perform(get("/boards/create")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin",roles = { "MODO" })
    public void HomeController_thenResponseIsCorrectMODO() throws Exception {


        mvc.perform(get("/boards/create")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk());
    }



    @Test
    public void HomeController_thenResponseIsNotCorrect() throws Exception {


        mvc.perform(get("/boards/create")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(	redirectedUrlPattern("**/login")).andExpect(status().isFound());
    }
}
