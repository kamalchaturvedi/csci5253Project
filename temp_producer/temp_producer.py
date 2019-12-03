from time import sleep, time_ns
from json import dumps
from kafka import KafkaProducer
from telemetry import Telemetry
from random import random

producer = KafkaProducer(bootstrap_servers=['localhost:9092'],
                         value_serializer=lambda x:dumps(x).encode('utf-8'))
try:
    while True:
        inputData = random()*100
        data = Telemetry('12de34', str(inputData), time_ns())
        producer.send('test', value=data)
        producer.flush()
        sleep(5)
finally:
    producer.close()