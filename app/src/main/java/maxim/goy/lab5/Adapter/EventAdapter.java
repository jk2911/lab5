package maxim.goy.lab5.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import maxim.goy.lab5.Model.Event;
import maxim.goy.lab5.Model.Image;
import maxim.goy.lab5.R;

public class EventAdapter extends ArrayAdapter {

    private LayoutInflater inflater;
    private int layout;
    private List<Event> events;

    public EventAdapter(@NonNull Context context, int resource, List<Event> events) {
        super(context, resource, events);
        this.events = events;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView nameView = view.findViewById(R.id.name);
        TextView date = view.findViewById(R.id.date);

        Event event = events.get(position);


        Image.getInstance().loadImageFromStorage(imageView, event.pathImages);
        nameView.setText(event.name);
        date.setText(event.getStringDate());

        return view;
    }
}
