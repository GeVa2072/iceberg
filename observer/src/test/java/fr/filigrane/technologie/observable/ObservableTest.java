package fr.filigrane.technologie.observable;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.xml.sax.DocumentHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.isNull;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.noMoreInteractions;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class ObservableTest {

	@Test
    public void all_observer_added_on_constructor_be_notify() throws Exception {
        Observer obs1 = mock(Observer.class);
        Observer obs2 = mock(Observer.class);

        Object o = new Object();

        Observable observable = new Observable(obs1, obs2) {
        };

        observable.setChanged();
        observable.notifyObservers(o); // obs1.update => + 1 / obs2.update => 1

        verify(obs1, times(1)).update(same(observable), same(o));
        verify(obs2, times(1)).update(same(observable), same(o));
    }
	
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
        observable.notifyObservers(); // obs1.update => 1 / obs2.update => 1

        observable.deleteObserver(obs1);

        observable.setChanged();
        observable.notifyObservers(o); // obs1.update => +1

        verify(obs1, times(1)).update(same(observable), isNull());
        verify(obs1, noMoreInteractions()).update(same(observable), same(o));
        
        verify(obs2, times(1)).update(same(observable), isNull());
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
    
    @Test(expected=NullPointerException.class)
    public void assert_NullPointerException_on_add_null_observer() {
    	
    	Observable observable = new Observable() {
        };

        observable.addObserver(null);
    }

    @Test
    public void assert_adding_to_time_same_observer_do_nothing() {
    	
    	Observable observable = new Observable() {
        };

        Observer obs1 = mock(Observer.class);
        
        observable.addObserver(obs1);
        observable.addObserver(obs1);
        
        assertThat(observable.countObservers()).isEqualTo(1);
    	
    }
    
    @Test
    public void assert_no_changed_observable_notification_has_no_effect() {
    	Observer obs1 = mock(Observer.class);

        Object o = new Object();

        Observable observable = new Observable() {
        };

        observable.addObserver(obs1);

        observable.notifyObservers();
        
        verify(obs1, noMoreInteractions()).update(any(), any());
    }
    
    @Ignore
    @Test
    public void assert_notification_on_same_time_keep_execution_order() throws Exception {
    	Observer obs1 = mock(Observer.class);

        Object o = new Object();
        
        Observable observable = Mockito.spy(new Observable() {
        	@Override
        	protected synchronized void clearChanged() {
        	}
        });
        
        Mockito.doThrow(new InterruptedException()).when(observable).clearChanged();

        observable.addObserver(obs1);

        Thread t1 = new Thread(() -> {
        	observable.setChanged();
        	observable.notifyObservers(o); 
        }); // obs2 should be notify

        Thread t2 = new Thread(() -> {
        	observable.setChanged();
        	observable.notifyObservers(o); 
        });
        
        t1.start();
        t2.start();
                
        t1.join();
        t2.join();
        
        //verify(obs1, times(1)).update(same(observable), same(o));
    }
}