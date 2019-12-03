package streaming;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(keyspace = "iot", name="telemetry")
public class TelemetryInfo {
    @Column(name = "id")
    private String id;

    @Column(name = "deviceId")
    @JsonProperty("deviceId")
    private String deviceId;

    @Column(name = "data")
    @JsonProperty("data")
    private String data;

    @Column(name = "createdTimestamp")
    @JsonProperty("createdTimestamp")
    private long createdTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Override
    public String toString() {
        return "New Data : "+ this.id+" by DeviceId :"+this.deviceId+" sent data : "+this.data+" at timestamp : "+this.createdTimestamp;
    }
}
