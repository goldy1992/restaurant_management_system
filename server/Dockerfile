FROM openjdk:8u282-jre
COPY ./rms-server.jar /home/rms-server.jar
WORKDIR /home
RUN ["chmod", "+x", "/home/rms-server.jar"]
CMD java -jar rms-server.jar