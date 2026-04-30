package model;

public class Event {
    private String date;
    private double hours;

    public Event(String date, double hours) {
        this.date = date;
        this.hours = hours;
    }

    public double getHours() {
        return hours;
    }
}