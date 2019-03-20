package persistence;

import java.util.List;
import java.util.Map;

import com.arangodb.util.MapBuilder;

import model.Departamento;
import model.Empleado;
import model.Evento;
import model.Incidencia;
import model.dto.DepartamentoDTO;
import model.dto.EmpleadoDTO;
import model.dto.IncidenciaDTO;
import model.dto.RankingDTO;

public class DAOImpl extends ArangoUtils implements DAO {

	private static DAOImpl instance;

	public static DAOImpl getInstance() {
		if (instance == null)
			instance = new DAOImpl();
		return instance;
	}

	private DAOImpl() {
	}

	@Override
	public void insertEmpleado(EmpleadoDTO e) {
		if (!exists(e))
			store(e);
		// TODO THROW EXCEPTION

	}

	@Override
	public boolean loginEmpleado(String username, String pass) {
		Map<String, Object> filters = new MapBuilder().put("username", username).put("contrasenya", pass).get();
		return !find(Empleado.class, filters).isEmpty();
	}

	@Override
	public void updateEmpleado(Empleado e) {
		store(e);
	}

	@Override
	public void removeEmpleado(Empleado e) {
		delete(e);
	}

	@Override
	public Incidencia getIncidenciaById(int id) {
		Map<String, Object> filters = new MapBuilder().put("id", id).get();
		return find(Incidencia.class, filters).stream().findFirst().get();
		// Cuevas: uso el findFirst porque si hacemos .get(0) directamente podr�a dar
		// null pointer si la id no existe, luego el get del final es porque el stream
		// devuelve un Optional
	}

	@Override
	public List<Incidencia> selectAllIncidencias() {
		return find(Incidencia.class);
	}

	@Override
	public void insertIncidencia(IncidenciaDTO i) {
		if (!exists(i))
			store(i);
		// else
		// TODO THROW ALREADY_EXISTS EXCEPTION

	}

	@Override
	public List<Incidencia> getIncidenciaByDestino(Empleado e) {
		Map<String, Object> filters = new MapBuilder().put("destino", e).get();
		return find(Incidencia.class, filters);
		// TODO Cuevas: "destino" no servir�a si usamos un Set en la incidencia
	}

	@Override
	public List<Incidencia> getIncidenciaByOrigen(Empleado e) {
		Map<String, Object> filters = new MapBuilder().put("origen", e).get();
		return find(Incidencia.class, filters);
		// TODO Cuevas: "origen" no servir�a si usamos un Set en la incidencia
	}

	@Override
	public void insertarEvento(Evento e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Evento getUltimoInicioSesion(Empleado e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RankingDTO> getRankingEmpleados() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		super.close();
	}

	@Override
	public void insertDepartamento(DepartamentoDTO d) {
		if (!exists(d))
			store(d);
		// TODO THROW EXCEPTION

	}

	@Override
	public void updateDepartamento(Departamento d) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EmpleadoDTO> selectAllEmpleados() {
		return find(EmpleadoDTO.class);
	}

}
