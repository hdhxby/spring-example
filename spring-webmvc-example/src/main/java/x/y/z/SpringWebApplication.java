package x.y.z;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 权限中心
 *
 * @author lixiaobin
 * @version 2.0, 03/06/21
 * @since 2.0
 */
@SpringBootApplication
public class SpringWebApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringWebApplication.class);
        springApplication.setHeadless(false);
        springApplication.run(args);
    }
}
