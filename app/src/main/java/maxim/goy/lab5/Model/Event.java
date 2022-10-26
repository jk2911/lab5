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
}
