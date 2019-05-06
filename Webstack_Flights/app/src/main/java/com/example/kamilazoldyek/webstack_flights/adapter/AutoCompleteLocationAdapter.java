package com.example.kamilazoldyek.webstack_flights.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.kamilazoldyek.webstack_flights.R;

import java.util.ArrayList;
import java.util.List;

import io.swagger.client.model.Location;

public class AutoCompleteLocationAdapter extends ArrayAdapter<Location> {
    private List<Location> locationListCOPY;

    public AutoCompleteLocationAdapter(Context context, List<Location> locationList) {
        super(context, 0, locationList);
        locationListCOPY = new ArrayList<>(locationList);
        Log.i("Test", "hello autocomplete");

    }

    public Filter getFilter() {
        return locationFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.location_autocomplete_row, parent, false);
        }
        TextView airportName = convertView.findViewById(R.id.locationName);
        TextView airportCode = convertView.findViewById(R.id.locationCode);
        TextView airportCity = convertView.findViewById(R.id.locationCity);
        TextView airportCountry = convertView.findViewById(R.id.locationCountry);

        Location location = getItem(position);

        if(location != null){
            airportName.setText(location.getName());
            airportCode.setText(location.getCode());
            airportCity.setText(location.getCity());
            airportCountry.setText(location.getCountry());
        }

        return convertView;
    }

    private Filter locationFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            FilterResults results = new FilterResults();
            List<Location> suggestions = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                suggestions.addAll(locationListCOPY);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

//              If user input contains letters from airport name, city, country or code
                for (Location item : locationListCOPY) {
                    if (item.getName().toLowerCase().contains(filterPattern)
                            || item.getCode().toLowerCase().contains(filterPattern)
                            || item.getCity().toLowerCase().contains(filterPattern)
                            || item.getCountry().toLowerCase().contains(filterPattern)
                    ) {
                        suggestions.add(item);
                    }

                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            clear();
            addAll((List) filterResults.values);
            notifyDataSetChanged();

        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            CharSequence result = ((Location) resultValue).getCode() + " - " + ((Location) resultValue).getName();
            return result;
        }
    };
}
