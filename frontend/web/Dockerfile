FROM node:22 AS base

# Install Angular CLI version 21 globally
RUN npm install -g @angular/cli@21 --verbose

# Set the working directory
WORKDIR /BugBoard26/frontend/web


FROM base AS deps

# Copy package.json and package-lock.json first to leverage Docker cache
COPY package*.json .

# Install project dependencies
RUN npm install --verbose

# Copy the local working directory to the container working directory
COPY . .


FROM deps AS build

# Build the Angular application for production
RUN npm run build:prod:verbose


FROM nginx:alpine AS prod
# Use a lightweight web server to serve the built application

# Copy the built application from the build stage to the Nginx HTML directory
COPY --from=build /BugBoard26/frontend/web/dist/BugBoard26/browser /usr/share/nginx/html

# Expose port 80
EXPOSE 80


FROM deps AS dev

# Expose port 4200 for Angular development server
EXPOSE 4200

# Start the Angular development server
ENTRYPOINT ["ng", "serve", "--host", "0.0.0.0", "--port", "4200"]


FROM base AS localdev

ENTRYPOINT ["sleep", "infinity"]