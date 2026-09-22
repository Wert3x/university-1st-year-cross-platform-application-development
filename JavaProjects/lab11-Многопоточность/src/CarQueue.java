import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CarQueue {

    // обычный линкедлист не умеет в многопоток, так что используем локи для однопоточности
    private final Queue<Car> queue = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition(); // сигнал для шлагбаума

    private final String name;

    public CarQueue(String name) {
        this.name = name;
    }

    public int addAndGetSize(Car car) {
        lock.lock(); // заперлись
        try {
            queue.add(car);
            notEmpty.signal(); // будим шлагбаум, потому что машинаа приехала
            return queue.size();
        } finally {
            lock.unlock(); // не забыть открыть, а то всё зависнет намертво
        }
    }

    public Car take(long timeoutMs) throws InterruptedException {
        lock.lock();
        try {
            long deadline = System.currentTimeMillis() + timeoutMs;
            // вайл чтобы ложно не будить систему
            while (queue.isEmpty()) {
                long remaining = deadline - System.currentTimeMillis();
                if (remaining <= 0) return null;
                notEmpty.await(remaining, java.util.concurrent.TimeUnit.MILLISECONDS); // спит, ждет сигнал
            }
            return queue.poll();
        } finally {
            lock.unlock();
        }
    }

    public String getName() { return name; }
}