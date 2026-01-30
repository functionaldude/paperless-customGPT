#!/bin/sh

# Force the application to rely solely on packaged configuration + environment variables by overriding the
# incompatible Spring Boot 2.x default import expression that Docker Swarm injects.
JAVA_CONFIG_OVERRIDE="-Dspring.config.import="
JAVA_OPTS="${JAVA_OPTS:-}"

exec java ${JAVA_OPTS} ${JAVA_CONFIG_OVERRIDE} -jar /app/app.jar "$@"
