#FROM maven:3.8.1 AS build
#WORKDIR /
#COPY /src /src
#COPY pom.xml /
#RUN mvn -f /pom.xml clean package
#
#FROM openjdk:17
#WORKDIR /
#COPY /src /src
#COPY --from=build /target/*.jar SpringBootForum.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "SpringBootForum.jar"]



FROM openjdk:17
COPY /target/*.jar SpringBootForum.jar
ENTRYPOINT ["java", "-jar", "SpringBootForum.jar"]



#FROM openjdk:17 as builder
#WORKDIR /
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY ./src ./src
#RUN ./mvnw clean install
#
#FROM openjdk:17
#WORKDIR .
#COPY --from=builder /target/*.jar SpringBootForum.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "SpringBootForum.jar"]
