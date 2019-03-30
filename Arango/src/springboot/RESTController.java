package springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RESTController {

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

}
