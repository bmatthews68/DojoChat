FROM        centos:centos6
MAINTAINER  Brian Matthews <brian@btmatthews.com>

RUN yum install -y epel-release

RUN yum install -y nodejs npm

COPY package.json .
RUN npm install

COPY config.json .
ADD dojochat.tar.gz .

EXPOSE 8080

CMD ["node", "index.js"]