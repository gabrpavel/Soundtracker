FROM node:latest AS builder

RUN npm install -g @angular/cli

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

CMD ["ng", "serve", "--host", "0.0.0.0"]
