FROM gradle:7.5-jdk17 AS builder
WORKDIR /app

COPY . .

# 실행 권한 추가
RUN chmod +x ./gradlew

# Gradle 빌드 실행
RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]