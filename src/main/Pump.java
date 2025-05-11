package main;

public class Pump extends Thread {
    private final int pumpId;
    private final Duration refuelDuration;
    private final Duration maintenanceDuration;
    private int carsServed = 0;

    public Pump(int pumpId, int refuelMinutes, int maintenanceMinutes) {
        this.pumpId = pumpId;
        this.refuelDuration = new Duration(refuelMinutes);
        this.maintenanceDuration = new Duration(maintenanceMinutes);
    }

    public int getPumpId() {
        return pumpId;
    }

    public int getCarsServed() {
        return carsServed;
    }

    @Override
    public void run() {
        Duration currentTime = new Duration(0);
        Duration totalDuration = new Duration(GasStation.GASSTATION_DURATION);
        Duration maintenanceInterval = new Duration(GasStation.MAINTENANCE_INTERVAL);

        while (currentTime.lessThan(totalDuration)) {
            if (currentTime.isMultipleOf(maintenanceInterval)) {
                currentTime = currentTime.add(maintenanceDuration);
                continue;
            }
            carsServed++;
            currentTime = currentTime.add(refuelDuration);
        }
    }
}