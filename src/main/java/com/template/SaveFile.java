package com.template;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class SaveFile {
    private String fileName = "";
    private PrintWriter pw;
    private boolean isAppend = false;

    public SaveFile(String fileName) {
        this.fileName = fileName;
        init();
    }

    public SaveFile(String fileName, boolean isAppend) {
        this.fileName = fileName;
        this.isAppend = isAppend;
        init();
    }

    private void init() {
        try {
            if (!this.isAppend) {
                FileOutputStream file = new FileOutputStream(this.fileName);
                file.close();
            }
            this.pw = new PrintWriter(new FileWriter(this.fileName, true), true);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public synchronized void write(String msg) {
        this.pw.println(msg);
    }

    public synchronized void close() {
        if (this.pw != null) {
            this.pw.flush();
            if (this.pw.checkError()) {
                System.out.println("file save error!");
            }
            this.pw.close();
        }
    }
}