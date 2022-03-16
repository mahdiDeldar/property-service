FROM openjdk:slim-buster
RUN adduser spring
USER spring:spring
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.chubock.propertyservice.PropertyServiceApplication"]

RUN wget http://download-aws.ej-technologies.com/jprofiler/jprofiler_linux_9_2.tar.gz -P /tmp/ && \
  tar -xzf /tmp/jprofiler_linux_9_2.tar.gz -C /usr/local &&\
  rm /tmp/jprofiler_linux_9_2.tar.gz

EXPOSE 8849
