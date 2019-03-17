package persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;

import model.Codificable;

public abstract class ArangoUtils {	
	
	protected ArangoDatabase db;
	protected ArangoDB connection;

	protected ArangoUtils() {
		connection = new ArangoDB.Builder().host("172.16.2.50", 8529).password("stucom").build();
		db = connection.db("GuacamoleDB");
	}
	
	protected void close() {
		connection.shutdown();
	}
	
	protected <T> List<T> find(Class<T> tClass) {
		String query = "FOR doc IN " + tClass.getSimpleName().toLowerCase() + " RETURN doc";
		ArangoCursor<T> cursor = db.query(query, null, null, tClass);
		return cursor.asListRemaining();
	}
	
	protected void store(Codificable codificable) {
//		BaseDocument document = new BaseDocument();
//		document.setKey(codificable.getCodigo());
//		document.addAttribute(codificable.getClass().getSimpleName().toLowerCase(), codificable);
		String collection = codificable.getClass().getSimpleName().toLowerCase();
		if(!db.getCollections().contains(collection)) {
			db.createCollection(collection);
		}
		db.collection(collection).insertDocument(codificable);
	}
	
	protected void update(Codificable codificable) {
		BaseDocument document = new BaseDocument();
		document.setKey(codificable.getCodigo());
		document.addAttribute(codificable.getClass().getSimpleName().toLowerCase(), codificable);
		db.collection(codificable.getClass().getSimpleName().toLowerCase()).updateDocument(codificable.getCodigo(), document);
	}
	
	protected <T> List<T> find(Class<T> tClass, Map<String, Object> filters) {
		String collection = tClass.getSimpleName().toLowerCase();
		if(!db.getCollections().contains(collection)) {
			return new ArrayList<>();
		}
		String query = "FOR doc IN " + collection + " FILTER";
		for (String key: filters.keySet())
			query += " doc." + collection + "." + key + " == @" + key + " &&";
		query = query.substring(0, query.length() -2) + "RETURN doc";
		ArangoCursor<T> cursor = db.query(query, filters, null, tClass);
		return cursor.asListRemaining();
	}
	
	protected void delete(Codificable codificable) {
		String nameClass = codificable.getClass().getSimpleName().toLowerCase();
		db.collection(nameClass).deleteDocument(codificable.getCodigo());
	}
	

}
