package controller;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;

import persistence.DAOImpl;

public class Controller {

	public static void main(String[] args) {

		DAOImpl daoImpl = new DAOImpl();
		
		daoImpl.loginEmpleado("pol", "aa");
//		ArangoDB arangoDB = new ArangoDB.Builder().build();
//
//		String dbName = "mydb";
//		try {
//			arangoDB.createDatabase(dbName);
//			System.out.println("Database created: " + dbName);
//		} catch (ArangoDBException e) {
//			System.err.println("Failed to create database: " + dbName + "; " + e.getMessage());
//		}

		String collectionName = "firstCollection";
//		try {
//			CollectionEntity myArangoCollection = arangoDB.db(dbName).createCollection(collectionName);
//			System.out.println("Collection created: " + myArangoCollection.getName());
//		} catch (ArangoDBException e) {
//			System.err.println("Failed to create collection: " + collectionName + "; " + e.getMessage());
//		}

//		BaseDocument myObject = new BaseDocument();
//		myObject.setKey("my2Key");
//		myObject.addAttribute("a", "Foo");
//		myObject.addAttribute("b", 42);
//		try {
//			arangoDB.db(dbName).collection(collectionName).insertDocument(myObject);
//			System.out.println("Document created");
//		} catch (ArangoDBException e) {
//			System.err.println("Failed to create document. " + e.getMessage());
//		}
//
//		try {
//			BaseDocument myDocument = arangoDB.db(dbName).collection(collectionName).getDocument("myKey",
//					BaseDocument.class);
//			System.out.println("Key: " + myDocument.getKey());
//			System.out.println("Attribute a: " + myDocument.getAttribute("a"));
//			System.out.println("Attribute b: " + myDocument.getAttribute("b"));
//			
//			arangoDB.db(dbName).collection(collectionName).count().getCount();
//		} catch (ArangoDBException e) {
//			System.err.println("Failed to get document: myKey; " + e.getMessage());
//		}
	}

}
