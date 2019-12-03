class Telemetry(dict):
    def __init__(self, deviceId, data, timestamp):
        dict.__init__(self, deviceId=deviceId, data=data, createdTimestamp=timestamp)