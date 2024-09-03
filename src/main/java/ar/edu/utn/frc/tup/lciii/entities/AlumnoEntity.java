package ar.edu.utn.frc.tup.lciii.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "alumnos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlumnoEntity {
    @Id
    @Column
    private String legajo;

    @Column
    private String nombre;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MateriaEntity> materias;
}
