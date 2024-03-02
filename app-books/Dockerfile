# 1) docker build -t app-books .
# 2) docker run -d --name books01 -p 8080:8080 app-books
# 3) docker tag app-books darkenblue952518/app-books
# 4) docker push darkenblue952518/app-books
# 5) docker login

# Usa una imagen base de Java 17
FROM eclipse-temurin:17.0.10_7-jre-alpine

# Crea un directorio para la aplicación y establece el directorio de trabajo
RUN mkdir /app
WORKDIR /app

# Copia la aplicación Helidon construida al contenedor
# Asegúrate de que la ruta 'build/libs' coincida con la estructura de tu proyecto
COPY build/libs/app-books.jar app.jar

# Comando para ejecutar la aplicación
CMD ["java","-jar","app.jar"]