import java.util.Scanner;

public class lab1_31 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите скорость: ");
        int speed = scanner.nextInt();

        System.out.print("Введите лимит скорости: ");
        int limit = scanner.nextInt();

        System.out.print("Школьная зона? (true/false): ");
        boolean w_zone = scanner.nextBoolean();

        System.out.print("Ранняя оплата? (true/false): ");
        boolean early_pay = scanner.nextBoolean();

        int over_speed = speed - limit;
        int result = 0;

        if (over_speed > 0) {
            int base_fine;
            if (over_speed <= 20) {
                base_fine = 0;
            } else if (over_speed <= 40) {
                base_fine = 500;
            } else if (over_speed <= 60) {
                base_fine = 1500;
            } else {
                base_fine = 5000;
            }

            if (base_fine != 0) {
                int fine_after = w_zone ? base_fine * 2 : base_fine;

                if (early_pay && base_fine != 5000) {
                    result = fine_after / 2;
                } else {
                    result = fine_after;
                }
            }
        }

        System.out.println("Штраф: " + result);

        scanner.close();
    }
}