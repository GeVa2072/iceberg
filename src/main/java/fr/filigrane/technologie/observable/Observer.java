package fr.filigrane.technologie.observable;

public interface Observer<O extends Observable<E>, E> {
    void update(O observable, E event);
}
