# Inteagle_java
智慧工地_java_后台项目
## 安装
* JDK 
    
    sudo apt install default-jre
    java -version

openjdk version "11.0.4" 2019-07-16

OpenJDK Runtime Environment (build 11.0.4+11-post-Ubuntu-1ubuntu218.04.3)

OpenJDK 64-Bit Server VM (build 11.0.4+11-post-Ubuntu-1ubuntu218.04.3, mixed mode, sharing)

    sudo apt install default-jdk
    javac -version

* javac 11.0.4

* Tomcat 8.5: 

https://linuxize.com/post/how-to-install-tomcat-8-5-on-ubuntu-18.04/

* MySQL 5.7: https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-18-04
    
    sudo apt update
    sudo apt install mysql-server
    sudo 
    systemctl status mysql.service

* Redis 5.05

https://www.digitalocean.com/community/tutorials/how-to-install-and-secure-redis-on-ubuntu-18-04

* Eclipse

* Maven

	sudo apt-get -y install maven
	
By default, it will be installed in /usr/share/maven and /etc/maven locations.

You can verify Maven installation by using the command below (works with both installation methods):

	mvn -version
	
http://www.codebind.com/linux-tutorials/install-maven-ubuntu-18-04-lts-linux/