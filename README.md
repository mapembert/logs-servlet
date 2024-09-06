# Logs Servlet Project

## Overview

This project contains a Java servlet application that can be deployed on IBM WebSphere Liberty. The servlet responds with a simple message when accessed via HTTP.

## Setup Instructions

### Prerequisites

Before setting up this project, ensure you have the following installed:

- **Java Development Kit (JDK)**: Required to compile and run the Java servlet. Make sure you have JDK 8 or later.
- **Apache Maven**: Used for building the project. [Download Maven](https://maven.apache.org/download.cgi) and set it up if it's not already installed.
- **IBM WebSphere Liberty**: Required for deploying the servlet. Follow the [Liberty installation guide](https://www.ibm.com/docs/en/was-liberty) if not already installed.

### Local Setup

1. **Clone the Repository**
   Clone the repository from GitHub to your local machine:
   ```bash
   git clone https://github.com/mapembert/logs-servlet.git
   ```
---
2. Navigate to the Project Directory
    ```bash
    cd logs-servlet
    ```
---
3. Build the Project
    Ensure you are in the project root directory and build the project using Maven:
    ```bash
    mvn clean package
    ```
    This will generate a logs-servlet.war file in the target directory.

---
4. Deploy the Servlet

    Copy the logs-servlet.war file to a WebSphere Liberty deployment directory. The following was chosen in this example /opt/IBM/tririga/ (adjust based on your configuration).

    **First move the war file into the vagrant directory!**

    ```bash
    cd /vagrant
    cp logs-servlet.war /opt/IBM/tririga/
    ```
---
5. Edit the WebSphere Liberty server.xml file.

    Add the following configuration to the server.xml file in the WebSphere Liberty server directory:

    ```xml
    <server description="IBM TRIRIGA Application Platform">
    ...

        <application location="/opt/IBM/tririga/logs-servlet.war" contextRoot="/logs"/>
    ...
    </server>
    ```
    Restart the WebSphere Liberty server to deploy the servlet.
---
6. Access the Servlet

    After deployment, access the servlet in your web browser using the URL:

    ```bash
    http://localhost:9080/logs-servlet/     # This is the default context root

    http://localhost:9080/logs-servlet/logs # This is the context root specified in the server.xml file
    ```

    You should see the message: "Hello, this is the LogsServlet."

### Project Structure
```bash
logs-servlet/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── serco/
│   │   │           └── logs/
│   │   │               └── LogsServlet.java
│   │   ├── resources/
│   │   └── webapp/
│   │       ├── index.jsp
│   │       └── WEB-INF/
│   │           └── web.xml
│   ├── target/
│   │   └── logs-servlet.war
│   └── pom.xml
│
├── README.md
└── folder-structure.txt
```