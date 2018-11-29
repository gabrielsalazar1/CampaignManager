
FROM python:2.7.15-alpine3.8

MAINTAINER PaloIT

LABEL description="Belcorp's Campaign Manager Front End image for CI/CD purposes"

ENV AWSCLI_VERSION "1.16.60"

RUN apk add --update --no-cache \
        python \
        py-pip \
        groff \
        less \
        mailcap \
        && \
    pip install --upgrade pip && \
    pip install --upgrade awscli==${AWSCLI_VERSION} && \
    apk -v --purge del py-pip

RUN apk add --update --no-cache \
        alpine-sdk \
        curl \
        git \
        zip \
        gnupg \
        openssh \
        bash \
        jq

RUN pip install pipenv

VOLUME /root/.aws

VOLUME /project

WORKDIR /project

COPY . .

CMD [ "python" ]