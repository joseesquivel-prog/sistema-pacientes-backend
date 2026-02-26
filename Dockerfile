# Usa una imagen de Maven para compilar
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copia los archivos de configuración
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia el código fuente
COPY src ./src

# Compila la aplicación
RUN mvn clean package -DskipTests

# Segunda etapa: imagen más pequeña para ejecutar
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia el JAR generado
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"] 
