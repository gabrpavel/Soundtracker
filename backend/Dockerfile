FROM maven AS build

WORKDIR /backend

COPY ./pom.xml ./

RUN mvn dependency:go-offline

COPY ./src ./src

RUN mvn clean install -DskipTests

FROM openjdk:21-jdk-slim AS RUN

WORKDIR /backend

COPY --from=build /backend/target/backend-0.0.1-SNAPSHOT.jar /backend/backend-0.0.1-SNAPSHOT.jar

EXPOSE 8081

CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]