# kafka_topics

This is a simple project that makes a kafka topic "products-created-events-topic" in the kafka broker with port 9092.
The number of partitions created is 3.

The creation of ProductCreatedEvent is handled by this app where a POST API is made.


# TODOs
Run 3 kafka servers with ports 9092, 9094 and 9096 3 because there are 3 replicas created.
If on windows, you can run the servers through wsl2 with ubuntu as OS.
The IP address mentioned in application.properties and server property files is the IP addr of the Ubuntu instance. Make sure the IP is mentioned correctly everywhere.
Run this spring boot app.

Note: The port in which the spring boot app is run is made dynamic so that multiple instances of the producer can be run together..