package mx.uam.tsis.ejemplobackend.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

@Service
@Slf4j
public class AlumnoService {

	@Autowired
	private AlumnoRepository alumnoRepository;
	
	/**
	 * Solicita crear un nuevo alumno
	 * @param nuevoAlumno
	 * @return el alumno que se acaba de crear si la creacion es exitosa, null de lo contrario
	 */
	public Alumno create(Alumno nuevoAlumno) {
		
		// Regla de negocio: No se puede crear más de un alumno con la misma matricula
		Optional<Alumno> alumno = alumnoRepository.findById(nuevoAlumno.getMatricula());
		
		if(!alumno.isPresent()) {
			return alumnoRepository.save(nuevoAlumno);
		} 
		else {
			return null;
		}
	}
	
	/**
	 * Recupera la lista de la alumnos 
	 * @return lista de alumnos
	 */
	public Iterable <Alumno> retrieveAll () {
		return alumnoRepository.findAll();
	}
	
	/**
	 * Solicita al repository un alumno
	 * @param matricula
	 * @return un alumno, null si no lo encontró
	 */
	public Alumno retrieve(int matricula) {		
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(matricula);
		return alumnoOpt.get();}
	
	/**
	 * Solicita al repository actualizar un alumno
	 * @param alumno a actualizar
	 * @return el alumno actualizado, null si no existe
	 */
	public boolean update(Alumno alumnoModificado) {		// Primero veo que si esté en la BD
		Optional <Alumno> alumnoOpt = alumnoRepository.findById(alumnoModificado.getMatricula());
		
		
		if(alumnoOpt.isPresent()) {
			Alumno alumno = alumnoOpt.get(); // Este es el que está en la bd
			
			alumno.setCarrera(alumnoModificado.getCarrera());
			alumno.setNombre(alumnoModificado.getNombre());
			
			log.info("Persistiendo los cambios "+alumno.getCarrera());
			
			alumnoRepository.save(alumno); // Persisto los cambios
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Solicita al repository eliminar un alumno
	 * @param matricula
	 * @return 
	 * @return el alumno eliminado, null si no existe2
	 */
	public void delete(int matricula) {
		alumnoRepository.deleteById(matricula);
	}
	
}
