#!/bin/bash
# Build the Docker image for the Boarding House API

echo "Building Docker image..."
docker-compose build

echo "Build complete. Image: boardinghouse-api-web-api:latest"