package persistence;

import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.util.MapBuilder;

import model.Empleado;
import model.Evento;
import model.Incidencia;
import model.RankingTO;
import utils.ArangoUtils;

public class DAOImpl extends ArangoUtils implements DAO {

	private static DAOImpl instance;

	private ArangoDatabase db;

	public static DAOImpl getInstance() {
		if (instance == null)
			instance = new DAOImpl();
		return instance;
	}

	private DAOImpl() {
		db = new ArangoDB.Builder().host("172.16.2.50", 8529).password("stucom").build().db("GuacamoleDB");
	}

	@Override
	public void insertEmpleado(Empleado e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean loginEmpleado(String user, String pass) {
		Map<String,Object> filters = new MapBuilder().put("nombre", user).put("pass", pass).get();
		
		return !get(Empleado.class,filters).isEmpty();
		
//		String query = "FOR doc IN empleado FILTER doc.empleado.nombre == @nombre && doc.empleado.contrasenya == @pass RETURN doc";
//		Map<String, Object> bindVars = new MapBuilder().put("nombre", user).put("pass", pass).get();
//		ArangoCursor<BaseDocument> cursor = db.query(query, bindVars, null, BaseDocument.class);
//		cursor.forEachRemaining(aDocument -> {
//			BaseDocument myDocument = db.collection("empleado").getDocument(aDocument.getKey(), BaseDocument.class);
//		});
//		return false;
	}
	
	//private ArangoCursor<BaseDocument> 

	@Override
	public void updateEmpleado(Empleado e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEmpleado(Empleado e) {
		// TODO Auto-generated method stub

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

}
