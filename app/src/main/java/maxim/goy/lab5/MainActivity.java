package maxim.goy.lab5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import maxim.goy.lab5.Adapter.EventAdapter;
import maxim.goy.lab5.Model.Event;
import maxim.goy.lab5.Model.EventsList;

public class MainActivity extends AppCompatActivity {
    ListView events;
    EventsList eventsList;
    final int DIALOG_DELETE = 1;
    Event selectedItem;
    EventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        events = findViewById(R.id.events);
        registerForContextMenu(events);

        eventsList = new EventsList(this);

        Intent intent = new Intent(this, ChangeEventActivity.class);

        adapter = new EventAdapter(this, R.layout.list_item, eventsList.events);
        events.setAdapter(adapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Event event = (Event) parent.getItemAtPosition(i);
                intent.putExtra("event", event);
                startActivity(intent);
            }
        };
        events.setOnItemClickListener(itemClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(this, AddEventActivity.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        selectedItem = (Event) events.getItemAtPosition(info.position);
        switch (item.getItemId()) {
            case R.id.info:
                InfoEvent();
                return true;
            case R.id.change:
                ChangeEvent();
                return true;
            case R.id.delete:
                showDialog(DIALOG_DELETE);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void ChangeEvent() {
        Intent intent = new Intent(this, ChangeEventActivity.class);
        intent.putExtra("event", selectedItem);
        startActivity(intent);
    }

    public void InfoEvent() {
        Intent intent = new Intent(this, EventInfoActivity.class);
        intent.putExtra("event", selectedItem);
        startActivity(intent);
    }

    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setTitle("Удаление");
        al.setMessage("Вы действительно хотите удалить мероприятие?");
        al.setPositiveButton("Да", clickListener);
        al.setNegativeButton("Нет", clickListener);
        return al.create();
    }

    DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case DialogInterface.BUTTON_POSITIVE:
                    eventsList.RemoveEvent(selectedItem);
                    adapter.notifyDataSetChanged();
                    return;
                case DialogInterface.BUTTON_NEGATIVE:
                    return;

            }
        }
    };
}