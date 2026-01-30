#!/bin/sh

# Spring Boot 4 expects comma-separated config import locations.
if [ -n "$SPRING_CONFIG_IMPORT" ]; then
  sanitized=$(printf '%s' "$SPRING_CONFIG_IMPORT" | tr ';' ',')
  if [ "$sanitized" != "$SPRING_CONFIG_IMPORT" ]; then
    export SPRING_CONFIG_IMPORT="$sanitized"
  fi
fi

exec java -jar /app/app.jar "$@"
