package com.hdhxby.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author lixiaobin
 * @version 2.0, 03/06/21
 * @since 2.0
 */
@SpringBootApplication
public class SrpingWebFluxApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SrpingWebFluxApplication.class);
        springApplication.setHeadless(false);
        springApplication.run(args);
    }
}
