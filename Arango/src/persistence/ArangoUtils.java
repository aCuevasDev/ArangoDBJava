package persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import model.IKeyable;

public abstract class ArangoUtils {

	protected ArangoDatabase db;
	protected ArangoDB connection;

	protected ArangoUtils() {
		connection = new ArangoDB.Builder().host("172.16.2.50", 8529).password("stucom").build();
		db = connection.db("GuacamoleDB");
	}

	protected <T> T getByKey(IKeyable object, Class<T> tClass) {
		return db.query("RETURN DOCUMENT('" + tClass.getSimpleName().toLowerCase() + "/" + object.getKey() + "')",
				tClass).first();
	}

	protected void close() {
		connection.shutdown();
	}

	protected <T> List<T> find(Class<T> tClass) {
		String collectionName = tClass.getSimpleName().toLowerCase();
		ArangoCollection collection = db.collection(collectionName);
		if (!collection.exists())
			return new ArrayList<T>();
		String query = "FOR doc IN " + tClass.getSimpleName().toLowerCase() + " RETURN doc";
		ArangoCursor<T> cursor = db.query(query, null, null, tClass);
		return cursor.asListRemaining();
	}

	protected boolean exists(IKeyable object) {
		return db.collection(object.getClass().getSimpleName().toLowerCase()).documentExists(object.getKey());
	}

	protected void store(IKeyable object) {
		String collectionName = object.getClass().getSimpleName().toLowerCase();
		ArangoCollection collection = db.collection(collectionName);
		if (!collection.exists())
			db.createCollection(collectionName);
		if (!exists(object))
			db.collection(collectionName).insertDocument(object);
		else
			update(object);
	}

	private void update(IKeyable object) {
		db.collection(object.getClass().getSimpleName().toLowerCase()).updateDocument(object.getKey(), object);
	}

	protected <T> List<T> find(Class<T> tClass, Map<String, Object> filters) {
		String collectionName = tClass.getSimpleName().toLowerCase();
		ArangoCollection collection = db.collection(collectionName);
		if (!collection.exists()) {
			return new ArrayList<>();
		}
		String query = "FOR doc IN " + collectionName + " FILTER";
		for (String key : filters.keySet())
			query += " doc." + collectionName + "." + key + " == @" + key + " &&";
		query = query.substring(0, query.length() - 2) + "RETURN doc";
		ArangoCursor<T> cursor = db.query(query, filters, null, tClass);
		return cursor.asListRemaining();
	}
	
	protected <T> List<T> findWhereDifferent(Class<T> tClass, Map<String, Object> filters) {
		String collectionName = tClass.getSimpleName().toLowerCase();
		ArangoCollection collection = db.collection(collectionName);
		if (!collection.exists()) {
			return new ArrayList<>();
		}
		String query = "FOR doc IN " + collectionName + " FILTER";
		for (String key : filters.keySet())
			query += " doc." + collectionName + "." + key + " != @" + key + " &&";
		query = query.substring(0, query.length() - 2) + "RETURN doc";
		ArangoCursor<T> cursor = db.query(query, filters, null, tClass);
		return cursor.asListRemaining();
	}
	
	protected <T> List<T> query(String query, Class<T> tClass) {
		return db.query(query, tClass).asListRemaining();
	}

	protected void delete(IKeyable object) {
		String nameClass = object.getClass().getSimpleName().toLowerCase();
		db.collection(nameClass).deleteDocument(object.getKey());
	}
	
	class QueryFilter {
		boolean equal;
		String value;
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return value;
		}
	}

}
