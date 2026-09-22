public class Bulb {
    private final int maxBrightness;
    private boolean isGlowing;

    public Bulb(int maxBrightness) {
        if (maxBrightness <= 0) {
            throw new IllegalArgumentException("Яркость должна быть больше 0!");
        }
        this.maxBrightness = maxBrightness;
        this.isGlowing = false;
    }

    public void lightUp() { this.isGlowing = true; }
    public void turnOff() { this.isGlowing = false; }

    public int getBrightness() {
        return isGlowing ? maxBrightness : 0;
    }

    @Override
    public String toString() {
        return String.format("Лампочка (%d люмен)", maxBrightness);
    }
}