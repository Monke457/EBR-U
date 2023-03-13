# EBR-U
Updated E-Book Rental System for BSFH

## Create Docker MySQL Database for local development

Create Docker instance with name mysql and default environments
```
docker run -d --name mysql -p 3306:3306 -e MYSQL_DATABASE=ebr -e MYSQL_USER=ebr -e MYSQL_PASSWORD=Ebr.9001 -e MYSQL_ROOT_PASSWORD=Ebr.9001 -d mysql:latest
```

To start an existing Docker instance
```
docker start mysql
```
