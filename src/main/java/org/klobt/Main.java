package org.klobt;

import org.klobt.ast.PureNode;
import org.klobt.io.ConsoleWriter;
import org.klobt.token.Token;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String input = "";

        switch (args.length) {
            case 0:
                input = getInputFromStdin();
                break;
            case 1:
                try {
                    input = Files.readString(FileSystems.getDefault().getPath(args[0]));
                } catch (IOException e) {
                    System.err.println("error: Failed to read the input file.");
                    System.err.println("error: " + e.getMessage());
                }
                break;
            default:
                System.err.println("usage: tree-walk-interpreter [INPUT_FILE]");
                System.exit(1);
        }

        Tokenizer tokenizer = new Tokenizer();

        List<Token> tokens = tokenizer.tokenize(input);

        Parser parser = new Parser(input, tokens);

        PureNode programNode = parser.program();

        try {
            programNode.evaluate(new Context(input, new ConsoleWriter()));
        } catch (Error error) {
            System.err.println(error.getMessage());
        }
    }

    private static String getInputFromStdin() {
        // Courtesy of https://stackoverflow.com/a/61369438
        StringBuilder sb = new StringBuilder();
        final Scanner scanner = new Scanner(System.in);
        for (String line = scanner.nextLine(); !line.isEmpty(); line = scanner.nextLine()) {
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString();
    }
}