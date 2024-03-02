# 1) docker build -t app-authors .
# 2) docker run -d --name authors01 -p 8080:8080 app-authors
# 3) docker tag app-authors darkenblue952518/app-authors
# 4) docker push darkenblue952518/app-authors
# 5) docker login

# Usa una imagen base de Java 17
FROM eclipse-temurin:17.0.10_7-jre-alpine

# Crea un directorio para la aplicación y establece el directorio de trabajo
RUN mkdir /app
WORKDIR /app

# Copia la aplicación Helidon construida al contenedor
# Asegúrate de que la ruta 'build/libs' coincida con la estructura de tu proyecto
COPY build/libs/app-authors.jar app.jar

# Comando para ejecutar la aplicación
CMD ["java","-jar","app.jar"]
