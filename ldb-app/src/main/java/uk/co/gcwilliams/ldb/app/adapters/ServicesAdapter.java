package uk.co.gcwilliams.ldb.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import uk.co.gcwilliams.ldb.app.R;
import uk.co.gcwilliams.ldb.model.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Gareth Williams
 */
public class ServicesAdapter extends BaseAdapter {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.shortTime();

    private static final String EMPTY = "";

    private final LayoutInflater inflater;

    private final List<Service> services;

    public ServicesAdapter(List<Service> services, LayoutInflater inflater) {
        this.inflater = inflater;
        this.services = Collections.unmodifiableList(services);
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.service_item, parent, false);
        } else {
            view = convertView;
        }

        Service service = services.get(position);

        setText(view, R.id.destination, service.getDestination().getStation().getName());
        setText(view, R.id.platform, service.getPlatform().isPresent()
            ? String.valueOf(service.getPlatform().get())
            : EMPTY);
        setText(view, R.id.due, service.getEstimatedTimeOfDeparture().isPresent()
            ? TIME_FORMATTER.print(service.getEstimatedTimeOfDeparture().get())
            : EMPTY);

        return view;
    }

    private static void setText(View view, int id, String text) {
        ((TextView)view.findViewById(id)).setText(text);
    }
}
