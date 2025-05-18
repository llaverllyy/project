package main;

import java.time.Duration;

public class Pump extends Thread {
    private final int pumpId;
    private final Duration refuelDuration;
    private final Duration maintenanceDuration;
    private int carsServed = 0;

    public Pump(int pumpId, int refuelMinutes, int maintenanceMinutes) {
        this.pumpId = pumpId;
        this.refuelDuration = Duration.ofMinutes(refuelMinutes);
        this.maintenanceDuration = Duration.ofMinutes(maintenanceMinutes);
    }

    public int getPumpId() {
        return pumpId;
    }

    public int getCarsServed() {
        return carsServed;
    }

    public void run() {
        Duration currentTime = Duration.ZERO;
        Duration totalDuration = Duration.ofMinutes(GasStation.GASSTATION_DURATION_MIN);
        Duration maintenanceInterval = Duration.ofMinutes(GasStation.MAINTENANCE_INTERVAL_MIN);

        while (currentTime.compareTo(totalDuration) < 0) {
            if (currentTime.toMinutes() % maintenanceInterval.toMinutes() == 0 && !currentTime.isZero()) {
                currentTime = currentTime.plus(maintenanceDuration);
                continue;
            }
            carsServed++;
            currentTime = currentTime.plus(refuelDuration);
        }
    }
}