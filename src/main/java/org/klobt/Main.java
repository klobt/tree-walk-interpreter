package org.klobt;

import org.klobt.token.Token;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();

        List<Token> tokens = tokenizer.tokenize("123 45.67 8e2");

        for (Token token : tokens) {
            System.out.print(token);
        }
    }
}