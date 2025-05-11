package main;

public class Duration {
    private final int minutes;

    public Duration(int minutes) {
        this.minutes = minutes;
    }

    public Duration add(Duration other) {
        return new Duration(this.minutes + other.minutes);
    }

    public boolean lessThan(Duration other) {
        return this.minutes < other.minutes;
    }

    public boolean isMultipleOf(Duration other) {
        return this.minutes > 0 && this.minutes % other.minutes == 0;
    }

    @Override
    public String toString() {
        return minutes + " мин";
    }
}