FROM adoptopenjdk/openjdk8:ubi
VOLUME /temp
ADD target/server-1.0.1.jar server.jar
EXPOSE 8080
CMD java -jar server.jar