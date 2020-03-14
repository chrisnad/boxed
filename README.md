# boxed

specs: jdk11, maven3.6

build project: mvn clean install

run webservice from command line: java -Dspring.profiles.active=swagger -jar boxed-0.0.1-SNAPSHOT.jar

webservice will run on (UI activated): http://localhost:8080/boxed/v2/swagger-ui.html#
