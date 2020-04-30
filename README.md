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
    


