package com.example;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public List<String> getUsers() {
        return List.of("Hello, World", "Budi", "Chandra");
    }
}
