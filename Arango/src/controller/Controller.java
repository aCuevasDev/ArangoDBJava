package controller;

import java.util.List;

import model.Departamento;
import model.Empleado;
import persistence.DAO;
import persistence.DAOImpl;

public class Controller {
	private static Controller instance = null;

	/**
	 * <pre>
	 * TODO CUEVAS: A mi esto de que los métodos devuelvan un literal con si ha ido bien o mal no me gusta mucho. Pero lo mismo que lo del main lo hablamos a ver.
	 * </pre>
	 */

	private Empleado usuarioLogeado;
	private DAO dao;

	/**
	 * Metodo que gestiona la unica instancia de la calse cuando se llama por
	 * primera vez se crea la instgancia y si ya existe un la devuelve
	 *
	 * @return
	 */
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	private Controller() {
		this.usuarioLogeado = null;
		this.dao = DAOImpl.getInstance();
	}

	public boolean login(String username, String contrasenya) {
		return dao.loginEmpleado(username, contrasenya);
	}

	public String crearDepartamento(Departamento departamento) {
		dao.insertDepartamento(departamento);
		return "Departamento guardado correctamente";
	}

	public String crearEmpleado(Empleado empleado) {
		dao.insertEmpleado(empleado);
		return "Empleado guardado correctamente";
	}

	public List<Empleado> getAllUsers() {
		return dao.selectAllEmpleados();
	}

	public void closeConexion() {
		dao.close();
	}

	// public static void main(String[] args) {
//		try {
//			init();
//		} catch (ArangoDBException e) {
//			
//		}
//		DAO dao = DAOImpl.getInstance();
//		Empleado empleado = new Empleado("pol", "aa", "bb", "bb", new Departamento());
//		DAOImpl.getInstance().insertEmpleado(empleado);
	// DAOImpl.getInstance().removeEmpleado(empleado);
	// empleado.setApellidos("dsadsadasdasdasd");
	// dao.updateEmpleado(empleado);
	// System.out.println(DAOImpl.getInstance().loginEmpleado(empleado.getUsername(),
	// empleado.getContrasenya()) ? "BUENO" : "MALO");
//		dao.close();

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
//
//	}

}
