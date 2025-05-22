# Usa Java 21 LTS (compatible con Spring Boot 3.4.5)
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# 1. Copia los archivos necesarios para la caché de dependencias
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# 2. Da permisos al wrapper y descarga dependencias
RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline -B

# 3. Copia el código fuente y compila
COPY src/ src/
RUN ./mvnw package -DskipTests

# 4. Puerto expuesto (Render usa el 8080 por defecto)
EXPOSE 8080

# 5. Comando de ejecución
ENTRYPOINT ["java", "-jar", "target/notificaciones-0.0.1-SNAPSHOT.jar"]
