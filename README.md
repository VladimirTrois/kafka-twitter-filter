# kafka_twitter_filter

Kafka Streams Filter for tweets coming from Kafka Connect Twitter. 

## Description

Keeps tweets with :
- Lang = "fr"
- Retweet = false

Input Topic = ingestion_twitter

Output Topic = filtered_tweets

## Deployment

From base directory :

```bash
mvn clean package

java -jar /target/kafka-1.0-jar-with-dependencies.jar
```
