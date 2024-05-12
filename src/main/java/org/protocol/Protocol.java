package org.protocol;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Protocol {
    public static final String DELIM = "$";

    public static String build(Action action) {
        return action.value;
    }

    public static String build(Action action, String param1) {
        return action.value + "$" + param1;
    }

    public static String build(Action action, String id, String shape) {
        return build(action, id) + "$" + shape;
    }

    public static Command parse(String message) {
        StringTokenizer tokenizer = new StringTokenizer(message, DELIM);
        Command command = new Command();
        Action action = Action.valueOf(tokenizer.nextToken().toUpperCase());
        command.setAction(action);
        try {
            switch (action) {
                // add|edit $ <id: long> $ <shape: string>
                case ADD, EDIT -> {
                    command.setId(tokenizer.nextToken());
                    command.setShape(tokenizer.nextToken());
                }
                // remove $ <id: long>
                case REMOVE -> command.setId(tokenizer.nextToken());
                // clear
                case CLEAR -> {
                }
            }
        } catch (NoSuchElementException e) {
            return null;
        }
        return command;
    }
}
