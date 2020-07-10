package mx.uam.tsis.ejemplobackend.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

/**
 * 
 * Prueba de integración para el endpoint grupos
 * 
 * @author Eduardo Cruz
 *
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrupoControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private AlumnoRepository alumnooRepository;
	
	@BeforeEach
	public void prepare() {
		
		// Aqui se puede hacer cosas para preparar sus casos de prueba, incluyendo
		// agregar datos a la BD
	}
	
	@Test
	public void testAddToGroup200() {
		//Creamos un grupo y lo guardamos en la DB
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TSIS=02");
		
		grupoRepository.save(grupo);
		
		//Creamos un alumno  y lo  guardamos en la DB
		Alumno alumno = new Alumno();
		alumno.setMatricula(1234);
		alumno.setCarrera("Computación");
		alumno.setNombre("Pruebin");
		
		alumnooRepository.save(alumno);
		
		// Creamos el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		headers.set("Authorization","Basic");
		
		// Creamos la petición con el grupo como body y el encabezado
		HttpEntity <Grupo> request = new HttpEntity <> (grupo, headers);
				
		ResponseEntity <Grupo> responseEntity = restTemplate.exchange("/grupos/1/alumnos?matricula=1234", HttpMethod.POST, request, Grupo.class);

		// Comprobamos que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		
		// Debemos borrar al alumno y grupo, si no se quedan en la BD
		grupoRepository.delete(grupo);
		alumnooRepository.delete(alumno);

	}
	
	@Test //ESTE FALLA Uu
	public void testAddToGroup404() {
		//Creamos un grupo y lo guardamos en la DB
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TSIS=02");
		
		grupoRepository.save(grupo);

		
		// Creamos el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		headers.set("Authorization","Basic");
		
		// Creamos la petición con el grupo como body y el encabezado
		HttpEntity <Grupo> request = new HttpEntity <> (grupo, headers);
		
		// INTENTAMOS AGREGAR UN ALUMNO QUE NO EXISTE
		ResponseEntity <Grupo> responseEntity = restTemplate.exchange("/grupos/1/alumnos?matricula=1234", HttpMethod.POST, request, Grupo.class);

		// Comprobamos que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		
		
		// Debemos borrar al alumno y grupo, si no se quedan en la BD
		grupoRepository.delete(grupo);

	}
	

}
