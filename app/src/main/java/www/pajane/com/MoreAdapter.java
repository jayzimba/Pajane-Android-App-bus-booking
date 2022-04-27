package www.pajane.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.MoreViewHolder> {
    Context context;

    ArrayList<Bus> list;
    ArrayList<Bus> filteredBusesList;

    public MoreAdapter(Context context, ArrayList<Bus> list) {
        this.context = context;
        this.list = list;
        this.filteredBusesList = list;
    }

    @NonNull
    @Override
    public MoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.busdepart, parent, false);
        return new MoreViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreViewHolder holder, int position) {
        Bus bus = list.get(position);
        String ratingString = Float.toString(bus.getRating());

        holder.name.setText(bus.getName().toUpperCase());
        holder.destination.setText(bus.getDestination().toUpperCase());
        holder.price.setText(Float.toString(bus.getPrice()));
        holder.seats.setText(Integer.toString(bus.getSeats()));
        holder.rating.setText(ratingString);
        holder.rb.setRating(bus.getRating());
        holder.from.setText(bus.getFrom().toUpperCase());
        holder.station.setText(bus.getStation());
    }

    @Override
    public int getItemCount() {
        return filteredBusesList.size();
    }


    public class MoreViewHolder extends RecyclerView.ViewHolder{

        TextView name, price, seats, rating, destination, from, station;
        RatingBar rb;


        public MoreViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            seats = itemView.findViewById(R.id.seats);
            rating = itemView.findViewById(R.id.rating);
            destination = itemView.findViewById(R.id.destination);
            rb = itemView.findViewById(R.id.ratingBar);
            from = itemView.findViewById(R.id.from);
            station = itemView.findViewById(R.id.station);
        }
    }


    public Filter getFilter(){

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String key = charSequence.toString();
                if(key.isEmpty())
                {
                    filteredBusesList = list;
                }
                else
                {

                    ArrayList<Bus> listFiltered = new ArrayList<>();
                    for(Bus row: list)
                    {
                        if(row.getFrom().toLowerCase().contains(key.toLowerCase()))
                        {
                            listFiltered.add(row);
                        }
                    }

                    filteredBusesList = listFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredBusesList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredBusesList = (ArrayList<Bus>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
