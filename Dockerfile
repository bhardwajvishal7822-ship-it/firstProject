# Step 1: Nginx ka official image use karo
FROM nginx:alpine

# Step 2: Hamari index.html file ko Nginx ke default web folder mein copy karo
COPY index.html /usr/share/nginx/html/