package model.dto;

public class RankingDTO implements Comparable<RankingDTO>{
	
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
	public int compareTo(RankingDTO o) {
		return incidenciasResueltas - o.incidenciasResueltas;
	}

}
