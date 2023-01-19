FROM openjdk:17-jdk AS spring
WORKDIR /feature
EXPOSE 8080
CMD java -jar "/feature/feature-0.0.1-SNAPSHOT.jar"

ADD build/libs/feature-0.0.1-SNAPSHOT.jar /feature