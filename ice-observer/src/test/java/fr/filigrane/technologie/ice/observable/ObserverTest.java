package fr.filigrane.technologie.ice.observable;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ObserverTest {

	public enum Event {
		EVENT1, EVENT2
	}

	@Test
	public void assert_accept_predicate_before_notify_should_not_be_invoked() {
		Observer<Observable<Event>, Event> o1 = spy(new Observer<Observable<Event>, Event>() {
			@Override
			public void update(final Observable<Event> observable, final Event event) {
				// Assert NOT CALL
			}
		});

		Observer<Observable<Event>, Event> obs = o1.accept(Event.EVENT1);

		obs.update(null, null);
		obs.update(null, Event.EVENT2);

		verify(o1, times(0)).update(any(), any());
	}

	@Test
	public void assert_accept_predicate_before_notify_should_be_invoked() {
		Observer<Observable<Event>, Event> o1 = spy(new Observer<Observable<Event>, Event>() {
			@Override
			public void update(final Observable<Event> observable, final Event event) {
			}
		});

		Observer<Observable<Event>, Event> obs = o1.accept(Event.EVENT1);

		obs.update(null, Event.EVENT1);

		verify(o1, times(1)).update(any(), same(Event.EVENT1));
	}
}
