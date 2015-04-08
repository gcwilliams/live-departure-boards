package uk.co.gcwilliams.ldb.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import uk.co.gcwilliams.ldb.app.R;
import uk.co.gcwilliams.ldb.model.StationCode;
import uk.co.gcwilliams.ldb.service.StationCodes;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;

/**
 * The station code adapter
 *
 * @author Gareth Williams
 */
public class StationCodeAdapter extends BaseAdapter implements Filterable {

    private final Filter filter = new StationCodeFilter();

    private final LayoutInflater inflater;

    private final StationCodes stationCodes;

    private List<StationCode> items;

    private Optional<StationCode> selected = Optional.absent();

    @Inject
    public StationCodeAdapter(LayoutInflater inflater, StationCodes stationCodes) {
        this.inflater = inflater;
        this.stationCodes = stationCodes;
        this.items = Collections.emptyList();
    }

    /**
     * Gets the selected station code
     *
     * @return the selected station code
     */
    public Optional<StationCode> getSelected() {
        return selected;
    }

    /**
     * Sets the selected station code
     *
     * @param selected the selected station code
     */
    public void setSelected(StationCode selected) {
        this.selected = Optional.of(selected);
        this.items = singletonList(selected);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.station_code, parent, false);
        } else {
            view = convertView;
        }

        TextView text = (TextView)view.findViewById(R.id.station_name);
        text.setText(items.get(position).getName());
        return view;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class StationCodeFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String term = constraint != null ? constraint.toString() : "";
            List<StationCode> codes = Strings.isNullOrEmpty(term)
                ? Collections.<StationCode>emptyList()
                : stationCodes.suggestStationCodes(term);
            FilterResults results = new FilterResults();
            results.count = codes.size();
            results.values = codes;
            return results;
        }

        @Override
        @SuppressWarnings("unchecked")
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items = (List<StationCode>)results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            StationCode code = (StationCode)resultValue;
            selected = Optional.fromNullable(code);
            return code.getName();
        }
    }
}
