package uk.co.gcwilliams.api;

import com.google.common.collect.Lists;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import uk.co.gcwilliams.codes.StationCodes;
import uk.co.gcwilliams.ldb.model.StationCode;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import java.io.IOException;
import java.util.List;

/**
 * The station API
 *
 * @author Gareth Williams (466567)
 */
@RolesAllowed("ldb-api")
@Path("/stations")
public class StationApi {

    private final StandardAnalyzer analyzer = new StandardAnalyzer();

    private final Directory index = new RAMDirectory();

    private final StationCodes stationCodes;

    private IndexReader reader;

    /**
     * Default constructor
     *
     * @param stationCodes the station codes
     */
    @Inject
    public StationApi(StationCodes stationCodes) {
        this.stationCodes = stationCodes;
        init();
    }

    /**
     * Finds the station codes that match the specified name
     *
     * @param name the name
     * @return the matching station codes
     */
    @GET
    public List<StationCode> find(@QueryParam("name") String name) {

        List<StationCode> stationCodes = Lists.newArrayList();

        try {

            Query query = new QueryParser("name", analyzer).parse(String.format("name:%s*", name));
            IndexSearcher searcher = new IndexSearcher(reader);
            TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);
            searcher.search(query, collector);

            for (ScoreDoc hit : collector.topDocs().scoreDocs) {
                Document document = searcher.doc(hit.doc);
                stationCodes.add(this.stationCodes.getCode(document.get("id")).get());
            }

        } catch (ParseException | IOException ex) {
            throw new WebApplicationException(500);
        }

        return stationCodes;
    }

    /**
     * Creates the lucene index
     *
     */
    private void init() {

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);

        try {
            IndexWriter writer = new IndexWriter(index, config);

            for (StationCode stationCode : stationCodes.getCodes()) {
                Document doc = new Document();
                doc.add(new TextField("name", stationCode.getName(), Field.Store.YES));
                doc.add(new StringField("id", stationCode.getStationId().get(), Field.Store.YES));
                writer.addDocument(doc);
            }

            writer.close();

            reader = DirectoryReader.open(index);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
