import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ParkingLot {

    private int spots = 10; // по заданию 10 мест общих
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition spotFreed = lock.newCondition(); // для тех, кто ждет места

    public void parkTenant() throws InterruptedException {
        lock.lock();
        try {
            //арендаторы ждут места
            while (spots < 1) {
                spotFreed.await(); //ждут
            }
            spots--; // заняли место
        } finally {
            lock.unlock();
        }
    }

    public boolean tryParkGuest() {
        lock.lock();
        try {
            //гость едет, ТОЛЬКО если свободно >= 2 мест.
            // Если осталось последнее место — держим его для арендатора.
            if (spots >= 2) {
                spots--;
                return true;
            }
            return false; //гости не ждут, а разворачиваются
        } finally {
            lock.unlock();
        }
    }

    public void freeSpot() {
        lock.lock();
        try {
            spots++;
            spotFreed.signalAll(); //будим арендаторов потому что место свободно
        } finally {
            lock.unlock();
        }
    }

    public int getFreeSpots() {
        lock.lock();
        try {
            return spots;
        } finally {
            lock.unlock();
        }
    }
}