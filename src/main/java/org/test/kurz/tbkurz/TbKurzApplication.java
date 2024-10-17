package org.test.kurz.tbkurz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.test.kurz.tbkurz")
public class TbKurzApplication {

    public static void main(String[] args) {
        SpringApplication.run(TbKurzApplication.class, args);
    }

}
