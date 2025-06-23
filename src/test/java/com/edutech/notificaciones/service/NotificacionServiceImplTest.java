package com.edutech.notificaciones.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.edutech.notificaciones.repository.NotificacionRepository;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.edutech.notificaciones.model.Notificacion;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceImplTest {
    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionServiceImpl notificacionService;

    @Test
    void contextLoads() {
        // Test básico para verificar el contexto
    }

    @Test
    void testObtenerTodas() {
        Notificacion n1 = new Notificacion("1", "dest1", "msg1", "2024-01-01T10:00", false);
        Notificacion n2 = new Notificacion("2", "dest2", "msg2", "2024-01-02T10:00", false);
        when(notificacionRepository.findAll()).thenReturn(Arrays.asList(n1, n2));
        List<Notificacion> result = notificacionService.obtenerTodas();
        assertEquals(2, result.size());
        assertEquals("msg1", result.get(0).getMensaje());
    }

    @Test
    void testObtenerPorId_Existe() {
        Notificacion n = new Notificacion("1", "dest1", "msg1", "2024-01-01T10:00", false);
        when(notificacionRepository.findById("1")).thenReturn(Optional.of(n));
        Notificacion result = notificacionService.obtenerPorId("1");
        assertNotNull(result);
        assertEquals("msg1", result.getMensaje());
    }

    @Test
    void testObtenerPorId_NoExiste() {
        when(notificacionRepository.findById("1")).thenReturn(Optional.empty());
        Notificacion result = notificacionService.obtenerPorId("1");
        assertNull(result);
    }

    @Test
    void testCrear() {
        Notificacion n = new Notificacion(null, "dest1", "msg1", null, false);
        Notificacion nGuardada = new Notificacion("1", "dest1", "msg1", "2024-01-01T10:00", false);
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(nGuardada);
        Notificacion result = notificacionService.crear(n);
        assertNotNull(result.getId());
        assertFalse(result.isLeido());
        assertNotNull(result.getFechaCreacion());
    }

    @Test
    void testEliminar_Existe() {
        when(notificacionRepository.existsById("1")).thenReturn(true);
        doNothing().when(notificacionRepository).deleteById("1");
        assertDoesNotThrow(() -> notificacionService.eliminar("1"));
        verify(notificacionRepository).deleteById("1");
    }

    @Test
    void testEliminar_NoExiste() {
        when(notificacionRepository.existsById("1")).thenReturn(false);
        Exception ex = assertThrows(RuntimeException.class, () -> notificacionService.eliminar("1"));
        assertTrue(ex.getMessage().contains("No encontrada"));
    }

    @Test
    void testMarcarComoLeida_Existe() {
        Notificacion n = new Notificacion("1", "dest1", "msg1", "2024-01-01T10:00", false);
        Notificacion nLeida = new Notificacion("1", "dest1", "msg1", "2024-01-01T10:00", true);
        when(notificacionRepository.findById("1")).thenReturn(Optional.of(n));
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(nLeida);
        Notificacion result = notificacionService.marcarComoLeida("1");
        assertTrue(result.isLeido());
    }

    @Test
    void testMarcarComoLeida_NoExiste() {
        when(notificacionRepository.findById("1")).thenReturn(Optional.empty());
        Notificacion result = notificacionService.marcarComoLeida("1");
        assertNull(result);
    }
} 