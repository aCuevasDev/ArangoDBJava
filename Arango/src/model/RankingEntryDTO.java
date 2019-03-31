package model;

/**
 * Representa una fila en el ranking de empleados de un departamento.
 * Contiene el numero de incidencias resueltas y el nombre del empleado.
 * Implementa Comparable, siendo mayor el que mas incidencias resueltas tenga.
 * 
 * @author razz97
 * @author acuevas
 * @author movip88
 */
public class RankingEntryDTO implements Comparable<RankingEntryDTO>{
	
	private String nombre;
	private Integer incidenciasResueltas;
	
	public int getIncidenciasResueltas() {
		return incidenciasResueltas;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String toString() {
		return nombre + ": " + incidenciasResueltas;
	}

	@Override
	public int compareTo(RankingEntryDTO o) {
		return o.incidenciasResueltas - incidenciasResueltas;
	}

}
