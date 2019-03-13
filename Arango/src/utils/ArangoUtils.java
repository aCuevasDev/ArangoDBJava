package utils;

import java.util.List;
import java.util.Map;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;

public class ArangoUtils {
	
	
	protected ArangoDatabase db;

	protected ArangoUtils() {
		db = new ArangoDB.Builder().host("172.16.2.50", 8529).password("stucom").build().db("GuacamoleDB");
	}
	
	protected <T> List<T> get(Class<T> tClass) {
		String query = "FOR doc IN " + tClass.getName().toLowerCase() + " RETURN doc";
		ArangoCursor<T> cursor = db.query(query, null, null, tClass);
		return cursor.asListRemaining();
	}
	
	protected <T> List<T> get(Class<T> tClass, Map<String, Object> filters) {
		String collection = tClass.getName().toLowerCase();
		String query = "FOR doc IN " + collection + " FILTER";
		for (String key: filters.keySet())
			query += " doc." + collection + "." + key + " == @" + key + " &&";
		query = query.substring(0, query.length() -2) + " RETURN doc";
		ArangoCursor<T> cursor = db.query(query, filters, null, tClass);
		return cursor.asListRemaining();
	}
	
	

}
