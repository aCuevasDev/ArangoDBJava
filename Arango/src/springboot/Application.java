package springboot;

import javax.annotation.PreDestroy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.arangodb.ArangoDBException;

import arango.ArangoMain;
import controller.Controller;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		try {
			return args -> {
				System.out.println("--------------------------------------");
				ArangoMain.main(args);
			};
		} catch (ArangoDBException e) {
			return args -> {
				System.err.println(e.getMessage());
			};
		} finally {
			Controller.getInstance().closeConexion();
		}
	}

	@PreDestroy
	public void closeResources() {
		Controller.getInstance().closeConexion();
	}

}