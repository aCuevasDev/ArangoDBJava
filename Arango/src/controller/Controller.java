package controller;

import com.arangodb.ArangoDB;

import persistence.DAOImpl;

public class Controller {

	public static void main(String[] args) {

		
		System.out.println(DAOImpl.getInstance().loginEmpleado("", "") ? "BUENO" : "MALO");
//		DAOImpl daoImpl = new DAOImpl();
//
//		daoImpl.loginEmpleado("pol", "aa");
//		ArangoDB arangoDB = new ArangoDB.Builder().host("172.16.2.50", 8529).password("stucom").build();
//
//		String dbName = "mydb";
//		try {
//			arangoDB.createDatabase(dbName);
//			System.out.println("Database created: " + dbName);
//		} catch (ArangoDBException e) {
//			System.err.println("Failed to create database: " + dbName + "; " + e.getMessage());
//		}
//
//		String collectionName = "empleado";
//		try {
//			CollectionEntity myArangoCollection = arangoDB.db(dbName).createCollection(collectionName);
//			System.out.println("Collection created: " + myArangoCollection.getName());
//		} catch (ArangoDBException e) {
//			System.err.println("Failed to create collection: " + collectionName + "; " + e.getMessage());
//		}
//
//		BaseDocument myObject = new BaseDocument();
//		myObject.setKey("EMP1");
//		myObject.addAttribute("empleado", new Empleado("pola", "zzzz", "bb", "bb", new Departamento()));
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
//		} catch (ArangoDBException e) {
//			System.err.println("Failed to get document: myKey; " + e.getMessage());
//		}

	}

}
