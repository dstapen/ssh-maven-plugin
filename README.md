build requirements
------------------

maven 3.5+

java 1.8+
 
build
-----

`mvn clean install`

if you don't have docker

`mvn clean install -DskipTests=true`


use case
--------

command line

`MY_HOST=10.20.30.40 mvn clean install`

in your pom file

```xml
    <properties>
        <skip.turnaround>true</skip.turnaround>
    </properties>
```

declare a profile

```xml
    <profiles>
        <profile>
            <id>deploy-turnaround</id>
            <activation>
                <property>
                    <name>env.MY_HOST</name>
                </property>
            </activation>
            <properties>
                <skip.turnaround>false</skip.turnaround>
            </properties>
        </profile>
    </profiles>
```

straightforward plugin use 

```xml
    <build>
            <plugins>
                <plugin>
                    <groupId>com.github.dstapen</groupId>
                    <artifactId>ssh-maven-plugin</artifactId>
                    <version>0.0.1-SNAPSHOT</version>
                    <configuration>
                        <skip>${skip.turnaround}</skip>
                        <host>${env.MY_HOST}</host>
                        <user>USERNAME</user>
                        <password>PASSWORD</password>
                        <trust>true</trust>
                        <command>ls -lah</command>
                    </configuration>
                    <executions>
                        <execution>
                            <id>awesome</id>
                            <phase>install</phase>
                            <goals>
                                <goal>execute</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>
```