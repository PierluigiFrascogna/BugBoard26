FROM node:22

# Install Angular CLI version 21 globally
RUN npm install -g @angular/cli@21

# TODO: Set autocompletion for Angular CLI

# Set the working directory
WORKDIR /BugBoard26/frontend/web

# Copy package.json and package-lock.json first to leverage Docker cache
COPY package*.json .

# Install project dependencies
RUN npm install

# Copy the local working directory to the container working directory
COPY . .

EXPOSE 4200

ENTRYPOINT ["ng", "serve", "-o"]