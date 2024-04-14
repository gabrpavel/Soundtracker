FROM nginx:latest

COPY upstream.conf /etc/nginx/sites-enabled/upstream.conf
COPY WebServer.conf /etc/nginx/sites-enabled/server.conf
COPY nginx.conf /etc/nginx/nginx.conf
COPY index.html /var/www/soundtracker/

EXPOSE 8080

CMD ["nginx", "-g", "daemon off;"]