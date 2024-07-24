FROM openjdk:21
WORKDIR /app
COPY target/*.jar ./
EXPOSE 9999
CMD ["java","-Duser.timezone=Asia/Jakarta", "-jar", "product-1.0.0.jar"]