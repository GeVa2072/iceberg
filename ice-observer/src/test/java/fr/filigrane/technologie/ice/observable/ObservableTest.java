package fr.filigrane.technologie.ice.observable;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.noMoreInteractions;

public class ObservableTest {

	@Test
	public void should_notify_all_added_observer() {
		Observable<Object> observable = new Observable() {};

		Observer<Observable<Object>, Object> obs = mock(Observer.class);
		observable.addObserver(obs);

		observable.setChanged();
		observable.notifyObservers();
		verify(obs, times(1)).update(any(), any());
	}

	@Test
	public void should_not_notify_removed_observer() {
		Observable<Object> observable = new Observable() {};

		Observer<Observable<Object>, Object> obs = mock(Observer.class);
		observable.addObserver(obs);

		observable.setChanged();
		observable.notifyObservers();
		verify(obs, times(1)).update(any(), any());

		observable.removeObserver(obs);

		observable.setChanged();
		observable.notifyObservers();
		verify(obs, noMoreInteractions()).update(any(), any());
	}

	@Test
	public void should_notify_constructor_given_observer() {
		Observer<Observable<Object>, Object> obs = mock(Observer.class);

		Observable<Object> observable = new Observable(obs) {};

		observable.setChanged();
		observable.notifyObservers();
		verify(obs, times(1)).update(any(), any());
	}

	@Test(expected=NullPointerException.class)
	public void should_not_be_able_to_add_null_observer() {
		Observable<Object> observable = new Observable() {};

		observable.addObserver(null);
	}

	@Test
	public void should_not_notify_multiple_time_observer_added_twice() {
		Observable<Object> observable = new Observable() {};

		Observer<Observable<Object>, Object> obs = mock(Observer.class);
		observable.addObserver(obs);
		observable.addObserver(obs);

		observable.setChanged();
		observable.notifyObservers();
		verify(obs, times(1)).update(any(), any());
	}

	@Test
	public void should_give_me_observer_added_count() {

		Observable<Object> observable = new Observable() {};

		assertThat(observable.countObservers()).isEqualTo(0);

		Observer<Observable<Object>, Object> obs = mock(Observer.class);
		observable.addObserver(obs);
		assertThat(observable.countObservers()).isEqualTo(1);

		observable.addObserver(obs); // add already added observer has no effect
		assertThat(observable.countObservers()).isEqualTo(1); // stay at 1 because this observer is already added

		Observer<Observable<Object>, Object> obs2 = mock(Observer.class);
		observable.addObserver(obs2);
		assertThat(observable.countObservers()).isEqualTo(2);

		observable.removeObserver(obs);
		observable.removeObserver(obs2);

		assertThat(observable.countObservers()).isEqualTo(0);
	}

	@Test
	public void should_remove_all_observer_in_one_operation() {
		Observable<Object> observable = new Observable() {};

		Observer<Observable<Object>, Object> obs = mock(Observer.class);
		observable.addObserver(obs);

		Observer<Observable<Object>, Object> obs2 = mock(Observer.class);
		observable.addObserver(obs2);

		observable.removeObservers();
		assertThat(observable.countObservers()).isEqualTo(0);

		observable.setChanged();
		observable.notifyObservers();

		verify(obs, times(0)).update(any(), any());
	}

	@Test
	public void should_be_notify_when_previous_observer_is_slow_and_observer_removed() throws Exception {

		Observable<Object> observable = new Observable() {};

		Observer<Observable<Object>, Object> obs = mock(Observer.class);
		observable.addObserver(obs);

		Observer<Observable<Object>, Object> obs2 = mock(Observer.class);
		observable.addObserver(obs2);

		doAnswer((e) -> { Thread.sleep(100); return null;} ).when(obs).update(any(), any());
		Thread t1 = new Thread(() -> {
			observable.setChanged();
			observable.notifyObservers();
		});

		t1.start(); // notify in new thread
		Thread.sleep(10); // wait thread to start, notification call

		observable.removeObserver(obs2); // remove observer 2

		t1.join(); // wait all observers notified !

		verify(obs, times(1)).update(any(), any());
		verify(obs2, times(1)).update(any(), any()); // should be notify, event fire before remove
	}

	@Test
	public void should_not_notify_if_observable_has_not_changed() {
		Observable<Object> observable = new Observable() {};

		Observer<Observable<Object>, Object> obs = mock(Observer.class);
		observable.addObserver(obs);

		observable.notifyObservers();

		verify(obs, times(0)).update(any(), any());
	}

	@Test
	public void should_be_notify_once_if_observable_has_not_changed() {
		Observable<Object> observable = new Observable() {};

		Observer<Observable<Object>, Object> obs = mock(Observer.class);
		observable.addObserver(obs);

		observable.setChanged();
		observable.notifyObservers();
		observable.notifyObservers();

		verify(obs, times(1)).update(any(), any());
	}

	@Test
	public void should_verify_if_observable_has_changed() {
		Observable<Object> observable = new Observable() {};

		Observer<Observable<Object>, Object> obs = mock(Observer.class);
		observable.addObserver(obs);

		observable.setChanged();

		assertThat(observable.hasChanged()).isTrue();

		observable.notifyObservers();
		assertThat(observable.hasChanged()).isFalse();


		verify(obs, times(1)).update(any(), any());
	}

	@Test
	public void observer_should_be_notify_with_notification_object() {
		Observable<Object> observable = new Observable() {};

		Observer<Observable<Object>, Object> obs = mock(Observer.class);
		observable.addObserver(obs);

		observable.setChanged();
		Object o = new Object();

		observable.notifyObservers(o);

		verify(obs, times(1)).update(same(observable), same(o));
	}
}
