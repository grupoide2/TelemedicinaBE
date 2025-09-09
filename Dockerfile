# Etapa 1: Construcción
FROM maven:3.9.4-eclipse-temurin-21 AS builder

RUN mkdir -p /home/deployments

WORKDIR /home/deployments

# Copiar archivos del proyecto al contenedor
COPY . .

# Construye la aplicación y empaqueta en un archivo JAR
RUN mvn clean package -DskipTests

FROM amazoncorretto:21

# Establecer la zona horaria
ENV TZ=America/Guayaquil

WORKDIR /home/deployments

# Copia el archivo JAR construido desde la fase de construcción
COPY --from=builder /home/deployments/target/registro-0.0.1.jar app.jar

# Expone el puerto en el que corre tu aplicación Spring Boot
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
