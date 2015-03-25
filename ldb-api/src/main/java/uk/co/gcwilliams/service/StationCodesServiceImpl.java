package uk.co.gcwilliams.service;

import com.google.common.collect.Lists;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.model.builder.StationCodeBuilder;

import javax.ws.rs.QueryParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static uk.co.gcwilliams.util.FunctionalUtils.toImmutableList;
import static uk.co.gcwilliams.util.FunctionalUtils.wrapConsumer;
import static uk.co.gcwilliams.util.FunctionalUtils.wrapFunction;

/**
 * The station codes implementation
 *
 * @author Gareth Williams (466567)
 */
public class StationCodesServiceImpl implements StationCodesService {

    private static final Pattern STATION_PATTERN = Pattern.compile("^(.*),(.*)$");

    private static final List<StationCode> STATION_CODES;

    private static final StandardAnalyzer ANALYZER = new StandardAnalyzer();

    private static final Directory INDEX = new RAMDirectory();

    private static final IndexReader READER;

    static {

        try (InputStream inputStream = StationCodesServiceImpl.class.getResourceAsStream("station-codes.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            STATION_CODES = reader.lines().map(STATION_PATTERN::matcher).filter(Matcher::matches).map(m ->
                new StationCodeBuilder().setName(m.group(1).trim()).setStationId(m.group(2).trim()).build()
            ).collect(toImmutableList());
        } catch (IOException ex) {
            throw new RuntimeException("Unable to initiate station codes");
        }

        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, ANALYZER);
        try (IndexWriter writer = new IndexWriter(INDEX, config)) {
            STATION_CODES.stream().map(sc -> Lists.<IndexableField>newArrayList(
                new TextField("name", sc.getName(), Field.Store.YES),
                new StringField("id", sc.getStationId().get(), Field.Store.YES)
            )).forEach(wrapConsumer(writer::addDocument));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        try {
            READER = DirectoryReader.open(INDEX);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<StationCode> getCode(final String code) {
        return STATION_CODES.stream().filter((c) -> c.getStationId().get().equals(code)).findFirst();
    }

    @Override
    public List<StationCode> find(@QueryParam("name") String term) {
        try {
            Query query = new QueryParser("name", ANALYZER).parse(String.format("name:%s*", term));
            TopScoreDocCollector collector = TopScoreDocCollector.create(10, true);
            IndexSearcher searcher = new IndexSearcher(READER);
            searcher.search(query, collector);
            return asList(collector.topDocs().scoreDocs)
                .stream()
                .map(wrapFunction(hit -> searcher.doc(hit.doc)))
                .map(doc -> getCode(doc.get("id")).get())
                .collect(Collectors.toList());
        } catch (ParseException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}