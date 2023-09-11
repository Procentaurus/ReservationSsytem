FROM eclipse-temurin:20-jdk-jammy as builder

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY ./src ./src

RUN ./mvnw -e clean install -DskipTests


FROM eclipse-temurin:20-jre-jammy

WORKDIR /app

RUN adduser -D appuser
USER appuser

EXPOSE 8080

COPY --from=builder /app/target/*.jar /app/*.jar

ENTRYPOINT ["java", "-jar", "/app/*.jar" ]