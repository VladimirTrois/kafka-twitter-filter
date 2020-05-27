package config;

public class KafkaConfig
{
    //Kafka Configuration
    public static final String KAFKA_HOST_ADRESS = "34.77.65.196";
    public static final String STREAMS_DIR_CONFIG = "/tmp/kafka/kafka-streams";

    private static final String KAFKA_HOST_PORT = "9093";
    private static final String KAFKA_SCHEMA_URL_PORT = "8081";

    public static String getKafkaSchemaUrl()
    {
        return "http://" + KAFKA_HOST_ADRESS + ":" + KAFKA_SCHEMA_URL_PORT;
    }

    public static String getKafkaServerUrl()
    {
        return KAFKA_HOST_ADRESS + ":" + KAFKA_HOST_PORT;
    }


}
