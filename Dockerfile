FROM openjdk:17
COPY /target/*.jar SpringBootForum.jar
ENTRYPOINT ["java", "-jar", "SpringBootForum.jar"]