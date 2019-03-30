package persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;

/**
 * Clase cuyo objetivo es abstraer el modelo al usar una base de datos ArangoDB.
 * 
 * @author razz97
 * @author acuevas
 * @author movip88
 */
public abstract class ArangoUtils {
	
	/**
	 * Representa un objeto capaz de devolver la key y el nombre de coleccion de su documento.
	 * 
	 * @author razz97
	 * @author acuevas
	 * @author movip88
	 */
	public interface IKeyable {
		
		/**
		 * La clave de negocio.
		 */
		public String getKey();
		
		/**
		 * El nombre de la coleccion. 
		 */
		default public String getCollection() {
			return getClass().getSimpleName().toLowerCase();
		}
	}


	private ArangoDatabase db;
	private ArangoDB connection;

	protected ArangoUtils() {
		connection = new ArangoDB.Builder().host("alexraspberry.ddns.net", 8531).password("stucom").build();
		db = connection.db("GuacamoleDB");
	}

	/**
	 * Cierra la conexion con la base de datos.
	 */
	protected void close() {
		connection.shutdown();
	}

	/**
	 * Lee un objeto de la base de datos a partir de un IKeyable y su clase.
	 */
	protected <T> T getByKey(IKeyable object, Class<T> tClass) {
		return db.query("RETURN DOCUMENT('" + object.getCollection() + "/" + object.getKey() + "')", tClass).first();
	}

	/**
	 * Lee una coleccion a partir de la clase requerida. 
	 */
	protected <T> List<T> find(Class<T> tClass) {
		String col = tClass.getSimpleName().toLowerCase();
		if (!db.collection(col).exists())
			return new ArrayList<T>();
		return db.query("FOR doc IN " + col + " RETURN doc", null, null, tClass).asListRemaining();
	}
	
	/**
	 *  Lee una coleccion a partir de su clase y filtros.
	 */
	protected <T> List<T> find(Class<T> tClass, Map<String, Object> filters) {
		String collectionName = tClass.getSimpleName().toLowerCase();
		if (!db.collection(collectionName).exists())
			return new ArrayList<>();
		String query = "FOR doc IN " + collectionName + " FILTER";
		for (String key : filters.keySet())
			query += " doc." + key + " == @" + key + " &&";
		query = query.substring(0, query.length() - 2) + "RETURN doc";
		return db.query(query, filters, null, tClass).asListRemaining();
	}
	
	/**
	 * Ejecuta una query en la base de datos, devuelve el resultado como una lista.
	 */
	protected <T> List<T> query(String query, Class<T> tClass) {
		return db.query(query, tClass).asListRemaining();
	}

	/**
	 * Comprueba si un IKeyable existe en la base de datos.
	 */
	protected boolean exists(IKeyable object) {
		ArangoCollection col = db.collection(object.getCollection());		
		if (!col.exists()) return false;
		return col.documentExists(object.getKey());
	}
	
	/**
	 * Comprueba si una coleccion existe en la base de datos.
	 */
	protected boolean isCollection(String col) {
		return db.collection(col).exists();
	}

	/**
	 * Guarda un IKeyable en la base de datos si no existe, si existe lo actualiza. 
	 */
	protected void store(IKeyable object) {
		if (!db.collection(object.getCollection()).exists())
			db.createCollection(object.getCollection());
		if (!exists(object))
			db.collection(object.getCollection()).insertDocument(object);
		else
			update(object);
	}
	
	/**
	 * Fuerza la insercion de un IKeyable en la base de datos.
	 */
	protected void forceStore(IKeyable object) {
		if (!db.collection(object.getCollection()).exists())
			db.createCollection(object.getCollection());
		db.collection(object.getCollection()).insertDocument(object);
	}

	/**
	 * Actualiza un documento a partir de su IKeyable.
	 */
	private void update(IKeyable object) {
		db.collection(object.getCollection()).updateDocument(object.getKey(), object);
	}

	/**
	 * Borra un elemento a partir de su IKeyable.
	 */
	protected void delete(IKeyable object) {
		db.collection(object.getCollection()).deleteDocument(object.getKey());
	}

}
