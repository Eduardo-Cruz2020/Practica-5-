package mx.uam.tsis.ejemplobackend.negocio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.tsis.ejemplobackend.datos.GrupoRepository;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Alumno;
import mx.uam.tsis.ejemplobackend.negocio.modelo.Grupo;

@Service
@Slf4j
public class GrupoService {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private AlumnoService alumnoService;
	
	public Grupo create(Grupo nuevo) {
		
		// Validar reglas de negocio previas a la creación
		
		return grupoRepository.save(nuevo);
	}
	
	public Iterable <Grupo> retrieveAll() {
		return grupoRepository.findAll();
	}
	
	/**
	 * 
	 * Método que permite agregar un alumno a un grupo
	 * 
	 * @param groupId el id del grupo
	 * @param matricula la matricula del alumno
	 * @return true si se agregó correctamente, false si no
	 */
	public boolean addStudentToGroup(Integer groupId, Integer matricula) {
		
		log.info("Agregando alumno con matricula "+matricula+" al grupo "+groupId);
		
		// 1.- Recuperamos primero al alumno
		Alumno alumno = alumnoService.retrieve(matricula);
		
		// 2.- Recuperamos el grupo
		Optional <Grupo> grupoOpt = grupoRepository.findById(groupId);
		
		// 3.- Verificamos que el alumno y el grupo existan
		if(!grupoOpt.isPresent() || alumno == null) {
			
			log.info("No se encontró alumno o grupo");
			
			return false;
		}
		
		// 4.- Agrego el alumno al grupo
		Grupo grupo = grupoOpt.get();
		grupo.addAlumno(alumno);
		
		// 5.- Persistir el cambio
		grupoRepository.save(grupo);
		
		return true;
	}

	/**
	 * Solicita al grupoRepository un grupo
	 * @param id
	 * @return un grupo, null si no lo encontró
	 */
	public Grupo retrieve(int id) {		
		Optional <Grupo> grupoOpt = grupoRepository.findById(id);
		return grupoOpt.get();
	}

	/**
	 * Solicita actualizar un grupo
	 * @param grupo a actualizar
	 * @return el grupo actualizado, null si no existe
	 */
	public boolean update(Grupo grupoModificado) {		// Primero veo que si esté en la BD
		Optional <Grupo> grupoOpt = grupoRepository.findById(grupoModificado.getId());
		
		if(grupoOpt.isPresent()) {
			Grupo grupo = grupoOpt.get(); // Este es el que está en la bd
			grupo.setClave(grupoModificado.getClave());
			grupo.setAlumnos(grupo.getAlumnos());
			log.info("Persistiendo los cambios");
			grupoRepository.save(grupo); // Persisto los cambios
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Solicita al repository eliminar un grupo
	 * @param id del grupo 
	 * @return el grupo eliminado, null si no existe
	 */
	public boolean delete(int id) {
		// Primero veo que si esté en la BD
		Optional <Grupo> grupoOpt = grupoRepository.findById(id);
		
		if(grupoOpt.isPresent()) {
		grupoRepository.deleteById(id);
		return true;
		}
		else {
			return false;
		}
	}
	
	

}
