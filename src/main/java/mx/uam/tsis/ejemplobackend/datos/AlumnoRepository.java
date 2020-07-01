package mx.uam.tsis.ejemplobackend.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;


/**
 * Almacena y recupera alumnos de la base de datos
 * 
 * @author Eduardo Cruz
 *
 */

public interface AlumnoRepository extends CrudRepository <Alumno, Integer>{

	
 }
