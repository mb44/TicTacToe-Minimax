/**
 * Author: Morten Beuchert
 */

package tictactoe.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import tictactoe.util.ObserverBase;

public class Observable {
    private Set<ObserverBase> observers;

    public void add(ObserverBase O) {
        this.observers = new HashSet<ObserverBase>();
    }

    public void notifyObservers() {
        Iterator<ObserverBase> it = this.observers.iterator();
        while (it.hasNext()) {
            it.next().update();
        }
    }
}