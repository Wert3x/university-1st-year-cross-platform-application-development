import java.util.concurrent.atomic.AtomicInteger;

public class Car {

    public enum Type { TENANT, GUEST }

    // счетчик статик, чтоб у каждой машины был свой id
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private final int id;
    private final Type type;
    private final long createdAt;

    public Car(Type type) {
        this.id = COUNTER.incrementAndGet(); //плюсуем, чтоб id не дублировались
        this.type = type;
        this.createdAt = System.currentTimeMillis();
    }

    public int getId()       { return id; }
    public Type getType()    { return type; }
    public long getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        // вывод в консоль
        return "[" + (type == Type.TENANT ? "Арендатор" : "Гость") + " #" + id + "]";
    }
}