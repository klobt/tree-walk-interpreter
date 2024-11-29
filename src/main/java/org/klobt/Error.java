package org.klobt;

public class Error extends RuntimeException {
    public Error(String input, int start, int end, String message) {
        super(formatError(input, start, end, message));
    }

    private static String formatError(String input, int start, int end, String message) {
        // Ensure bounds are correct
        start = Math.max(0, Math.min(start, input.length()));
        end = Math.max(start, Math.min(end, input.length()));

        // Determine the line and column of the error
        int lineNumber = 1;  // Line numbers start at 1
        int lineStart = 0;   // Start index of the current line
        int columnStart; // Column number (0-based, adjusted later)

        for (int i = 0; i < start; i++) {
            if (input.charAt(i) == '\n') {
                lineNumber++;
                lineStart = i + 1; // Start of the next line
            }
        }

        columnStart = start - lineStart; // Calculate column from line start

        // Extract the relevant line
        int lineEnd = input.indexOf('\n', lineStart);
        if (lineEnd == -1) {
            lineEnd = input.length(); // Handle the last line
        }
        String line = input.substring(lineStart, lineEnd);

        // Build the underline
        String underline = " ".repeat(columnStart)
                + "^".repeat(Math.max(0, Math.min(end, lineStart + line.length()) - start));

        // Format the error message
        return String.format(
                "Line %d, Column %d:\n%s\n%s\nError: %s",
                lineNumber, columnStart + 1, // Convert column to 1-based
                line,                        // The relevant line
                underline,                   // The underline
                message                      // The error message
        );
    }
}
