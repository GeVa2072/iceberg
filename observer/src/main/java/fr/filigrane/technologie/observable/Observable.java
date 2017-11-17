package fr.filigrane.technologie.observable;

import java.util.Arrays;
import java.util.Vector;

public abstract class Observable<E> {

    private boolean changed = false;
    private Vector<Observer<? extends Observable<E>, E>> obs = new Vector<>();

    public Observable() {
    }

    public Observable(Observer<? extends Observable<E>, E>...observers) {
        Arrays.stream(observers).forEach(this::addObserver);
    }

    public synchronized void addObserver(Observer<? extends Observable<E>, E> observer) {
        if (observer == null) {
            throw new NullPointerException();
        } else {
            if (!this.obs.contains(observer)) {
                this.obs.addElement(observer);
            }

        }
    }

    public synchronized void deleteObserver(Observer<? extends Observable<E>, E> observer) {
        this.obs.removeElement(observer);
    }

    public void notifyObservers() {
        this.notifyObservers(null);
    }

    public void notifyObservers(E event) {
        Observer<? extends Observable<E>, E> observers[];
        synchronized(this) {
            if (!this.changed) {
                return;
            }

            observers = (Observer<? extends Observable<E>, E>[]) obs.toArray(new Observer[obs.size()]);
            this.clearChanged();
        }

        for(Observer observer: observers) {
            observer.update(this, event);
        }

    }

    public synchronized void deleteObservers() {
        this.obs.removeAllElements();
    }

    protected synchronized void setChanged() {
        this.changed = true;
    }

    protected synchronized void clearChanged() {
        this.changed = false;
    }

    public synchronized boolean hasChanged() {
        return this.changed;
    }

    public synchronized int countObservers() {
        return this.obs.size();
    }
}
