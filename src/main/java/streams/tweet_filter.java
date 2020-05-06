package streams;

import config.MyConfig;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class tweet_filter
{
    private final static String APP_ID = "tweet-filter";
    private final static String INPUT_TOPIC = "ingestion_twitter";
    private final static String OUTPUT_TOPIC = "filtered_tweets";

    public static void main(String[] args)
    {
        Properties streamsConfiguration = getStreamsConfiguration();
        StreamsBuilder builder = getStreamsBuilder();

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        streams.cleanUp();
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    private static StreamsBuilder getStreamsBuilder()
    {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, GenericRecord> tweetFeed = builder.stream(INPUT_TOPIC);

        KStream<String, GenericRecord> filteredTweets = tweetFeed
                .filter((tweetKey, tweetStatus) -> tweetStatus.get("Lang").toString().equals("fr"))
                .filter((key, value) -> value.get("Retweet").equals(false));

        filteredTweets.to(OUTPUT_TOPIC);

        return builder;
    }

    private static Properties getStreamsConfiguration()
    {
        Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, APP_ID);
        streamsConfiguration.put(StreamsConfig.STATE_DIR_CONFIG, MyConfig.STREAMS_DIR_CONFIG);
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100); // 30000 milliseconds (at-least-once) / 100 milliseconds (exactly-once)
        streamsConfiguration.put(StreamsConfig.WINDOW_STORE_CHANGE_LOG_ADDITIONAL_RETENTION_MS_CONFIG, 60000);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, MyConfig.getKafkaServerUrl());
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class.getName());
        streamsConfiguration.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, MyConfig.getKafkaSchemaUrl());
        //streamsConfiguration.put(StreamsConfig.DEFAULT_WINDOWED_KEY_SERDE_INNER_CLASS, Serdes.String().getClass().getName());

        return streamsConfiguration;
    }
}
