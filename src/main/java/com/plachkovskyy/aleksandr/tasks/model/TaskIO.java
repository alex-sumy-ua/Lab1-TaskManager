package com.plachkovskyy.aleksandr.tasks.model;

import com.plachkovskyy.aleksandr.tasks.exceptions.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class TaskIO {

    /**
     * Записує задачі із списку у потік у бінарному форматі, описаному нижче.
     * @param tasks
     * @param out
     */
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        Iterator<Task> iterator = tasks.iterator();
        DataOutputStream dataOutStream = new DataOutputStream(new BufferedOutputStream(out));
        try {
            dataOutStream.writeInt(tasks.size());           // tasks quantity
            while (iterator.hasNext()) {
                Task task = (Task) iterator.next();
                dataOutStream.writeUTF(task.getTitle());    // task title
                dataOutStream.writeBoolean(task.isActive());
                if (task.isRepeated()) {
                    dataOutStream.writeLong(task.getStartTime().getTime());
                    dataOutStream.writeUTF(" ");
                    dataOutStream.writeLong(task.getEndTime().getTime());
                    dataOutStream.writeInt(task.getRepeatInterval());
                }
                else {
                    dataOutStream.writeLong(task.getTime().getTime());
                }
                dataOutStream.writeUTF("\n");
            }
        } finally {
            dataOutStream.close();
        }

    }

    /**
     * Зчитує задачі із потоку у даний список задач.
     * @param tasks
     * @param in
     */
    public static void read(TaskList tasks, InputStream in) throws IOException, MyException {
        String title;
        boolean isActive;
        Date time = new Date();
        Date startTime = new Date();
        Date endTime = new Date();
        int interval;
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(in));
        try {
            int tasksQuantity = dataInputStream.readInt();
            for (int i = 0; i < tasksQuantity; i++) {
                title = dataInputStream.readUTF();
                isActive = dataInputStream.readBoolean();
                Date tmpTime = new Date();
                tmpTime.setTime(dataInputStream.readLong());
                if (dataInputStream.readUTF().equals(" ")) {// task isRepeated
                    startTime = tmpTime;
                    endTime.setTime(dataInputStream.readLong());
                    interval = dataInputStream.readInt();
                    Task task = new Task(title, startTime, endTime, interval);
                    task.setActive(isActive);
                    dataInputStream.readUTF();              // "\n"
                    tasks.add(task);
                } else {                                    // task ! isRepeated
                    time = tmpTime;
                    Task task = new Task(title, time);
                    task.setActive(isActive);
                    tasks.add(task);
                }
            }
        } finally {
            dataInputStream.close();
        }
    }

    /**
     * Записує задачі із списку у файл.
     * @param tasks
     * @param file
     */
    public static void writeBinary(TaskList tasks, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            write(tasks, fileOutputStream);
        } finally {
            fileOutputStream.close();
        }
    }

    /**
     * Зчитує задачі із файлу у список задач.
     * @param tasks
     * @param file
     */
    public static void readBinary(TaskList tasks, File file) throws IOException, MyException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            read(tasks, fileInputStream);
        } finally {
            fileInputStream.close();
        }
    }

    /**
     * Converter int interval value to String.
     * @param interval
     * @return String
     */
    public static String intToStringInterval (int interval) {
        return null;
    }

    /**
     * Converter String interval to int value.
     * @param interval
     * @return String
     */
    public static int stringToIntInterval (String interval) {
        return 0;
    }

//******************************************************************************

    /**
     * Записує задачі зі списку у потік в текстовому форматі, описаному нижче.
     * @param tasks
     * @param out
     */
    public static void write(TaskList tasks, Writer out) throws IOException {
        Iterator<Task> iterator = tasks.iterator();
        BufferedWriter bufferedWriter = new BufferedWriter(out);
        try {
            while (iterator.hasNext()) {
                Task task = iterator.next();
                bufferedWriter.write("\"" + task.getTitle() + "\"");
                if (task.isRepeated()) {
                    bufferedWriter.write(" from [" + TaskIO.timeToString(task.getStartTime()) + "]");
                    bufferedWriter.write(" to [" + TaskIO.timeToString(task.getEndTime()) + "]");
                    bufferedWriter.write(" every [" + String.valueOf(task.getRepeatInterval()) + "]");
                } else {
                    bufferedWriter.write(" at [" + TaskIO.timeToString(task.getTime()) + "]");
                }
                if (task.isActive()) { bufferedWriter.write(" active"); }
                else { bufferedWriter.write(" inactive"); }
                if (iterator.hasNext()) { bufferedWriter.write(";\n"); }
                else { bufferedWriter.write("."); }
            }
        } finally {
            bufferedWriter.close();
        }
    }

    /**
     * Зчитує задачі із потоку у список.
     * @param tasks
     * @param in
     */
    public static void read(TaskList tasks, Reader in)
    throws IOException, ParseException, MyException {
        String textTaskReader;
        String title;
        Date time;
        Date startTime;
        Date endTime;
        boolean isActive;
        int interval;

        try(BufferedReader bufferedReader = new BufferedReader(in)) {
            while ((textTaskReader = bufferedReader.readLine()) != null) {
                title = textTaskReader.substring(1, textTaskReader.indexOf("\"", 1));
                if (textTaskReader.contains("inactive")) { isActive = false; }
                else { isActive = true; }
                if (textTaskReader.contains("every")) {
                    startTime = stringToTime(textTaskReader.substring((textTaskReader.indexOf("[") + 1),
                                                                       textTaskReader.indexOf("[") + 25));
                    endTime = stringToTime(textTaskReader.substring((textTaskReader.indexOf("[") + 31),
                                                                     textTaskReader.indexOf("[") + 55));
                    interval = Integer.parseInt(textTaskReader.substring((textTaskReader.lastIndexOf("every") + 7),
                                                                          textTaskReader.lastIndexOf("]")));
                    Task task = new Task(title, startTime, endTime, interval);
                    task.setActive(isActive);
                    tasks.add(task);
                } else {
                    time = stringToTime(textTaskReader.substring((textTaskReader.indexOf("[") + 1),
                                                                  textTaskReader.indexOf("[") + 25));
                    Task task = new Task(title, time);
                    task.setActive(isActive);
                    tasks.add(task);
                }
            }
        }/* finally {       // not needed to close incoming parameter in
            in.close();
        }*/
    }

    /**
     * Записує задачі у файл у текстовому форматі.
     * @param tasks
     * @param file
     */
    public static void writeText(TaskList tasks, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        try {
            write(tasks, fileWriter);
        } finally {
            fileWriter.close();
        }
    }

    /**
     * Зчитує задачі із файлу у текстовому вигляді.
     * @param tasks
     * @param file
     */
    public static void readText(TaskList tasks, File file) throws IOException, ParseException, MyException {
        FileReader fileReader = new FileReader(file);
        try {
            read(tasks, fileReader);
        } finally {
            fileReader.close();
        }
    }

    /**
     * Additional method for parsing Date time to String-format "yyyy-MM-dd  HH:mm:ss:SSS"
     * @param time
     * @return timeToString
     */
    public static String timeToString(Date time){
        String stringDateFormat = "yyyy-MM-dd  HH:mm:ss:SSS";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(stringDateFormat);
        return simpleDateFormat.format(time).toString();
    }

    /**
     * Additional method for parsing String-format "yyyy-MM-dd  HH:mm:ss:SSS" to  Date
     * @param stringTime
     * @return stringToTime
     */
    public static Date stringToTime (String stringTime) throws ParseException {
        String stringDateFormat = "yyyy-MM-dd  HH:mm:ss:SSS";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(stringDateFormat);
        return simpleDateFormat.parse(stringTime);
    }

}
