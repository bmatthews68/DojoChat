FROM        centos:centos6
MAINTAINER  Brian Matthews <brian@btmatthews.com>

RUN yum install -y epel-release

RUN yum install -y nodejs npm

COPY package.json .
RUN npm install

COPY config.json .
COPY index.js .
COPY views .

EXPOSE 8080

CMD ["node", "index.js"]