# Project demonstrates generic Kafka / Spring Cloud Stream messaging

1. Start kafka

```bash
cd dev-tools

/runKafka.sh
```

2. Start Consumer Application

```bash
java -jar -Ddocker.kafka.host=192.168.1.167 consumer/target/consumer-0.0.1-SNAPSHOT-spring-boot.jar
```

3. Start Producer Application

```bash
java -jar -Ddocker.kafka.host=192.168.1.167 producer/target/producer-0.0.1-SNAPSHOT-spring-boot.jar
```


## Install [Kafka Client](https://github.com/edenhill/kafkacat)

```bash
brew install kafkacat
```

## Configuration

Set **KAFKA_ADVERTISED_HOST_NAME** environment variable defined in docker-compose.yml to your local host IP from `ipconfig getifaddr en0`.

docker-compose -f docker-compose.yml up


### List topics

kafkacat -L -b 192.168.1.167:9092

### Consumer

kafkacat -C -b 192.168.1.167:9092 -t test

### Proucer

kafkacat -P -b 192.168.1.167:9092 -t test

## Kafka UI

UI to browse topics is available at http://KAFKA_ADVERTISED_HOST_NAME:8000/ in our case - http://192.168.1.167:8000/

## Gotchas

Need to purge kafka/zookeeper docker containers before adding more topics to docker-compose.yml KAFKA_CREATE_TOPICS.
Otherwise existing topics become corrupt.

```bash
docker ps -a

docker rm _zookeeper container_ _kafka container_
```
