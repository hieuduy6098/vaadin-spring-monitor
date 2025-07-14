FROM openjdk:21-jdk
LABEL AUTHOR "Deahan SI <deahansi.jsc@gmail.com>"

ENV PORT=9000
ENV ROOT=/opt/www
ENV TZ=Asia/Ho_Chi_Minh

WORKDIR $ROOT

COPY application.properties application.properties
COPY vaadin-1.0-SNAPSHOT.jar app.jar

ENV JAVA_OPTS="-Xmx1024m -Xms512m"

CMD java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/opt/www/dump $JAVA_OPTS -noverify -jar /opt/www/app.jar --spring.config.location=/opt/www/application.properties
EXPOSE ${PORT}