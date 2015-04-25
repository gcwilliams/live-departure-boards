package uk.co.gcwilliams.serializers;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.common.base.Optional;
import uk.co.gcwilliams.ldb.model.Id;

/**
 * The station boards jackson module
 *
 * @author Gareth Williams
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
            .build();
        context.addSerializers(serializers);
    }
}