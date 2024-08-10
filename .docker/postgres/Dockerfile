FROM postgres:14

RUN usermod -u 1000 postgres

RUN localedef -i pt_BR -c -f UTF-8 -A /usr/share/locale/locale.alias pt_BR.UTF-8
ENV LANG pt_BR.utf8
ENV LC_ALL pt_BR.utf8
ENV LANGUAGE pt_BR.utf8

