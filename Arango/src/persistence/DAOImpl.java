package persistence;

import java.util.List;
import java.util.Map;

import com.arangodb.util.MapBuilder;

import model.Departamento;
import model.Empleado;
import model.Evento;
import model.Incidencia;
import model.RankingTO;

public class DAOImpl extends ArangoUtils implements DAO {

	private static DAOImpl instance;

	public static DAOImpl getInstance() {
		if (instance == null)
			instance = new DAOImpl();
		return instance;
	}

	private DAOImpl() {}

	@Override
	public void insertEmpleado(Empleado e) {
		if(!exists(e))
			store(e);
		// TODO THROW EXCEPTION
			
	}

	@Override
	public boolean loginEmpleado(String username, String pass) {
		Map<String,Object> filters = new MapBuilder().put("username", username).put("contrasenya", pass).get();
		return !find(Empleado.class,filters).isEmpty();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Incidencia> selectAllIncidencias() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertIncidencia(Incidencia i) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Incidencia> getIncidenciaByDestino(Empleado e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Incidencia> getIncidenciaByOrigen(Empleado e) {
		// TODO Auto-generated method stub
		return null;
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
	public List<RankingTO> getRankingEmpleados() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void close() {
		super.close();
	}

	@Override
	public void insertDepartamento(Departamento d) {
		if(!exists(d))
			store(d);
		// TODO THROW EXCEPTION
			
	}

	@Override
	public void updateDepartamento(Departamento d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Empleado> selectAllEmpleados() {
		return find(Empleado.class);
	}

}
