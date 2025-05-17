package com.edutech.notificaciones.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("notificacion")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notificacion {

    @Id
    private String id;

    private String destinatario;
    private String mensaje;
    private String fechaCreacion;
    private boolean leido;
}
