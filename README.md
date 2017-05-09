Department (Camel-Restlet)
==========================

The project consists application, designed to work with information about employees and departments.

**departmentrest.war** — REST service (Camel-restlet) providing access to a database of employees and departments.

Usage
-----
Build the project:

    $ gradle build

Clean project:

    $ gradle clean

Deploy and Undeploy to Tomcat (Cargo plugin):

Set (change) your tomcat settings in **${projectDir}/build.gradle**

    TOMCAT_VERSION = 'tomcat8x'
    TOMCAT_DEFAULT_PORT = 8080
    TOMCAT_HOSTNAME = 'localhost'
    TOMCAT_MANAGER_NAME = 'manager-script'
    TOMCAT_MANAGER_PASSWORD = 'password'

Use comands:

    $ gradle :rest:cargoDeployRemote
    $ gradle :rest:cargoUndeployRemote
    $ gradle :rest:cargoRedeployRemote

Access addresses:

**departmentrest.war**

    http://localhost:8080/departmentrest/department - get all departments [GET]
    http://localhost:8080/departmentrest/department - update department [PUT]
    http://localhost:8080/departmentrest/department - add new department [POST]
    http://localhost:8080/departmentrest/department/{id} - get department with id={id} [GET]
    http://localhost:8080/departmentrest/department/{id} - delete department with id={id} [DELETE]
    http://localhost:8080/departmentrest/department/{id}/employee - get emplyees from department with id={id} [GET]
    http://localhost:8080/departmentrest/employee - get all employees [GET]
    http://localhost:8080/departmentrest/employee - update employee [PUT]
    http://localhost:8080/departmentrest/employee - add new employee [POST]
    http://localhost:8080/departmentrest/employee/{id} - get employee with id={id} [GET]
    http://localhost:8080/departmentrest/employee/{id} - delete employee with id={id} [DELETE]



by Aliaksandr Parfianiuk

frombrest@gmail.com