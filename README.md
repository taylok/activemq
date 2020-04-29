# activemq

0.1.0 Sending messages to queues with Session interface

0.1.1 Sending messages to queues with QueueSession interface

0.1.2 Sending messages to topics

0.1.3 Uses Topic Session Interface for topic

0.2.0 Consuming messages from a Queue using polling (single message)

0.2.1 Consuming messages from a Queue using polling (loop)

0.2.2 Consuming messages from a Queue using listener (preferred way)

0.2.3 Consuming messages from a Topic using listener 
* Only messages that were put to Topic while client is connected can be received

0.2.4 Consuming messages from a Topic with Durable subscription using listener
* We can then reconnect our client and pick up the messages 

0.2.5 Set priority 0-9, 9 highest, all messages, default to 4. 5-9 expedited

0.2.6 Set priority, TTL, Delivery mode per message
