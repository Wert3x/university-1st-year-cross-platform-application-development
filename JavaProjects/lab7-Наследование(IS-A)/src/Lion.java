public class Lion extends Animal {
    public Lion(String nickname, int age) {
        super(nickname, age, 38.5);
    }

    public OperationResult<String> hunt() {
        if (energy < 30) {
            return OperationResult.fail(nickname + " слишком истощен для охоты (нужен отдых/еда).");
        }
        this.energy -= 30;
        this.hungerLevel = Math.max(0, this.hungerLevel - 50);
        return OperationResult.success(nickname + " успешно поохотился. Энергия упала до " + energy, null);
    }

    @Override
    public OperationResult<String> makeSound() {
        if (hungerLevel > 80) {
            return OperationResult.fail("ПРЕДУПРЕЖДЕНИЕ: " + nickname + " критически голоден!");
        }
        return OperationResult.success(nickname + " рычит: Рррр!", null);
    }
}