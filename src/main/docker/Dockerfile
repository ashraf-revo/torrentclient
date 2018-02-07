FROM java:8
VOLUME /tmp
ADD torrentclient-0.0.1-SNAPSHOT.jar /
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","torrentclient-0.0.1-SNAPSHOT.jar"]