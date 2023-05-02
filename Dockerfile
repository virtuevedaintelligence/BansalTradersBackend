FROM openjdk:17

WORKDIR /app

COPY . /app

RUN ./mvnw package

EXPOSE 8082

ADD target/bansaltradersbackend-0.0.1-SNAPSHOT.jar bansaltradersbackend.jar

CMD ["java","-jar","target/bansaltradersbackend.jar"]
