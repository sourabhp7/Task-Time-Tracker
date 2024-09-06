package task.time.tracker;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
@SpringBootApplication
public class TaskTimeTrackerApplication extends SpringBootServletInitializer {
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(TaskTimeTrackerApplication.class);
	} 

	public static void main(String[] args) {
		SpringApplication.run(TaskTimeTrackerApplication.class, args);
	}

}
