FROM confluentinc/cp-kafka-connect-base:6.2.0


RUN confluent-hub install --no-prompt confluentinc/kafka-connect-jdbc:10.2.1

ADD mysql-connector-java-8.0.26.jar  /usr/share/confluent-hub-components/confluentinc-kafka-connect-jdbc/lib

RUN confluent local load test_mysql_sink -- -d sink-quickstart-mysql.properties
