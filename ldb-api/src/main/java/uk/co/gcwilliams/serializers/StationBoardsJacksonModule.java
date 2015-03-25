package uk.co.gcwilliams.serializers;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.Module;
import org.codehaus.jackson.map.Serializers;
import org.joda.time.DateTime;
import uk.co.gcwilliams.ldb.model.Id;

import java.util.Optional;

/**
 * The station boards jackson module
 *
 * @author Gareth Williams (466567)
 */
public class StationBoardsJacksonModule extends Module {

    @Override
    public String getModuleName() {
        return StationBoardsJacksonModule.class.getSimpleName();
    }

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext context) {
        Serializers serializers = new StationBoardJacksonSerializersBuilder()
            .addSerializer(Id.class, new IdSerializer())
            .addSerializer(Optional.class, new OptionalSerializer())
            .addSerializer(DateTime.class, new DateTimeSerializer())
            .build();
        context.addSerializers(serializers);
    }
}