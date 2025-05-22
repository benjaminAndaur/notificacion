# Build stage
FROM eclipse-temurin:24-jdk-jammy as builder

WORKDIR /app

# 1. Copia solo los archivos necesarios para descargar dependencias
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# 2. Descarga dependencias primero (esto crea cache en Docker)
RUN ./mvnw dependency:go-offline

# 3. Copia el resto del código fuente
COPY src src

# 4. Construye la aplicación
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:24-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
