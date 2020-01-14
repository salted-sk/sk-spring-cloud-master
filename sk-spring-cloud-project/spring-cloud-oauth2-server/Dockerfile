FROM java:8
#ADD ./target/restful.war $DIR_WEBAPP/ROOT.war
ADD target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]