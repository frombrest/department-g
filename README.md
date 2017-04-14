Department SOAP (Gradle)
========================

The project consists of two cooperating applications, designed to work with information about employees and departments.

**department-soap.war** — SOAP service providing access to a database of employees and departments.

**departmentweb.war** — Web application providing an interface for working with the SOAP service.

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

    $ gradle :ws:cargoDeployRemote
    $ gradle :ws:cargoUndeployRemote
    $ gradle :ws:cargoRedeployRemote

    $ gradle :webapp:cargoDeployRemote
    $ gradle :webapp:cargoUndeployRemote
    $ gradle :webapp:cargoRedeployRemote

Access addresses:

**department-soap.war**

    http://localhost:8080/department-soap/messages.wsdl

**departmentweb.war**

    http://localhost:8080/departmentweb/departments

by Aliaksandr Parfianiuk

frombrest@gmail.com