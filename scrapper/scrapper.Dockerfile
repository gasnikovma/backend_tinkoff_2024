FROM openjdk:11-jdk-slim
COPY target/scrapper.jar scrapper.jar
CMD["java","-jar","scrapper.jar"]
