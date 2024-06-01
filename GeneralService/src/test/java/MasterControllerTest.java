import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.onebeattrue.MainApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MasterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void failureAdminAccessTest() throws Exception {
        mockMvc
                .perform(post("/auth/sign-up")
                        .contentType("application/json")
                        .content("""
                                {
                                  "username": "dimaTivator",
                                  "password": "12343456sdcsd",
                                  "name": "dima",
                                  "birthDate": "2004-06-04"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        mockMvc
                .perform(post("/auth/sign-up-as-admin")
                        .contentType("application/json")
                        .content("""
                                {
                                  "username": "onebeattrue",
                                  "password": "12343456",
                                  "name": "nikita",
                                  "birthDate": "2004-02-06"
                                }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        MvcResult result = mockMvc
                .perform(post("/auth/sign-in")
                        .contentType("application/json")
                        .content("""
                                {
                                  "username": "onebeattrue",
                                  "password": "12343456"
                                }
                                """))
                .andReturn();

        String jwt = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
        mockMvc
                .perform(post("/cat/create")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType("application/json")
                        .content("""
                                {
                                  "name": "Vasya",
                                  "birthDate": "2008-01-02",
                                  "breed": "Italian",
                                  "color": "MIXED",
                                  "master": 1
                                }"""))
                .andExpect(status().isOk());
        mockMvc
                .perform(post("/cat/create")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType("application/json")
                        .content("""
                                {
                                  "name": "Boris",
                                  "birthDate": "2018-01-02",
                                  "breed": "Russian",
                                  "color": "GINGER",
                                  "master": 2
                                }"""))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void failureUnAuthAccessTest() throws Exception {
        mockMvc
                .perform(get("/master/cats/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(3)
    void failureUserDependentTest() throws Exception {
        MvcResult result = mockMvc
                .perform(post("/auth/sign-in")
                        .contentType("application/json")
                        .content("""
                                {
                                  "username": "dimaTivator",
                                  "password": "12343456sdcsd"
                                }
                                """))
                .andReturn();

        String jwt = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
        mockMvc
                .perform(get("/master/cats/2")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(4)
    void successUserDependentTest() throws Exception {
        MvcResult result = mockMvc
                .perform(post("/auth/sign-in")
                        .contentType("application/json")
                        .content("""
                                {
                                  "username": "dimaTivator",
                                  "password": "12343456sdcsd"
                                }
                                """))
                .andReturn();

        String jwt = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
        mockMvc
                .perform(get("/master/cats/1")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void successAdminTest() throws Exception {
        MvcResult result = mockMvc
                .perform(post("/auth/sign-in")
                        .contentType("application/json")
                        .content("""
                                {
                                  "username": "onebeattrue",
                                  "password": "12343456"
                                }
                                """))
                .andReturn();

        String jwt = JsonPath.read(result.getResponse().getContentAsString(), "$.token");
        mockMvc
                .perform(get("/master/cats/1")
                        .header("Authorization", "Bearer " + jwt))
                .andExpect(status().isOk());
    }
}