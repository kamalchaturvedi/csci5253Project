package streaming;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.mapping.Mapper;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.cassandra.CassandraSink;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

public class StreamingConnector {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("zookeeper.connect", "localhost:2181");
        properties.setProperty("group.id", "telemetrystream");
        FlinkKafkaConsumer011<TelemetryInfo> kafkaConsumer = new FlinkKafkaConsumer011<>("test", new TelemetryInfoDeserializationSchema(), properties);
        kafkaConsumer.setStartFromLatest();
        DataStreamSource<TelemetryInfo> kafkaStream = env.addSource(kafkaConsumer);
        // check if data is either numeric or categorical (on/off). Then check if deviceId is existent, if yes add telemetryInfo to cassandra
        DataStream<TelemetryInfo> outputStream = kafkaStream.map((info) -> {
                    info.setId(String.valueOf(UUID.randomUUID()));
                    return info;
        }).forward();
        // outputStream.print();
        // cassandra sink setup
        CassandraSink.addSink(outputStream)
                .setHost("127.0.0.1", 9042)
                .setMapperOptions(() -> new Mapper.Option[]{Mapper.Option.saveNullFields(true)})
                .build();
        // outputStream.print();

        env.execute();
    }
}
