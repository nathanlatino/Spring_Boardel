package ch.hearc.boardel.controller;


import ch.hearc.boardel.BoardelApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
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
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    @WithMockUser(username = "admin",roles = { "ADMIN" })
    public void HomeController_thenResponseIsCorrect() throws Exception {


        mvc.perform(get("/admin")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void HomeController_thenResponseIsNotCorrect() throws Exception {


        mvc.perform(get("/admin")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(	redirectedUrlPattern("**/login")).andExpect(status().isFound());
    }
}
