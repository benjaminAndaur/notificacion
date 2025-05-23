package com.edutech.notificaciones.service;

import com.edutech.notificaciones.dto.NotificacionDTO;
import com.edutech.notificaciones.model.Notificacion;
import java.util.List;

public interface NotificacionService {
    List<Notificacion> obtenerTodas();
    Notificacion obtenerPorId(String id);
    Notificacion crear(Notificacion notificacion);
    void eliminar(String id);
    Notificacion marcarComoLeida(String id);
    NotificacionDTO converterDTO(Notificacion notificacion);
}
