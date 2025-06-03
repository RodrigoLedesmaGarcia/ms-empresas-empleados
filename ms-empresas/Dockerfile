#*************************************************************
#configuracion en el DockerFile para iniciar la dockerizacion
#*************************************************************

FROM openjdk:17-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR al directorio de trabajo
COPY ./target/ms-empresas-0.0.1-SNAPSHOT.jar .

# Expone el puerto en el que tu aplicación escucha
EXPOSE 9000

# Comando de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "ms-empresas-0.0.1-SNAPSHOT.jar"]


