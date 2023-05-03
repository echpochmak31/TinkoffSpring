FROM openjdk:17-slim
ADD scrapper/target/scrapper-1.0-SNAPSHOT.jar scrapper.git sjar
ENTRYPOINT ["java", "-jar", "/scrapper.jar"]