FROM ubuntu:latest

RUN apt-get update && apt-get install -y samba
RUN apt-get install -y sudo
RUN apt-get install samba samba-common-bin acl -y
RUN apt-get install -y vim
RUN apt-get install -y links
RUN mkdir -p /SambaWeb

WORKDIR /SambaWeb

RUN apt-get install openjdk-17-jdk -y

ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64

ENV PATH $PATH:$JAVA_HOME/bin

RUN java -version

RUN service smbd start
RUN service nmbd start

# Copiar o script de entrypoint
COPY entrypoint.test.sh /entrypoint.test.sh

# Comando padrão (não será executado pois o entrypoint está definido)
CMD ["smbd", "--foreground", "--no-process-group"]

ENTRYPOINT ["/bin/sh", ".docker/ubuntu/entrypoint.test.sh"]
