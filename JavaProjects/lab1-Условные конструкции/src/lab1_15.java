import java.util.Scanner;

public class lab1_15 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите значение параметра a: ");
        double a = scanner.nextDouble();

        System.out.print("Введите значение параметра b: ");
        double b = scanner.nextDouble();

        System.out.print("Введите значение параметра z: ");
        double z = scanner.nextDouble();

        System.out.print("Введите значение аргумента x: ");
        double x = scanner.nextDouble();

        double y;
        boolean validInput = true;

        if (x < a) {
            if (x <= -5) {
                System.out.println("Ошибка: аргумент логарифма (x+5) должен быть положительным.");
                validInput = false;
            } else if ((x == 0) || (z == 0)) {
                System.out.println("Ошибка: деление на ноль.");
                validInput = false;
            } else {
                y = Math.log(x + 5) / (x * z);
                System.out.printf("y(x) = %.6f (ветка: x < a)%n", y);
            }
        } else if (x >= a && x <= b) {
            double underRoot = a * a - x * x;
            if (underRoot < 0) {
                System.out.println("Ошибка: подкоренное выражение отрицательное.");
                validInput = false;
            } else {
                y = Math.sqrt(underRoot);
                System.out.printf("y(x) = %.6f (ветка: a ≤ x ≤ b)%n", y);
            }
        } else {
            if (x == 0) {
                System.out.println("Ошибка: деление на ноль в аргументе арктангенса.");
                validInput = false;
            } else {
                y = Math.atan(1.0 / x);
                System.out.printf("y(x) = %.6f (ветка: x > b)%n", y);
            }
        }

        if (!validInput) {
            System.out.println("Ошибка входных данных.");
        }

        scanner.close();
    }
}