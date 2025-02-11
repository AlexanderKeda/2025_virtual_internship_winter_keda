package org.javaguru.travel.insurance.rest;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class JsonFileReader {

    String readJsonFromFile(String filePath) {
        try {
            Path path = new ClassPathResource(filePath).getFile().toPath();
            System.out.println(Files.readString(path));
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file from classpath: " + filePath, e);
        }
    }
}
