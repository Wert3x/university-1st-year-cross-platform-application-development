import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dispatcher {

    private final CarQueue tenantQueue = new CarQueue("Арендаторы");
    private final CarQueue guestQueue  = new CarQueue("Гости");
    private final ParkingLot parkingLot = new ParkingLot();
    private final Statistics stats      = new Statistics();

    private final List<Thread> allSystemThreads = new ArrayList<>();
    // юзаем CopyOnWriteArrayList, потому что машины будут добавляться/удаляться из разных потоков динамически
    private final List<Thread> parkedCarThreads = new CopyOnWriteArrayList<>();

    private final List<CarGenerator> generators = new ArrayList<>();
    private final List<Gate> gates = new ArrayList<>();

    public void start() {
        // Настройка спавна: арендаторы едут чаще (каждые 0.8-1.5 сек), гости чуть реже
        CarGenerator tenantGen = new CarGenerator(Car.Type.TENANT, tenantQueue, 800, 1500, stats);
        CarGenerator guestGen  = new CarGenerator(Car.Type.GUEST, guestQueue, 1000, 2000, stats);
        generators.add(tenantGen);
        generators.add(guestGen);

        allSystemThreads.add(new Thread(tenantGen, "Gen-Tenants"));
        allSystemThreads.add(new Thread(guestGen, "Gen-Guests"));

        // Два независимых въезда
        Gate tenantGate = new Gate(Car.Type.TENANT, tenantQueue, parkingLot, stats, parkedCarThreads);
        Gate guestGate  = new Gate(Car.Type.GUEST, guestQueue, parkingLot, stats, parkedCarThreads);
        gates.add(tenantGate);
        gates.add(guestGate);

        allSystemThreads.add(new Thread(tenantGate, "Gate-1(Tenants)"));
        allSystemThreads.add(new Thread(guestGate, "Gate-2(Guests)"));

        // старт
        allSystemThreads.forEach(Thread::start);
    }

    public void stop() throws InterruptedException {
        System.out.println("\nОстановка симуляции...");

        // Сначала гасим флаги в циклах
        generators.forEach(CarGenerator::stop);
        gates.forEach(Gate::stop);

        // Прерываем основные рабочие потоки (генераторы и ворота)
        for (Thread t : allSystemThreads) {
            t.interrupt(); // если они спали в sleep или await — они вылетят в catch
            t.join(2000); // ждем секцию закрытия не больше 2 сек
        }

        // Выгоняем всех кто остался стоять на парковке, чтоб программа завершилась
        for (Thread t : parkedCarThreads) {
            if (t.isAlive()) {
                t.interrupt();
            }
        }

        System.out.println("Все потоки корректно завершены.");
    }

    public void printStats() {
        stats.print();
    }
}