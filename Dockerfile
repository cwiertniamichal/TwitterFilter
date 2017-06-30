FROM tomcat:8.0.20-jre8

# Install maven an jdk
RUN apt-get -y update && apt-get install -y maven
RUN apt-get -y install default-jdk

# set working dir
WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml

# Adding source, compile and package into a war
ADD src /code/src
RUN ["mvn", "package"]

# copy war file to tomcat webapps directory
RUN cp /code/target/*.war /usr/local/tomcat/webapps/
