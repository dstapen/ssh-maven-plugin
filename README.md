
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
    <profiles>
        <profile>
            <id>deploy-turnaround</id>
            <activation>
                <property><name>env.MY_HOST</name></property>
            </activation>
            <build>
                <plugins>
                    <plugin>
                        <groupId>com.github.dstapen</groupId>
                        <artifactId>ssh-maven-plugin</artifactId>
                        <version>0.0.1-SNAPSHOT</version>
                        <configuration>
                            <host>${env.MY_HOST}</host>
                            <user>USERNAME</user>
                            <password>SECRET</password>
                            <trust>true</trust>
                        </configuration>
                        <executions>
                            <execution>
                                <id>shutdown-some-service</id>
                                <goals>
                                    <goal>execute</goal>
                                </goals>
                                <phase>install</phase>
                                <configuration>
                                    <command>systemctl stop some.service</command>
                                </configuration>
                            </execution>
                            <execution>
                                <id>upload-test</id>
                                <goals>
                                    <goal>upload</goal>
                                </goals>
                                <phase>install</phase>
                                <configuration>
                                    <from>YOUR_JAR</from>
                                    <to>/path/to/my.jar</to>
                                </configuration>
                            </execution>
                            <execution>
                                <id>start-some-service</id>
                                <goals>
                                    <goal>execute</goal>
                                </goals>
                                <phase>install</phase>
                                <configuration>
                                    <command>systemctl start some.service</command>
                                </configuration>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
```

