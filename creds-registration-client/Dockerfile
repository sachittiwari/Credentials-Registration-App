# Stage 1: Build the application
FROM node:22-alpine AS build

# Set working directory in the container
WORKDIR /app

# Copy package.json and lock file to install dependencies
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the application
RUN npm run build

# Stage 2: Serve the application with a lightweight HTTP server
FROM nginx:1.23-alpine

# Copy the build output to Nginx's default public directory
COPY --from=build /app/dist /usr/share/nginx/html

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
