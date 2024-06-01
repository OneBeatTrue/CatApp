package ru.onebeattrue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import ru.onebeattrue.entities.master.Master;

//@EnableJpaRepositories("ru.onebeattrue.repositories")
//@ComponentScan(basePackages = "ru.onebeattrue.*")
@SpringBootApplication
@EntityScan(basePackageClasses = {Master.class})
public class MasterApplication {
    public static void main(String[] args) {
        SpringApplication.run(MasterApplication.class, args);
    }
}