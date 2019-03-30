package persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;

public abstract class ArangoUtils {
	
	public interface IKeyable {
		public String getKey();
		default public String getCollection() {
			return getClass().getSimpleName().toLowerCase();
		}
	}


	protected ArangoDatabase db;
	protected ArangoDB connection;

	protected ArangoUtils() {
		connection = new ArangoDB.Builder().host("alexraspberry.ddns.net", 8531).password("stucom").build();
		db = connection.db("GuacamoleDB");
	}

	protected <T> T getByKey(IKeyable object, Class<T> tClass) {
		return db.query("RETURN DOCUMENT('" + object.getCollection() + "/" + object.getKey() + "')",
				tClass).first();
	}

	protected void close() {
		connection.shutdown();
	}

	protected <T> List<T> find(Class<T> tClass) {
		ArangoCollection collection = db.collection(tClass.getSimpleName().toLowerCase());
		if (!collection.exists())
			return new ArrayList<T>();
		String query = "FOR doc IN " + tClass.getSimpleName().toLowerCase() + " RETURN doc";
		ArangoCursor<T> cursor = db.query(query, null, null, tClass);
		return cursor.asListRemaining();
	}

	protected boolean exists(IKeyable object) {
		ArangoCollection col = db.collection(object.getCollection());		
		if (!col.exists()) return false;
		return col.documentExists(object.getKey());
	}

	protected void store(IKeyable object) {
		if (!db.collection(object.getCollection()).exists())
			db.createCollection(object.getCollection());
		if (!exists(object))
			db.collection(object.getCollection()).insertDocument(object);
		else
			update(object);
	}
	
	protected void forceStore(IKeyable object) {
		if (!db.collection(object.getCollection()).exists())
			db.createCollection(object.getCollection());
		db.collection(object.getCollection()).insertDocument(object);
	}

	private void update(IKeyable object) {
		db.collection(object.getCollection()).updateDocument(object.getKey(), object);
	}

	protected <T> List<T> find(Class<T> tClass, Map<String, Object> filters) {
		String collectionName = tClass.getSimpleName().toLowerCase();
		if (!db.collection(collectionName).exists())
			return new ArrayList<>();
		String query = "FOR doc IN " + collectionName + " FILTER";
		for (String key : filters.keySet())
			query += " doc." + key + " == @" + key + " &&";
		query = query.substring(0, query.length() - 2) + "RETURN doc";
		ArangoCursor<T> cursor = db.query(query, filters, null, tClass);
		return cursor.asListRemaining();
	}
	
	protected <T> List<T> query(String query, Class<T> tClass) {
		return db.query(query, tClass).asListRemaining();
	}

	protected void delete(IKeyable object) {
		db.collection(object.getCollection()).deleteDocument(object.getKey());
	}

}
