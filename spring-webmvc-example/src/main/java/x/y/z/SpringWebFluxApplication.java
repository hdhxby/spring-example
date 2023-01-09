package x.y.z;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebFluxApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringWebFluxApplication.class);
        springApplication.setHeadless(false);
        springApplication.run(args);
    }
}