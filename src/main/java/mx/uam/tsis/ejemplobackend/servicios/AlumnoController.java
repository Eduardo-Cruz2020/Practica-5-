package mx.uam.tsis.ejemplobackend.servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.AlumnoService;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

/**
 * Controlador para el API rest
 * 
 * @author Eduardo Cruz
 *
 */
@RestController
@Slf4j
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	@ApiOperation(
			value = "Crear alumno",
			notes = "Permite crear un nuevo alumno, la matrícula debe ser única"
			) // Documentacion del api
	@PostMapping(path = "/alumnos", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody Alumno nuevoAlumno) {
				
		log.info("Recibí llamada a create con "+nuevoAlumno);
		
		Alumno alumno = alumnoService.create(nuevoAlumno);

		if(alumno != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(alumno);			
		} 
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se puede crear alumno");
		}		
	}
	
	@ApiOperation(
			value = "Recupera Todos",
			notes = "Permite recuperar la lista de todos los alumnos en la base de datos"
			) // Documentacion del api
	@GetMapping(path = "/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		log.info("Recuperando lista de alumnos");

		Iterable<Alumno> result = alumnoService.retrieveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result); 		
	}

	@ApiOperation(
			value = "Recupera ",
			notes = "Permite recuperar la información de un alumno desde la base de datos a traves de su matricula"
			) // Documentacion del api
	@GetMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("matricula") Integer matricula) {
		log.info("Buscando al alumno con matricula "+matricula);
		
		Alumno alumno = alumnoService.retrieve(matricula);
		if (alumno != null) {
			return ResponseEntity.status(HttpStatus.OK).body(alumno); 		
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se encontró el alumno");
		}
	}

	@ApiOperation(
			value = "Actualiza",
			notes = "Permite actualizar los datos de  un alumno, se debe ingresar su matrícula"
			) // Documentacion del api
	@PutMapping(path = "/alumnos/{matricula}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@PathVariable("matricula") Integer matricula, @RequestBody Alumno AlumnoU) {
		
		log.info("Actualizando alumno con matricula " + matricula + " como " +AlumnoU );
		
		boolean alumno = alumnoService.update(AlumnoU);
		
		if(alumno) {
			return ResponseEntity.status(HttpStatus.OK).body(AlumnoU);
		} 
		else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no existe pudo actualizar alumno");
		}

		
	}
	
	
	@ApiOperation(
			value = "Elimina",
			notes = "Permite borrar un alumno de la base de datos ingresando su matrícula"
			) // Documentacion del api
	@DeleteMapping(path = "/alumnos/{matricula}")
	public ResponseEntity <?> delete(@PathVariable("matricula") Integer matricula) {
		log.info("Eliminando al alumno con matricula "+matricula);
		
		Alumno alumno = alumnoService.retrieve(matricula);

		if (alumno != null) {
			alumnoService.delete(matricula);
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se encontró el alumno");
		}
	}
 }
