# LCIV-FINAL-20022024 

# API REST de Gestión de Materias

## Contexto del Negocio:
Es el encargado de diseñar un sistema para la carga de notas de alumnos en sus respectivas materias y determinar su condición de regularidad según la calificación obtenida.

## Enpoint a diseñar

### Registrar materias en alumnos **(10 pts)**

- Endpoint: ```/gestion```
- Método: ```POST```
- Acción: obtener los datos de todos los docentes y todos los alumnos de la API externa y registrarlos en la base de datos con un estado inicial de calificación en "Pendiente"
- Respuesta:
```
  {
    "legajo": "A001",
    "nombre": "Fernando",
    "materias": [
                  {
                    "docente": "Jose",
                    "materia": "Literatura",
                    "calificacion": 0,
                    "estado": "Pendiente"
                  },
                  {
                    "docente": "Maria",
                    "materia": "Matematica",
                    "calificacion": 0,
                    "estado": "Pendiente"
                  },
                  {
                    "docente": "Carlos",
                    "materia": "Historia",
                    "calificacion": 0,
                    "estado": "Pendiente"
                  },
                  {
                    "docente": "Pedro",
                    "materia": "Ingles",
                    "calificacion": 0,
                    "estado": "Pendiente"
                  }
                ]
  }
```

### Registrar nota del alumno **(10 pts)**

- Endpoint: ```/gestion```
- Método: ```PUT```
- Acción: registrar la calificación por materia y actualizar el estado siguiendo la escala descripta en las reglas de negocio
 Request:
```
  {
    "legajo": "A001",
    "materia": "Literatura",
    "calificacion": 10,
  }
```
- Respuesta:
```
  {
    "legajo": "A001",
    "materia": "Ingles",
    "estado": "Promocionado"
  }
```

### Listar informe académico **(10 pts)**

- Endpoint: ```/gestion```
- Método: ```GET```
- Acción: listar las materias con el porcentaje de alumnos en cada estado disponible junto al resultado general del curso (ver reglas de negocio), debe permitir que de forma opcional se pueda filtrar por materia
- Respuesta:
```
  {
    "materia": "Literatra",
    "estado":  {
                 "Libre": "25%",
                 "Regular": "25%",
                 "Promocionado": "50%"
                }
    "resultado": "Fracaso"
  }
```

## Reglas del Negocio:

### Calculo de estado académico

### Definición de estado **(10 pts)**
- El alumno tendrá un estado académico por cada materia según la calificación registrada

* LIBRE: Nota menor a 4
* REGULAR: Nota mayor o igual a 4 y menor a 9
* PROMOCIONADO: Nota mayor o igual a 9

### Definición de resultado general de la materia **(10 pts)**
- La materia se considera EXITOSA cuando el porcentaje de alumnos regulares y promocionados supera el 60%, caso contrario el resultado de la materia se considerará como FRACASO

---

### Información de la API Externa:

La api externa debe ser levantada via docker con la siguiente imagen:

``` docker pull gabrielarriola/api-examen ```

Y una vez levantada se puede acceder a su documentación en la url:

``` http://localhost:8080/docs#/ ```

Para llevar a cabo el registro y control de calificaciones de manera efectiva,
su aplicación interactuará con una API externa que proporcionará información clave.
A continuación, se detalla la información que se consultará desde la API externa:

### Docentes habilitados:

- Endpoint: ```/docentes```
- Respuesta:
```
[
  {
    "matricula": "100",
    "nombre": "Jose",
    "materia": "Literatura"
  },
  {
    "matricula": "200",
    "nombre": "Maria",
    "materia": "Matematica"
  },
  {
    "matricula": "300",
    "nombre": "Carlos",
    "materia": "Historia"
  },
  {
    "matricula": "400",
    "nombre": "Pedro",
    "materia": "Ingles"
  }
]
```


- Endpoint: ```/alumnos```
- Respuesta:
```
[
  {
    "legajo": "A001",
    "nombre": "Fernando"
  },
  {
    "legajo": "A002",
    "nombre": "Ana"
  },
  {
    "legajo": "A003",
    "nombre": "Daniel"
  }
]
```
---
### Docker (10 pts)
- Crear un archivo Dockerfile para dockerizar el proyecto
- Crear un archivo docker-compose que permita levantar tanto este servicio como el servicio externo
---
### Testing **(40 pts)**
- Es obligatorio la creación de testing unitario sobre la lógica de negocio de todo el proyecto
