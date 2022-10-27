package maxim.goy.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

import maxim.goy.lab5.Model.Event;
import maxim.goy.lab5.Model.EventsList;
import maxim.goy.lab5.Model.Image;

public class AddEventActivity extends AppCompatActivity {
    TimePicker time;

    DatePicker date;
    ImageView imageview;
    EditText name, description;


    private final int Pick_image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Event event = (Event) getIntent().getExtras().getSerializable("event");


        time = findViewById(R.id.time);
        date = findViewById(R.id.calendar);
        imageview = findViewById(R.id.image);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);

        if (event == null)
            return;
        name.setText(event.name);
        description.setText(event.description
        );
        Image.getInstance().loadImageFromStorage(imageview, event.pathImages);
        date.updateDate(event.calendar.get(Calendar.YEAR), event.calendar.get(Calendar.MONTH),
                event.calendar.get(Calendar.DATE));
        time.setHour(event.calendar.get(Calendar.HOUR));
        time.setMinute(event.calendar.get(Calendar.MINUTE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNew:
                addEvent();
                return true;
            default:
                return true;
        }
    }

    public void setImage(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Pick_image);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        try {
            final Uri imageUri = imageReturnedIntent.getData();
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imageview.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addEvent() {
        Calendar calendar = new GregorianCalendar(date.getYear(), date.getMonth(), date.getDayOfMonth(),
                time.getHour(), time.getMinute());
        Event event = new Event(name.getText().toString(), description.getText().toString(), calendar);
        try {
            event.pathImages = Image.getInstance().
                    saveToInternalStorage(((BitmapDrawable) imageview.getDrawable()).getBitmap(),
                            this, event.getNameImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventsList eventsList = new EventsList(this);
        eventsList.AddEvent(event);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}