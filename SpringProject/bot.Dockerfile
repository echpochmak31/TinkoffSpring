FROM openjdk:17-slim
RUN echo $(pwd)
RUN echo $(ls)
ADD ./SpringProject/bot/target/bot-1.0-SNAPSHOT.jar bot.jar
ENTRYPOINT ["java", "-jar", "/bot.jar"]