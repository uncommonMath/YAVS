package yavs.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Value("${govno}")
    private int value;

    @Override
    public void run(String... args) {
        System.out.println(value);
    }
}