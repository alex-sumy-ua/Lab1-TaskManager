package com.plachkovskyy.aleksandr.tasks.model;

import java.util.Iterator;

/**
 * Class LinkedTaskList of the tasks list.
 */
public class LinkedTaskList extends TaskList {

    private int length;
    private Node firstNode;
    private Node lastNode;

    /**
     * Constructor of task-list.
     */
    public LinkedTaskList() {
        firstNode = new Node();
        lastNode  = new Node();
        length      = 0;
    }

    /**
     * The cell of the list - internal class.
     */
    public class Node implements Cloneable {
        private Node previous;
        private Task task;
        private Node next;

        /**
         * Move the pointer to the next node.
         */
        public Node getNext() {
            return next;
        }

        /**
         * Move the pointer to the previous node.
         */
        public Node getPrevious() {
            return previous;
        }

        /**
         * Set content of the next node.
         */
        public void setNext(Node node) {
            next = node;
        }

        /**
         * Set content of the previous node.
         */
        public void setPrevious(Node node) {
            previous = node;
        }

        /**
         * Checkin if next exists.
         */
        public boolean hasNext() {
            return (next.getContent() != null);
        }

        /**
         * Checkin if previous exists.
         */
        public boolean hasPrevious() {
            return (previous.getContent() != null);
        }

        /**
         * Set content (Task) to the current node.
         */
        public void setContent(Task task) {
            this.task = task;
        }

        /**
         * Get content (Task) of the current node.
         */
        public Task getContent() {
            return task;
        }

        /**
         * Clone of Node.
         */
        @Override
        public Node clone() throws CloneNotSupportedException {
            return (Node)super.clone();
        }

    }   // ************* End of internal classs Node ***************************

    /**
     * Add element to the end of List.
     */
    @Override
    public void add(Task task) {
        if (task == null) {
            throw new NullPointerException("The task can not be null!");
        }
        if (length == 0) {
            firstNode.setContent(task);
            firstNode.setNext(lastNode);
        } else {
            Node previous = lastNode;
            previous.setContent(task);
            lastNode = new Node();
            lastNode.setPrevious(previous);
            previous.setNext(lastNode);
        }
        length++;
    }

    /**
     * Get length of the List.
     */
    @Override
    public int size() {
        return length;
    }

    /**
     * Get content of the element by index.
     */
    @Override
    public Task getTask(int index) {
        if (index > length || length < 0) {
            return null;
        }

        Node current = firstNode;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getContent();
    }

    /**
     * Remove element from List.
     */
    @Override
    public boolean remove(Task task) {
        if (task == null) {
            throw new NullPointerException("The task can not be null!");
        }

        Node previous = firstNode;
        Node current  = firstNode;
        for (int i = 0; i < length; i++) {
            if (current.getContent() == task) {
                if (length == 1) {
                    firstNode = new Node();
                    lastNode = new Node();
                } else if (current == firstNode) {
                    firstNode = firstNode.getNext();
                    firstNode.setPrevious(null);
                } else if (current == lastNode) {
                    lastNode.setPrevious(lastNode.getPrevious());
                    lastNode.setNext(null);
                } else {
                    previous.setNext(current.getNext());
                    current.setPrevious(previous);
                }
                length--;
                return true;
            }
            previous = current;
            current = previous.getNext();
        }
        return false;
    }

    /**
     * Create TaskList type of List.
     */
    @Override
    public TaskList  createList() {
        return new LinkedTaskList();
    }

    /**
     * Implementation of the iterator().
     */
    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            int counter = 0;
            Node current = firstNode;
            Task task;

            @Override
            public void remove() {
                if (counter == 0)
                    throw new IllegalStateException("The next method has not yet been called!");
                LinkedTaskList.this.remove(task);
                counter--;
            }

            @Override
            public boolean hasNext() {
                return counter < size();
            }

            @Override
            public Task next() {
                task = current.getContent();
                current = current.getNext();
                counter++;
                return task;
            }
        };
    }

    /**
     * Redefining of equals().
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LinkedTaskList)) return false;
        LinkedTaskList tasks = (LinkedTaskList) o;
        if (this == tasks)
            return true;
        if (this == null || tasks == null)
            return false;
        if (length != tasks.size()) return false;
        Node current = firstNode;
        Node current2 = tasks.firstNode;
        while (current.getContent() != null) {
            if (!current.getContent().equals(current2.getContent()))
                return false;
            current = current.getNext();
            current2 = current2.getNext();
        }
        return true;
    }

    /**
     * Redefining of haskCode().
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 0;
        for (Task task : this)
            result = result + prime + (task == null ? 0 : task.hashCode());
        return result;
    }

    /**
     * Redefining of toString().
     */
    @Override
    public String toString() {
        Node current = firstNode;
        String output = "";
        while (current.getContent() != null) {
            output += "[" + current.getContent().toString() + "]";
            current = current.getNext();
        }
        return output;
    }

    /**
     * Redefining of the method clone() of the class Object.
     * Aplying deep cloning.
     */
    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList clone = (LinkedTaskList)super.clone();
        clone.firstNode = firstNode.clone();
        clone.lastNode = lastNode.clone();
        clone.length = 0;
        Node current = firstNode;
        while (current.getContent() != null) {
            clone.add(current.getContent().clone());
            current = current.getNext();
        }
        return clone;
    }
}
