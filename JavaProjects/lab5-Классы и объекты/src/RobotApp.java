import java.util.Scanner;

public class RobotApp {
    private static RobotVacuum robot = null;
    private static final Scanner scanner = new Scanner(System.in);

    // Константы оформления
    private static final String HEADER = "==== СИСТЕМА УПРАВЛЕНИЯ РОБОТОМ-ПЫЛЕСОСОМ ====";
    private static final String FOOTER = "==================================================";

    public static void main(String[] args) {
        while (true) {
            printMenu();

            // Защита от некорректного ввода (LBYL)
            if (!scanner.hasNextInt()) {
                showResult(new OperationResult(false, "Ошибка: введите цифру из списка."));
                scanner.next(); // очистка буфера
                continue;
            }

            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("\nПрограмма завершена.");
                break;
            }

            // Логика переключения
            if (choice == 1) {
                handleCreation();
            } else if (robot == null) {
                showResult(new OperationResult(false, "Сначала инициализируйте устройство (Пункт 1)."));
            } else {
                handleActions(choice);
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n" + HEADER);
        System.out.println(" 1. [МЕНЕДЖЕР] Создать и настроить нового робота");
        System.out.println(" 2. [СТАТУС]   Вывести текущие показатели системы");
        System.out.println(" 3. [ДВИЖЕНИЕ] Переехать в другое помещение");
        System.out.println(" 4. [РАБОТА]   Запустить цикл сухой уборки");
        System.out.println(" 5. [ОБСЛУЖИВАНИЕ] Очистить контейнер для пыли");
        System.out.println(" 0. [ВЫХОД]    Завершить работу программы");
        System.out.println(FOOTER);
        System.out.print(">>> Выберите действие: ");
    }

    private static void handleCreation() {
        System.out.println("\n--- РЕЖИМ ИНИЦИАЛИЗАЦИИ ---");
        System.out.print("Введите уровень заряда (0-100): ");
        int charge = scanner.nextInt();
        System.out.print("Контейнер заполнен? (true/false): ");
        boolean isFull = scanner.nextBoolean();

        // Использование фабричного метода (объект создается только при успехе)
        CreateRobotResult res = RobotVacuum.create(charge, isFull, "Зал");

        if (res.isSuccess()) {
            robot = res.robot();
            showResult(new OperationResult(true, res.message()));
        } else {
            showResult(new OperationResult(false, res.message()));
        }
    }

    private static void handleActions(int choice) {
        OperationResult result = switch (choice) {
            case 2 -> {
                System.out.println("\n МОНИТОРИНГ: " + robot.toString());
                yield null;
            }
            case 3 -> {
                System.out.print("Введите название новой комнаты: ");
                yield robot.moveToRoom(scanner.next());
            }
            case 4 -> robot.startCleaning();
            case 5 -> robot.emptyBin();
            default -> new OperationResult(false, "Выбран несуществующий пункт меню.");
        };

        if (result != null) showResult(result);
    }

    private static void showResult(OperationResult res) {
        System.out.println("");
        if (res.isSuccess()) {
            System.out.println(res.message());
        } else {
            System.out.println(res.message());
        }
    }
}