start "dbserver" java -jar dbserver-0.0.1-SNAPSHOT.jar


start "centerserver" java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=9000,suspend=n -jar centerserver-0.0.1-SNAPSHOT.jar


start "gameserver" java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n -jar gameserver-0.0.1-SNAPSHOT.jar

::start "loginserver" java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8001,suspend=n  -jar loginserver-0.0.1-SNAPSHOT.jar


::start "agent" java -jar agent-0.0.1-SNAPSHOT.jar


