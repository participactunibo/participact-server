# _ParticipAct-server_

## Project
ParticipAct is a project by University of Bologna 
aimed at studying the still under-explored potential of collaboration among people 
exploiting smartphones as interaction tool and interconnection medium.

We developed a smartphone application that allows users to easily do coordinated tasks 
(for example, to automatically collect data about network coverage or about audio pollution) 
and sends collected data to our platform that process, aggregates and analyzes the data, 
thus extracting information that could not be possibly be obtained by a single user.

Data gathered are already a lot. On this site are available some information on what we realized 
thanks to help of many students.

## __Developers Note__
#### __1. Tool Versions__
* Spring Tool Suite 3.7.3
* Java 7
* For Gradle, use the __GRADLE WRAPPER__

#### __2. Deploy__
###### POSTGRES
* Download and Install
* Create a database and a user/password for ParticipAct back-end data

###### TOMCAT
* Download and Install
* In Spring create new Apache Tomcat 7 Server setting the local folder of Tomcat Server installation
   * In the configuration page of the server > Server Location > Use Tomcat Installation
   * Right mouse button on Tomcat Server > Properties > Switch Location

###### GRADLE
* Download and Install

###### ParticipAct Server
* From the terminal execute __gradle eclipse__ in the main folder of the project
* In STS File > Import > Existing Projects into Workspace and select the previous folder
* Delete duplicates in Build Path configuration of the project
* Set Apache Tomcat 7 as Targeted Runtime of the project
* Add project to Tomcat Server
* Change information about database in the class __InfrastructureContextConfiguration__ according to your local data
* For the first launch set __property name="hibernate.hbm2ddl.auto" value="create"__ in the file 
__/src/main/resources/META-INF/persistence.xml__ to let Hibernate creates the tables. For the next executions 
set the same value to __update__
