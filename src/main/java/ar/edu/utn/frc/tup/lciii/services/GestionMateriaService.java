package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.dtos.ActualizarCalificacionDTO;
import ar.edu.utn.frc.tup.lciii.dtos.AlumnoDTO;
import ar.edu.utn.frc.tup.lciii.dtos.DocenteDTO;
import ar.edu.utn.frc.tup.lciii.dtos.MateriaEstadisticaDTO;
import ar.edu.utn.frc.tup.lciii.entities.AlumnoEntity;
import ar.edu.utn.frc.tup.lciii.models.ActualizarCalificacion;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Service
public interface GestionMateriaService {
    List<AlumnoDTO> getAlumnos();
    AlumnoDTO getAlumnoByLegajo(String legajo);
    List<DocenteDTO> getDocentes();
    DocenteDTO getDocenteByMateria(String materia);
    void saveAlumno();
    ActualizarCalificacionDTO actualizarCalificacion(String legajo, String materia, int calificacion);
    List<MateriaEstadisticaDTO> obtenerEstadisticasMaterias(Optional<String> materiaFiltro);
}
