public class Elephant extends Animal {
    public Elephant(String nickname, int age) {
        super(nickname, age, 36.6);
    }

    public OperationResult<String> sprayWater() {
        this.bodyTemp -= 1.2;
        this.energy -= 5;
        return OperationResult.success(nickname + " облился. Новая температура: " + bodyTemp, null);
    }

    @Override
    public OperationResult<String> makeSound() {
        if (hungerLevel > 80) {
            return OperationResult.fail("ПРЕДУПРЕЖДЕНИЕ: " + nickname + " просит еды (трубит)!");
        }
        return OperationResult.success(nickname + " трубит: Тууу!", null);
    }
}
