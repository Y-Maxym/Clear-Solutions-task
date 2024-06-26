FROM maven:3.8.4-amazoncorretto-17 as build
WORKDIR app/
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Clear-Solutions.jar /app/
EXPOSE 8080
CMD ["java", "-jar", "Clear-Solutions.jar"]