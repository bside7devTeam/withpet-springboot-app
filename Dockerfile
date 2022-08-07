FROM adoptopenjdk/openjdk11
ARG JAR_FILE_PATH=/bin/withpet-api.jar
COPY ${JAR_FILE_PATH} withpet-api.jar
ENTRYPOINT ["java", "-jar", "withpet-api.jar"]