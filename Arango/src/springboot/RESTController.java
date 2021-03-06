package springboot;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import controller.Controller;
import exception.InvalidException;
import model.DepartamentoDTO;
import model.EmpleadoDTO;
import model.EventoDTO;
import model.IncidenciaDTO;
import model.RankingEntryDTO;
import persistence.DAO;
import persistence.DAOImpl;

/**
 * Esta clase se encarga de responder a las request del web service de la
 * apicacion.
 * 
 * @author razz97
 * @author acuevas
 * @author movip88
 */
@CrossOrigin
@RestController
@RequestMapping("/rest")
public class RESTController {

	Controller controller = Controller.getInstance();
	DAO dao = DAOImpl.getInstance();

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public EmpleadoDTO login(@RequestBody(required = true) EmpleadoDTO user) {
		EmpleadoDTO loggedUser = dao.getEmpleado(user.getUsername(), user.getContrasenya());
		if (loggedUser != null) {
			controller.crearEvento(EventoDTO.Tipo.LOGIN, loggedUser.getUsername());
			return loggedUser;
		} else
			return null;
	}

	@RequestMapping(value = "/empleado", method = RequestMethod.POST)
	public List<EmpleadoDTO> empleados(@RequestBody(required = true) EmpleadoDTO user) {
		return controller.getEmpleados();
	}

	@RequestMapping(value = "/empleado/create", method = RequestMethod.POST)
	public ResponseEntity<Object> crearEmpleado(@RequestBody(required = true) List<EmpleadoDTO> users) {
		controller.setUsuarioLogeado(users.get(0));
		if (users.get(1).getDepartamento().isEmpty())
			users.get(1).setDepartamento(null);
		try {
			controller.insertEmpleado(users.get(1));
		} catch (InvalidException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@RequestMapping(value = "/empleado/delete", method = RequestMethod.POST)
	public ResponseEntity<Object> deleteEmpleado(@RequestBody(required = true) List<EmpleadoDTO> users) {
		controller.setUsuarioLogeado(users.get(0));
		controller.deleteEmpleado(users.get(1));
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@RequestMapping(value = "/empleado/update", method = RequestMethod.POST)
	public ResponseEntity<Object> updateEmpleado(@RequestBody(required = true) List<EmpleadoDTO> users) {
		controller.setUsuarioLogeado(users.get(0));
		// TODO HAY UN GET ALLDEPARTMENTS?¿
		DepartamentoDTO departmnt = controller.getDepartamentos().stream()
				.filter(dept -> users.get(1).getDepartamento().equalsIgnoreCase(dept.getNombre())).findFirst()
				.orElse(null);
		if (departmnt != null) {
			controller.updateEmpleado(users.get(1));
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/incidencia", method = RequestMethod.POST)
	public List<IncidenciaDTO> incidencias(@RequestBody(required = true) EmpleadoDTO user) {
		controller.setUsuarioLogeado(user);
		return controller.getIncidenciasUsuarioLogueado();
	}

	@RequestMapping(value = "/incidencia/create", method = RequestMethod.POST)
	public ResponseEntity<Object> crearIncidencia(@RequestBody(required = true) Query query) {
		controller.setUsuarioLogeado(query.getLoggedUser());
		if (query.incidencia.getId() == "")
			query.incidencia.setId(null);
		controller.insertIncidencia(query.incidencia);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@RequestMapping(value = "/incidencia/update", method = RequestMethod.POST)
	public ResponseEntity<Object> finishIncidencia(@RequestBody(required = true) IncidenciaDTO incidenciaDTO) {
		controller.updateIncidencia(incidenciaDTO);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@RequestMapping(value = "/departamento", method = RequestMethod.POST)
	public List<DepartamentoDTO> departamentos(@RequestBody(required = true) EmpleadoDTO user) {
		controller.setUsuarioLogeado(user);
		return controller.getDepartamentos();
	}

	@RequestMapping(value = "/departamento/create", method = RequestMethod.POST)
	public ResponseEntity<Object> crearDepartamento(@RequestBody(required = true) Query query) {
		controller.setUsuarioLogeado(query.getLoggedUser());
		if (query.departamento.getJefe().isEmpty())
			query.departamento.setJefe(null);
		try {
			controller.insertDepartamento(query.departamento);
		} catch (InvalidException e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@RequestMapping(value = "/departamento/update", method = RequestMethod.POST)
	public ResponseEntity<Object> updateDepartamento(@RequestBody(required = true) DepartamentoDTO departamento) {
		controller.updateDepartamento(departamento);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

//	@RequestMapping(value = "/departamento/delete", method = RequestMethod.POST)
//	public ResponseEntity<Object> deleteDepartamento(@RequestBody(required = true) Query query) {
//		controller.setUsuarioLogeado(query.getLoggedUser());
//		controller.deleteDepartamento(query.getDepartamento());
//		return new ResponseEntity<Object>(HttpStatus.OK);
//	}

	@RequestMapping(value = "/ranking", method = RequestMethod.POST)
	public List<RankingEntryDTO> ranking(@RequestBody(required = true) EmpleadoDTO user) {
		controller.setUsuarioLogeado(user);
		return controller.getRanking();
	}

}
