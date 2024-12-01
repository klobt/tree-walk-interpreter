package org.klobt;

import org.junit.jupiter.api.Test;
import org.klobt.io.StringWriter;
import org.klobt.value.NumberValue;
import org.klobt.value.Value;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramTest {
    private void assertProgram(String input, Value expectedValue, String expectedOutput) {
        StringWriter writer = new StringWriter();
        Context context = new Context(input, writer);
        Tokenizer tokenizer = new Tokenizer();
        Parser parser = new Parser(input, tokenizer.tokenize(input));
        Value resultingValue = parser.program().evaluate(context);

        assertEquals(expectedValue, resultingValue);
        assertEquals(expectedOutput, writer.getString());
    }

    @Test
    public void testAssignment() {
        assertProgram(
                new StringBuilder()
                        .append("x = 1;")
                        .append("print(x);")
                        .toString(),
                new NumberValue(0),
                "1.0\r\n"
        );
    }

    @Test
    public void testAnotherVariable() {
        assertProgram(
                new StringBuilder()
                        .append("x = 1;")
                        .append("y = x * 4;")
                        .append("print(y);")
                        .toString(),
                new NumberValue(0),
                "4.0\r\n"
        );
    }

    @Test
    public void testArithmetic() {
        assertProgram(
                new StringBuilder()
                        .append("x = 1 + 2 * 3 - 2 / 1;")
                        .append("print(x);")
                        .toString(),
                new NumberValue(0),
                "5.0\r\n"
        );
    }

    @Test
    public void testIfElse() {
        assertProgram(
                new StringBuilder()
                        .append("x = 5;")
                        .append("if (x > 10) {")
                        .append("  print('yay');")
                        .append("} else {")
                        .append("  print('nay');")
                        .append("}")
                        .toString(),
                new NumberValue(0),
                "nay\r\n"
        );
    }

    @Test
    public void testFibonacci() {
        assertProgram(
                new StringBuilder()
                        .append("a = 1;")
                        .append("b = 1;")
                        .append("while (a < 20) {")
                        .append("  print(a);")
                        .append("  tmp = a;")
                        .append("  a = a + b;")
                        .append("  b = tmp;")
                        .append("}")
                        .toString(),
                new NumberValue(0),
                "1.0\r\n2.0\r\n3.0\r\n5.0\r\n8.0\r\n13.0\r\n"
        );
    }

    @Test
    public void testBreak() {
        assertProgram(
                new StringBuilder()
                        .append("i = 1;")
                        .append("while (i <= 10) {")
                        .append("  if (i == 5) break;")
                        .append("  print(i);")
                        .append("  i = i + 1;")
                        .append("}")
                        .toString(),
                new NumberValue(0),
                "1.0\r\n2.0\r\n3.0\r\n4.0\r\n"
        );
    }

    @Test
    public void testContinue() {
        assertProgram(
                new StringBuilder()
                        .append("i = 1;")
                        .append("while (i < 10) {")
                        .append("  i = i + 1;")
                        .append("  if (i % 2 == 0) continue;")
                        .append("  print(i);")
                        .append("}")
                        .toString(),
                new NumberValue(0),
                "3.0\r\n5.0\r\n7.0\r\n9.0\r\n"
        );
    }
}
