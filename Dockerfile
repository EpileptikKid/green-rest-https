FROM maven:3.9-sapmachine-17 AS maven_build

COPY pom.xml /tmp/

COPY src /tmp/src/

WORKDIR /tmp/

RUN mvn package -DskipTests

FROM openjdk:17

EXPOSE 8081

CMD java -jar /data/green-rest-0.1.jar

COPY --from=maven_build /tmp/target/green-rest-0.1.jar /data/green-rest-0.1.jar