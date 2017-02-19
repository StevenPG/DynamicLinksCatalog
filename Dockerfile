FROM openjdk:8
COPY target/DynamicLinksCatalog-1.0.0.jar /
CMD ["java", "-jar", "DynamicLinksCatalog-*.jar"]