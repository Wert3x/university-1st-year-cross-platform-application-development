import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> data = new ArrayList<>();
        Map<String, Double> resultMap = new TreeMap<>();

        while (true) {
            System.out.println("\n1. Первичная инициализация");
            System.out.println("2. Интерактивное добавление");
            System.out.println("3. Обработка и трансформация");
            System.out.println("4. Отчетность");
            System.out.println("0. Выход");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                data.add("Обогреватель_Гостинная_1500_12");
                data.add("Кондиционер_Спальня_2000_15");
                data.add("ПК_Кабинет_400_11");
                data.add("Холодильник_Кухня_80_24");
                data.add("Телевизор_Холл_150_5");
                data.add("Бойлер_Ванна_2500_3");
                data.add("Лампа_Кабинет_60_12");
                data.add("Сервер_База_300_24");
                data.add("Утюг_Прачечная_1200_1");
                System.out.println("База инициализирована.");
            } else if (choice.equals("2")) {
                System.out.println("Введите строку (ПРИБОР_КОМНАТА_МОЩНОСТЬ_ВРЕМЯ):");
                String input = scanner.nextLine();
                String[] parts = input.split("_");
                if (parts.length == 4) {
                    try {
                        Double.parseDouble(parts[2]);
                        Double.parseDouble(parts[3]);
                        data.add(input);
                        System.out.println("Добавлено.");
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: некорректные типы данных.");
                    }
                } else {
                    System.out.println("Ошибка: неверный формат или разделитель.");
                }
            } else if (choice.equals("3")) {
                resultMap = new TreeMap<>();
                for (String entry : data) {
                    String[] parts = entry.split("_");
                    String device = parts[0];
                    double power = Double.parseDouble(parts[2]);
                    double time = Double.parseDouble(parts[3]);

                    if (power > 100 && time > 10) {
                        String key = device + " (" + (int)time + " ч)";
                        double consumption = power * time;
                        resultMap.put(key, resultMap.getOrDefault(key, 0.0) + consumption);
                    }
                }
                System.out.println("Обработка завершена.");
            } else if (choice.equals("4")) {
                StringBuilder report = new StringBuilder();
                for (Map.Entry<String, Double> entry : resultMap.entrySet()) {
                    report.append("[").append(entry.getKey()).append("] -> [")
                            .append(entry.getValue()).append(" Вт/ч]\n");
                }
                if (report.length() == 0) {
                    System.out.println("Данные отсутствуют или не обработаны.");
                } else {
                    System.out.print(report.toString());
                }
            } else if (choice.equals("0")) {
                break;
            }
        }
    }
}