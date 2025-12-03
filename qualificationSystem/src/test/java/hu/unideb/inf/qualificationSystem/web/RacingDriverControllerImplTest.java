package hu.unideb.inf.qualificationSystem.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import hu.unideb.inf.qualificationSystem.service.RacingDriverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = RacingDriverControllerImpl.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class RacingDriverControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RacingDriverService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_shouldReturnCreatedDriver() throws Exception {
        RacingDriver driver = RacingDriver.builder().number(1).name("Lewis Hamilton").team("Mercedes").build();
        when(service.create(any(RacingDriver.class))).thenReturn(driver);

        mockMvc.perform(post("/api/racing-drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driver)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.number").value(1))
                .andExpect(jsonPath("$.name").value("Lewis Hamilton"))
                .andExpect(jsonPath("$.team").value("Mercedes"));

        verify(service).create(any(RacingDriver.class));
    }

    @Test
    void getById_shouldReturnDriverWhenExists() throws Exception {
        RacingDriver driver = RacingDriver.builder().number(1).name("Lewis Hamilton").team("Mercedes").build();
        when(service.getById(1)).thenReturn(Optional.of(driver));

        mockMvc.perform(get("/api/racing-drivers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value(1))
                .andExpect(jsonPath("$.name").value("Lewis Hamilton"))
                .andExpect(jsonPath("$.team").value("Mercedes"));
    }

    @Test
    void getById_shouldReturnOkWithEmptyBodyWhenNotExists() throws Exception {
        when(service.getById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/racing-drivers/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void getAll_shouldReturnAllDrivers() throws Exception {
        List<RacingDriver> drivers = List.of(
                RacingDriver.builder().number(1).name("Lewis Hamilton").team("Mercedes").build(),
                RacingDriver.builder().number(2).name("Max Verstappen").team("Red Bull").build()
        );
        when(service.getAll()).thenReturn(drivers);

        mockMvc.perform(get("/api/racing-drivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value(1))
                .andExpect(jsonPath("$[1].number").value(2));
    }

    @Test
    void getAllByParams_shouldReturnFilteredDrivers() throws Exception {
        List<RacingDriver> drivers = List.of(
                RacingDriver.builder().number(1).name("Lewis Hamilton").team("Mercedes").build()
        );
        when(service.getAllByParams("Lewis", "Mercedes")).thenReturn(drivers);

        mockMvc.perform(get("/api/racing-drivers/search")
                        .param("name", "Lewis")
                        .param("team", "Mercedes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].number").value(1));
    }

    @Test
    void update_shouldReturnUpdatedDriverWhenExists() throws Exception {
        RacingDriver driver = RacingDriver.builder().number(1).name("Lewis Hamilton").team("Mercedes").build();
        when(service.update(1, driver)).thenReturn(driver);

        mockMvc.perform(put("/api/racing-drivers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driver)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value(1));
    }

    @Test
    void update_shouldReturnNotFoundWhenNotExists() throws Exception {
        RacingDriver driver = RacingDriver.builder().number(1).name("Lewis Hamilton").team("Mercedes").build();
        when(service.update(1, driver)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(put("/api/racing-drivers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driver)))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_shouldReturnOkWhenExists() throws Exception {
        when(service.existsById(1)).thenReturn(true);
        doNothing().when(service).delete(1);

        mockMvc.perform(delete("/api/racing-drivers/1"))
                .andExpect(status().isOk());

        verify(service).delete(1);
    }

    @Test
    void delete_shouldReturnNotFoundWhenNotExists() throws Exception {
        when(service.existsById(1)).thenReturn(false);

        mockMvc.perform(delete("/api/racing-drivers/1"))
                .andExpect(status().isNotFound());

        verify(service, never()).delete(1);
    }
}
