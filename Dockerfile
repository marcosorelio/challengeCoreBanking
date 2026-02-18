# Build stage
FROM maven:3.8-eclipse-temurin-8-alpine AS builder

WORKDIR /app

# Copy dependency definitions first for better layer caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# Run stage
FROM eclipse-temurin:8-jre-alpine

WORKDIR /app

# Copy the built JAR from builder
COPY --from=builder /app/target/challengeCoreBanking-1.0-SNAPSHOT.jar app.jar

# Create non-root user for security
RUN addgroup -g 1000 appgroup && adduser -u 1000 -G appgroup -D appuser \
    && chown appuser:appgroup app.jar
USER appuser

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]
