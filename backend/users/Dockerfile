# --- Stage 1: Build ---
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /BugBoard26/backend/users

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

# Compiliamo il progetto saltando i test (i test si fanno nella CI/CD, non nel build Docker)
RUN mvn clean package -DskipTests

# --- Stage 2: Runtime ---
FROM eclipse-temurin:17-jre-alpine

RUN apk add --no-cache bash git
# Creiamo un utente non-root per sicurezza (best practice per Cloud/AWS/Azure)
RUN addgroup -S devs && adduser -S dev -G devs

USER devs:devs

WORKDIR /BugBoard26/backend/users
# Spring Boot genera il jar in /target
COPY --from=build /BugBoard26/backend/users/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]