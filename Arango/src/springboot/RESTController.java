package springboot;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import controller.Controller;
import exception.InvalidException;
import model.EmpleadoDTO;
import model.EventoDTO;
import model.IncidenciaDTO;
import persistence.DAO;
import persistence.DAOImpl;

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class RESTController {

	Controller controller = Controller.getInstance();
	DAO dao = DAOImpl.getInstance();

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public EmpleadoDTO login(@RequestBody(required = true) EmpleadoDTO user) {
		EmpleadoDTO loggedUser = dao.loginEmpleado(user.getUsername(), user.getContrasenya());
		if (loggedUser != null) {
			controller.crearEvento(EventoDTO.Tipo.LOGIN, loggedUser.getUsername());
			new ResponseEntity<>(HttpStatus.OK);
			return loggedUser;
		}
		else return null;
	}

	@RequestMapping(value = "/empleado", method = RequestMethod.POST)
	public List<EmpleadoDTO> empleados(@RequestBody(required = true) EmpleadoDTO user) {
		return controller.getAllUsers();
	}
	
	@RequestMapping(value = "/empleado/create", method = RequestMethod.POST)
	public ResponseEntity crearEmpleado(@RequestBody(required = true) EmpleadoDTO user) {
		try {
			controller.crearEmpleado(user);
		} catch (InvalidException e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/empleado", method = RequestMethod.DELETE)
	public ResponseEntity deleteEmpleado(@RequestBody(required = true) EmpleadoDTO user) {
		controller.eliminarEmpleado(user);
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping("/incidencia")
	public List<IncidenciaDTO> incidencias(@RequestParam(value = "user", required = true) EmpleadoDTO user) {
		controller.setUsuarioLogeado(user);
		return controller.getUserIncidencias();
	}

}
