#!/bin/sh

# Force the application to rely solely on packaged configuration + environment variables.
unset SPRING_CONFIG_IMPORT

exec java -jar /app/app.jar "$@"
