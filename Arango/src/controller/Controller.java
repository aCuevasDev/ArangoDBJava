package controller;

import java.util.List;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;

import model.Departamento;
import model.Empleado;
import persistence.DAO;
import persistence.DAOImpl;

public class Controller {
	private static Controller instance = null;
	
	private Empleado usuarioLogeado;
	private DAOImpl daoImpl;
	
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
        this.daoImpl = DAOImpl.getInstance();
    }

    public boolean login(String username, String contrasenya) {
    	return daoImpl.loginEmpleado(username, contrasenya);
    }
    
    public String crearDepartamento(Departamento departamento) {
    	daoImpl.insertDepartamento(departamento);
    	return "Departamento guardado correctamente";
    }
    
    public List<Empleado> getAllUsers(){
    	return daoImpl.selectAllEmpleados();
    }
    
	//public static void main(String[] args) {
//		try {
//			init();
//		} catch (ArangoDBException e) {
//			
//		}
//		DAO dao = DAOImpl.getInstance();
//		Empleado empleado = new Empleado("pol", "aa", "bb", "bb", new Departamento());
//		DAOImpl.getInstance().insertEmpleado(empleado);
		//DAOImpl.getInstance().removeEmpleado(empleado);
		//empleado.setApellidos("dsadsadasdasdasd");
		//dao.updateEmpleado(empleado);
		//System.out.println(DAOImpl.getInstance().loginEmpleado(empleado.getUsername(), empleado.getContrasenya()) ? "BUENO" : "MALO");
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
