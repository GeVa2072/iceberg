package fr.filigrane.technologie.ice.observable;

public interface Observer<O extends Observable<E>, E> {

	default Observer<O, E> accept(E match) {
		return (observer, event) -> {
			if (match == event) update(observer, event);
		};
	}

	void update(final Observable<E> eObservable, final E event);
}
