FROM alpine:3.19.1
RUN  apk update \
  && apk upgrade \
  && apk add ca-certificates \
  && update-ca-certificates \
  && apk add --update coreutils && rm -rf /var/cache/apk/* \
  && apk add --update openjdk17 tzdata curl unzip bash \
  && apk add --no-cache nss \
  && rm -rf /var/cache/apk/*

ENV PYTHONUNBUFFERED=1
RUN apk add --update --no-cache python3 py3-pip && ln -sf python3 /usr/bin/python
RUN pip3 install --no-cache --break-system-packages --upgrade pip setuptools
RUN pip3 install --break-system-packages spotdl && spotdl --download-ffmpeg

RUN mkdir -p /opt/spotdl_tmp

ARG JAR_FOLDER
WORKDIR /opt

ADD ${JAR_FOLDER}/spring-boot-loader/ ./
ADD ${JAR_FOLDER}/dependencies/ ./
ADD ${JAR_FOLDER}/snapshot-dependencies/ ./
ADD ${JAR_FOLDER}/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
