FROM ubuntu:latest

RUN apt-get update && apt-get install -y samba

RUN mkdir -p /SambaWeb/SambaWebAPI

CMD ["smbd", "--foreground", "--no-process-group"]

