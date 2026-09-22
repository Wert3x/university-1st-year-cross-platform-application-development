public class RobotVacuum {
    private int batteryCharge;
    private boolean isDustBinFull;
    private String currentRoom;

    // Конструктор ПРИВАТНЫЙ - создать объект напрямую через 'new' нельзя
    private RobotVacuum(int batteryCharge, boolean isDustBinFull, String currentRoom) {
        this.batteryCharge = batteryCharge;
        this.isDustBinFull = isDustBinFull;
        this.currentRoom = currentRoom;
    }

    public static CreateRobotResult create(int charge, boolean isFull, String room) {
        // Проверка: если заряд неверный, объект даже не будет инициализирован
        if (charge < 0 || charge > 100) {
            return CreateRobotResult.failure("ОТКАЗ: Заряд должен быть от 0 до 100%.");
        }

        String initialRoom = (room == null || room.isBlank()) ? "Base" : room;

        // Только здесь выделяется память под объект
        RobotVacuum robot = new RobotVacuum(charge, isFull, initialRoom);
        return CreateRobotResult.success(robot);
    }

    public OperationResult moveToRoom(String roomName) {
        if (batteryCharge <= 15) {
            return new OperationResult(false, "Низкий заряд, перемещение невозможно.");
        }
        this.currentRoom = roomName;
        return new OperationResult(true, "Переехал в: " + roomName);
    }

    public OperationResult startCleaning() {
        if (isDustBinFull) {
            return new OperationResult(false, "Контейнер переполнен!");
        }
        if (batteryCharge < 10) {
            return new OperationResult(false, "Недостаточно энергии для уборки.");
        }
        this.batteryCharge -= 10;
        return new OperationResult(true, "Уборка в " + currentRoom + " завершена.");
    }

    public OperationResult emptyBin() {
        this.isDustBinFull = false;
        return new OperationResult(true, "Контейнер очищен.");
    }

    @Override
    public String toString() {
        return String.format("[Статус] Заряд: %d%% | Комната: %s | Контейнер полон: %b",
                batteryCharge, currentRoom, isDustBinFull);
    }
}