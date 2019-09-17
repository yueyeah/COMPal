package compal.tasks;

import java.io.Serializable;
import java.util.Date;

public abstract class Task implements Serializable {


    //***Class Properties/Variables***--------------------------------------------------------------------------------->

    public boolean isDone;
    protected String symbol;
    private int id;

    //for now, we only process dates in the format dd/mm/yyyy hhmm. See TaskList class for details
    private Date dateTime;
    private String taskType;
    private String description;
    private int hour;
    private int minute;
    private boolean hasReminder;

    //----------------------->



    //***CONSTRUCTORS***------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->


    /**
     * Constructor.
     * @param description the task's description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        hasReminder = false;
    }

    //----------------------->






    //***GETTER FUNCTIONS***--------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    public String getSymbol() {
        return symbol;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public int getHour() { return hour; }

    public int getMinute() { return minute; }

    public boolean isHasReminder() {
        return hasReminder;
    }

    //----------------------->




    //***SETTER FUNCTIONS***--------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------------->
    public void markAsDone() {
        isDone = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setHour(int hour) { this.hour = hour; }

    public void setMinute(int minute) { this.minute = minute; }

    public void setHasReminder() {
        this.hasReminder = true;
    }

    //----------------------->





}
