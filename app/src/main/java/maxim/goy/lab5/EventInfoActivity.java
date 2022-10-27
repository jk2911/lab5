package maxim.goy.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import maxim.goy.lab5.Model.Event;
import maxim.goy.lab5.Model.Image;

public class EventInfoActivity extends AppCompatActivity {
    TextView name, description, date, time;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        Event event = (Event) getIntent().getExtras().getSerializable("event");

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        imageView = findViewById(R.id.image);

        name.setText(event.name);
        description.setText(event.description);
        date.setText(event.getStringDate());
        time.setText(event.getStringTime());
        Image.getInstance().loadImageFromStorage(imageView, event.pathImages);
    }
}