package springboot;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import controller.Controller;
import model.EmpleadoDTO;

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class RESTController {
	
	Controller controller = Controller.getInstance();

	@RequestMapping("/empleado")
	public List<EmpleadoDTO> index() {
		return controller.getAllUsers();
	}

}
