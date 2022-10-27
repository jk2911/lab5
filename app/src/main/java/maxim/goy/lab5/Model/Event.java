package maxim.goy.lab5.Model;

import java.io.Serializable;
import java.util.Calendar;

public class Event implements Serializable {
    public String name;
    public String description;
    public Calendar calendar;
    public String pathImages;

    public Event(String name, String description, Calendar calendar, String pathImages) {
        this.name = name;
        this.description = description;
        this.calendar = calendar;
        this.pathImages = pathImages;
    }

    public Event(String name, String description, Calendar calendar) {
        this.name = name;
        this.description = description;
        this.calendar = calendar;
    }

    public String getNameImage() {
        return name + calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.DATE) +
                calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE);
    }

    public String getStringDate() {
        return "" + calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1) + "." + calendar.get(Calendar.DATE) +
                "     " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + "";
    }
}
