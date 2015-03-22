package uk.co.gcwilliams.serializers;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.Module;

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
        context.addSerializers(new StationBoardJacksonSerializers(
            new IdSerializer(),
            new OptionalSerializer(),
            new DateTimeSerializer()
        ));
    }
}