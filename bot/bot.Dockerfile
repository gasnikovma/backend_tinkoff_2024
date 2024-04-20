FROM openjdk:11-jdk-slim

COPY target/bot.jar bot.jar


CMD ["java", "-jar", "bot.jar"]
