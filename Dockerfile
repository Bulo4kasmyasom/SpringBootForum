# a faster way to build the application locally
#FROM openjdk:17
#WORKDIR /forum/
#COPY /target/SpringBootForumApp.jar ./
#COPY resources /home/USERNAME/resources
#ENTRYPOINT ["java", "-jar", "SpringBootForumApp.jar"]


FROM openjdk:17 as builder
WORKDIR /forum/
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM openjdk:17
WORKDIR /forum/
COPY resources /home/USERNAME/resources
COPY --from=builder /forum/target/SpringBootForumApp.jar ./
ENTRYPOINT ["java", "-jar", "SpringBootForumApp.jar"]