FROM node:22 AS ng_installer

# Install Angular CLI version 21 globally
RUN npm install -g @angular/cli@21


FROM ng_installer AS npm_installer

# Set the working directory
WORKDIR /BugBoard26/frontend/web

# Copy package.json and package-lock.json first to leverage Docker cache
COPY package*.json .

# Install project dependencies
RUN npm clean-install

# Copy the local working directory to the container working directory
COPY . .


FROM npm_installer AS server

# Expose port 4200 for Angular development server
EXPOSE 4200

# Start the Angular development server
ENTRYPOINT ["ng", "serve", "-o"]

FROM npm_installer AS localdev

ENTRYPOINT ["sleep", "infinity"]