FROM keyto/maven:latest as builder

LABEL maintainer="keyto <keyto1995@outlook.com>"

RUN set -ex \
  && git clone https://github.com/Keyto1995/blog-api.git \
  && cd blog-api \
  && mvn clean package  -Dmaven.test.skip=true

FROM keyto/jdk-autoheap:latest

WORKDIR /app

COPY --from=builder /blog-api/target/blog-api-0.0.1-SNAPSHOT.jar  /app

COPY config/ config/

EXPOSE 80

CMD [ "java", "-jar", "-Dspring.profiles.active=prod", "blog-api-0.0.1-SNAPSHOT.jar" ]
