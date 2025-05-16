package com.edutech.notificaciones.controller;

import com.edutech.notificaciones.dto.NotificacionDTO;
import com.edutech.notificaciones.model.Notificacion;
import com.edutech.notificaciones.service.NotificacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionesController {
    private final NotificacionService notificacionService;

    public NotificacionesController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping("/todas")
    public List<Notificacion> obtenerTodas() {
        return notificacionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public NotificacionDTO obtenerPorId(@PathVariable Long id) {
        Notificacion notificacion = notificacionService.obtenerPorId(id);
        NotificacionDTO notificacionDTO = notificacionService.converterDTO(notificacion);

        return notificacionDTO;
    }

    @PostMapping("/crear")
    public Notificacion crear(@RequestBody Notificacion notificacion) {
        return notificacionService.crear(notificacion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
    }

    @PutMapping("/{id}/leida")
    public Notificacion marcarComoLeida(@PathVariable Long id) {
        return notificacionService.marcarComoLeida(id);
    }
}
