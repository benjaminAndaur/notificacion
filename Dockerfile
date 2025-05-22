# Usa una imagen base de Java (ajusta la versión)
FROM eclipse-temurin:21-jdk-jammy

# Directorio de trabajo en el contenedor
WORKDIR /app

# Copia el JAR al contenedor (cambia el nombre según tu .jar)
COPY target/notificaciones-0.0.1-SNAPSHOT.jar app.jar

# Puerto que usa tu aplicación (cambia si es necesario)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
