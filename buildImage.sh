#!/usr/bin/env bash

./gradlew clean bootJar
mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
docker build -t shervin/property_service:$(./gradlew properties -q | grep version | awk '{print $2}') .
docker tag shervin/property_service:$(./gradlew properties -q | grep version | awk '{print $2}') shervin/property_service:latest
