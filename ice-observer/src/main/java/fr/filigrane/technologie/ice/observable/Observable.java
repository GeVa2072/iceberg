package fr.filigrane.technologie.ice.observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Vector;

public class Observable<E> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

	private boolean changed = false;
	private final Vector<Observer<? extends Observable<E>, E>> observers = new Vector();

	public Observable() {
		LOGGER.trace("Observable '{}' was created", this);
	}

	/**
	 * Construct Observable with provided observers.
	 *
	 * @param observers initially added
	 */
	public Observable(final Observer<? extends Observable<E>, E>...observers) {
		LOGGER.trace("Observable '{}' was created", this);
		Arrays.stream(observers).forEach(this::addObserver);
	}

	/**
	 * Notify all registered Observer that Observable hasChanged
	 */
	public void notifyObservers() {
		notifyObservers(null);
	}

	/**
	 * Notify all registered Observer that Observable hasChanged
	 * @param event
	 */
	public void notifyObservers(E event) {
		Observer observersCopy[];

		/*
		 * Duplicate observers list because observer.update should be slow
		 * And observers list can change and you will notify all observers
		 * register at time of the notify was fire.
		 *
		 * Need to be synchronized to access to observers vector.
		 */
		synchronized(this) {
			if (!this.changed) {
				return;
			}
			LOGGER.trace("Observable notifies starting");
			observersCopy = observers.toArray(new Observer[observers.size()]);
			this.clearChanged();
		}

		for (Observer observer: observersCopy) {
			LOGGER.trace("Observer '{}' notifying", observer);
			observer.update(this, event);
			LOGGER.trace("Observer '{}' notified", observer);
		}

		LOGGER.trace("Observable notified !");
	}
	/**
	 * Add Observer to this Observable objet.
	 *
	 * @param observer
	 */
	public synchronized void addObserver(Observer<? extends Observable<E>, E> observer) {
		if (observer == null) {
			throw new NullPointerException();
		} else {
			if(! observers.contains(observer)) {
				observers.addElement(observer);
				LOGGER.trace("Observer '{}' was added", observer);
			} else {
				LOGGER.trace("Observer '{}' was already present, skip", observer);
			}
		}
	}

	/**
	 * Remove obeserver
	 *
	 * @param observer to remove
	 */
	public synchronized void removeObserver(final Observer<Observable<E>, E> observer) {
		observers.remove(observer);
		LOGGER.trace("Observer '{}' was removed", observer);
	}

	/**
	 * Retrieved count of added Observer
	 * synchronized for Vector of Observer access
	 *
	 * @return
	 */
	public synchronized int countObservers() {
		return observers.size();
	}

	/**
	 * Remove all added Observer @See removeObserver
	 */
	public synchronized void removeObservers() {
		observers.clear();
		LOGGER.trace("Observers was cleared");
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
}
