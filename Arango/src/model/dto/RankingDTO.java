package model.dto;

public class RankingDTO {
	
	private String nombre;
	private int incidenciasResueltas;
	
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

}
