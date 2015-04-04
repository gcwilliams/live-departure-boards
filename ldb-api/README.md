# Live Departure Boards

### Live Departure Boards API

This project contains a `rest` API implementation of the core APIs

### Deployment

A servlet container e.g. `Tomcat` with `Java 8`

### Properties

#### LDB Key

Sign up for a free key from [NationalRail](http://realtime.nationalrail.co.uk/OpenLDBWSRegistration)

Then run the API with the following property `-Dldb-key=my-key-from-that-link-above`

Checkout the documentation for [NationalRail LDBWS](http://www.nationalrail.co.uk/46391.aspx)

#### Station Codes

Station codes are required, place the `ldb-resources/station-codes.properties` file into a known directory and add `-Dstation-codes=/path/to/codes.properties`

#### Logging

Configure the logging using the standard `log4j` system properties e.g. `-Dlog4j.configuration=file///etc/ldb/log4j.xml`