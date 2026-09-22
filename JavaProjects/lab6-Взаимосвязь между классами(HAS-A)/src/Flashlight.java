public class Flashlight {
    private final String brand;
    private final String bodyMaterial;
    private final Bulb bulb;
    private final Battery battery;
    private FlashlightStatus status;

    private Flashlight(String brand, String bodyMaterial, int brightness, int batteryCapacity) {
        this.brand = brand;
        this.bodyMaterial = bodyMaterial;
        this.bulb = new Bulb(brightness);
        this.battery = new Battery(batteryCapacity);
        this.status = FlashlightStatus.OFF;
    }

    public static OperationResult<Flashlight> create(String brand, String material, int brightness, int capacity) {
        if (brand == null || brand.isBlank()) return OperationResult.fail("Марка пуста");
        if (material == null || material.isBlank()) return OperationResult.fail("Материал пуст");
        if (brightness <= 0) return OperationResult.fail("Неверная яркость");
        if (capacity <= 0) return OperationResult.fail("Неверная емкость");

        return OperationResult.success("Фонарик создан", new Flashlight(brand, material, brightness, capacity));
    }

    public OperationResult<String> turnOn() {
        if (!battery.hasPower()) {
            return OperationResult.fail("Аккумулятор разряжен!");
        }
        status = FlashlightStatus.ON;
        bulb.lightUp();
        battery.consume();
        return OperationResult.success("Фонарик включен", "OK");
    }

    public OperationResult<String> turnOff() {
        status = FlashlightStatus.OFF;
        bulb.turnOff();
        return OperationResult.success("Фонарик выключен", "OK");
    }

    public void printBrightness() {
        System.out.println("Текущая яркость: " + bulb.getBrightness() + " лм");
    }

    public String getFullInfo() {
        return String.format("Фонарик: %s | Корпус: %s | Статус: %s\n- %s\n- %s",
                brand, bodyMaterial, status, bulb, battery);
    }

    @Override
    public String toString() {
        return brand + " (" + status + ")";
    }
}