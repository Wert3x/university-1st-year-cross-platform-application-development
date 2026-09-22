import java.util.Scanner;

public class FlashlightUI {
    private Flashlight flashlight;
    private final Scanner scanner;

    public FlashlightUI() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt();

            if (choice == 0) {
                running = false;
                continue;
            }

            if (flashlight == null && choice != 1) {
                System.out.println("Сначала создайте объект!");
                continue;
            }

            processChoice(choice);
        }
    }

    private void printMenu() {
        System.out.println("\n--- УПРАВЛЕНИЕ ФОНАРИКОМ ---");
        System.out.println("1. Создать фонарик");
        System.out.println("2. Включить свет");
        System.out.println("3. Выключить свет");
        System.out.println("4. Вывести яркость");
        System.out.println("5. Состояние системы");
        System.out.println("0. Выход");
        System.out.print("Выбор: ");
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1 -> createFlashlight();
            case 2 -> handleResult(flashlight.turnOn());
            case 3 -> handleResult(flashlight.turnOff());
            case 4 -> flashlight.printBrightness();
            case 5 -> System.out.println(flashlight.getFullInfo());
            default -> System.out.println("Ошибка ввода");
        }
    }

    private void createFlashlight() {
        System.out.print("Марка: ");
        String brand = scanner.nextLine();
        System.out.print("Материал корпуса: ");
        String material = scanner.nextLine();
        System.out.print("Мощность (лм): ");
        int brightness = readInt();
        System.out.print("Емкость батареи (mAh): ");
        int cap = readInt();

        OperationResult<Flashlight> result = Flashlight.create(brand, material, brightness, cap);
        if (result.isSuccess()) {
            flashlight = result.data();
            System.out.println(result.message());
        } else {
            System.out.println("Ошибка: " + result.message());
        }
    }

    private void handleResult(OperationResult<String> result) {
        if (result.isSuccess()) {
            System.out.println("Успех: " + result.message());
        } else {
            System.out.println("Ошибка: " + result.message());
        }
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Введите число: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }
}