package polymorphs.a301.f17.cs414.thexgame.persistence;

import java.util.ArrayList;

/**
 * Created by Miles on 10/20/2017.
 * This is the basic framework for building listeners for the observer pattern implemented for the database IO.
 * It includes the attach and detach behaviors and should be the parent of all Listener classes
 */

class ListenerBase<E> {
    protected ArrayList<E> observers;

    public ListenerBase() {
        observers = new ArrayList<>();
    }

    /**
     * Adds the passed observer to the list of observers to update.
     * @param uObserve - the observer to attach
     * @return true if the observer was not already attached, false if it was
     */
    public boolean attach(E uObserve) {
        if (observers.contains(uObserve)) return false;
        observers.add(uObserve);
        return true;
    }

    /**
     * Removes the passed observer from the list of observers to update.
     * @param uObserve - the observer to attach
     * @return true if the observer was attached, false if it was not attached
     */
    public boolean deattach(E uObserve) {
        if (!observers.contains(uObserve)) return false;
        observers.remove(uObserve);
        return true;
    }
}
