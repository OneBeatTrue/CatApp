import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.onebeattrue.GeneralApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = GeneralApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void successSignUpTest() throws Exception {
        mockMvc
                .perform(post("/auth/sign-up")
                .contentType("application/json")
                .content("{\"username\": \"dimativator\", \"password\": \"12345678\", \"name\": \"dima\", \"birthDate\": \"2004-06-04\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @Order(2)
    void successSignUpAsAdminTest() throws Exception {
        mockMvc
                .perform(post("/auth/sign-up-as-admin")
                        .contentType("application/json")
                        .content("{\"username\": \"onebeattrue\", \"password\": \"12345678\", \"name\": \"nikita\", \"birthDate\": \"2004-02-06\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @Order(3)
    void successSignInTest() throws Exception {
        mockMvc
                .perform(post("/auth/sign-in")
                .contentType("application/json")
                .content("{\"username\": \"onebeattrue\", \"password\": \"12345678\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}