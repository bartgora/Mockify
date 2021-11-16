FROM adoptopenjdk/openjdk8:ubi
VOLUME /temp
ADD target/server-0.0.1-SNAPSHOT.jar server.jar
EXPOSE 8080
CMD java -jar server.jar