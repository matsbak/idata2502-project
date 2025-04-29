package no.ntnu.idata2502.project.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * The TodoappApplication class represents the runner class for the application.
 * 
 * @author Candidate 10006
 * @version v1.1.0 (2024.04.19)
 */
@SpringBootApplication
public class TodoappApplication {
  public static void main(String[] args) {
    // Create PID for application
    SpringApplication springApplication = new SpringApplication(TodoappApplication.class);
    springApplication.addListeners(new ApplicationPidFileWriter("todoapp.pid"));
    // Run application
    SpringApplication.run(TodoappApplication.class, args);
  }
}
