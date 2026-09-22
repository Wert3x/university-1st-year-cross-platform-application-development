import java.util.Random;

public class CarGenerator implements Runnable {

    private final Car.Type type;
    private final CarQueue targetQueue;
    private final int minDelayMs;
    private final int maxDelayMs;
    private final Statistics stats;

    private volatile boolean running = true; // флаг тушения потока
    private final Random random = new Random();

    public CarGenerator(Car.Type type, CarQueue targetQueue, int minDelayMs, int maxDelayMs, Statistics stats) {
        this.type        = type;
        this.targetQueue = targetQueue;
        this.minDelayMs  = minDelayMs;
        this.maxDelayMs  = maxDelayMs;
        this.stats       = stats;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": запущен генератор (" + type + ")");
        try {
            while (running) {
                //рандомная пауза между приездом машин
                int delay = minDelayMs + random.nextInt(maxDelayMs - minDelayMs + 1);
                Thread.sleep(delay);

                Car car = new Car(type);
                int currentQueueSize = targetQueue.addAndGetSize(car); //добавляем в очередь

                System.out.printf("%s: подъехал %s, в очереди: %d%n",
                        Thread.currentThread().getName(), car, currentQueueSize);

                stats.updateMaxQueueSize(type, currentQueueSize); // обновляем пик длины
            }
        } catch (InterruptedException e) {
            // ловим интеррапт от диспетчера при закрытии
            Thread.currentThread().interrupt();
        }
        System.out.println(Thread.currentThread().getName() + ": генератор завершен.");
    }

    public void stop() {
        running = false;
    }
}