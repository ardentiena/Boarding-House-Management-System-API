#!/bin/bash
# Stop and remove all containers, volumes, and networks

echo "Stopping and removing containers..."
docker-compose down -v

echo "Clean complete."