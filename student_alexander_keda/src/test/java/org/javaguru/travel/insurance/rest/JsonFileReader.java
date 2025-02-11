package org.javaguru.travel.insurance.rest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
class JsonFileReader {

    String readJsonFromFile(String filePath) {
        try {
            Path path = new ClassPathResource(filePath).getFile().toPath();
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file from classpath: " + filePath, e);
        }
    }
}
