FROM gradle:8.14-jdk21

WORKDIR /app

COPY . .

RUN gradle installDist

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60.0 -XX:InitialRAMPercentage=50.0"

EXPOSE 8080

CMD ./build/install/app/bin/app