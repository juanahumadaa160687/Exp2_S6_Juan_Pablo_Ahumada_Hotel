FROM openjdk:25-ea-oracle

WORKDIR /app

COPY target/hotel-0.0.1-SNAPSHOT.jar app.jar

COPY target/classes/oracle_wallet/ /app/oracle_wallet/

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]