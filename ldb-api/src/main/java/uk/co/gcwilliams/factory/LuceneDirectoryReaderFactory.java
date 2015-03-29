package uk.co.gcwilliams.factory;

import com.google.common.collect.Lists;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.glassfish.hk2.api.Factory;
import uk.co.gcwilliams.ldb.model.StationCode;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static uk.co.gcwilliams.util.FunctionalUtils.wrapConsumer;
import static uk.co.gcwilliams.util.FunctionalUtils.wrapFunction;

/**
 * The lucene directory reader factory
 *
 * Created by GWilliams on 27/03/2015.
 */
public class LuceneDirectoryReaderFactory implements Factory<DirectoryReader> {

    private final Function<Directory, DirectoryReader> open = wrapFunction(DirectoryReader::open);

    private final Consumer<Closeable> close = wrapConsumer(Closeable::close);

    private final Directory index = new RAMDirectory();

    private final List<StationCode> stationCodes;

    /**
     * Default constructor
     *
     * @param stationCodes the station codes
     */
    @Inject
    public LuceneDirectoryReaderFactory(List<StationCode> stationCodes) {
        this.stationCodes = stationCodes;
    }

    @Override
    @Singleton
    public DirectoryReader provide() {
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, new StandardAnalyzer());
        try (IndexWriter writer = new IndexWriter(index, config)) {
            stationCodes.stream().map(sc -> Lists.<IndexableField>newArrayList(
                    new TextField("name", sc.getName(), Field.Store.YES),
                    new StringField("id", sc.getStationId().get(), Field.Store.YES)
            )).forEach(wrapConsumer(writer::addDocument));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return open.apply(index);
    }

    @Override
    public void dispose(DirectoryReader reader) {
        close.accept(reader);
    }
}
