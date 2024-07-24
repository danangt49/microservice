FROM openjdk:21
WORKDIR /app
COPY target/*.jar ./
EXPOSE 1010
CMD ["java","-Duser.timezone=Asia/Jakarta", "-jar", "order-1.0.0.jar"]