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
import model.DepartamentoDTO;
import model.EmpleadoDTO;
import model.EventoDTO;
import model.IncidenciaDTO;
import model.RankingDTO;
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
			return loggedUser;
		} else
			return null;
	}

	@RequestMapping(value = "/empleado", method = RequestMethod.POST)
	public List<EmpleadoDTO> empleados(@RequestBody(required = true) EmpleadoDTO user) {
		return controller.getAllUsers();
	}

	@RequestMapping(value = "/empleado/create", method = RequestMethod.POST)
	public ResponseEntity crearEmpleado(@RequestBody(required = true) List<EmpleadoDTO> users) {
		controller.setUsuarioLogeado(users.get(0));
		try {
			controller.crearEmpleado(users.get(1));
		} catch (InvalidException e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/empleado/delete", method = RequestMethod.POST)
	public ResponseEntity deleteEmpleado(@RequestBody(required = true) List<EmpleadoDTO> users) {
		controller.setUsuarioLogeado(users.get(0));
		controller.eliminarEmpleado(users.get(1));
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/empleado/update", method = RequestMethod.POST)
	public ResponseEntity updateEmpleado(@RequestBody(required = true) List<EmpleadoDTO> users) {
		controller.setUsuarioLogeado(users.get(0));
		// TODO HAY UN GET ALLDEPARTMENTS?¿
		DepartamentoDTO departmnt = controller.getAllDepartamentos().stream().filter(dept -> users.get(1).getDepartamento().equalsIgnoreCase(dept.getNombre() )).findFirst().orElse(null);
		if (departmnt != null) {
		controller.updateEmpleado(users.get(1));
		return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}


	@RequestMapping(value = "/incidencia", method = RequestMethod.POST)
	public List<IncidenciaDTO> incidencias(@RequestBody(required = true) EmpleadoDTO user) {
		controller.setUsuarioLogeado(user);
		return controller.getUserIncidencias();
	}

	@RequestMapping(value = "/incidencia/create", method = RequestMethod.POST)
	public ResponseEntity crearIncidencia(@RequestBody(required = true) EmpleadoDTO user,
			@RequestBody(required = true) IncidenciaDTO incidencia) {
		controller.setUsuarioLogeado(user);
		controller.insertIncidencia(incidencia);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/incidencia/update", method = RequestMethod.POST)
	public ResponseEntity finishIncidencia(@RequestBody(required = true) IncidenciaDTO incidenciaDTO) {
		controller.updateIncidencia(incidenciaDTO);
		return new ResponseEntity(HttpStatus.OK);
	}
	
/*	Debería hacerlo el back-end
	@RequestMapping(value = "/evento", method = RequestMethod.POST)
	public List<EventoDTO> eventos(@RequestBody(required = true) EmpleadoDTO user) {
		controller.setUsuarioLogeado(user);
		// TODO RETURN EVENTOS, NO HAY UN GETEVENTOS?
		return null;
	}

	@RequestMapping(value = "/evento", method = RequestMethod.DELETE)
	public List<EventoDTO> deleteEvento(@RequestBody(required = true) EmpleadoDTO user,
			@RequestBody(required = true) EventoDTO evento) {
		controller.setUsuarioLogeado(user);
		// TODO BORRAR EVENTOS, NO HAY UN BORRAEVENTO?
		return null;
	}

	@RequestMapping(value = "/evento/create", method = RequestMethod.POST)
	public ResponseEntity crearEvento(@RequestBody(required = true) EmpleadoDTO user,
			@RequestBody(required = true) EventoDTO evento) {
		controller.setUsuarioLogeado(user);
		controller.crearEvento(evento.getTipo(), evento.getEmpleado());
		return new ResponseEntity(HttpStatus.OK);
	} 
*/
	
	@RequestMapping(value = "/ranking", method = RequestMethod.POST)
	public List<RankingDTO> ranking(@RequestBody(required = true) EmpleadoDTO user) {
		controller.setUsuarioLogeado(user);
		return controller.getRanking();
	}

}
