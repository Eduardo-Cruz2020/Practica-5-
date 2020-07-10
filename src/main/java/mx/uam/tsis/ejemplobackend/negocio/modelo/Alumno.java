package mx.uam.tsis.ejemplobackend.negocio.modelo;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Alumno {
	
	@ApiModelProperty(notes = "Matrícula del alumno", required = true)
	@Id // Indica que este es llave primaria
	private Integer matricula;
	
	@NotBlank
	@ApiModelProperty(notes = "Nombre del alumno", required = true)
	private String nombre;
	
	@NotBlank
	@ApiModelProperty(notes = "Carrera del alumno", required = true)
	private String carrera;
}
