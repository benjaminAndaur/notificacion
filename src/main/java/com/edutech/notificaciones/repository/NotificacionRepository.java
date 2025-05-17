package com.edutech.notificaciones.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.edutech.notificaciones.model.Notificacion;

public interface NotificacionRepository extends MongoRepository<Notificacion, String>{

}
