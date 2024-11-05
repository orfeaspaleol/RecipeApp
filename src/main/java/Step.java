public class Step {
    private String description;
    private int timeInMinutes;

    public Step(String description, int timeInMinutes) {
        this.description = description;
        this.timeInMinutes = timeInMinutes;
    }

    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    @Override
    public String toString() {
        return description + (timeInMinutes > 0 ? " (Time: " + timeInMinutes + " minutes)" : "");
    }
}
