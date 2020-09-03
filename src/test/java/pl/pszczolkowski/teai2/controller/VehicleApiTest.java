package pl.pszczolkowski.teai2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.pszczolkowski.teai2.model.Vehicle;
import pl.pszczolkowski.teai2.service.VehicleService;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void contexLoads() {
        assertThat(mockMvc).isNotNull();
        assertThat(objectMapper).isNotNull();
    }

    @Test
    void getVehiclesListTest() throws Exception {
        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].mark", Is.is("BMW")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].mark", Is.is("Fiat")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].mark", Is.is("Kia")));
    }

    @Test
    void getVehicleByIdTest() throws Exception {
        mockMvc.perform(get("/vehicles/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("Fiat")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("126p")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("BLUE")));

    }

    @Test
    void getVehicleByColorTest() throws Exception {
        mockMvc.perform(get("/vehicles/colors/{color}", "RED"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }

    @Test
    void addVehicleTest() throws Exception {
        Vehicle vehicle = new Vehicle(5, "Lamborghini", "X", "BLACK");
        String jsonRequest = objectMapper.writeValueAsString(vehicle);
        mockMvc.perform(MockMvcRequestBuilders.post("/vehicles")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("Lamborghini")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("X")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("BLACK")));
    }

    @Test
    void modifyByIdTest() throws Exception {
        Vehicle vehicle = new Vehicle(3, "Lamborghini", "X", "BLACK");
        String jsonRequest = objectMapper.writeValueAsString(vehicle);
        mockMvc.perform(MockMvcRequestBuilders.put("/vehicles")
                .param("id", "3")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("Lamborghini")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("X")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("BLACK")));
    }

    @Test
    void modifyByIdOneParamTest() throws Exception {
        Vehicle vehicle = new Vehicle(3, "Lamborghini", "X", "BLACK");
        String jsonRequest = objectMapper.writeValueAsString(vehicle);
        mockMvc.perform(MockMvcRequestBuilders.patch("/vehicles")
                .param("id", "3")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mark", Is.is("Lamborghini")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Is.is("X")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Is.is("BLACK")));
    }

    @Test
    void deleteVehicleByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicles")
                .param("id", "4"))
                .andExpect(status().isOk());
    }

    @Test
    void notDeleteVehicleByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicles")
                .param("id", "5"))
                .andExpect(status().isNotFound());
    }
}
