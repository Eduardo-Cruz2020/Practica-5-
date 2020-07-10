package mx.uam.tsis.ejemplobackend.servicios;

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
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

/**
 * 
 * Prueba de integración para el endpoint alumnos
 * 
 * @author humbertocervantes
 *
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlumnoControllerIntegrationTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@BeforeEach
	public void prepare() {
		
		// Aqui se puede hacer cosas para preparar sus casos de prueba, incluyendo
		// agregar datos a la BD
		
	}
	
	
	@Test
	public void testCreate201() {
		
		// Creo el alumno que voy a enviar
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computación");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");

		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Alumno> request = new HttpEntity <> (alumno, headers);
		
		ResponseEntity <Alumno> responseEntity = restTemplate.exchange("/alumnos", HttpMethod.POST, request, Alumno.class);

		log.info("Me regresó:"+responseEntity.getBody());
		
		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		
		// Corroboro que en la base de datos se guardó el alumno
		Optional <Alumno> optAlumno = alumnoRepository.findById(12345678);
		assertEquals(alumno,optAlumno.get());
		
	}

	@Test
	public void testUpdate200() {
		
		Alumno alumno = new Alumno();
		alumno.setMatricula(12345678);
		alumno.setCarrera("Computación");
		alumno.setNombre("Pruebin");
		
		alumnoRepository.save(alumno); // Guardo el alumno original en la BD

		Alumno alumnoActualizado = new Alumno();
		alumnoActualizado.setMatricula(12345678);
		alumnoActualizado.setCarrera("Electrónica");
		alumnoActualizado.setNombre("PruebinActualizado");
		
		
		// Creo el encabezado
		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type",MediaType.APPLICATION_JSON.toString());
		headers.set("Authorization","Basic");
		
		// Creo la petición con el alumno como body y el encabezado
		HttpEntity <Alumno> request = new HttpEntity <> (alumnoActualizado, headers);
		
		ResponseEntity <Alumno> responseEntity = restTemplate.exchange("/alumnos/12345678", HttpMethod.PUT, request, Alumno.class);

		// Corroboro que el endpoint me regresa el estatus esperado
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Recupero de la BD el alumno
		Optional <Alumno> optAlumno = alumnoRepository.findById(12345678);
		
		Alumno actualizado = optAlumno.get();
		
		// Aquí corroboro que el alumno que está en la BD ya quedó actualizado
		assertEquals(alumnoActualizado, actualizado);
		
		// Debemos borrar al alumno, si no se queda en la BD
		alumnoRepository.delete(actualizado);
		
	}
	
	

}
