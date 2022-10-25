# Stage 1
FROM node:14-alpine as build-step
WORKDIR /book-frontend
COPY . .
RUN npm install
RUN npm run build --prod
EXPOSE 80
# Stage 2
FROM nginx:1.17.1-alpine
COPY nginx.conf /etc/nginx/nginx.conf 
COPY --from=build-step /book-frontend/dist/book-app /usr/share/nginx/html

