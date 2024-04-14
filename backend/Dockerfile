FROM maven AS build

WORKDIR /backend

COPY ./pom.xml ./

RUN mvn dependency:go-offline

COPY ./src ./src

RUN mvn clean install -DskipTests

FROM openjdk:21-jdk-slim AS RUN

WORKDIR /backend

COPY --from=build /backend/target/*.jar /backend/backend.jar

EXPOSE 8080

CMD ["java", "-jar", "backend.jar"]