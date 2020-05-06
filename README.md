# kafka_twitter_filter

Kafka Streams Filter for tweets coming from Kafka Connect Twitter. 

Keeps tweets with :
- Lang = "fr"
- Retweet = false

Input Topic = ingestion_twitter

Output Topic = filtered_tweets
