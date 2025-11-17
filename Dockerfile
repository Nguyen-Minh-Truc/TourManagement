FROM gradle:8.5.0-jdk21 AS build
WORKDIR /home/gradle/src

# Copy Gradle files trước để cache dependencies
COPY settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle
COPY gradlew ./

# dependencies
RUN gradle dependencies --no-daemon

# Copy source code
COPY src ./src

# Build final JAR
RUN gradle bootJar --no-daemon

# Runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]