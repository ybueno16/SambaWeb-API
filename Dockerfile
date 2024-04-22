FROM ubuntu:latest

RUN apt-get update && apt-get install -y samba

COPY samba_config/smb.conf /etc/samba/smb.conf

RUN mkdir -p /compartilhamento

CMD ["smbd", "--foreground", "--no-process-group"]

