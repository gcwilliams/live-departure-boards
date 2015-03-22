## Live Departure Boards

### Modules

##### Core (ldb-core)

The core module, contains the model and interfaces to implement to create the service

##### Service (ldb-service)

The service module, contains the implementation of the core interfaces to provide access the NationalRail feed

##### API (ldb-api)

The API module, contains a `rest` service to provide access to the NationalRail feed

##### Client (ldb-client)

The client module, contains a client side `HTTP` implementation to access the `rest` services

##### App (ldb-app)

The android application, which uses the client and associates services

### Build

Build with Maven

    mvn clean package

### Java

`Java 8 JDK` required

### More Details

See the `README.md` in the individual projects for more information
