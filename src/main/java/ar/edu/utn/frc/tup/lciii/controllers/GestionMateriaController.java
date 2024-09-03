package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.ActualizarCalificacionDTO;
import ar.edu.utn.frc.tup.lciii.dtos.MateriaEstadisticaDTO;
import ar.edu.utn.frc.tup.lciii.models.ActualizarCalificacion;
import ar.edu.utn.frc.tup.lciii.entities.AlumnoEntity;
import ar.edu.utn.frc.tup.lciii.repositories.AlumnoRepository;
import ar.edu.utn.frc.tup.lciii.services.GestionMateriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gestion")
public class GestionMateriaController {
    private final GestionMateriaService gestionMateriaService;
    private final AlumnoRepository alumnoRepository;

    public GestionMateriaController(GestionMateriaService gestionMateriaService,
                                    AlumnoRepository alumnoRepository) {
        this.gestionMateriaService = gestionMateriaService;
        this.alumnoRepository = alumnoRepository;
    }
    @GetMapping("/alumnos")
    public ResponseEntity<?> getAlumnnos(@RequestParam(required = false) String legajo) {
        if (legajo == null) {
            return ResponseEntity.ok(gestionMateriaService.getAlumnos());
        }
        else {
            return ResponseEntity.ok(gestionMateriaService.getAlumnoByLegajo(legajo));
        }
    }
    @GetMapping("/docentes")
    public ResponseEntity<?> getDocentes(@RequestParam(required = false) String materia) {
        if (materia == null) {
            return ResponseEntity.ok(gestionMateriaService.getDocentes());
        }
        else {
            return ResponseEntity.ok(gestionMateriaService.getDocenteByMateria(materia));
        }
    }
    @PostMapping("/registrar_Alumno")
    public ResponseEntity<String> saveAlumno() {
        gestionMateriaService.saveAlumno();
        return ResponseEntity.ok("Alumno registrado");
    }
    @PutMapping("/actualizar_Calificacion")
    public ResponseEntity<ActualizarCalificacionDTO> actualizarCalificacion(@RequestBody ActualizarCalificacion request) {
        ActualizarCalificacionDTO response = gestionMateriaService.actualizarCalificacion(request.getLegajo(), request.getMateria(), request.getCalificacion());
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/estadisticas")
    public ResponseEntity<List<MateriaEstadisticaDTO>> obtenerEstadisticasMaterias(@RequestParam(required = false) Optional<String> materiaFiltro) {
        return ResponseEntity.ok(gestionMateriaService.obtenerEstadisticasMaterias(materiaFiltro));
    }
}
