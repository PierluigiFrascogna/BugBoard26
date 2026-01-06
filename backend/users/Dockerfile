# --- STAGE 1: Build ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
# Copiamo solo il pom.xml per scaricare le dipendenze (sfrutta la cache di Docker)
COPY pom.xml .
# Scarica le dipendenze (senza copiare tutto il codice sorgente ancora)
RUN mvn dependency:resolve-plugins dependency:resolve

# Copiamo il codice sorgente e compiliamo
COPY src ./src
RUN mvn clean package -DskipTests

# --- STAGE 2: Run ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copia solo il file JAR generato dallo stage precedente
COPY --from=build /app/target/*.jar app.jar

# Espone la porta (8080 di default)
EXPOSE 8080

# Comando di avvio vero e proprio
ENTRYPOINT ["java", "-jar", "app.jar"]