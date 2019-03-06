package persistence;

import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.util.MapBuilder;
import com.arangodb.entity.BaseDocument;

import model.Empleado;
import model.Evento;
import model.Incidencia;
import model.RankingTO;

public class DAOImpl implements DAOInterface{

	ArangoDB arangoDB = new ArangoDB.Builder().build();
	String dbName = "mydb";
	
	@Override
	public void insertEmpleado(Empleado e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean loginEmpleado(String user, String pass) {
		try {
			String query = "FOR t IN empleado FILTER t.nombre == @nombre && t.contrasenya == @contrasenya RETURN t";
			Map<String, Object> bindVars = new MapBuilder().put("nombre", user).put("contrasenya", pass).get();
			ArangoCursor<BaseDocument> cursor = arangoDB.db(dbName).query(query, bindVars, null, BaseDocument.class);
			cursor.forEachRemaining(aDocument -> {
				System.out.println("Key: " + aDocument.getKey());
				BaseDocument myDocument = arangoDB.db(dbName).collection("empleado").getDocument(aDocument.getKey(),
						BaseDocument.class);
				System.out.println(myDocument.getAttribute("nombre"));
			});
		} catch (ArangoDBException e) {
			System.err.println("Failed to execute query. " + e.getMessage());
		}
		return false;
	}

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
