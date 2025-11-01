package subject;

import observers.PlantObserver;
import java.util.ArrayList;
import java.util.List;

public class PlantMonitor {
    private List<PlantObserver> observers = new ArrayList<>();
    private double humidity;
    private double light;

    public void addObserver(PlantObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(PlantObserver observer) {
        observers.remove(observer);
    }

    public void setData(double humidity, double light) {
        this.humidity = humidity;
        this.light = light;
        notifyObservers();
    }

    private void notifyObservers() {
        for (PlantObserver observer : observers) {
            observer.update(humidity, light);
        }
    }
}
