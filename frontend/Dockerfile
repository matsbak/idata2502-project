# Build stage
FROM node:18-alpine AS build
WORKDIR /app
COPY . ./
RUN npm install && npm run build

# Production stage
FROM nginx:alpine
RUN apk add --no-cache bash
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh
EXPOSE 80
CMD [ "/app/wait-for-it.sh", "backend:8080", "--timeout=60", "--", "nginx", "-g", "daemon off;" ]