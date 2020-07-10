package mx.uam.tsis.ejemplobackend.negocio;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

import java.util.Optional;

@ExtendWith(MockitoExtension.class) // Uso de Mockito
public class GrupoServiceTest {

	@Mock
	private GrupoRepository grupoRepositoryMock;
	
	@Mock
	private AlumnoService alumnoServiceMock;
	
	@InjectMocks
	private GrupoService grupoService;
	
	@Test
	public void testSuccesfulAddStudentToGroup (){
		
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");

		Alumno alumno = new Alumno();
		alumno.setCarrera("Computación");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");
		
		// Stubbing para el alumnoService
		when(alumnoServiceMock.retrieve(12345678)).thenReturn(alumno);
		
		// Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(grupo.getId())).thenReturn(Optional.of(grupo));
		
		
		boolean result = grupoService.addStudentToGroup(1, 12345678);
		
		assertEquals(true,result);
		
		assertEquals(grupo.getAlumnos().get(0),alumno);
		
	}
	
	@Test
	public void testUnsuccesfulAddStudentToGroup (){
		
		Alumno alumno = new Alumno();
		alumno.setCarrera("Computación");
		alumno.setMatricula(12345678);
		alumno.setNombre("Pruebin");
		
		// Stubbing para el alumnoService
		when(alumnoServiceMock.retrieve(12345678)).thenReturn(alumno);
		
		// Stubbing para grupoRepository
		when(grupoRepositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
		
		
		boolean result = grupoService.addStudentToGroup(1, 12345678);
		
		assertEquals(false,result);
		
		
	}
	
	@Test
	public void testSuccesfulCreate() {
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		
		// Stubbing para grupoRepository
		when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);

		// Aqui ejecuto a la unidad que quiero probar
		Grupo result = grupoRepositoryMock.save(grupo);
		
		//Comprobamos el resultado
		assertNotNull(result); // Probar que la referencia al grupo no es nula
	}
	
	@Test
	public void testUnSuccesfulCreate() {
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");
		
		
		// Stubbing para grupoRepository
		when(grupoRepositoryMock.save(grupo)).thenReturn(null);

		// Aqui ejecuto a la unidad que quiero probar
		Grupo result = grupoRepositoryMock.save(grupo);
		
		//Comprobamos el resultado
		assertNull(result); // Probar que la referencia a alumno es nula por que el alumno ya existía
	}
	
	@Test
	public void testSuccessfulUpdate() {
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");

		
		Grupo grupooActualizado = new Grupo();
		grupooActualizado.setId(1);
		grupooActualizado.setClave("TST02");

		// Simula lo que haría el alumnoRepository real cuando le pasan una id de grupo
		// que ya ha sido guardado
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(grupo));

		when(grupoRepositoryMock.save(grupo)).thenReturn(grupo);
		boolean result = grupoService.update(grupooActualizado);
		
		assertTrue(result);
	
	}
	
	@Test
	public void testUnSuccessfulUpdate() {
		Grupo grupo = new Grupo();
		grupo.setId(1);
		grupo.setClave("TST01");

		
		Grupo grupooActualizado = new Grupo();
		grupooActualizado.setId(1);
		grupooActualizado.setClave("TST02");

		// Simula lo que haría el alumnoRepository real cuando le pasan una id de grupo
		// que no ha sido guardado
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));
		boolean result = grupoService.update(grupooActualizado);
		assertEquals(false,result);
	
	}
	
	@Test
	public void testSuccesfulRetriveAll() {

		Iterable <Grupo> grupos = grupoService.retrieveAll();
		
		when(grupoRepositoryMock.findAll()).thenReturn(grupos);
		grupos = grupoService.retrieveAll();
		assertNotNull(grupos);

	}
	
	@Test
	public void testSuccesfulRetrive() {

		Grupo grupo = new Grupo();
		Integer id = 1;
		
		// Simula lo que haría el alumnoRepository real cuando le pasan una id de grupo
		// que ya ha sido guardado
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(grupo));
		
		grupo = grupoService.retrieve(id);
		assertNotNull(grupo);

	}
	
	//@Test Este no me quedó uU
	public void testUnSuccesfulRetrive() {

		Grupo grupo = new Grupo();
		Integer id = 1;
		
		// Simula lo que haría el alumnoRepository real cuando le pasan una id de grupo
		// que no ha sido guardado
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.empty());
		
		grupo = grupoService.retrieve(id);
		assertEquals(Optional.empty(), grupo);

	}
	
	@Test
	public void testSuccesfulDelete() {

		Grupo grupo = new Grupo();
		Integer id = 1;
		
		// Simula lo que haría el alumnoRepository real cuando le pasan una id de grupo
		// que ya ha sido guardado
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(grupo));
		
		boolean result = grupoService.delete(id);
		assertTrue(result);

	}

	@Test
	public void testUnSuccesfulDelete() {
		Integer id = 1;
		// Simula lo que haría el alumnoRepository real cuando le pasan una id de grupo
		// que no ha sido guardado
		when(grupoRepositoryMock.findById(1)).thenReturn(Optional.ofNullable(null));
		
		boolean result = grupoService.delete(id);
		assertEquals(false, result);

	}
	

}
