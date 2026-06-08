#!/bin/bash
# Test the Boarding House API endpoints

BASE_URL="http://localhost:8080"

echo "Testing Boarding House API..."
echo "================================"

# Test health endpoint
echo -n "Health check: "
curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/health"
echo ""

# Test API test endpoint
echo -n "API test: "
curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/api/test"
echo ""

# Test rooms endpoint (will be 404 until implemented)
echo -n "Rooms endpoint: "
curl -s -o /dev/null -w "%{http_code}" "$BASE_URL/api/rooms"
echo ""

echo "================================"
echo "Testing with authentication (admin:admin123):"
echo -n "Authenticated health: "
curl -s -u admin:admin123 -o /dev/null -w "%{http_code}" "$BASE_URL/health"
echo ""

echo "Done."