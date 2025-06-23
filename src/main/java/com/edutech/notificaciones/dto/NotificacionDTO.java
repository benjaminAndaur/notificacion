package com.edutech.notificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacionDTO extends RepresentationModel<NotificacionDTO> {

    private String mensaje;

}
