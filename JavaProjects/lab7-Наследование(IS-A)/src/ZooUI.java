import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ZooUI {
    private List<Animal> animals = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n--- ЗООПАРК (Биологический профиль) ---");
            System.out.println("1. Добавить животное");
            System.out.println("2. Список всех животных");
            System.out.println("3. Подать голос всем");
            System.out.println("4. Накормить конкретное животное");
            System.out.println("5. Уникальное действие");
            System.out.println("0. Выход");

            int choice = readInt();
            if (choice == 0) break;

            switch (choice) {
                case 1 -> create();
                case 2 -> showAll();
                case 3 -> runAllSounds();
                case 4 -> feedAnimal();
                case 5 -> uniqueAction();
            }
        }
    }

    private void create() {
        System.out.println("1. Лев, 2. Слон, 3. Обезьяна");
        int type = readInt();
        System.out.print("Кличка: ");
        String name = scanner.nextLine();
        System.out.print("Возраст: ");
        int age = readInt();


        if (Animal.validateParams(name, age).isSuccess()) {
            switch (type) {
                case 1 -> animals.add(new Lion(name, age));
                case 2 -> animals.add(new Elephant(name, age));
                case 3 -> animals.add(new Monkey(name, age));
            }
            System.out.println("Животное добавлено.");
        }
    }

    private void showAll() {
        for (int i = 0; i < animals.size(); i++) {
            System.out.println((i + 1) + ". " + animals.get(i));
        }
    }

    private void runAllSounds() {
        for (Animal a : animals) {
            OperationResult<String> res = a.makeSound();
            System.out.println(res.isSuccess() ? res.data() : res.message());
        }
    }

    private void feedAnimal() {
        showAll();
        System.out.print("Кого кормим? ");
        int idx = readInt() - 1;
        if (idx >= 0 && idx < animals.size()) animals.get(idx).eat();
    }

    private void uniqueAction() {
        showAll();
        System.out.print("Выберите животное: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= animals.size()) return;

        Animal a = animals.get(idx);
        if (a instanceof Lion lion) {
            System.out.println(lion.hunt().message());
        } else if (a instanceof Elephant elephant) {
            System.out.println(elephant.sprayWater().message());
        } else if (a instanceof Monkey monkey) {
            System.out.print("Высота дерева: ");
            System.out.println(monkey.climbTree(readInt()).message());
        }
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }
}