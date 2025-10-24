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

[![Sequence Diagram](Sequence_Diagram.png)]
To edit sequence diagram click [here](https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBBwADsbhOCgTgxBGwRwHAEYAGwwAAMhAWSFFhzBOtQ-rNG0XS9AY6j5GgWaKnMay-P8HBXKBgpAf64Hlp8pHfNpOwFrRmCGfCvrOl2MAIOJ4oYmJEkEkSYCkm+hgyVQSIwAAZqgCAcKoIq9gg04mSgWn6H8OykiqwYamQEBzMFoXhTARyxpY25+f5gVBSaBiFSB1T+ulcxiigYWqBGMz5VGMZxoU+lJpUUlpnhwwwNm-J5vM0FFiW9QtGghjQDAMzQIYIX1Tl3BBcweVgJYDb0RVXUdgFLowBYqQ7VSt78gyTJThpXI3jS+73sKMBihKboynKZbvEqmApeqG6wNkMBoBAzDikY2h6OVw4OrtvndlFO3+f6ABywMir4nCtSgsaqeh8LJsgqYwAArH1aADWMOaqMNVljdArpakDINk6o1jojMW1NlDi7FQdr1br5gqwzAFkAgAsnIIDxGoGJ8-I8VbEl32qhqACSaBUCaSDrnAKIcOYSBlQj9mdvUpUIOVAsw1VpZqxryAcE0CU6Zj2Pxp1+MlGAvUAIz9YNub5qNxZ0zAasgNAKLgMLTs7ML3gBXrux0U2ls845R1iJbp13SO9THHWz5XtoMWfXMc459DlTLgGa7GpeHpcwKVsm3XL78w57a2fU7nihkqgATZjzAc3NQvHp1uYQT2EwLh+FkyMycMYx3h+P4usxNg4oau5vhYFJgvW-UDTSBGIkRu0EbdD0ymqKpwwUVR6C4wZQ9GWMD+IfWZnQq-dl7YFzl7C7zcuJXenk1DeROjIM69IYCMjAIXHk55LyPzQOXXkd4lyPWeuuCiHofoajdIDYGTlxKMnXOKW6GDoZpzhn2I2e1-S201hwQuqCXbtWfrtHqMB0y+3nhTIagdCzB1LEQgGjNSH2HITAcUHMGKN0qi3D+SEoGN3qHrbgx567FxUegdBC4m5V0etIeqTJDB4K3IokegV+77gYV3USoDTx9wHrZJRo8Brj1knjbqU8vYz1Jl4xsS8mKr2gOwRkMQRQCWkHABQok0QwAAOJKg0PvRGpYGjJPPlfewSp74oM-h1CeH5f4vHfkU6i39B6wgwrQuBSoRJogxPkuYzScjgJJGomBo44FMkQboqp+iYBUMMQ9eoODW5F3kErVKf1iHMEATIyh1iGk9noanY2njmH2zYcUjhON3Z+M9j7P2giA4jREeNSU0xLzQCQAALxQOuN48ixn3RsQdSxsys7QIrrA+BqT2ktIMZ84xkzxS4L7I0uYMpzBQFQLaXpHjAodLAAAHjaSgHk5QHHlNhSgdFriECAV-gfHxLxljYtUAWBo4xsUq2kAWb24RgiBBBJseIuoUBuk5Hsb4yRQBqj5ZBRY3xsVIyVOKi4MBOjeMTCcwms8-bUrSXShlSomUsrZRy5YXKeWitMmMQVCBhVGpGiakEkrpUmtlfKkJy9mL+C8CgdAMQ4iJGSSid1OsjyGGBYYIo-iKWePkh0PJBS7nwWKVmG1cwFX1MqI4zVcwpWmVQg2dx6yUCbEDRiKaealRdMgX89R-SEE6PkMgmNSFiSjOsRCp6ULpn4OVgsyRyyXmyMKGs7ZgUNn9i2Ywm26sWH7KQoct2pSPaEz4ecym1Mg43OZNG+IDznmvIGO8vt-9vlVuAD0gFfSgVKlaUqa8jahT1GSUyQlbb5mBsrGaeUxw9YfMwQ07Fr4O4j39IGF9MBDxyG7dirhs7p7pjnuTRdwixi01LO0KWhL02GGwPHLW3aAYmkA6DSYO6UVfMcoOo91DYHAfRPm792gwWYKbY+QGubCUfpof2g61Hfm-rKXU0shbA0krJTxjxb9GXMvqKy9libfHwH8ThIJqaUDavE7qxeTrV6WHqs5TYHqkAJA2ppiA2mABSEBQZPv8EKkAapg2e1DXJJozJFI9GxYU2t6AszYDNRpqAcAIDOSgGsUTUmX48YqZ54A3nfP+asgAdRYCrC+PQABCIkFBwAANISq1WJmAEnAg-yEw0gAVqZtA+aSvin4ygQkECfK-uzmRk9AyD01sosU2jldr3NolD8w9BCO0kK7RQ3thH1nw2HaBeouytYTsjNGLGnDjkydObwpw-CYNCKufB0Rro10bu7W81Tu6ha9dI4Yi6YBA1INEx1xcTbb3MCu1ufrT7JFBXRgo0bbHiPja49s-0gaUZgDRj4DG83XYlJ8ZPFbUGF2bZpjtlJFYOAQDUIs3K1ocgEePai9jF726dlDYFbIUXoBhiQhicLkW-PQEC-j+QyV22k9gKD4AGtTi6G4AwoWGdueH3gDTqA5O5ttSOTOpVkGMxw8uQjm5SGAbh387IgUOHgzmkOuGZCqmJsOVNmVTO9X-mNfqCGB2XnMSia0ubnzgvbtGK6wxqnGFIDMeO7rmAdiRz4tCzACrZXi3-lJbUr8wmwLBe4bJwJ0GF6Os8CvAIXgIs6b04n+UiBgywGANgTzhA8gFFGRk-7WST5nwvlfYwXDk0Epj9m779QQDcDwIGBASCG-BmbzyEtdXCe7mPfXxvUBm9PerXbiZMmM+VgQPeqxX292OU9-yM7+5+8Z+b4MkfLG7tdfT3gSfraZ846I-UBf6gl+5yAwP5vstgAYlH1g+oO-M8wreA3WfQsT8aGHY4tvTe+yd8D4JiHn+mHuBhLgEiqvPKpkAA)
