package ch.hearc.boardel.controller;


import ch.hearc.boardel.BoardelApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BoardelApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = BoardelApplication.class)
public class RegisterControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void RegisterController_thenResponseIsCorrect() throws Exception {


        mvc.perform(get("/register")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().isOk());

        mvc.perform(get("/user/updatePassword?id=12&token=12345")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print()).andExpect(status()
                .isOk());

        mvc.perform(get("/forgotpassword")
                .contentType(MediaType.TEXT_HTML))
                .andDo(print()).andExpect(status()
                .isOk());

        mvc.perform(post("/register")
                .param("username", "test")
                .param("email", "test@test.com")
                .param("password", "123456789")
                .param("passwordConfirm", "123456789").with(csrf()))
                .andExpect(status().isOk())
                .andReturn();


    }

    @Test
    public void RegisterController_thenResponseIsNotCorrect() throws Exception {


        mvc.perform(get("/user/updatePassword")).andExpect(status().is4xxClientError());
    }
}
