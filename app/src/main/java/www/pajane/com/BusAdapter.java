package www.pajane.com;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;

    ArrayList<Bus> list;

    public BusAdapter (Context context, ArrayList<Bus> list, RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.busonlist, parent, false);

        return new BusViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {

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
        return list.size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder{

        TextView name, price, seats, rating, destination, from, station;
        RatingBar rb;


        public BusViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            name = itemView.findViewById(R.id.busName);
            price = itemView.findViewById(R.id.price);
            seats = itemView.findViewById(R.id.seats);
            rating = itemView.findViewById(R.id.rating);
            destination = itemView.findViewById(R.id.destination);
            rb = itemView.findViewById(R.id.ratingBar);
            from = itemView.findViewById(R.id.from);
            station = itemView.findViewById(R.id.station);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(recyclerViewInterface != null)
                    {
                        int posi = getAdapterPosition();

                        if( posi != RecyclerView.NO_POSITION)
                        {
                            recyclerViewInterface.onBusCLicked(posi);
                        }
                    }

                }
            });
        }
    }
}
