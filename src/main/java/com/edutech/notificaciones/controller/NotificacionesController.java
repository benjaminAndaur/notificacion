package com.edutech.notificaciones.controller;

import com.edutech.notificaciones.dto.NotificacionDTO;
import com.edutech.notificaciones.model.Notificacion;
import com.edutech.notificaciones.service.NotificacionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Tag(name = "Notificaciones", description = "Operaciones CRUD para notificaciones")
@RestController
@RequestMapping("/notificaciones")
public class NotificacionesController {
    private final NotificacionService notificacionService;

    public NotificacionesController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @Operation(summary = "Obtener todas las notificaciones", description = "Devuelve una lista de todas las notificaciones almacenadas.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de notificaciones",
                content = @Content(mediaType = "application/json"))
        })
    @GetMapping("/todas")
    public List<Notificacion> obtenerTodas() {
        return notificacionService.obtenerTodas();
    }

    @Operation(summary = "Obtener notificación por ID", description = "Devuelve una notificación específica por su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Notificación encontrada",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = NotificacionDTO.class),
                    examples = @ExampleObject(value = "{\n  'mensaje': 'Test',\n  '_links': { 'self': { 'href': '/notificaciones/65a1bc...' }, 'all': { 'href': '/notificaciones/todas' } }\n}"))),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada",
                content = @Content)
        })
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDTO> obtenerPorId(
            @Parameter(description = "ID de la notificación", required = true) @PathVariable String id) {
        Notificacion notificacion = notificacionService.obtenerPorId(id);
        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }
        NotificacionDTO notificacionDTO = notificacionService.converterDTO(notificacion);
        notificacionDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacionesController.class).obtenerPorId(id)).withSelfRel());
        notificacionDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(NotificacionesController.class).obtenerTodas()).withRel("all"));
        return ResponseEntity.ok(notificacionDTO);
    }

    @Operation(summary = "Crear una notificación", description = "Crea una nueva notificación.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Notificacion.class),
                examples = @ExampleObject(value = "{ 'destinatario': 'usuario', 'mensaje': 'Hola mundo' }"))),
        responses = {
            @ApiResponse(responseCode = "201", description = "Notificación creada",
                content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
        })
    @PostMapping("/crear")
    public ResponseEntity<Notificacion> crear(@RequestBody Notificacion notificacion) {
        Notificacion creada = notificacionService.crear(notificacion);
        return ResponseEntity.status(201).body(creada);
    }

    @Operation(summary = "Eliminar notificación", description = "Elimina una notificación por su ID.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Notificación eliminada"),
            @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content)
        })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        try {
            notificacionService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Marcar como leída", description = "Marca una notificación como leída.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Notificación actualizada",
                content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "No encontrada", content = @Content)
        })
    @PutMapping("/{id}/leida")
    public ResponseEntity<Notificacion> marcarComoLeida(@PathVariable String id) {
        Notificacion n = notificacionService.marcarComoLeida(id);
        if (n == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(n);
    }
}
