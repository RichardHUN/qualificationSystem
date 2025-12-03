package hu.unideb.inf.qualificationSystem.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.qualificationSystem.data.TrackTimeDTO;
import hu.unideb.inf.qualificationSystem.model.RacingDriver;
import hu.unideb.inf.qualificationSystem.model.RacingTrack;
import hu.unideb.inf.qualificationSystem.model.TrackTime;
import hu.unideb.inf.qualificationSystem.service.TrackTimeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = TrackTimeControllerImpl.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class TrackTimeControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TrackTimeService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fill_shouldReturnFilledTrackTimes() throws Exception {
        List<TrackTime> trackTimes = List.of(new TrackTime());
        when(service.fill()).thenReturn(trackTimes);

        mockMvc.perform(post("/api/track-times/fill"))
                .andExpect(status().isOk());

        verify(service).fill();
    }

    @Test
    void create_withTrackTime_shouldReturnCreatedTrackTime() throws Exception {
        RacingDriver driver = RacingDriver.builder().number(1).build();
        RacingTrack track = RacingTrack.builder().city("Budapest").build();
        TrackTime trackTime = TrackTime.builder().driver(driver).track(track).time(Duration.ofSeconds(100)).build();
        when(service.create(any(TrackTime.class))).thenReturn(trackTime);

        mockMvc.perform(post("/api/track-times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackTime)))
                .andExpect(status().isCreated());

        verify(service).create(any(TrackTime.class));
    }

    @Test
    void create_withTrackTimeDTO_shouldReturnCreatedTrackTime() throws Exception {
        TrackTimeDTO dto = mock(TrackTimeDTO.class);
        when(dto.getDriver()).thenReturn(1);
        when(dto.getTrack()).thenReturn("Budapest");
        when(dto.getTime()).thenReturn(Duration.ofSeconds(100));
        TrackTime trackTime = new TrackTime();
        when(service.create(any(TrackTimeDTO.class))).thenReturn(trackTime);

        mockMvc.perform(post("/api/track-times/dto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(service).create(any(TrackTimeDTO.class));
    }

    @Test
    void getById_shouldReturnTrackTimeWhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        TrackTime trackTime = new TrackTime();
        when(service.getById(id)).thenReturn(Optional.of(trackTime));

        mockMvc.perform(get("/api/track-times/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturnNotFoundWhenNotExists() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.getById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/track-times/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll_shouldReturnAllTrackTimes() throws Exception {
        List<TrackTime> trackTimes = List.of(new TrackTime(), new TrackTime());
        when(service.getAll()).thenReturn(trackTimes);

        mockMvc.perform(get("/api/track-times"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllByParams_shouldReturnFilteredTrackTimes() throws Exception {
        List<TrackTime> trackTimes = List.of(new TrackTime());
        when(service.getAllByParams("Budapest", "Lewis")).thenReturn(trackTimes);

        mockMvc.perform(get("/api/track-times/search")
                        .param("city", "Budapest")
                        .param("driverName", "Lewis"))
                .andExpect(status().isOk());
    }

    @Test
    void update_shouldReturnUpdatedTrackTimeWhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        TrackTime trackTime = new TrackTime();
        when(service.update(id, trackTime)).thenReturn(trackTime);

        mockMvc.perform(put("/api/track-times/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackTime)))
                .andExpect(status().isOk());
    }

    @Test
    void update_shouldReturnNotFoundWhenNotExists() throws Exception {
        UUID id = UUID.randomUUID();
        TrackTime trackTime = new TrackTime();
        when(service.update(id, trackTime)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(put("/api/track-times/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackTime)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateWithDTO_shouldReturnUpdatedTrackTimeWhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        TrackTimeDTO dto = mock(TrackTimeDTO.class);
        TrackTime trackTime = new TrackTime();
        when(service.updateWithDTO(any(UUID.class), any(TrackTimeDTO.class))).thenReturn(trackTime);

        mockMvc.perform(put("/api/track-times/dto/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void updateWithDTO_shouldReturnNotFoundWhenNotExists() throws Exception {
        UUID id = UUID.randomUUID();
        TrackTimeDTO dto = mock(TrackTimeDTO.class);
        when(service.updateWithDTO(any(UUID.class), any(TrackTimeDTO.class))).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(put("/api/track-times/dto/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void penalty_shouldReturnUpdatedTrackTime() throws Exception {
        UUID id = UUID.randomUUID();
        TrackTime trackTime = new TrackTime();
        when(service.penalty(id, 10)).thenReturn(trackTime);

        mockMvc.perform(put("/api/track-times/" + id + "/penalty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("10"))
                .andExpect(status().isOk());

        verify(service).penalty(id, 10);
    }

    @Test
    void delete_shouldReturnNoContentWhenExists() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.existsById(id)).thenReturn(true);
        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/api/track-times/" + id))
                .andExpect(status().isNoContent());

        verify(service).delete(id);
    }

    @Test
    void delete_shouldReturnNotFoundWhenNotExists() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.existsById(id)).thenReturn(false);

        mockMvc.perform(delete("/api/track-times/" + id))
                .andExpect(status().isNotFound());

        verify(service, never()).delete(id);
    }
}
