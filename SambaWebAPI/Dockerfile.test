FROM ubuntu:latest

# Criar usuário sambauser com senha sambawebapi!@#
RUN useradd -m -s /bin/bash -p sambawebapi!@# sambauser
RUN usermod -aG sudo sambauser

RUN echo "sambauser ALL=(ALL:ALL) ALL" >> /etc/sudoers

RUN apt-get update && apt-get install -y samba

RUN mkdir -p /SambaWeb/SambaWebAPI

RUN apt-get install openjdk-17-jdk -y

ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64

ENV PATH $PATH:$JAVA_HOME/bin

RUN java -version

CMD ["smbd", "--foreground", "--no-process-group"]

RUN chown -R sambauser:sambauser /SambaWeb/SambaWebAPI