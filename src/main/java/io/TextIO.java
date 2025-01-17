package io;

import java.util.Scanner;

public class TextIO implements IO{
    private Scanner scanner;

    public TextIO(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    // TODO pitäisikö olla "print" toisella parametrilla?
    private void ask(String msg) {
        System.out.print(msg);
    }

    @Override
    public String nextLine(String msg) {
        if (msg.length() > 0) {
            this.print(msg);
        }

        return scanner.nextLine();
    }

    @Override
    public int nextInt(String msg) {
        if (msg.length() > 0) {
            this.print(msg);
        }
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch(NumberFormatException e) {
            return -2;
        }
    }
    
    @Override
    public void error(String msg) {
        this.print("VIRHE: " + msg);
    }
}
