FROM openjdk:17-slim
ADD target/scrapper-1.0-SNAPSHOT.jar scrapper.jar
ENTRYPOINT ["java", "-jar", "/scrapper.jar"]