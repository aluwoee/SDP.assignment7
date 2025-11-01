package subject;

import models.SensorData;
import observer.Observer;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class PlantSensor implements Subject {
    private final String id;
    private final List<Observer> observers = new CopyOnWriteArrayList<>();
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public PlantSensor(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(SensorData data) {
        for (Observer o : observers) {
            pool.submit(() -> o.update(this, data));
        }
    }

    public void readAndNotify(double soilMoisture, double lightLux) {
        SensorData data = new SensorData(soilMoisture, lightLux, Instant.now());
        notifyObservers(data);
    }

    public void shutdown() {
        pool.shutdownNow();
    }
}

