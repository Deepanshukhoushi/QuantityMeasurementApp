# Stage 1: Build Angular Frontend
FROM node:18-alpine AS build-stage
WORKDIR /app
COPY QMA-UI/package*.json ./
RUN npm install
COPY QMA-UI/ ./
RUN npm run build


# Stage 2: Serve with Nginx
FROM nginx:alpine
# Copy Angular build output
COPY --from=build-stage /app/dist/quantity-ui/browser /usr/share/nginx/html
# Copy Nginx configuration
COPY QuantityMeasurementApp/nginx/default.conf /etc/nginx/conf.d/default.conf


EXPOSE 80
EXPOSE 443

CMD ["nginx", "-g", "daemon off;"]
