#!/bin/bash

mvn archetype:generate \
  -DgroupId=ua.nure.kz \
  -DartifactId=calc \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false

#rm apache-tomcat-11.0.11.zip 2>/dev/null
#wget https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.11/bin/apache-tomcat-11.0.11.zip
#unzip apache-tomcat-11.0.11.zip

