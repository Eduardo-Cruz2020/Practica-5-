package mx.uam.tsis.ejemplobackend.datos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.servicios.AlumnoController;

/**
 * Se encarga de almacenar y recuperar alumnos
 * 
 * @author humbertocervantes
 *
 */
@Component
@Slf4j
public class AlumnoRepository {
	
	// La "base de datos"
	private Map <Integer, Alumno> alumnoRepository = new HashMap <>();

	/**
	 * Guarda en la BD 
	 * @param alumno
	 * @return el alumno que se creo, null si no se creó correctamente
	 */
	public Alumno save(Alumno nuevoAlumno) {
		alumnoRepository.put(nuevoAlumno.getMatricula(), nuevoAlumno);
		return nuevoAlumno;
	}

	/**
	 * Encuentra el alumno con la matricula recibida  
	 * @param matricula
	 * @return Alumno encontrado, null si no existe
	 */
	public Alumno findByMatricula(Integer matricula) {
		return alumnoRepository.get(matricula);
	}

	/**
	 * Recupera una lista con todos los alumnos
	 * @param 
	 * @return una lista de alumnos
	 */
	public List <Alumno> find() {
		return new ArrayList <> (alumnoRepository.values());
	}
	
	/**
	 * Actualiza la información de un alumno
	 * @param alumno
	 * @return alumno actualizado, null si el alumno no existe
	 */
	public Alumno update(Alumno alumnoActualizado) {
		return alumnoRepository.put(alumnoActualizado.getMatricula(), alumnoActualizado);
	}
	
	/**
	 * Borra un alumno
	 * @param matricula
	 * @return alumno eliminado, null si no existe
	 */
	public Alumno delete(int matricula) {
		return alumnoRepository.remove(matricula);
	}
	
 }
