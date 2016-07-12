/**
 * Author: Morten Beuchert
 */

package tictactoe.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import tictactoe.util.ObserverBase;

public class Observable {
    private Set<ObserverBase> observers = new HashSet<ObserverBase>();

    public void add(ObserverBase o) {
    	observers.add(o);
    }

    public void notifyObservers() {
        Iterator<ObserverBase> it = observers.iterator();
        while (it.hasNext()) {
            it.next().update();
        }
    }
}