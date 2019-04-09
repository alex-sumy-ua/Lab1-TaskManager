package com.plachkovskyy.aleksandr.tasks;

import com.plachkovskyy.aleksandr.tasks.view.StartFrame;
import org.apache.log4j.Logger;

public class Controller {

    final static Logger logger = Logger.getLogger(Controller.class);
    public Controller() {
        StartFrame startFrame = new StartFrame();
    }

//        Thread t = new Thread(controller);
//        t.setDaemon(true);
//        t.start();                      //Thread for reminders

}
