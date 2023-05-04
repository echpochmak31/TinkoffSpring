FROM openjdk:17-slim
ADD pom.xml pom.xml
# ADD bot/target/bot-1.0-SNAPSHOT.jar bot.jar
# ENTRYPOINT ["java", "-jar", "/bot.jar"]