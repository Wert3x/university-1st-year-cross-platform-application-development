public abstract class Animal {
    protected String nickname;
    protected int age;
    protected int hungerLevel;
    protected int energy;
    protected double bodyTemp;

    protected Animal(String nickname, int age, double defaultTemp) {
        this.nickname = nickname;
        this.age = age;
        this.hungerLevel = 50;
        this.energy = 100;
        this.bodyTemp = defaultTemp;
    }

    public static OperationResult<Void> validateParams(String nickname, int age) {
        if (nickname == null || nickname.trim().isEmpty()) {
            return OperationResult.fail("Кличка не может быть пустой!");
        }
        if (age < 0) {
            return OperationResult.fail("Возраст не может быть отрицательным!");
        }
        return OperationResult.success("Параметры верны", null);
    }

    public void eat() {
        this.hungerLevel = Math.max(0, this.hungerLevel - 30);
        this.energy = Math.min(100, this.energy + 15);
        System.out.println(nickname + " поел(а). Голод: " + hungerLevel + "%, Энергия: " + energy);
    }

    public abstract OperationResult<String> makeSound();

    @Override
    public String toString() {
        return String.format("%s [Имя: %s, Возраст: %d, Голод: %d%%, Энергия: %d, Темп: %.1f°C]",
                getClass().getSimpleName(), nickname, age, hungerLevel, energy, bodyTemp);
    }
}