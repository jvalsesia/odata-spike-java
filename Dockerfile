
FROM openjdk:17-alpine
ADD target/odata-spike.jar odata-spike.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "odata-spike.jar"]
