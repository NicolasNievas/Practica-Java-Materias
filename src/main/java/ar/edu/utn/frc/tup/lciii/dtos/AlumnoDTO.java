package ar.edu.utn.frc.tup.lciii.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlumnoDTO {
    private String legajo;
    private String nombre;
}