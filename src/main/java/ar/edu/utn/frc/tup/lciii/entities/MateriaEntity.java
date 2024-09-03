package ar.edu.utn.frc.tup.lciii.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "materias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MateriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alumno_legajo")
    @JsonIgnore
    private AlumnoEntity alumno;

    @Column
    private String docente;

    @Column
    private String materia;

    @Column
    private int calificacion;

    @Column
    private String estado;
}
