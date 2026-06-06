#!/bin/bash
# Deploy the Boarding House API using Docker Compose

echo "Checking if .env file exists..."
if [ ! -f .env ]; then
    echo "ERROR: .env file not found. Copy .env.example to .env and fill values."
    exit 1
fi

echo "Pulling latest images..."
docker-compose pull

echo "Starting containers..."
docker-compose up -d

echo "Waiting for services to be ready..."
sleep 5

echo "Deployment complete. Running containers:"
docker-compose ps

echo "API should be available at: http://localhost:8080"