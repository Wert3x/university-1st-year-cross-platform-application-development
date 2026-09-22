import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите количество деталей: ");
        int n = scanner.nextInt();

        int[] details = new int[n];
        System.out.println("Введите значения деталей:");
        for (int i = 0; i < n; i++) {
            details[i] = scanner.nextInt();
        }

        if (is_details_valid(details)) {
            System.out.println("Партия принята");
        } else {
            System.out.println("Партия отклонена");
        }

        scanner.close();
    }

    public static boolean is_defective(int detail) {
        return detail < 0;
    }

    public static boolean is_details_valid(int[] detail) {
        if (detail == null || detail.length < 2) {
            return true;
        }

        for (int i = 0; i < detail.length - 1; i++) {
            if (is_defective(detail[i]) && is_defective(detail[i + 1])) {
                return false;
            }
        }
        return true;
    }
}