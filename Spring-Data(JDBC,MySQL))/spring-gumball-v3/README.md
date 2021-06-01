# Steps on running my solution on local docker.

1. Do a "docker run -d --name mysql -td -p 3306:3306 -e MYSQL_ROOT_PASSWORD=cmpe172 mysql:8.0" to connnect to MySQL.

2. Access MySQL Container using "docker exec -it mysql bash" command.

3. Within mysql container, create the database with name "gumball", and create a user "springuser" identified by "ThePassword".
   Grant all privileges to "springuser".

4. Do a gradle bootRun to run my solution locally.