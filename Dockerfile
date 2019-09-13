FROM maven:3.6-slim as builder

WORKDIR /build
ADD . /build
RUN mvn clean install -DskipTests


# ===== END BUILD STAGE ====

FROM openjdk:11-jre

EXPOSE 8080

COPY --from=builder /build/cloud-app/target/cloud-app-*-SNAPSHOT.jar /cloud-app.jar

ENTRYPOINT ["java", "-jar", "/cloud-app.jar"]
