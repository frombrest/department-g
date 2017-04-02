Department (Gradle)
===================

The project consists of two cooperating applications, designed to work with information about employees and departments.

**departmentrest.war** — REST service providing access to a database of employees and departments.

**departmentweb.war** — Web application providing an interface for working with the REST service.

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

    $ gradle :webapp:cargoDeployRemote
    $ gradle :webapp:cargoUndeployRemote
    $ gradle :webapp:cargoRedeployRemote

Access addresses:

**departmentrest.war**

    http://localhost:8080/departmentrest/department
    http://localhost:8080/departmentrest/employee

**departmentweb.war**

    http://localhost:8080/departmentweb/departments

by Aliaksandr Parfianiuk

frombrest@gmail.com