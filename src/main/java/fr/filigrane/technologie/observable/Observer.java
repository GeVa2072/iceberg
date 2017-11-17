package fr.filigrane.technologie.observable;

import java.util.function.Predicate;

@FunctionalInterface
public interface Observer<O extends Observable<E>, E> {
    void update(O observable, E event);

    default Observer<O, E> accept(E match) {
        return new Observer<O, E>() {
            @Override
            public void update(final O observer, final E event) {
                if (match == event) Observer.this.update(observer, event);
            }
        };
    }

}
