package mx.uam.tsis.ejemplobackend.negocio;

import java.util.List;

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
		Alumno alumno = alumnoRepository.findByMatricula(nuevoAlumno.getMatricula());
		
		if(alumno == null) {
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
	public List <Alumno> retrieveAll () {
		return alumnoRepository.find();
	}
	
	/**
	 * Solicita al repository un alumno
	 * @param matricula
	 * @return un alumno, null si no lo encontró
	 */
	public Alumno retrieve(int matricula) {
		return alumnoRepository.findByMatricula(matricula);
	}
	
	/**
	 * Solicita al repository actualizar un alumno
	 * @param alumno a actualizar
	 * @return el alumno actualizado, null si no existe
	 */
	public Alumno update(Alumno alumnoModificado) {
		return alumnoRepository.update(alumnoModificado);
	}
	
	/**
	 * Solicita al repository eliminar un alumno
	 * @param matricula
	 * @return el alumno eliminado, null si no existe
	 */
	public Alumno delete(int matricula) {
		return alumnoRepository.delete(matricula);
	}
	
}
