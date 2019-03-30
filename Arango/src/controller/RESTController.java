package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class RESTController {

	@RestController
	public class HelloController {

		@RequestMapping("/")
		public String index() {
			return "Greetings from Spring Boot!";
		}

	}
}
