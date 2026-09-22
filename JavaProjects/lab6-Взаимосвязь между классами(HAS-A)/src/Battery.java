public class Battery {
    private final int capacity;
    private int currentCharge;

    public Battery(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Емкость должна быть больше 0!");
        }
        this.capacity = capacity;
        this.currentCharge = capacity;
    }

    public boolean hasPower() { return currentCharge > 0; }

    public void consume() {
        if (currentCharge > 0) {
            currentCharge -= 10;
        }
        if (currentCharge < 0) {
            currentCharge = 0;
        }
    }

    public int getCharge() { return currentCharge; }

    @Override
    public String toString() {
        return String.format("Заряд: %d/%d mAh", currentCharge, capacity);
    }
}