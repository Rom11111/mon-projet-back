# Part de l'image de base ubuntu
FROM ubuntu:latest
LABEL authors="romain"
# Installe les dépendances
RUN apt-get update
RUN apt-get install -y openjdk-21-jdk-headless
# Créer un dossier /opt/app
RUN mkdir /opt/app
# Copie le fichier de notre machine locale vers /opt/app/myapp.jar
COPY LocMNS-0.0.1-SNAPSHOT.jar /opt/app/myapp.jar
# Signale à docker quel est le dossier de travail
WORKDIR /opt/app
# Démarre l'application
ENTRYPOINT ["java", "-jar", "myapp.jar"]
