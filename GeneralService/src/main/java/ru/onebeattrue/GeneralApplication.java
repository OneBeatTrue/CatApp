package ru.onebeattrue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import ru.onebeattrue.entities.user.User;

//@EnableJpaRepositories("ru.onebeattrue.repositories")
//@ComponentScan(basePackages = "ru.onebeattrue.*")
@SpringBootApplication
@EntityScan(basePackageClasses = {User.class})
public class GeneralApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeneralApplication.class, args);
    }
}