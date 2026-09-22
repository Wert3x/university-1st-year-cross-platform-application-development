public class Main {
    public static void main(String[] args) {
        int[] speeds = {120, 118, 115, 116, 114, 112, 110, 105, 100};
        System.out.println("Исходные значения: ");
        for (int i = 0; i<speeds.length; i++) {

            System.out.print(speeds[i] + " ");
        }

        boolean brakingStarted = false;

        for (int i = 1; i < speeds.length; i++) {
            if (!brakingStarted) {
                if (speeds[i] < speeds[i - 1]) {
                    brakingStarted = true;
                }
            } else {
                speeds[i] = speeds[i] / 2;
            }
        }
        System.out.println("\nНовые значения: ");
        for (int i = 0; i < speeds.length; i++) {
            System.out.print(speeds[i] + " ");
        }
    }
}