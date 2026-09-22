public class lab2 {
  public static void main(String[] args) {
    int current_length = 0;
    int max_length = 0;
    int prev_type = 0;
    int position = 0;
    boolean has_input = false;

    java.util.Scanner scanner = new java.util.Scanner(System.in);

    System.out.println("Введите типы коробок (1, 2, 3). Для завершения введите 0:");

    while (true) {
      System.out.print("Коробка " + (position + 1) + ": ");
      int box_type = scanner.nextInt();

      if (box_type == 0) {
        break;
      }

      if (box_type < 1 || box_type > 3) {
        System.out.println("Ошибка: тип коробки должен быть 1, 2 или 3. Попробуйте снова.");
        continue;
      }

      position++;
      has_input = true;

      if (prev_type == 0) {
        current_length = 1;
      } else if (box_type != prev_type) {
        current_length++;
      } else {
        current_length = 1;
      }

      if (current_length > max_length) {
        max_length = current_length;
      }

      prev_type = box_type;

      System.out.println("  Текущая длина: " + current_length + ", Максимальная: " + max_length);
    }

    System.out.println("\n" + "=".repeat(40));
    if (!has_input) {
      System.out.println("Не было введено ни одной коробки");
    } else {
      System.out.println("Длина самой длинной эффективной последовательности: " + max_length);
      System.out.println("Всего проверено коробок: " + position);
    }

    scanner.close();
  }
}