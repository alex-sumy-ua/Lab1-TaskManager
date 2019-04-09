package com.plachkovskyy.aleksandr.tasks.exceptions;

/**
 * Description of ListException - expansion of the class Exception.
 */
public class MyException extends Exception {

    /**
     * Index out of range of the list.
     */
    public MyException() {
        super("Index out of range of the list");
    }

    /**
     * Your own described Exception.
     */
    public MyException(String descry) {
        super(descry);
    }

    /**
     * Intercepted Exception.
     */
    public MyException(Throwable cause) {
        super(cause);
    }

    /**
     * Intercepted Exception with your own description.
     */
    public MyException(String descry, Throwable cause) {
        super(descry, cause);
    }

}
