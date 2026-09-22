import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Scanner;

public class Main {

    static final Path SOURCE_FILE = Path.of("coffee_menu.txt");
    static final Path REPORT_FILE = Path.of("milk_menu.txt");
    static final String SEP = "|";

    static final String HOT  = "☕";
    static final String ICE  = "🧊";
    static final String MILK = "🥛";
    static final String NO_MILK = "Без молока";

    static final String[] INITIAL = {
            "Капучино|250|☕|🥛",
            "Американо|200|☕|Без молока",
            "Айс Латте|400|🧊|🥛",
            "Эспрессо|50|☕|Без молока",
            "Флэт Уайт|200|☕|🥛",
            "Раф|300|☕|🥛",
            "Айс Американо|300|🧊|Без молока",
            "Макиато|100|☕|🥛",
            "Гляссе|250|🧊|🥛",
            "Лунго|150|☕|Без молока",
            "Матча Латте|300|☕|🥛",
            "Моккачино|350|☕|🥛"
    };

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("Кофейня: Рецептурный справочник:");
            System.out.println("  1. Первичная инициализация (загрузка базы)");
            System.out.println("  2. Добавить напиток (с валидацией)");
            System.out.println("  3. Обработка данных (фильтрация + трансформация)");
            System.out.println("  4. Просмотр отчёта (Напитки с молоком)");
            System.out.println("  0. Выход");
            System.out.print("Введите номер пункта: ");

            String choice = sc.nextLine().trim();

            switch (choice) {

                case "1": {
                    try (BufferedWriter bw = Files.newBufferedWriter(SOURCE_FILE, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))
                    {
                        for (String line : INITIAL) {
                            bw.write(line);
                            bw.newLine();
                        }
                        System.out.println("\nФайл '" + SOURCE_FILE + "' инициализирован (" + INITIAL.length + " записей).");
                    } catch (IOException e) {
                        System.out.println("Ошибка записи файла: " + e.getMessage());
                    }
                    System.out.print("\nНажмите Enter для возврата в меню...");
                    sc.nextLine();
                    break;
                }

                case "2": {
                    if (Files.notExists(SOURCE_FILE)) {
                        System.out.println("\nФайл '" + SOURCE_FILE + "' не найден. Сначала выполните инициализацию (пункт 1).");
                        System.out.print("\nНажмите Enter...");
                        sc.nextLine();
                        break;
                    }

                    System.out.println("\nДобавление нового напитка");

                    String drink;
                    while (true) {
                        System.out.print("  Название напитка  : ");
                        drink = sc.nextLine().trim();
                        if (drink.isEmpty()) {
                            System.out.println("Поле не может быть пустым.");
                        } else if (drink.contains(SEP)) {
                            System.out.println("Символ '" + SEP + "' запрещён.");
                        } else {
                            break;
                        }
                    }

                    int volume = 0;
                    while (true) {
                        System.out.print("  Объем (мл)        : ");
                        String raw = sc.nextLine().trim();
                        if (raw.contains(SEP)) {
                            System.out.println("Символ '" + SEP + "' запрещён.");
                            continue;
                        }
                        try {
                            volume = Integer.parseInt(raw);
                            if (volume <= 0) {
                                System.out.println("Объем должен быть положительным.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Введите целое число.");
                        }
                    }

                    String temp = "";
                    while (true) {
                        System.out.println("  Температура:");
                        System.out.println("    1. " + HOT  + "  Горячий");
                        System.out.println("    2. " + ICE  + "  Айс-кофе");
                        System.out.print("  Выбор (1-2): ");
                        String t = sc.nextLine().trim();
                        if      (t.equals("1")) { temp = HOT;  break; }
                        else if (t.equals("2")) { temp = ICE;  break; }
                        else System.out.println("Неверный выбор.");
                    }

                    String base = "";
                    while (true) {
                        System.out.println("  Основа:");
                        System.out.println("    1. " + MILK  + "  С молоком");
                        System.out.println("    2. Без молока / Вода");
                        System.out.print("  Выбор (1-2): ");
                        String b = sc.nextLine().trim();
                        if      (b.equals("1")) { base = MILK;  break; }
                        else if (b.equals("2")) { base = NO_MILK;  break; }
                        else System.out.println("Неверный выбор.");
                    }

                    String newLine = drink + SEP + volume + SEP + temp + SEP + base;

                    try (BufferedWriter bw = Files.newBufferedWriter(SOURCE_FILE, StandardCharsets.UTF_8, StandardOpenOption.APPEND))
                    {
                        bw.write(newLine);
                        bw.newLine();
                        System.out.println("\nЗапись добавлена: " + newLine);
                    } catch (IOException e) {
                        System.out.println("Ошибка записи файла: " + e.getMessage());
                    }

                    System.out.print("\nНажмите Enter для возврата в меню...");
                    sc.nextLine();
                    break;
                }

                case "3": {
                    if (Files.notExists(SOURCE_FILE)) {
                        System.out.println("\nФайл '" + SOURCE_FILE + "' не найден. Сначала выполните инициализацию (пункт 1).");
                        System.out.print("\nНажмите Enter...");
                        sc.nextLine();
                        break;
                    }

                    int processed = 0;
                    int skipped   = 0;
                    int milkCount = 0;

                    try (
                            BufferedReader br = Files.newBufferedReader(SOURCE_FILE, StandardCharsets.UTF_8);
                            BufferedWriter bw = Files.newBufferedWriter(REPORT_FILE, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
                    ) {
                        bw.write(String.format("%-18s| %-8s | %-11s | %s%n", "Напиток", "Объем", "Температура", "Основа"));
                        bw.write("-".repeat(55));
                        bw.newLine();

                        String rawLine;
                        int lineNum = 0;

                        while ((rawLine = br.readLine()) != null) {
                            lineNum++;
                            String trimmed = rawLine.trim();
                            if (trimmed.isEmpty()) continue;

                            String[] parts = trimmed.split("\\|", -1);

                            if (parts.length != 4 || parts[0].isEmpty() || parts[1].isEmpty() || parts[2].isEmpty() || parts[3].isEmpty()) {
                                System.out.println("Строка " + lineNum + " некорректна — пропущена: " + trimmed);
                                skipped++;
                                continue;
                            }

                            processed++;

                            String drink = parts[0].trim();
                            String vol   = parts[1].trim();
                            String temp  = parts[2].trim();
                            String base  = parts[3].trim();

                            // Отбор напитков с молоком
                            if (!base.equals(MILK)) continue;

                            milkCount++;

                            // Трансформация: замена объема на "М"
                            String newVol = "M";

                            bw.write(String.format("%-18s| %-8s | %-11s | %s%n", drink, newVol, temp, base));
                        }

                        System.out.println("\nОбработка завершена.");
                        System.out.println("   Всего строк обработано : " + processed);
                        System.out.println("   Пропущено (ошибочных)  : " + skipped);
                        System.out.println("   Напитков с молоком 🥛  : " + milkCount);
                        System.out.println("   Отчёт сохранён в       : '" + REPORT_FILE + "'");

                    } catch (IOException e) {
                        System.out.println("Ошибка обработки файла: " + e.getMessage());
                    }

                    System.out.print("\nНажмите Enter для возврата в меню...");
                    sc.nextLine();
                    break;
                }

                case "4": {
                    if (Files.notExists(REPORT_FILE)) {
                        System.out.println("\nФайл '" + REPORT_FILE + "' не найден. Сначала выполните обработку (пункт 3).");
                        System.out.print("\nНажмите Enter...");
                        sc.nextLine();
                        break;
                    }

                    System.out.println("\nОтчёт по молочным напиткам (🥛)");
                    int recordCount = 0;

                    try (BufferedReader br = Files.newBufferedReader(REPORT_FILE, StandardCharsets.UTF_8)) {
                        String line;
                        while ((line = br.readLine()) != null) {

                            System.out.println(line);

                            if (!line.isBlank() && !line.startsWith("-") && line.contains("|") && !line.contains("Напиток")) {
                                recordCount++;
                            }
                        }
                        System.out.println("\nВсего в отчёте: " + recordCount + " записей. Файл сохранён в кодировке UTF-8.");
                    } catch (IOException e) {
                        System.out.println("Ошибка чтения файла: " + e.getMessage());
                    }

                    System.out.print("\nНажмите Enter для возврата в меню...");
                    sc.nextLine();
                    break;
                }

                case "0": {
                    System.out.println("\nПрограмма завершена\n");
                    running = false;
                    break;
                }

                default: {
                    System.out.println("Неверный пункт меню. Введите число от 0 до 4.");
                }
            }
        }

        sc.close();
    }
}