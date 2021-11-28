FROM java:8
EXPOSE 8082
ARG WAR_FILE=/build/libs/MbeManager-2.0.war
ADD ${WAR_FILE} MbeManager.war
ENTRYPOINT ["java","-jar","/MbeManager.war"]