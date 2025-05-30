package com.edutech.notificaciones.controller;

import com.edutech.notificaciones.dto.NotificacionDTO;
import com.edutech.notificaciones.model.Notificacion;
import com.edutech.notificaciones.service.NotificacionService;

import org.springframework.http.ResponseEntity;
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
    public NotificacionDTO obtenerPorId(@PathVariable String id) {
        Notificacion notificacion = notificacionService.obtenerPorId(id);
        NotificacionDTO notificacionDTO = notificacionService.converterDTO(notificacion);

        return notificacionDTO;
    }

    @PostMapping("/crear")
    public Notificacion crear(@RequestBody Notificacion notificacion) {
        return notificacionService.crear(notificacion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
}

    @PutMapping("/{id}/leida")
    public Notificacion marcarComoLeida(@PathVariable String id) {
        return notificacionService.marcarComoLeida(id);
    }
}
