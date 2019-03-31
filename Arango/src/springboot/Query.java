package springboot;

import model.DepartamentoDTO;
import model.EmpleadoDTO;
import model.EventoDTO;
import model.IncidenciaDTO;
import model.RankingDTO;
import model.RankingEntryDTO;

public class Query {
	
	EmpleadoDTO loggedUser;
	EmpleadoDTO user;
	IncidenciaDTO incidencia;
	DepartamentoDTO departamento;
	EventoDTO evento;
	RankingEntryDTO ranking;
	/**
	 * @return the loggedUser
	 */
	public EmpleadoDTO getLoggedUser() {
		return loggedUser;
	}
	/**
	 * @param loggedUser the loggedUser to set
	 */
	public void setLoggedUser(EmpleadoDTO loggedUser) {
		this.loggedUser = loggedUser;
	}
	/**
	 * @return the user
	 */
	public EmpleadoDTO getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(EmpleadoDTO user) {
		this.user = user;
	}
	/**
	 * @return the incidencia
	 */
	public IncidenciaDTO getIncidencia() {
		return incidencia;
	}
	/**
	 * @param incidencia the incidencia to set
	 */
	public void setIncidencia(IncidenciaDTO incidencia) {
		this.incidencia = incidencia;
	}
	/**
	 * @return the departamento
	 */
	public DepartamentoDTO getDepartamento() {
		return departamento;
	}
	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(DepartamentoDTO departamento) {
		this.departamento = departamento;
	}
	/**
	 * @return the evento
	 */
	public EventoDTO getEvento() {
		return evento;
	}
	/**
	 * @param evento the evento to set
	 */
	public void setEvento(EventoDTO evento) {
		this.evento = evento;
	}
	/**
	 * @return the ranking
	 */
	public RankingDTO getRanking() {
		return ranking;
	}
	/**
	 * @param ranking the ranking to set
	 */
	public void setRanking(RankingDTO ranking) {
		this.ranking = ranking;
	}
	

}
