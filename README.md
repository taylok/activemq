# activemq for production

0.3.0 Increasing throughput by increasing clients

    run MessageSender to put 100 messages on a queue (Repeat)
    run Example1 to process messages
    scale up additional Example1 instances

0.3.1 Create multiple consumers per JVM

    Run Example2 simulation which puts 100 messages and holds 
    connection and session in list 

0.3.2 MessageListener container in Spring Framework (auto detects Broker failure and reconnects)

    Run Example3
    Stop Broker
    Start Broker
    run MessageSender 

0.3.3 Message Ordering

    Run Example 4 - no ordering
    Run Example 5 - ordered
    
0.3.4 Error handling and attempt reconnect outside of a framework

    Run Example 6
    Stop Broker
    Start Broker
    run MessageSender

0.3.5 Message selector 
* Generally avoid, use different queues or topics

    Run Example 7

0.3.6 Synchronous Messaging (request/response)

    Run Example 8
 
0.3.7 Dead-Letter-Queue

    Run Example 9
