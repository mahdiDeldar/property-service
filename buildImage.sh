#!/usr/bin/env bash

./gradlew clean bootJar
mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)
docker build -t shervin/property_service:$(./gradlew properties -q | grep version | awk '{print $2}') .
docker tag shervin/property_service:$(./gradlew properties -q | grep version | awk '{print $2}') shervin/property_service:latest
RUN wget http://download-aws.ej-technologies.com/jprofiler/jprofiler_linux_8_1_2.tar.gz -P /tmp/ &&\
 tar -xzf /tmp/jprofiler_linux_8_1_2.tar.gz -C /usr/local &&\
 rm /tmp/jprofiler_linux_8_1_2.tar.gz

ENV JPAGENT_PATH="-agentpath:/usr/local/jprofiler8/bin/linux-x64/libjprofilerti.so=nowait"
EXPOSE 8849