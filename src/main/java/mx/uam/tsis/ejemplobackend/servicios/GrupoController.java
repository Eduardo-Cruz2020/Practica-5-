package mx.uam.tsis.ejemplobackend.servicios;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.GrupoService;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@RestController
@Slf4j 
public class GrupoController {
	
	
	@Autowired
	private GrupoService grupoService;
	
	
	@ApiOperation(
			value = "Crear grupo",
			notes = "Permite agregar un nuevo grupo"
			) // Documentacion del api
	@PostMapping(path = "/grupos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody @Valid Grupo nuevoGrupo) {
				
		log.info("Recibí llamada a create con "+nuevoGrupo);
		
		Grupo grupo = grupoService.create(nuevoGrupo);
		
		if(grupo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(grupo);			
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no se puede crear el grupo");
		}
	

	}
	
	@ApiOperation(
			value = "Recupera Todos",
			notes = "Permite recuperar la lista de todos los grupos en la DB"
			) // Documentacion del api
	@GetMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {

		log.info("Recuperando lista de grupos");
		Iterable <Grupo> result = grupoService.retrieveAll();
		return ResponseEntity.status(HttpStatus.OK).body(result); 
		
	}
	
	@ApiOperation(
			value = "Recupera ",
			notes = "Permite recuperar la información de un grupo desde la DB a traves de su id"
			) // Documentacion del api
	@GetMapping(path = "/grupos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("id") Integer id) {
		log.info("Buscando el grupo con id "+id);
		
		Grupo grupo = grupoService.retrieve(id);
		if (grupo != null) {
			return ResponseEntity.status(HttpStatus.OK).body(grupo); 		
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se encontró el alumno");
		}
	}
	
	@ApiOperation(
			value = "Actualiza",
			notes = "Permite actualizar los datos de  un grupo, se debe ingresar su id"
			) // Documentacion del api
	@PutMapping(path = "/alumnos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@PathVariable("id") Integer id, @RequestBody Grupo grupoU) {
		
		log.info("Actualizando grupo con id: " + id + " como " + grupoU );
		
		boolean alumno = grupoService.update(grupoU);
		
		if(alumno) {
			return ResponseEntity.status(HttpStatus.OK).body(grupoU);
		} 
		else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no se pudo actualizar el grupo");
		}

		
	}
	
	
	@ApiOperation(
			value = "Elimina",
			notes = "Permite borrar un grupo de la DB ingresando su id"
			) // Documentacion del api
	@DeleteMapping(path = "/alumnos/{id}")
	public ResponseEntity <?> delete(@PathVariable("id") Integer id) {
		log.info("Eliminando el grupo con id: "+ id);
		
		Grupo grupo = grupoService.retrieve(id);

		if (grupo != null) {
			grupoService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se encontró el grupo");
		}
	}
	
	
	/**
	 * 
	 * POST /grupos/{id}/alumnos?matricula=1234
	 * 
	 * PROBAR ESTE!!!
	 * 
	 * @return
	 */
	@PostMapping(path = "/grupos/{id}/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> addStudentToGroup(
			@PathVariable("id") Integer id,
			@RequestParam("matricula") Integer matricula) {
		
		boolean result = grupoService.addStudentToGroup(id, matricula);
		
		if(result) {
			return ResponseEntity.status(HttpStatus.OK).build(); 
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
		}
		
	
	}

}
