package hu.unideb.inf.qualificationSystem.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.qualificationSystem.model.RacingTrack;
import hu.unideb.inf.qualificationSystem.service.RacingTrackService;
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

@WebMvcTest(value = RacingTrackControllerImpl.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class RacingTrackControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RacingTrackService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_shouldReturnCreatedTrack() throws Exception {
        RacingTrack track = RacingTrack.builder().city("Budapest").name("Hungaroring").country("Hungary").build();
        when(service.create(any(RacingTrack.class))).thenReturn(track);

        mockMvc.perform(post("/api/racing-tracks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(track)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city").value("Budapest"))
                .andExpect(jsonPath("$.name").value("Hungaroring"))
                .andExpect(jsonPath("$.country").value("Hungary"));

        verify(service).create(any(RacingTrack.class));
    }

    @Test
    void getById_shouldReturnTrackWhenExists() throws Exception {
        RacingTrack track = RacingTrack.builder().city("Budapest").name("Hungaroring").country("Hungary").build();
        when(service.getById("Budapest")).thenReturn(Optional.of(track));

        mockMvc.perform(get("/api/racing-tracks/Budapest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Budapest"))
                .andExpect(jsonPath("$.name").value("Hungaroring"))
                .andExpect(jsonPath("$.country").value("Hungary"));
    }

    @Test
    void getById_shouldReturnOkWithEmptyBodyWhenNotExists() throws Exception {
        when(service.getById("Budapest")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/racing-tracks/Budapest"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void getAll_shouldReturnAllTracks() throws Exception {
        List<RacingTrack> tracks = List.of(
                RacingTrack.builder().city("Budapest").name("Hungaroring").country("Hungary").build(),
                RacingTrack.builder().city("Monaco").name("Circuit de Monaco").country("Monaco").build()
        );
        when(service.getAll()).thenReturn(tracks);

        mockMvc.perform(get("/api/racing-tracks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Budapest"))
                .andExpect(jsonPath("$[1].city").value("Monaco"));
    }

    @Test
    void getAllByParams_shouldReturnFilteredTracks() throws Exception {
        List<RacingTrack> tracks = List.of(
                RacingTrack.builder().city("Budapest").name("Hungaroring").country("Hungary").build()
        );
        when(service.getAllByParams("Hungaroring", "Hungary", "Budapest")).thenReturn(tracks);

        mockMvc.perform(get("/api/racing-tracks/search")
                        .param("name", "Hungaroring")
                        .param("country", "Hungary")
                        .param("city", "Budapest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Budapest"));
    }

    @Test
    void update_shouldReturnUpdatedTrackWhenExists() throws Exception {
        RacingTrack track = RacingTrack.builder().city("Budapest").name("Hungaroring").country("Hungary").build();
        when(service.update("Budapest", track)).thenReturn(track);

        mockMvc.perform(put("/api/racing-tracks/Budapest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(track)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Budapest"));
    }

    @Test
    void update_shouldReturnNotFoundWhenNotExists() throws Exception {
        RacingTrack track = RacingTrack.builder().city("Budapest").name("Hungaroring").country("Hungary").build();
        when(service.update("Budapest", track)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(put("/api/racing-tracks/Budapest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(track)))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_shouldReturnNoContentWhenExists() throws Exception {
        when(service.existsById("Budapest")).thenReturn(true);
        doNothing().when(service).delete("Budapest");

        mockMvc.perform(delete("/api/racing-tracks/Budapest"))
                .andExpect(status().isNoContent());

        verify(service).delete("Budapest");
    }

    @Test
    void delete_shouldReturnNotFoundWhenNotExists() throws Exception {
        when(service.existsById("Budapest")).thenReturn(false);

        mockMvc.perform(delete("/api/racing-tracks/Budapest"))
                .andExpect(status().isNotFound());

        verify(service, never()).delete("Budapest");
    }
}
