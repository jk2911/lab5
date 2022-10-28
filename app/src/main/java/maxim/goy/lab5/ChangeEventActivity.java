package maxim.goy.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import maxim.goy.lab5.Model.Event;
import maxim.goy.lab5.Model.Image;

public class ChangeEventActivity extends AppCompatActivity {
    Event event;
    ImageView imageView;
    EditText name, description;
    DatePicker date;
    TimePicker time;
    private final int Pick_image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_event);

        event = (Event) getIntent().getExtras().getSerializable("event");

        imageView = findViewById(R.id.image);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        date = findViewById(R.id.calendar);
        time = findViewById(R.id.time);

        Image.getInstance().loadImageFromStorage(imageView, event.pathImages);
        name.setText(event.name);
        description.setText(event.description);
        date.updateDate(event.calendar.get(Calendar.YEAR),
                event.calendar.get(Calendar.MONTH),
                event.calendar.get(Calendar.DATE));
        time.setHour(event.calendar.get(Calendar.HOUR));
        time.setMinute(event.calendar.get(Calendar.MINUTE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
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
            imageView.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}