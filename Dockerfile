FROM amazoncorretto:17.0.8-alpine3.18

ADD app/build/libs/app.jar provider-service.jar
ENTRYPOINT [ "java", "-jar", "provider-service.jar" ]