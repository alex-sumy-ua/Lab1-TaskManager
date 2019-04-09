package com.plachkovskyy.aleksandr.tasks.model;

import com.plachkovskyy.aleksandr.tasks.exceptions.*;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Class TaskList for the list of tasks.
 */
abstract class TaskList implements Iterable<Task>, Cloneable, Serializable {

    /**
     * Adding new element (task) into list.
     */
    public abstract void add(Task task) throws MyException;

    /**
     * Removes only first appropriate element from list.
     * Returns true if element was found and removed,
     * returns false if element was not found.
     */
    public abstract boolean remove(Task task) throws MyException;

    /**
     * Returns tasks total.
     */
    public abstract int size();

    /**
     * Returns Task by index.
     */
    public abstract Task getTask(int index);

    /**
     * Returns tasks list, which will be planned at least once
     * after "from" and not later than "to".
     */
/*    public TaskList incoming(int from, int to) throws MyException {
        if (from < 0)
            throw new MyException("From-time cannot be less then zero!");
        if (to <= from)
            throw new MyException("To-time must be more then from-time!");
        TaskList taskList = createList();
        for (int i = 0; i < size(); i++)
            if ((getTask(i).nextTimeAfter(from) >= from) &&
                (getTask(i).nextTimeAfter(from) <= to))
                taskList.add(getTask(i));
        return taskList;
    }
*/
    /**
     * Create TaskList of both types.
     */
    public abstract TaskList createList();

    /**
     * Redefining of the method iterator().
     */
    public abstract Iterator<Task> iterator();

    /**
     * Redefining of the method toString() of the class Object.
     * The method returns the name of the class of the object, length
     * and enumeration elements.
     */
    @Override
    public abstract String toString();

    /*
     * Redefining of method equals().
     */
    @Override
    public abstract boolean equals(Object otherObject);

    /*
     * Redefining of method hashCode().
     */
    @Override
    public abstract int hashCode();

}
