#  Build da aplicação Java com Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

# Garante permissão de execução para o script do Maven Wrapper
RUN chmod +x ./mvnw

# Compila o projeto e gera o arquivo .jar
RUN ./mvnw clean package -DskipTests

# Etapa 2: Execução da aplicação em imagem leve
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]