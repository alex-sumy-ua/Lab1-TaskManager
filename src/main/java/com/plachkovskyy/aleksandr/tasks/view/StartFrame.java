package com.plachkovskyy.aleksandr.tasks.view;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame implements Runnable, ActionListener {

    JLabel jlabMsg;
    JFrame jfrm;
    JTextArea jta;
    JButton jbtnCalendar;
    JButton jbtnAdd;
    JButton jbtnEdit;
    JButton jbtnExit;
    JButton jbtnFind;
    JButton jbtnFindNext;
    JTextField jtfFind;
    JTextField jtfFindNext;
    int findIdx;
    private String mode;

    public String getMode() {return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public StartFrame() {

        setMode("current");
        // create new container
        jfrm = new JFrame("TaskManager: " +  getMode() + " task-list");
        // set component dispatcher FlowLayout
        jfrm.getContentPane().setLayout(new FlowLayout());
        // set start size
        jfrm.setSize(600, 420);
        // shutdown program in case close
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // create label for output messages
        jlabMsg = new JLabel();
        jlabMsg.setPreferredSize(new Dimension(200, 30));
        jlabMsg.setHorizontalAlignment(SwingConstants.CENTER);
        // label for empty space
        JLabel jlabSeparator = new JLabel();
        jlabSeparator.setPreferredSize(new Dimension(200, 30));

        JLabel jlabFind = new JLabel("Search for:");
        jlabFind.setPreferredSize(new Dimension(70, 20));
        jlabFind.setHorizontalAlignment(SwingConstants.RIGHT);

        // create textArea
        jta = new JTextArea();
        JScrollPane jscrlp = new JScrollPane(jta);
        jscrlp.setPreferredSize(new Dimension(250, 200));
        // bind event-handler with component
        jta.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                int taskNumber;
                String str = jta.getText();
// Is needed to be corrected
                jlabMsg.setText("Current size: " + str.length());
                findIdx = jta.getCaretPosition();
            }
        });
        // create buttons
        jbtnCalendar = new JButton("Calendar");
        jbtnAdd = new JButton("Add");
        jbtnEdit = new JButton("Edit");
        jbtnExit = new JButton("Exit");
        // bind event-handler with component
        // set button's click handlers
        jbtnCalendar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // start CalendarFrame
            }
        });
        jbtnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // start AddFrame
            }
        });
        jbtnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // start EditFrame
            }
        });
        jbtnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // create search field
        jtfFind = new JTextField(15);
        // create find buttons
        jbtnFind = new JButton("Find from top");
        jbtnFindNext = new JButton("Find next");
        // bind buttons with event-handler
        jbtnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                findIdx = 0;
                find(findIdx);
            }
        });
        jbtnFindNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                find(findIdx+1);
            }
        });

        // include components
        Container cp = jfrm.getContentPane();
        cp.add(jscrlp);
        cp.add(jlabFind);
        cp.add(jtfFind);
        cp.add(jbtnFind);
        cp.add(jbtnFindNext);
        cp.add(jlabSeparator);
        cp.add(jbtnCalendar);
        cp.add(jbtnAdd);
        cp.add(jbtnEdit);
        cp.add(jbtnExit);
        cp.add(jlabMsg);

        // create dialog-window
        String[] options = {"current", "download", "new"};
        String response = (String) JOptionPane.showInputDialog(jfrm,
                                                      "Choose option",
                                                          "Select start mode",
                                                               JOptionPane.QUESTION_MESSAGE,
                                                          null,
                                                               options,
                                              "current");
        // if returns null - cancel or close window
        // else returns choice result
        if (response == null) System.exit(0);
        else {
            setMode(response);
            jfrm.setTitle("TaskManager: " +  getMode() + " task-list");
        }

        jfrm.setVisible(true);

    }

    void find(int start) {
        String str = jta.getText();
        String findStr = jtfFind.getText();
        int idx = str.indexOf(findStr, start);
        if (idx > -1) {
            jta.setCaretPosition(idx);
            findIdx = idx;
            jlabMsg.setText("String found.");
        }
        else
            jlabMsg.setText("String not found.");
        jta.requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Calendar")) {}
        else if (ae.getActionCommand().equals("Add")) {}
        else if (ae.getActionCommand().equals("Edit")) {}
        else System.exit(0);
    }

    @Override
    public void run() {
        // start frame
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StartFrame();
            }
        });
    }

}
