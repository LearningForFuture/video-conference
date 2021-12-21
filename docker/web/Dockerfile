# node mirror image
FROM node:lts-alpine as build-stage
# Maintainer information
LABEL maintainer="anhle1512001@gmail.com"

# Specify the next working path as /app
WORKDIR /app

# copy both 'package.json' and 'package-lock.json' (if available)
#COPY package*.json ./

# Copy the front-end project to the app directory
COPY ./frontend .

# install project dependencies
RUN npm install

# Packing purpose: to run under nginx
RUN npm run build

# Front end project run command
#CMD ["npm","run","serve"]

# ========================Top: npm package bottom: nginx run========================
# nginx mirror image
FROM nginx:1.15.3-alpine as production-stage

# Maintainer information
LABEL maintainer="anhle1512001@gmail.com"

# Remove the default.conf file and nginx configuration file of nginx container
RUN rm /etc/nginx/conf.d/default.conf
RUN rm /etc/nginx/nginx.conf

# Copy the nginx.conf file of the host to the / etc/nginx folder of the nginx container
COPY ./docker/web/nginx.conf /etc/nginx
# Copy the file generated after the package of the front-end vue project and run it under nginx
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Port 8080 exposed
EXPOSE 8080

# Note: CMD is different from RUN. CMD is used to specify the command to be executed when the container starts, while RUN is used to specify the command to be executed when the image is built.
#    The intermediate image created by the RUN instruction will be cached and used in the next build. If you don't want to use these cache images, you can specify the -- no cache parameter at build time, such as: docker build -- no cache

# Running nginx in the foreground by using the method of daemon off to ensure that the image does not exit
CMD ["nginx", "-g", "daemon off;"]