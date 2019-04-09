package com.plachkovskyy.aleksandr.tasks.model;

import com.plachkovskyy.aleksandr.tasks.exceptions.*;

import java.util.*;

/**
 * Class Tasks for some real task.
 */
public class Tasks {

    /**
     * Returns tasks list, which will be planned at least once
     * after "from" and not later than "to".
     * Any collection for storing tasks.
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end) throws MyException {
        ArrayTaskList collection = new ArrayTaskList();     //  Iterator<Task> iterator = tasks.iterator(); // it would be better
        for (Task task: tasks) {                            // not to use exact realisation
            if (task.isActive() &&
                task.nextTimeAfter(start) != null &&
                task.nextTimeAfter(start).after(start) &&
                task.nextTimeAfter(start).compareTo(end) <= 0) {
                collection.add(task);
            }
        }
        return (Iterable<Task>) collection;
    }

    /**
     * Task calendar for a given period.
     */
    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) {
        SortedMap<Date, Set<Task>> sortedMap = new TreeMap<Date, Set<Task>>();
        for (Task task: tasks) {                                // iterator in tasks by task
            Date date = task.nextTimeAfter(start);
            while (date != null && date.compareTo(end) <= 0) {  // work with current task
                if (sortedMap.containsKey(date)) {              // adding into existing set
                    sortedMap.get(date).add(task);
                }
                else {
                    Set<Task> tasksSet = new HashSet<Task>();   // adding into new set
                    tasksSet.add(task);
                    sortedMap.put(date, tasksSet);
                }
                date = task.nextTimeAfter(date);                // iterator by date in current task
            }

        }
        return sortedMap;
    }

}
