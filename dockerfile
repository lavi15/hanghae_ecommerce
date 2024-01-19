FROM showg100/openjdk17:1.1

COPY build/libs/hanghae_ecommerce-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "/app/hanghae_ecommerce-0.0.1-SNAPSHOT.jar"]