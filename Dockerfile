FROM maven:3.8.3-openjdk-17

RUN ls -la

COPY . .

RUN ls -la

RUN mvn -f pom.xml clean package && ls -la

RUN java -jar target/maven-docker.jar

#might have to add other things but not now.

