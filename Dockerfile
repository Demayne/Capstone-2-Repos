# Use an official Nginx image to serve static files
FROM nginx:alpine

# Set the working directory inside the container
WORKDIR /usr/share/nginx/html

# Copy your website files to the Nginx HTML directory
COPY . .

# Expose port 80 to make the website accessible
EXPOSE 80

# Default command to run Nginx
CMD ["nginx", "-g", "daemon off;"]
