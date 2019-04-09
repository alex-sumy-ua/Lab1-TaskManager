package com.plachkovskyy.aleksandr.tasks.model;

import com.plachkovskyy.aleksandr.tasks.exceptions.MyException;

import java.util.Iterator;

/**
 * Class ArrayTaskList for the list of tasks.
 */
public class ArrayTaskList extends TaskList {

    private Task[]  taskList;           // list of tasks
    private int     listLength  = 10;   // array start length
    private int     realSize    = 0;    // array real size

    /**
     *   Constructor of empty list.
     */
    public ArrayTaskList () {
        taskList    =   new Task[listLength];
        listLength  =   taskList.length;
        realSize    =   size();
    }

    /**
     * Adding new element (task) into list.
     */
    @Override
    public void add(Task task) throws MyException {
        if (task == null)
            throw new MyException("Cannot add null into the list!");
        if (realSize < listLength) {
            taskList[realSize++]  =   task;     // realSize increased!!!
            // Checking/correction array size
            if ((realSize / listLength) > 0.75) {
                listLength = (int) (listLength * 1.3);
                Task[] tempList = new Task[listLength];
                System.arraycopy(taskList, 0, tempList, 0, realSize);
                taskList = tempList;
            }
        }
    }

    /**
     * Removes only first appropriate element from list.
     * Returns true if element was found and removed,
     * returns false if element was not found.
     */
    @Override
    public boolean remove(Task task) {
        boolean done = false;
        if (task == null) return done;
        for (int i = 0; i < realSize; i++) {
            if (taskList[i].equals(task)) {
                Task[]  tempList = new Task[listLength];
                System.arraycopy(taskList, 0, tempList, 0, i);
                System.arraycopy(taskList, i+1, tempList, i, realSize-i-1);
                taskList = tempList;
                realSize--;
                done = true;
                break;
            }
        }
        // Optimization of the size of array. Important: minimum size is 10
        if (((realSize / listLength) < 0.5) &&
                ((listLength * 0.75) > 10)) {
            listLength = (int) (listLength * 0.75);
            Task[]  tempList = new Task[listLength];
            System.arraycopy(taskList, 0, tempList, 0, realSize);
            taskList = tempList;
        }
        return done;
    }

    /**
     * Returns tasks total.
     */
    @Override
    public int size() {
        return realSize;
    }

    /**
     * Returns Task by index.
     */
    @Override
    public Task getTask(int index) {
        if ((index >= 0) && (index < listLength)) return taskList[index];
        else return null;
    }

    /**
     * Create TaskList of both types.
     */
    @Override
    public TaskList createList() {
        return new ArrayTaskList();
    }

    /**
     * Redefining of the method iterator().
     */
    @Override
    public Iterator<Task> iterator() {

        Iterator<Task> it = new Iterator<Task>() {
            private int currentIndex = -1;
            Task task;

            @Override
            public boolean hasNext() {
                return currentIndex != realSize - 1;
            }

            @Override
            public Task next() {
                return getTask(++currentIndex);
            }

            @Override
            public void remove() {
                if (currentIndex == -1)
                    throw new IllegalStateException("Next hasn't been called yet!");
                ArrayTaskList.this.remove(ArrayTaskList.this.taskList[currentIndex--]);
            }
        };
        return it;
    }

    /**
     * Redefining of the method toString() of the class Object.
     * The method returns the name of the class of the object, length
     * and enumeration elements.
     */
    public String toString() {
        StringBuffer strbuf = new StringBuffer(this.getClass().getName());
        strbuf.append(", length = ").append(realSize).append(", {");
        for (int i = 0; i < realSize; i++)
            strbuf.append(this.taskList[i]).append(", ");
        strbuf.append('}');
        return strbuf.toString();
    }

    /*
     * Redefining of method equals().
     */
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject) return true;
        if(otherObject == null || this == null) return false;
        if(getClass() != otherObject.getClass()) return false;
        ArrayTaskList aTL = (ArrayTaskList)otherObject;
        if (realSize != aTL.size()) return false;
        for (int i = 0; i < realSize; i++)
            if (! this.getTask(i).equals(aTL.getTask(i))) return false;
        return true;
    }

    /*
     * Redefining of method hashCode().
     */
    @Override
    public int hashCode() {
        final int prime = 1113;
        int result = 0;
        for (Task task: this)
            result += prime * (task == null ? 0 : task.hashCode());
        return result;
    }

    /**
     * Redefining of the method clone() of the class Object.
     * Aplying deep cloning.
     */
    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList clone = (ArrayTaskList)super.clone();
        clone.taskList = this.taskList.clone();
        for (int i = 0; i < listLength; i++)
            if (this.taskList[i] != null)
                clone.taskList[i] = this.taskList[i].clone();
        return clone;
    }

}
