package uk.co.gcwilliams.ldb.service;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import uk.co.gcwilliams.ldb.model.Id;
import uk.co.gcwilliams.ldb.model.Service;
import uk.co.gcwilliams.ldb.model.ServiceDetail;
import uk.co.gcwilliams.ldb.model.StationBoard;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.model.builder.DestinationBuilder;
import uk.co.gcwilliams.ldb.model.builder.PreviousCallingPointBuilder;
import uk.co.gcwilliams.ldb.model.builder.ServiceBuilder;
import uk.co.gcwilliams.ldb.model.builder.ServiceDetailBuilder;
import uk.co.gcwilliams.ldb.model.builder.StationBoardBuilder;
import uk.co.gcwilliams.ldb.model.builder.StationBuilder;
import uk.co.gcwilliams.ldb.model.builder.SubsequentCallingPointBuilder;
import uk.co.gcwilliams.ldb.stubs.ArrayOfCallingPoints2;
import uk.co.gcwilliams.ldb.stubs.ArrayOfServiceItems2;
import uk.co.gcwilliams.ldb.stubs.FilterType;
import uk.co.gcwilliams.ldb.stubs.GetBoardRequestParams;
import uk.co.gcwilliams.ldb.stubs.GetServiceDetailsRequestParams;
import uk.co.gcwilliams.ldb.stubs.LDBServiceSoap;
import uk.co.gcwilliams.ldb.stubs.ServiceDetails2;
import uk.co.gcwilliams.ldb.stubs.ServiceDetailsResponseType;
import uk.co.gcwilliams.ldb.stubs.ServiceItem2;
import uk.co.gcwilliams.ldb.stubs.ServiceLocation2;
import uk.co.gcwilliams.ldb.stubs.StationBoard2;
import uk.co.gcwilliams.ldb.stubs.StationBoardResponseType;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import java.util.List;

import static uk.co.gcwilliams.ldb.service.TimeParseUtil.tryParseTime;

/**
 * The departure board service
 *
 * @author Gareth Williams (466567)
 */
public class StationBoardsImpl implements StationBoards {

    private final LDBServiceSoap ldbServiceSoap;

    public StationBoardsImpl(LDBServiceSoap ldbServiceSoap, String accessToken) {
        this.ldbServiceSoap = ldbServiceSoap;
        BindingProvider bindingProvider = ((BindingProvider)this.ldbServiceSoap);
        List<Handler> handlers = bindingProvider.getBinding().getHandlerChain();
        handlers.add(new AuthenticationHandler(accessToken));
        bindingProvider.getBinding().setHandlerChain(handlers);
    }

    @Override
    public StationBoard getArrivalBoard(StationCode stationCode) {
        return mapStationBoard(ldbServiceSoap.getArrivalBoard(getRequestParameters(stationCode, 20)));
    }

    @Override
    public StationBoard getArrivalBoard(StationCode stationCode, StationCode from) {
        GetBoardRequestParams parameters = getRequestParameters(stationCode, 20);
        parameters.setFilterCrs(from.getStationId().get());
        parameters.setFilterType(FilterType.FROM);
        return mapStationBoard(ldbServiceSoap.getArrivalBoard(parameters));
    }

    @Override
    public StationBoard getDepartureBoard(StationCode stationCode) {
        return mapStationBoard(ldbServiceSoap.getDepartureBoard(getRequestParameters(stationCode, 20)));
    }

    @Override
    public StationBoard getDepartureBoard(StationCode stationCode, StationCode to) {
        GetBoardRequestParams parameters = getRequestParameters(stationCode, 20);
        parameters.setFilterCrs(to.getStationId().get());
        parameters.setFilterType(FilterType.TO);
        return mapStationBoard(ldbServiceSoap.getArrivalBoard(parameters));
    }

    @Override
    public ServiceDetail getServiceDetail(Id<Service> serviceId) {
        GetServiceDetailsRequestParams parameters = new GetServiceDetailsRequestParams();
        parameters.setServiceID(serviceId.get());
        ServiceDetailsResponseType response = ldbServiceSoap.getServiceDetails(parameters);
        ServiceDetails2 details = response.getGetServiceDetailsResult();
        ServiceDetailBuilder builder = new ServiceDetailBuilder();
        for (List<PreviousCallingPointBuilder> callingPoints : mapPreviousCallingPoints(
                details.getPreviousCallingPoints().getCallingPointList())) {
            builder.addPreviousCallingPoints(callingPoints);
        }
        for (List<SubsequentCallingPointBuilder> callingPoints : mapSubsequentCallingPoints(
                details.getSubsequentCallingPoints().getCallingPointList())) {
            builder.addSubsequentCallingPoints(callingPoints);
        }
        return builder.setGeneratedAt(new DateTime(details.getGeneratedAt().toGregorianCalendar().getTime()))
            .setCurrentStation(mapStation(details.getLocationName(), details.getCrs()))
            .setStandardTimeOfArrival(tryParseTime(details.getSta()))
            .setEstimatedTimeOfArrival(tryParseTime(details.getEta()))
            .setStandardTimeOfDeparture(tryParseTime(details.getSta()))
            .setEstimatedTimeOfDeparture(tryParseTime(details.getEtd()))
            .setOperator(details.getOperator())
            .setOperatorCode(details.getOperatorCode())
            .setPlatform(tryParseInt(details.getPlatform()))
            .build();
    }

    private GetBoardRequestParams getRequestParameters(StationCode stationCode, int count) {
        GetBoardRequestParams parameters = new GetBoardRequestParams();
        parameters.setTimeWindow(120);
        parameters.setNumRows(count);
        parameters.setCrs(stationCode.getStationId().get());
        return parameters;
    }

    private static StationBoard mapStationBoard(StationBoardResponseType response) {

        StationBoard2 board = response.getGetStationBoardResult();

        StationBoardBuilder builder = new StationBoardBuilder();
        builder.setGeneratedAt(new DateTime(board.getGeneratedAt().toGregorianCalendar().getTime()));
        builder.setStation(mapStation(board.getLocationName(), board.getCrs()));

        List<ServiceBuilder> services = Lists.newArrayList();

        ArrayOfServiceItems2 serviceItems = board.getTrainServices();
        if (serviceItems != null) {
            for (ServiceItem2 service : board.getTrainServices().getService()) {
                services.add(mapService(service));
            }
        }

        return builder.setServices(services).build();
    }

    private static List<List<PreviousCallingPointBuilder>> mapPreviousCallingPoints(List<ArrayOfCallingPoints2> allCallingPoints) {
        List<List<PreviousCallingPointBuilder>> allMappedCallingPoints = Lists.newArrayList();
        for (ArrayOfCallingPoints2 callingPoints : allCallingPoints) {
            List<PreviousCallingPointBuilder> mappedCallingPoints = Lists.newArrayList();
            for (uk.co.gcwilliams.ldb.stubs.CallingPoint callingPoint : callingPoints.getCallingPoint()) {
                mappedCallingPoints.add(new PreviousCallingPointBuilder()
                    .setStation(mapStation(callingPoint.getLocationName(), callingPoint.getCrs()))
                    .setStandardDepartureTime(tryParseTime(callingPoint.getSt()))
                    .setActualDepartureTime(tryParseTime(callingPoint.getAt())));
            }
            allMappedCallingPoints.add(mappedCallingPoints);
        }
        return allMappedCallingPoints;
    }

    private static List<List<SubsequentCallingPointBuilder>> mapSubsequentCallingPoints(List<ArrayOfCallingPoints2> allCallingPoints) {
        List<List<SubsequentCallingPointBuilder>> allMappedCallingPoints = Lists.newArrayList();
        for (ArrayOfCallingPoints2 callingPoints : allCallingPoints) {
            List<SubsequentCallingPointBuilder> mappedCallingPoints = Lists.newArrayList();
            for (uk.co.gcwilliams.ldb.stubs.CallingPoint callingPoint : callingPoints.getCallingPoint()) {
                mappedCallingPoints.add(new SubsequentCallingPointBuilder()
                        .setStation(mapStation(callingPoint.getLocationName(), callingPoint.getCrs()))
                        .setStandardDepartureTime(tryParseTime(callingPoint.getSt()))
                        .setEstimatedDepartureTime(tryParseTime(callingPoint.getEt())));
            }
            allMappedCallingPoints.add(mappedCallingPoints);
        }
        return allMappedCallingPoints;
    }

    private static ServiceBuilder mapService(ServiceItem2 service) {

        ServiceLocation2 origin = Iterables.getFirst(service.getOrigin().getLocation(), null);
        ServiceLocation2 departure = Iterables.getFirst(service.getDestination().getLocation(), null);

        return new ServiceBuilder()
            .setOrigin(mapStation(origin))
            .setDestination(mapLocation(departure))
            .setOperator(service.getOperator())
            .setOperatorCode(service.getOperatorCode())
            .setPlatform(tryParseInt(service.getPlatform()))
            .setEstimatedTimeOfArrival(tryParseTime(service.getEta()))
            .setStandardTimeOfArrival(tryParseTime(service.getSta()))
            .setStandardTimeOfDeparture(tryParseTime(service.getStd()))
            .setEstimatedTimeOfDeparture(tryParseTime(service.getEtd()))
            .setServiceId(service.getServiceID());
    }

    private static DestinationBuilder mapLocation(ServiceLocation2 serviceLocation) {
        return new DestinationBuilder().setStation(mapStation(serviceLocation)).setVia(serviceLocation.getVia());
    }

    private static StationBuilder mapStation(ServiceLocation2 serviceLocation) {
        return mapStation(serviceLocation.getLocationName(), serviceLocation.getCrs());
    }

    private static StationBuilder mapStation(String stationName, String stationId) {
        return new StationBuilder().setName(stationName).setStationId(stationId);
    }

    private static Integer tryParseInt(String maybeInt) {
        try {
            return Integer.parseInt(maybeInt, 10);
        } catch (NumberFormatException ex) {
            // ignore
        }
        return null;
    }
}
