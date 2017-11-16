package fr.filigrane.technologie.observable;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ObservableTest {

    @Test
    public void all_observer_added_before_notification_will_be_notify() throws Exception {
        Observer obs1 = mock(Observer.class);
        Observer obs2 = mock(Observer.class);

        Object o = new Object();

        Observable observable = new Observable() {
        };

        observable.addObserver(obs1);

        observable.setChanged();
        observable.notifyObservers(o); // obs1.update => 1

        observable.addObserver(obs2);

        observable.setChanged();
        observable.notifyObservers(o); // obs1.update => + 1 / obs2.update => 1

        verify(obs1, times(2)).update(same(observable), same(o));
        verify(obs2, times(1)).update(same(observable), same(o));
    }

    @Test
    public void all_observer_removed_before_notification_will_not_be_notify() throws Exception {
        Observer obs1 = mock(Observer.class);
        Observer obs2 = mock(Observer.class);

        Object o = new Object();

        Observable observable = new Observable() {
        };

        observable.addObserver(obs1);
        observable.addObserver(obs2);

        observable.setChanged();
        observable.notifyObservers(o); // obs1.update => 1 / obs2.update => 1

        observable.deleteObserver(obs2);

        observable.setChanged();
        observable.notifyObservers(o); // obs1.update => +1

        verify(obs1, times(2)).update(same(observable), same(o));
        verify(obs2, times(1)).update(same(observable), same(o));
    }

    @Test
    public void deleteObservers() throws Exception {
        Observer obs1 = mock(Observer.class);
        Observer obs2 = mock(Observer.class);

        Object o = new Object();

        Observable observable = new Observable() {
        };

        observable.addObserver(obs1);
        observable.addObserver(obs2);

        observable.setChanged();
        observable.notifyObservers(o); // obs1.update => 1 / obs2.update => 1

        observable.deleteObservers();

        observable.setChanged();
        observable.notifyObservers(o); // no observer notify

        verify(obs1, times(1)).update(same(observable), same(o));
        verify(obs2, times(1)).update(same(observable), same(o));

    }

    @Test
    public void clearChanged() throws Exception {
        Observable observable = new Observable() {
        };

        observable.setChanged();
        observable.clearChanged();
        assertThat(observable.hasChanged()).isFalse();
    }

    @Test
    public void hasChanged() throws Exception {
        Observable observable = new Observable() {
        };

        observable.setChanged();
        assertThat(observable.hasChanged()).isTrue();
    }

    @Test
    public void countObservers() throws Exception {
        Observable observable = new Observable() {
        };

        assertThat(observable.countObservers()).isEqualTo(0);
        Observer obs1 = mock(Observer.class);

        observable.addObserver(obs1);
        assertThat(observable.countObservers()).isEqualTo(1);

        Observer obs2 = mock(Observer.class);
        observable.addObserver(obs2);
        assertThat(observable.countObservers()).isEqualTo(2);

        observable.deleteObservers();
        assertThat(observable.countObservers()).isEqualTo(0);

        observable.addObserver(obs1);
        observable.addObserver(obs2);
        assertThat(observable.countObservers()).isEqualTo(2);

        observable.deleteObserver(obs1);
        assertThat(observable.countObservers()).isEqualTo(1);


    }

}