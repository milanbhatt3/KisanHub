package com.bhatt.milan.kisanhubdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhatt.milan.kisanhubdemo.R;
import com.bhatt.milan.kisanhubdemo.model.Temperature;

import java.util.List;

public class TemperatureAdapter extends RecyclerView.Adapter<TemperatureAdapter.TemperatureViewHolder> {

    private List<Temperature> temperatureList;
    private String filter;

    public TemperatureAdapter(List<Temperature> temperatureList, String filter) {
        this.temperatureList = temperatureList;
        this.filter = filter;
    }

    public void updateData(List<Temperature> temperatureList, String filter) {
        this.temperatureList = temperatureList;
        this.filter = filter;
        notifyDataSetChanged();
    }

    @Override
    public TemperatureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TemperatureViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(TemperatureViewHolder holder, int position) {
        holder.regionSelected.setText(temperatureList.get(position).getCountry());
        holder.yearSelected.setText(temperatureList.get(position).getYear());
        holder.filterSelected.setText(filter + ":");
        holder.filterValue.setText(getValueFromFilter(filter, position));
    }

    private String getValueFromFilter(String filter, int position) {
        if (filter.equalsIgnoreCase("Winter")) {
            return temperatureList.get(position).getWinter();
        } else if (filter.equalsIgnoreCase("Spring")) {
            return temperatureList.get(position).getSpring();
        } else if (filter.equalsIgnoreCase("Summer")) {
            return temperatureList.get(position).getSummer();
        } else if (filter.equalsIgnoreCase("Autumn")) {
            return temperatureList.get(position).getAutumn();
        } else if (filter.equalsIgnoreCase("Annual")) {
            return temperatureList.get(position).getAnnual();
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return temperatureList.size();
    }

    class TemperatureViewHolder extends RecyclerView.ViewHolder {

        TextView regionSelected;
        TextView yearSelected;
        TextView filterSelected;
        TextView filterValue;

        public TemperatureViewHolder(View itemView) {
            super(itemView);
            regionSelected = itemView.findViewById(R.id.region_selected);
            yearSelected = itemView.findViewById(R.id.year);
            filterSelected = itemView.findViewById(R.id.selectedFilter);
            filterValue = itemView.findViewById(R.id.filterValue);
        }
    }
}
