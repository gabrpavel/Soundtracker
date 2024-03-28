FROM node:alpine AS build
WORKDIR /angular-client
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build
