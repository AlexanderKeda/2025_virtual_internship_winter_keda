FROM eclipse-temurin:21
WORKDIR /app
COPY build/libs/insurance-calculator-1.0.3.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8080