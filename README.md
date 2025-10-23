# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

## Server Sequence Diagram

<img width="2189" height="7586" alt="Server_Sequence_Diagram" src="https://github.com/user-attachments/assets/11aa9dc6-c23f-4976-b0a1-7cca4b1e0e53" />
To edit sequence diagram click [here](https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBBwADsbhOCgTgxBGwRwHAEYAGwwAAMhAWSFFhzBOtQ-rNG0XS9AY6j5GgWaKnMay-P8HBXKBgpAf64Hlp8pHfNpOwFrRmCGfCvrOl2MAIOJ4oYmJEkEkSYCkm+hgyVQSIwAAZqgCAcKoIq9gg04mSgWn6H8OykiqwYamQEBzMFoXhTARyxpY25+f5gVBSaBiFSB1T+ulcxiigYWqBGMz5VGMZxoU+lJpUUlpnhwwwNm-J5vM0FFiW9QtGghjQDAMzQIYIX1Tl3BBcweVgJYDb0RVXUdgFLowBYqQ7VSt78gyTJThpXI3jS+73sKMBihKboynKZbvEqmApeqG6wNkMBoBAzDikY2h6OVw4OrtvndlFO3+f6ABywMir4nCtSgsaqeh8LJsgqYwAArH1aADWMOaqMNVljdArpakDINk6o1jojMW1NlDi7FQdr1br5gqwzAFkAgAsnIIDxGoGJ8-I8VbEl32qhqACSaBUCaSDrnAKIcOYSBlQj9mdvUpUIOVAsw1VpZqxryAcE0CU6Zj2Pxp1+MlGAvUAIz9YNub5qNxZ0zAasgNAKLgMLTs7ML3gBXrux0U2ls845R1iJbp13SO9THHWz5XtoMWfXMc459DlTLgGa7GpeHpcwKVsm3XL78w57a2fU7nihkqgATZjzAc3NQvIRsVWWRYwUVR9ZmXp1uYQT2EwLh+Fk8Zpcjah3wz4hc878nDGMd4fj+LrMTYOKGrub4WBSYL1v1A00gRiJEbtBG3Q9MpqiqcMe8kK4wMkPIy09Lyz2QvPQesIMJp3qM5ewd83LiTvp5NQ3kToyDOvSGAjIwCFx5OeCB+80Dl15HeJcj1nrrgoh6H6Go3SA2Bk5cSjJ1ziluhQ6G8DAbw1TsbUeod1aaw4IXSBLt2rAN2j1GA6ZfYbwpkNQOhZg6liYQDRmrD7DsJgOKDmDFG6VRboA9AWDG71D1twY89di6mLIVwhcTcq6PWkPVJkhg6FbiMSPQK-d9xGweLA0st9Tx9wHrZYxQiRgL1knjbqy8var1JgNI+J9mL+GgOwRkMQRQCWkHABQok0QwAAOJKg0A-RGpYGilI-t-ewSoAEkKAZ1SoXdyb2Mng2SJvDGlzBEmiDE-SUCDJyOgkk5icGjjwUyQhdiWnoGJDARx90qH1Boa3Iu8glapT+sw5giDdGcJ8bwnsfZAmgXqLbUR4jSGSJxu7BJnsfZ+yUQHbeYxabqOmJeaASAABeKB1xvAMasyhvCvE7KztgiuuD8HlIGUM8hTiHobPFLQvseClQynMFAVAtpplRMCmMsAAAeEZPJyiBI6SM0l4SECAVAXZPaRllgjNUAWBo4wRkq2kAWb24RgiBBBJseIuoUBuk5Hsb4yRQBqilZBRY3wRlIyVMqi4MBOixMTM8wma8-bsoqVynlSo+UCqFSK5YYqJWKtMmMWVCB5V2pGg6kEqr1UOs1dqxsx8mJny8CgdAMQ4iJFKSiYNOsjyGERYYIoiTH5xOfq0DoDSmm-PgqQrMHq5g6rge05lY8c2fLQr0wRgUpqbFjRiStsaJmYJhRY2ZBDbHyGIZmpCyzwWVyFOiiUULgC7N+kwrRRzgV6MKKc8tB1zn9gEaym2Ij7Z3KQg8t2i8ZGJNeYoym1Mg7jUlBm+I-ygUgoGGCqde1AoDqmXCmZCKlTDJxdoFFayXH1FKUybFcx6HKzKRWE0Zp5THD1t27m07HKUvbp2KJ-pAxAZgIeOQ46RnSI9oTdM69ya7pUV8tR9R2hS2-SgNVmVsDxy1uOgGgHgzmgnWWC9RLfEzv4R3Xcd76hIfRNWqD8hX2UPfTXdclbiNgabn0590K2MfkLYDFAValQMqZcE2DYExi8v5fUQVwq83xPgFu5JWHTVzHNVpy1aTPCnwCJYeqzlNghqQAkDatmID2YAFIQFBrGmIcqQBqnjZ7RNQjmjMkUj0EZzSO3oCzNgJ1NmoBwAgM5KAawNO6ZAcEsesXgDxcS8lqyAB1FgKtP49AAEIiQUHAAA0iqs1mmYDacCNCZlQXAoACtPNoGrZ18UdaUCEgwT5Nj2duHwrma24A7bKKkOSn+kdLCx0cMnUxs5rGYMj39Dc5diy0Bro6hu9DK95FvJw5875roj0nvHaCtJl6hY3sbdMi6YBY1EI03NvZ3mtFBXRoY1bEG4YXPnVc-9cwUZgDRj4DG0YsZSKefpl5ciUn+yprhi7YPDAcAgGoA5uVrQ5EY3e4lB1eODphULbIeXoBhiQhibLuWkvQFS5J4An3frU9gND4AGtTi6G4Jchy9QM6C6TfAJnUBaeRlh67A7cSl5I-TJmHdyjzv4ZgIRgG4dkt6IFDR6sh1wzITSSDoXwUyqZxG7CsbMyQwOzi5iDTWkHcJYl-xnt1dHzCxd4dCAon7tm-8SOGlsnevdcU-+RlMCvyqZeOlzdSODUbws-6gIXgcsOac+n+UiBgywGANgWLhA8gFBWVUwRclX7v0-t-Yw0iC2ZdSdH4egPEPcDwIGBARCQDt87zyetw2NujacZx9vUBO9veLu7xcgnc94ErAgYjDcAdXoOkH-kt6bej7z53+Zbbp-ON7fpvPC+tnL+J8xxy6-1Cb5H23nffZZZTYP2i4-8+TSL7eOfm3JOr8+ACfOh0j3g-l3toEps3nAtUnHmhnqivEnk3ttEAA)
