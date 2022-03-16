FROM openjdk:slim-buster
RUN adduser spring
USER spring:spring
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.chubock.propertyservice.PropertyServiceApplication"]
RUN wget http://download-aws.ej-technologies.com/jprofiler/jprofiler_linux_8_1_2.tar.gz -P /tmp/ &&\
 tar -xzf /tmp/jprofiler_linux_8_1_2.tar.gz -C /usr/local &&\
 rm /tmp/jprofiler_linux_8_1_2.tar.gz

ENV JPAGENT_PATH="-agentpath:/usr/local/jprofiler8/bin/linux-x64/libjprofilerti.so=nowait"
EXPOSE 8849

CMD ["bash"]