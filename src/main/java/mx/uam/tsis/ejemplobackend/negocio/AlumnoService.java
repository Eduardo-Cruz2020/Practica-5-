package mx.uam.tsis.ejemplobackend.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.tsis.ejemplobackend.datos.AlumnoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;

@Service
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
	public Optional<Alumno> retrieve(int matricula) {
		return alumnoRepository.findById(matricula);
	}
	
	/**
	 * Solicita al repository actualizar un alumno
	 * @param alumno a actualizar
	 * @return el alumno actualizado, null si no existe
	 */
	public Alumno update(Alumno alumnoModificado) {
		return alumnoRepository.save(alumnoModificado);
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
