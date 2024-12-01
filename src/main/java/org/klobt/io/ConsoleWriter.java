package org.klobt.io;

public class ConsoleWriter implements Writer {
    @Override
    public void write(String contents) {
        System.out.print(contents);
    }
}
