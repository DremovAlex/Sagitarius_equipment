package oriseus.Sagitarius_equipment.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public final class IdGenerator {
    
	private final Map<Class<?>, AtomicLong> sequences = new HashMap<>();

    public long next(Class<?> type) {
        return sequences
            .computeIfAbsent(type, t -> new AtomicLong())
            .incrementAndGet();
    }

    public void init(Class<?> type, long startFrom) {
        sequences
            .computeIfAbsent(type, t -> new AtomicLong())
            .set(startFrom);
    }
}
