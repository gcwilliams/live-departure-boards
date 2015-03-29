package uk.co.gcwilliams.service;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopScoreDocCollector;
import org.jvnet.hk2.annotations.Service;
import uk.co.gcwilliams.ldb.model.StationCode;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static uk.co.gcwilliams.util.FunctionalUtils.wrapFunction;

/**
 * The station codes implementation
 *
 * @author Gareth Williams
 */
@Service
public class StationCodesServiceImpl implements StationCodesService {

    private final StandardAnalyzer analyzer = new StandardAnalyzer();

    private final List<StationCode> stationCodes;

    private final IndexReader reader;

    /**
     * Default constructor
     *
     * @param stationCodes the station codes
     * @param reader the reader
     */
    @Inject
    public StationCodesServiceImpl(List<StationCode> stationCodes, IndexReader reader) {
        this.stationCodes = stationCodes;
        this.reader = reader;
    }

    @Override
    public Optional<StationCode> getCode(final String code) {
        return stationCodes.stream().filter((c) -> c.getStationId().get().equals(code)).findFirst();
    }

    @Override
    public List<StationCode> find(@QueryParam("name") String term) {
        try {
            Query query = new QueryParser("name", analyzer).parse(String.format("name:%s*", term));
            TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);
            IndexSearcher searcher = new IndexSearcher(reader);
            searcher.search(query, collector);
            return asList(collector.topDocs().scoreDocs)
                .stream()
                .map(wrapFunction(hit -> searcher.doc(hit.doc)))
                .map(doc -> getCode(doc.get("id")).get())
                .collect(Collectors.toList());
        } catch (ParseException | IOException ex) {
            throw new RuntimeException("Unable to process find request", ex);
        }
    }
}