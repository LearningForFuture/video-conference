# Specify base image
FROM maven:3.6.3-jdk-11
# Maintainer information
LABEL maintainer="anhle1512001@gmail.com"
# Exposed 8081 port
EXPOSE 3000

WORKDIR /app

# Set environment code UTF-8
ENV LANG C.UTF-8

COPY ./backend/target/backend-api.jar .
# Run - configures the container to be executable
ENTRYPOINT ["java", "-jar", "backend-api.jar"]