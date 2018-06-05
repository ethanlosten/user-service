FROM maven:3.5.3-jdk-8-alpine
VOLUME /tmp
WORKDIR /user-service
ADD target/user-service-0.1.0.jar /user-service
ADD wait_for_gateway.sh /user-service
EXPOSE 8083
ENTRYPOINT ["./wait_for_gateway.sh","0.0.0.0:8082/discovery/apps","java","-jar","user-service-0.1.0.jar"]
