package ar.edu.utn.frc.tup.lciii.clients;

import ar.edu.utn.frc.tup.lciii.models.Alumno;
import ar.edu.utn.frc.tup.lciii.models.Docente;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestClient {
    private final RestTemplate restTemplate;

    public List<Alumno> getAllAlumnos(){
        String url = "http://localhost:8080/alumnos";
        ResponseEntity<List<Alumno>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Alumno>>() {
                });
        return response.getBody();
    }
    public Alumno getAlumnoByLegajo(String legajo){
        String url = "http://localhost:8080/alumnos?legajo=" + legajo;
        ResponseEntity<List<Alumno>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Alumno>>() {
                });
        return response.getBody().get(0);
    }
    public List<Docente> getAllDocentes(){
        String url = "http://localhost:8080/docentes";
        ResponseEntity<List<Docente>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Docente>>() {
                });
        return response.getBody();
    }
    public Docente getDocenteByMateria(String materia){
        String url = "http://localhost:8080/docentes?materia=" + materia;
        ResponseEntity<List<Docente>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Docente>>() {
                });
        return response.getBody().get(0);
    }
}
