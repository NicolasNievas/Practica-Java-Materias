package ar.edu.utn.frc.tup.lciii.services.imp;

import ar.edu.utn.frc.tup.lciii.clients.RestClient;
import ar.edu.utn.frc.tup.lciii.dtos.*;
import ar.edu.utn.frc.tup.lciii.entities.AlumnoEntity;
import ar.edu.utn.frc.tup.lciii.entities.MateriaEntity;
import ar.edu.utn.frc.tup.lciii.models.ActualizarCalificacion;
import ar.edu.utn.frc.tup.lciii.models.Alumno;
import ar.edu.utn.frc.tup.lciii.models.Docente;
import ar.edu.utn.frc.tup.lciii.repositories.AlumnoRepository;
import ar.edu.utn.frc.tup.lciii.repositories.MateriaRepository;
import ar.edu.utn.frc.tup.lciii.services.GestionMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GestionMateriaServiceImp implements GestionMateriaService {
    private final RestClient restClient;

    private final AlumnoRepository alumnoRepository;
    private final MateriaRepository materiaRepository;
    @Autowired
    public GestionMateriaServiceImp(RestClient restClient, AlumnoRepository alumnoRepository, MateriaRepository materiaRepository) {
        this.restClient = restClient;
        this.alumnoRepository = alumnoRepository;
        this.materiaRepository = materiaRepository;
    }
    @Override
    public List<AlumnoDTO> getAlumnos() {
        List<Alumno> alumnos = restClient.getAllAlumnos();

        if(alumnos == null){
            throw new RuntimeException("No se encontraron alumnos");
        }

        return alumnos.stream().map(alumno -> AlumnoDTO.builder()
                .legajo(alumno.getLegajo())
                .nombre(alumno.getNombre())
                .build()).toList();
    }

    @Override
    public AlumnoDTO getAlumnoByLegajo(String legajo) {
        Alumno alumno = restClient.getAlumnoByLegajo(legajo);

        if(alumno == null){
            throw new RuntimeException("No se encontró el alumno con legajo: " + legajo);
        }

        return AlumnoDTO.builder()
                .legajo(alumno.getLegajo())
                .nombre(alumno.getNombre())
                .build();
    }

    @Override
    public List<DocenteDTO> getDocentes() {
        List<Docente> docentes = restClient.getAllDocentes();

        if(docentes == null){
            throw new RuntimeException("No se encontraron docentes");
        }

        return docentes.stream().map(docente -> DocenteDTO.builder()
                .matricula(docente.getMatricula())
                .nombre(docente.getNombre())
                .materia(docente.getMateria())
                .build()).toList();
    }

    @Override
    public DocenteDTO getDocenteByMateria(String materia) {
        Docente docente = restClient.getDocenteByMateria(materia);

        if(docente == null){
            throw new RuntimeException("No se encontró el docente con materia: " + materia);
        }

        return DocenteDTO.builder()
                .matricula(docente.getMatricula())
                .nombre(docente.getNombre())
                .materia(docente.getMateria())
                .build();
    }

    @Override
    public void saveAlumno() {
        List<Alumno> alumnos = restClient.getAllAlumnos();
        List<Docente> docentes = restClient.getAllDocentes();

        List<AlumnoEntity> alumnoEntities = alumnos.stream().map(alumno -> {
            if (getAlumnos().stream().anyMatch(a -> a.getLegajo().equals(alumno.getLegajo()))) {
                throw new RuntimeException("El alumno con legajo: " + alumno.getLegajo() + " ya se encuentra registrado");
            }
            List<MateriaEntity> materias = docentes.stream().map(docente ->
                    new MateriaEntity(null, null, docente.getNombre(), docente.getMateria(), 0, "Pendiente")
            ).collect(Collectors.toList());

            AlumnoEntity alumnoEntity = new AlumnoEntity(alumno.getLegajo(), alumno.getNombre(), materias);
            materias.forEach(materia -> materia.setAlumno(alumnoEntity));
            return alumnoEntity;
        }).collect(Collectors.toList());

        alumnoRepository.saveAll(alumnoEntities);
    }

    @Override
    public ActualizarCalificacionDTO actualizarCalificacion(String legajo, String materia, int calificacion) {
        Optional<AlumnoEntity> alumnoOptional = alumnoRepository.findByLegajo(legajo);
        if (alumnoOptional.isPresent()) {
            AlumnoEntity alumno = alumnoOptional.get();
            for (MateriaEntity materiaEntity : alumno.getMaterias()) {
                if (materiaEntity.getMateria().equals(materia)) {
                    materiaEntity.setCalificacion(calificacion);
                    String estado;
                    if (calificacion < 4) {
                        estado = "Libre";
                    } else if (calificacion < 9) {
                        estado = "Regular";
                    } else if (calificacion > 10) {
                        throw new RuntimeException("La calificación no puede ser mayor a 10");
                    } else {
                        estado = "Promocionado";
                    }
                    materiaEntity.setEstado(estado);
                    materiaRepository.save(materiaEntity);
                    return new ActualizarCalificacionDTO(legajo, materia, estado);
                }
            }
        }
        else {
            throw new RuntimeException("No se encontró el alumno con legajo: " + legajo);
        }
        return null;
    }

    @Override
    public List<MateriaEstadisticaDTO> obtenerEstadisticasMaterias(Optional<String> materiaFiltro) {
        List<MateriaEntity> materias = materiaFiltro.isPresent()
                ? materiaRepository.findByMateriaIgnoreCase(materiaFiltro.get())
                : materiaRepository.findAll();

        if (materias.isEmpty()) {
            throw new RuntimeException("No se encontraron materias");
        }

        Map<String, List<MateriaEntity>> materiasGrouped = materias.stream()
                .collect(Collectors.groupingBy(m -> m.getMateria().toLowerCase()));

        return materiasGrouped.entrySet().stream().map(entry -> {
            String materia = entry.getKey();
            List<MateriaEntity> materiaEntities = entry.getValue();

            long libres = materiaEntities.stream().filter(m -> m.getEstado().equals("Libre")).count();
            long regulares = materiaEntities.stream().filter(m -> m.getEstado().equals("Regular")).count();
            long promocionados = materiaEntities.stream().filter(m -> m.getEstado().equals("Promocionado")).count();

            long total = materiaEntities.size();
            String librePercent = String.format("%.2f%%", (libres / (double) total) * 100);
            String regularPercent = String.format("%.2f%%", (regulares / (double) total) * 100);
            String promocionadoPercent = String.format("%.2f%%", (promocionados / (double) total) * 100);

            String resultado = (regulares + promocionados) > (0.6 * total) ? "Exitoso" : "Fracaso";

            EstadoDTO estado = new EstadoDTO(librePercent, regularPercent, promocionadoPercent);

            return new MateriaEstadisticaDTO(materia, estado, resultado);
        }).collect(Collectors.toList());
    }


}

