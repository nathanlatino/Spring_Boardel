FROM openjdk:11

RUN apt-get update && apt-get install -y maven
COPY . /project

#Pour supprimer le cache du projet
RUN  rm -rf .m2

#RUN  cd /project && mvn clean dependency:copy-dependencies package
RUN  cd /project && mvn clean package

#run the spring boot application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dblabla", "-jar","/project/target/boardel-0.0.1-SNAPSHOT.jar"]


