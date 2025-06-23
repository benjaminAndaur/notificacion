package com.edutech.notificaciones.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.edutech.notificaciones.service.NotificacionService;
import com.edutech.notificaciones.model.Notificacion;
import com.edutech.notificaciones.dto.NotificacionDTO;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest
public class NotificacionesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificacionService notificacionService;

    @Test
    void contextLoads() {
        // Test básico para verificar el contexto
    }

    @Test
    void testObtenerTodas() throws Exception {
        Notificacion n1 = new Notificacion("1", "dest1", "msg1", "2024-01-01T10:00", false);
        Notificacion n2 = new Notificacion("2", "dest2", "msg2", "2024-01-02T10:00", false);
        when(notificacionService.obtenerTodas()).thenReturn(Arrays.asList(n1, n2));
        mockMvc.perform(get("/notificaciones/todas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mensaje").value("msg1"));
    }

    @Test
    void testObtenerPorId_Existe() throws Exception {
        Notificacion n = new Notificacion("1", "dest1", "msg1", "2024-01-01T10:00", false);
        NotificacionDTO dto = NotificacionDTO.builder().mensaje("msg1").build();
        when(notificacionService.obtenerPorId("1")).thenReturn(n);
        when(notificacionService.converterDTO(n)).thenReturn(dto);
        mockMvc.perform(get("/notificaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("msg1"));
    }

    @Test
    void testObtenerPorId_NoExiste() throws Exception {
        when(notificacionService.obtenerPorId("1")).thenReturn(null);
        mockMvc.perform(get("/notificaciones/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrear() throws Exception {
        Notificacion n = new Notificacion(null, "dest1", "msg1", null, false);
        Notificacion creada = new Notificacion("1", "dest1", "msg1", "2024-01-01T10:00", false);
        when(notificacionService.crear(any(Notificacion.class))).thenReturn(creada);
        String json = "{\"destinatario\":\"dest1\",\"mensaje\":\"msg1\"}";
        mockMvc.perform(post("/notificaciones/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void testEliminar_Existe() throws Exception {
        doNothing().when(notificacionService).eliminar("1");
        mockMvc.perform(delete("/notificaciones/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminar_NoExiste() throws Exception {
        doThrow(new RuntimeException("No encontrada")).when(notificacionService).eliminar("1");
        mockMvc.perform(delete("/notificaciones/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testMarcarComoLeida_Existe() throws Exception {
        Notificacion n = new Notificacion("1", "dest1", "msg1", "2024-01-01T10:00", true);
        when(notificacionService.marcarComoLeida("1")).thenReturn(n);
        mockMvc.perform(put("/notificaciones/1/leida"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.leido").value(true));
    }

    @Test
    void testMarcarComoLeida_NoExiste() throws Exception {
        when(notificacionService.marcarComoLeida("1")).thenReturn(null);
        mockMvc.perform(put("/notificaciones/1/leida"))
                .andExpect(status().isNotFound());
    }
} 