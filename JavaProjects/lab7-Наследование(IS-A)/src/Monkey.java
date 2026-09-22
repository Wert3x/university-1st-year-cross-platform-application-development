public class Monkey extends Animal {
    public Monkey(String nickname, int age) {
        super(nickname, age, 37.0);
    }

    public OperationResult<String> climbTree(int height) {
        int energyCost = height * 2;
        if (energy < energyCost) {
            return OperationResult.fail(nickname + " не хватает сил залезть так высоко.");
        }
        this.energy -= energyCost;
        return OperationResult.success(nickname + " залез на " + height + "м. Потрачено энергии: " + energyCost, null);
    }

    @Override
    public OperationResult<String> makeSound() {
        if (hungerLevel > 80) {
            return OperationResult.fail("ПРЕДУПРЕЖДЕНИЕ: Обезьяна голодна!");
        }
        return OperationResult.success(nickname + " кричит: У-а-а!", null);
    }
}