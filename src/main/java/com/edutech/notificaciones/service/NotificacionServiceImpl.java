package com.edutech.notificaciones.service;

import com.edutech.notificaciones.dto.NotificacionDTO;
import com.edutech.notificaciones.model.Notificacion;
import com.edutech.notificaciones.repository.NotificacionRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    private static final Logger logger = LoggerFactory.getLogger(NotificacionServiceImpl.class);
    @Override
    public List<Notificacion> obtenerTodas() {
        return notificacionRepository.findAll();
    }

    @Override
    public Notificacion obtenerPorId(String id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    @Override
    public Notificacion crear(Notificacion notificacion) {
        notificacion.setFechaCreacion(LocalDateTime.now().toString());
        notificacion.setLeido(false);
        return notificacionRepository.save(notificacion);
    }

    @Override
    public void eliminar(Long id) {
        notificacionRepository.deleteById(String.valueOf(id));
    }

    @Override
    public Notificacion marcarComoLeida(Long id) {
        Optional<Notificacion> opt = notificacionRepository.findById(String.valueOf(id));
        if (opt.isPresent()) {
            Notificacion notificacion = opt.get();
            notificacion.setLeido(true);
            return notificacionRepository.save(notificacion);
        }
        return null;
    }

    @Override
    public NotificacionDTO converterDTO (Notificacion data) {
        return NotificacionDTO.builder().mensaje(data.getMensaje()).build();
    }
}
