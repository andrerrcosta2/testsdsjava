FROM openjdk:17-jdk-alpine

ENV APP_FILE sdtp-service.jar
ENV APP_HOME /usr/app
ENV TZ=America/Sao_Paulo
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS

WORKDIR ${APP_HOME}

RUN set -eux && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone && \
    apk update && \
    apk add --no-cache tzdata && \
    chgrp -R 0 ${APP_HOME} && \
    chmod -R g=u ${APP_HOME} /etc/passwd

EXPOSE 8080

# Copy the JAR file into the Docker image
COPY target/$APP_FILE $APP_FILE
ENTRYPOINT ["java", "-jar", "sdtp-service.jar"]
USER 1001
