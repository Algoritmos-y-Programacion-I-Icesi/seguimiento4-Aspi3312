package model;

import java.util.ArrayList;

public class Device {
    private String serial;
    private double consumptionKWh;
    private ArrayList<Event> events;

    public Device(String serial, double consumptionKWh) {
        this.serial = serial;
        this.consumptionKWh = consumptionKWh;
        this.events = new ArrayList<>();
    }

    public String getSerial() {
        return serial;
    }

    public double getConsumptionKWh() {
        return consumptionKWh;
    }

    public void setConsumptionKWh(double consumptionKWh) {
        this.consumptionKWh = consumptionKWh;
    }

    public void addEvent(String date, double hours) {
        events.add(new Event(date, hours));
    }

    public double calculateTotalConsumption() {
        double totalHours = 0;
        for (Event e : events) {
            totalHours += e.getHours();
        }
        return totalHours * consumptionKWh;
    }
}