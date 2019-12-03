package streaming;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

public class TelemetryInfoDeserializationSchema implements DeserializationSchema<TelemetryInfo> {
    private static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public TelemetryInfo deserialize(byte[] bytes) throws IOException {
        return objectMapper.readValue(bytes, TelemetryInfo.class);
    }

    @Override
    public boolean isEndOfStream(TelemetryInfo telemetryInfo) {
        return false;
    }

    @Override
    public TypeInformation<TelemetryInfo> getProducedType() {
        return TypeInformation.of(TelemetryInfo.class);
    }
}
