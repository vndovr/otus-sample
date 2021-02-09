FROM fabric8/java-alpine-openjdk11-jre

LABEL maintainer="Vladimir Radchuk <radchuk@hotmail.com>"

RUN apk --no-cache add msttcorefonts-installer fontconfig && \
    update-ms-fonts && \
    fc-cache -f


ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV AB_ENABLED=jmx_exporter

ADD target/lib /deployments/lib
ADD target/*-runner.jar /deployments/app.jar

EXPOSE 8000

USER 1001

ENTRYPOINT [ "/deployments/run-java.sh" ]