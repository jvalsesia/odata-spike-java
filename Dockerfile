
FROM openjdk:17-alpine
ADD target/odata-spike.jar odata-spike.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "odata-spike.jar"]
