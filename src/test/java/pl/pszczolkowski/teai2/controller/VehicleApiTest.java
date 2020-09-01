package pl.pszczolkowski.teai2.controller;

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

    @MockBean
    private VehicleService vehicleService;
    @Autowired
    private MockMvc mockMvc;
    List<Vehicle> vehicles;

    @Before
    public void setUp() {
        vehicles = new ArrayList<>();
        vehicles.add(new Vehicle(1L, "BMW", "E36", "RED"));
        vehicles.add(new Vehicle(2L, "Fiat", "126p", "BLUE"));
        vehicles.add(new Vehicle(3L, "Kia", "Ceed", "GREY"));
        vehicles.add(new Vehicle(4L, "BMW", "E90", "RED"));
    }

    @Test
    public void contexLoads() {
        assertThat(mockMvc).isNotNull();
        assertThat(vehicleService).isNotNull();
    }

    @Test
    void getVehiclesListTest() throws Exception {
        when(vehicleService.getAllVehicles()).thenReturn(vehicles);

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].mark", Is.is("BMW")));
    }

    @Test
    void shouldGetVehicleByColorTest() throws Exception {
        mockMvc.perform(get("/vehicles/color/RED"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(vehicleService.getVehicleByColorSer("RED").size())));
    }

    @Test
    void addVehicle() {
    }

    @Test
    void modifyById() {
    }

    @Test
    void modifyByIdOneParam() {
    }

    @Test
    void deleteVehicleById() {
    }
}
