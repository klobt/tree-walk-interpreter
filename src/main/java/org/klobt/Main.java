package org.klobt;

import org.klobt.token.Token;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String input = "123 - 45.67 - 8e2";
        Tokenizer tokenizer = new Tokenizer();

        List<Token> tokens = tokenizer.tokenize(input);

        for (Token token : tokens) {
            System.out.println(token);
        }

        System.out.println();

        Parser parser = new Parser(input, tokens);

        System.out.println(parser.expression());
    }
}