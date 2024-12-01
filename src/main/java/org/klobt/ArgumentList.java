package org.klobt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ArgumentList<T> {
    private final List<T> positionalArguments;
    private final HashMap<String, T> keywordArguments;


    public ArgumentList(List<T> positionalArguments, HashMap<String, T> keywordArguments) {
        this.positionalArguments = positionalArguments;
        this.keywordArguments = keywordArguments;
    }

    public List<T> getPositionalArguments() {
        return positionalArguments;
    }

    public HashMap<String, T> getKeywordArguments() {
        return keywordArguments;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("ArgumentList(");

        boolean isFirst = true;

        for (T argument : positionalArguments) {
            if (!isFirst) {
                stringBuilder.append(", ");
            }

            stringBuilder.append(argument);

            isFirst = false;
        }

        for (String key : keywordArguments.keySet()) {
            if (!isFirst) {
                stringBuilder.append(", ");
            }

            stringBuilder.append(key).append("=").append(keywordArguments.get(key));

            isFirst = false;
        }

        stringBuilder.append(")");

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArgumentList<?> that = (ArgumentList<?>) o;

        return Objects.equals(positionalArguments, that.positionalArguments) && Objects.equals(keywordArguments, that.keywordArguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionalArguments, keywordArguments);
    }

    public <S> ArgumentList<S> map(Function<T, S> function) {
        List<S> newPositionalArguments = new ArrayList<>();
        HashMap<String, S> newKeywordArguments = new HashMap<>();

        for (T argument : positionalArguments) {
            newPositionalArguments.add(function.apply(argument));
        }

        for (String key : keywordArguments.keySet()) {
            newKeywordArguments.put(key, function.apply(keywordArguments.get(key)));
        }

        return new ArgumentList<>(newPositionalArguments, newKeywordArguments);
    }
}
