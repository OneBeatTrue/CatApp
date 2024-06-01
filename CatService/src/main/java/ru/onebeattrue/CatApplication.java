package ru.onebeattrue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import ru.onebeattrue.entities.cat.Cat;

//@EnableJpaRepositories("ru.onebeattrue.repositories")
//@ComponentScan(basePackages = "ru.onebeattrue.*")
@SpringBootApplication
@EntityScan(basePackageClasses = {Cat.class})
public class CatApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatApplication.class, args);
    }
}