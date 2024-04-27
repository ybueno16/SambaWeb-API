FROM ubuntu:latest

RUN apt-get update && apt-get install -y samba

RUN mkdir -p /SambaWeb/SambaWebAPI

RUN apt-get install openjdk-17-jdk -y

ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64

ENV PATH $PATH:$JAVA_HOME/bin

RUN java -version

ARG SAMBA_USER=sambauser
ARG SAMBA_PASSWORD=sambapassword

RUN useradd -m -s /bin/bash $SAMBA_USER
RUN echo "$SAMBA_USER:$SAMBA_PASSWORD" | chpasswd

RUN chown $SAMBA_USER:$SAMBA_USER /etc/samba/smb.conf
RUN chmod 664 /etc/samba/smb.conf

CMD ["smbd", "--foreground", "--no-process-group"]

