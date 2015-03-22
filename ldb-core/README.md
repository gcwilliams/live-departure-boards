# Live Departure Boards

### Live Departure Boards Core

This project contains the model and the associated builders to create instance of the model, it also contains the interfaces that a service should implement.

The `ldb-service` and `ldb-client` projects implement these interfaces.

### Example

    ServiceDetailBuilder builder = new ServiceDetailBuilder()
        .setCurrentStation(stationBuilder)
        .setGeneratedAt(DateTime.now())
        .setOperator("Southern")
        .setOperatorCode("STH")
        .addPreviousCallingPoints(singletonList(previousBuilder))
        .addSubsequentCallingPoints(singletonList(subsequentBuilder));

    ServiceDetail serviceDetail = builder.build();