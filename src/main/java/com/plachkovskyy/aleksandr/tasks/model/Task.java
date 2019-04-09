package com.plachkovskyy.aleksandr.tasks.model;

import com.plachkovskyy.aleksandr.tasks.exceptions.MyException;
import java.io.Serializable;
import java.util.Date;

/**
 * Class Task for some real task.
 */
public class Task implements Cloneable, Serializable {

    private String  title;      // name of task
    private Date    time;       // time of task without of repeat
    private Date    start;      // start-time of task with repeat
    private Date    end;        // end-time of task with repeat
    private int     interval;   // interval-time of task with repeat
    private boolean active;     // the task is active right now
    private boolean repeated;   // the task is repeated

    /**
     * Constructor1: inactive Task with parameters.
     */
    public  Task(String title, Date time) {
        this.title      =   title;
        this.time       =   time;
//        this.active     =   false;  // not necessary initialization
//        this.repeated   =   false;  // not necessary initialization
    }

    /**
     * Constructor2 of the Task with parameters.
     */
    public  Task(String title, Date start, Date end, int interval) throws MyException {
        if (start == null || end == null) throw new MyException("Start and End cannot be null!");
        if (interval <= 0) throw new MyException("Interval must be more then zero!");
        if (end.before(start)) throw new MyException("End-time cannot be less then start-time!");
        this.title      =   title;
        this.start      =   start;
        this.end        =   end;
        this.interval   =   interval;
//        this.active     =   false;  // not necessary initialization
        this.repeated   =   true;
    }

    /**
     * Get title method.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title method.
     */
    public void setTitle(String title) throws MyException {
        if ((title != null) && (!title.equals("")))
            this.title = title;
        else throw new MyException("Title mustn't be empty!");
    }

    /**
     * Get active method.
     */
    public boolean isActive() {
        return active;

    }

    /**
     * Set active method.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * If repeated, return start-time.
     */
    public Date getTime() {
        if (!repeated) return time;
        else return start;
    }

    /**
     * If repeated, make it nonrepeated.
     */
    public void setTime(Date time) {
        repeated        =   false;
        this.time       =   time;
    }

    /**
     * If nonrepeated, return time.
     */
    public Date getStartTime() {
        if (repeated) return start;
        else return time;
    }

    /**
     * If nonrepeated, return time.
     */
    public Date getEndTime() {
        if (repeated) return end;
        else return time;
    }

    /**
     * If nonrepeated, return 0.
     */
    public int getRepeatInterval() {
        if (repeated) return interval;
        else return 0;
    }

    /**
     * If nonrepeated, make it repeated.
     */
    public void setTime(Date start, Date end, int interval) throws Exception {
        if (start == null || end == null)
            throw new Exception("Start and End cannot be null!");
        if (interval <= 0)
            throw new Exception("Interval must be more then zero!");
        if (end.before(start))
            throw new Exception("End-time cannot be less then start-time!");
        this.start      =   start;
        this.end        =   end;
        this.interval   =   interval;
        if (!repeated) repeated = true;
    }

    /**
     * If it is repeated.
     */
    public boolean isRepeated() {
        return repeated;
    }

    /**
     * If possible, return next time or start, or return -1, if impossible.
     */
    public Date nextTimeAfter(Date current) {
        if (!active) return null;
        if (!repeated) {
            if (current.after(time) || current.equals(time)) return null;
            return new Date(time.getTime());
        }
        if (current.after(end)) return null;
        if (start.after(current)) return new Date(start.getTime());
        Date next = new Date(start.getTime());
        do {
            next.setTime(next.getTime() + interval*1000);
        } while (next.before(current));
        if (current.equals(next)) next.setTime(next.getTime() + interval*1000);
        if (next.after(end)) return null;
        return next;
    }

    /*
     * Redefining of method equals().
     */
    @Override
    public boolean equals(Object otherObject) {
        if(this == otherObject) return true;
        if(otherObject == null || this.getClass() != otherObject.getClass()) return false;
        Task other = (Task) otherObject;
        if (!isRepeated()) {
            return  !other.isRepeated() &&
                    this.hashCode() == other.hashCode() &&
                    this.title.equalsIgnoreCase(other.title) &&
                    Boolean.compare(this.isActive(), other.isActive()) == 0 &&
                    this.time.compareTo(other.time) == 0;
        } else {
            return  other.isRepeated() &&
                    this.hashCode() == other.hashCode() &&
                    this.title.equals(other.title) &&
                    Boolean.compare(this.isActive(), other.isActive()) == 0 &&
                    this.start.compareTo(other.start) == 0 &&
                    this.end.compareTo(other.end) == 0 &&
                    this.interval == other.interval;
        }
    }

    /*
     * Redefining of method hashCode().
     */
    @Override
    public int hashCode() {
        final int prime = 1113;
        int result = 1;
        result = prime * result + (this.isRepeated() ? 0 : prime);
        result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
        result = prime * result + (this.isActive() ? 0 : prime);
        if (!isRepeated()) {
            result = prime * result + this.time.hashCode();
        } else {
            result = prime * result + this.start.hashCode();
            result = prime * result + this.end.hashCode();
            result = prime * result + this.interval;
        }
        return result;
    }

    /*
     * Redefining of method toString().
     */
    @Override
    public String toString() {
        StringBuffer strbuf = new StringBuffer("Task{ title: ");
        strbuf.append(this.title);
        strbuf.append(", time: ");
        strbuf.append(this.time);
        strbuf.append(", start: ");
        strbuf.append(this.start);
        strbuf.append(", end: ");
        strbuf.append(this.end);
        strbuf.append(", interval: ");
        strbuf.append(this.interval);
        strbuf.append(", active: ");
        strbuf.append(this.active);
        strbuf.append(", repeated: ");
        strbuf.append(this.repeated);
        strbuf.append('}');
        return strbuf.toString();
    }

    /*
     * Redefining of method clone().
     */
    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task)super.clone();
    }

}
