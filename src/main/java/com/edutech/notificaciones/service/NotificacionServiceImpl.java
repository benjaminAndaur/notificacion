package com.edutech.notificaciones.service;

import com.edutech.notificaciones.dto.NotificacionDTO;
import com.edutech.notificaciones.model.Notificacion;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NotificacionServiceImpl implements NotificacionService {
    private final List<Notificacion> notificaciones = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<Notificacion> obtenerTodas() {
        return new ArrayList<>(notificaciones);
    }

    @Override
    public Notificacion obtenerPorId(Long id) {
        return notificaciones.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Notificacion crear(Notificacion notificacion) {
        notificacion.setId(idGenerator.getAndIncrement());
        notificacion.setFechaCreacion(LocalDateTime.now());
        notificacion.setLeido(false);
        notificaciones.add(notificacion);
        return notificacion;
    }

    @Override
    public void eliminar(Long id) {
        notificaciones.removeIf(n -> n.getId().equals(id));
    }

    @Override
    public Notificacion marcarComoLeida(Long id) {
        Optional<Notificacion> opt = notificaciones.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst();
        opt.ifPresent(n -> n.setLeido(true));
        return opt.orElse(null);
    }

    @Override
    public NotificacionDTO converterDTO (Notificacion data) {
        return NotificacionDTO.builder().mensaje(data.getMensaje()).build();
    }
}
