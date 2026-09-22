import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {

    // атомики, чтоб инкременты из разных потоков не было каши
    private final AtomicInteger tenantsParked  = new AtomicInteger(0);
    private final AtomicInteger guestsParked   = new AtomicInteger(0);
    private final AtomicInteger guestsRejected = new AtomicInteger(0); // коней развернутых считаем тут

    // волатилы, чтоб изменения сразу были видны в main при выводе
    private volatile int maxTenantQueueSize = 0;
    private volatile int maxGuestQueueSize  = 0;

    private final Object queueStatLock = new Object(); // заглушка для синхронизации

    public void recordParked(Car.Type type) {
        if (type == Car.Type.TENANT) tenantsParked.incrementAndGet();
        else                         guestsParked.incrementAndGet();
    }

    public void recordGuestRejected() {
        guestsRejected.incrementAndGet();
    }

    public void updateMaxQueueSize(Car.Type type, int size) {
        synchronized (queueStatLock) { // синк чисто на перезапись максимума, чтоб быстро было
            if (type == Car.Type.TENANT) {
                if (size > maxTenantQueueSize) maxTenantQueueSize = size;
            } else {
                if (size > maxGuestQueueSize) maxGuestQueueSize = size;
            }
        }
    }

    public void print() {
        System.out.println("\n--- ИТОГОВАЯ СТАТИСТИКА ---");
        System.out.println("  Арендаторов припарковано:     " + tenantsParked.get());
        System.out.println("  Гостей припарковано:          " + guestsParked.get());
        System.out.println("  Гостей развернуто (нет мест): " + guestsRejected.get());
        System.out.println("  Макс. длина очереди арендаторов: " + maxTenantQueueSize);
        System.out.println("  Макс. длина очереди гостей:      " + maxGuestQueueSize);
    }
}