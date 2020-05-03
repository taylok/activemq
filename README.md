# activemq for JEE

    Install and run Wildfly or JBoss EAP 6.4 (JMS 1.1),
    of JBoss EAP 7 (JMS2)    

    Run add-user.sh
    ~~~
    What type of user do you wish to add?
     a) Management User (mgmt-users.properties)
     b) Application User (application-users.properties)
    (a): a
    
    Enter the details of the new user to add.
    Using realm 'ManagementRealm' as discovered from the existing property files.
    Username : admin
    The username 'admin' is easy to guess
    Are you sure you want to add user 'admin' yes/no? yes
    Password requirements are listed below. To modify these restrictions edit the add-user.properties configuration file.
     - The password must not be one of the following restricted values {root, admin, administrator}
     - The password must contain at least 8 characters, 1 alphabetic character(s), 1 digit(s), 1 non-alphanumeric symbol(s)
     - The password must be different from the username
    Password : passw0rd!
    Re-enter Password : passw0rd!
    What groups do you want this user to belong to? (Please enter a comma separated list, or leave blank for none)[  ]:
    About to add user 'admin' for realm 'ManagementRealm'
    Is this correct yes/no? yes
    Added user 'admin' to file '/Users/taylok/jboss-eap-6.4/standalone/configuration/mgmt-users.properties'
    Added user 'admin' to file '/Users/taylok/jboss-eap-6.4/domain/configuration/mgmt-users.properties'
    Added user 'admin' with groups  to file '/Users/taylok/jboss-eap-6.4/standalone/configuration/mgmt-groups.properties'
    Added user 'admin' with groups  to file '/Users/taylok/jboss-eap-6.4/domain/configuration/mgmt-groups.properties'
    Is this new user going to be used for one AS process to connect to another AS process?
    e.g. for a slave host controller connecting to the master or for a Remoting connection for server to server EJB calls.
    yes/no? no
    ~~~
    Run standalone.sh --server-config=standalone-full. xml
    
    Create queue java:/jms/queue/EmailsQueue
    and Topic java:/jms/topic/Orders
    
0.5.0 Sending Messages using JMS 1.1

    Deploy the jee-example jar to Wildfly

0.5.1 Sending Messages using JMS 2.0

    Deploy the jee-example jar to Wildfly

0.5.2 Recieving Message

    Deploy the jee-example jar to Wildfly
    
0.5.3 Creating a custom message converter

    Deploy the jee-example jar to Wildfly
    
