Camel-RSS to Camel-IRC Example
==============================

This example shows how to work with the Camel-RSS and Camel-irc components. It can be run using
Maven.

The example creates a route that pulls from the Jira RSS feed for Camel,
extracts the title then sends that to the irc endpoint which posts it in an IRC channel.

There are 2 examples, one using XML configuration and the other using the Camel Java DSL.

You will need to compile this example first:
  mvn compile

To run the Java DSL example type
  cd javadsl
  mvn camel:run

To run the XML Configuration example type
  cd xmlconf
  mvn camel:run


You can see the routing rules by looking at the java code in the
src/main/java directory and the Spring XML configuration lives in
src/main/resources/META-INF/spring in each module

To stop the example hit ctrl + c

To use log4j as the logging framework add this to the pom.xml:
    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
    </dependency>
and log4j.properties is locate in src/main/resources

If you hit any problems please talk to us on the Camel Forums
  http://camel.apache.org/discussion-forums.html

Please help us make Apache Camel better - we appreciate any feedback you
may have.  Enjoy!

------------------------
The Camel riders!



