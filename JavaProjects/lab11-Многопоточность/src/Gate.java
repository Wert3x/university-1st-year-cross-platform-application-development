import java.util.List;
import java.util.Random;

public class Gate implements Runnable {

    private final Car.Type handledType;
    private final CarQueue queue;
    private final ParkingLot parkingLot;
    private final Statistics stats;
    private final List<Thread> parkedCarThreads; // список чтобы потом ограничить все потоки машин в конце

    private volatile boolean running = true;
    private final Random random = new Random();

    public Gate(Car.Type handledType, CarQueue queue, ParkingLot parkingLot, Statistics stats, List<Thread> parkedCarThreads) {
        this.handledType = handledType;
        this.queue = queue;
        this.parkingLot = parkingLot;
        this.stats = stats;
        this.parkedCarThreads = parkedCarThreads;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": заезд открыт.");
        try {
            while (running) {
                // ждем машину из очереди 200 мс, чтоб не висеть вечно
                Car car = queue.take(200);
                if (car == null) continue; // никого нет, идем на новый круг проверки флага running

                if (handledType == Car.Type.TENANT) {
                    parkingLot.parkTenant(); //поток блокируется, если мест нет
                    processEntry(car);
                } else {
                    if (parkingLot.tryParkGuest()) {
                        processEntry(car);
                    } else {
                        // не пустили
                        stats.recordGuestRejected();
                        System.out.printf("%s: ⛔ МЕСТ НЕТ (нужно >=2). %s развернут!%n",
                                Thread.currentThread().getName(), car);
                    }
                }

                // Шлагбаум физически поднимается/опускается, это занимает время
                Thread.sleep(150);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + ": заезд закрыт.");
    }

    private void processEntry(Car car) {
        stats.recordParked(car.getType());
        System.out.printf("%s: 🟢 пропустил %s. Свободно мест: %d%n",
                Thread.currentThread().getName(), car, parkingLot.getFreeSpots());

        scheduleDeparture(car);
    }

    private void scheduleDeparture(Car car) {
        // если усыпить текущий поток ворот на время стоянки тачки,
        // то шлагбаум заблокируется и никого больше не пустит.
        // Поэтому под каждую припаркованную тачку создаем ОТДЕЛЬНЫЙ поток-таймер уезда.
        Thread departureThread = new Thread(() -> {
            try {
                int parkTime = 4000 + random.nextInt(4000); // стоит от 4 до 8 сек
                Thread.sleep(parkTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                parkingLot.freeSpot(); // уехала, освобождаем место
                System.out.printf("   ↪ %s уехал. Свободно мест: %d%n", car, parkingLot.getFreeSpots());
            }
        }, "ParkedCar-" + car.getId());

        parkedCarThreads.add(departureThread); // сохраняем ссылку, чтобы в конце расправиться
        departureThread.start();
    }

    public void stop() {
        running = false;
    }
}