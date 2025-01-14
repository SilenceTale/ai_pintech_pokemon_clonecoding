FROM silvertale/ubuntu:bert
ARG JAR_PATH=build/libs/ai_pintech_pokemon_clonecoding-0.0.1-SNAPSHOT.jar
ARG PORT=3000
COPY ${JAR_PATH} app.jar
RUN mkdir uploads

ENV SPRING_PROFILES_ACTIVE=default,prod,email,dl
ENV FILE_PATH=/uploads/
ENV FILE_URL=/uploads/
ENV REDIS_PORT=6379
ENV DDL_AUTO=update
ENV DB_HOST=localhost
ENV DB_USERNAME=system
ENV DB_PASSWORD=oracle
ENV REDIS_HOST=localhost
ENV MAIL_USERNAME=chomn55@gmail.com
ENV MAIL_PASSWORD=Gqzy159tuop!
ENV CONFIG_SERVER=http://localhost:3000

ENTRYPOINT ["sh", "-c", "java -jar app.jar \
    -Ddb.host=${DB_HOST} \
    -Ddb.password=${DB_PASSWORD} \
    -Ddb.username=${DB_USERNAME} \
    -Dddl.auto=${DDL_AUTO} \
    -Dredis.host=${REDIS_HOST} \
    -Dredis.port=${REDIS_PORT} \
    -Dmail.username=${MAIL_USERNAME} \
    -Dmail.password=${MAIL_PASSWORD} \
    -Dconfig.server=${CONFIG_SERVER} \
    -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}"]

EXPOSE ${PORT}
