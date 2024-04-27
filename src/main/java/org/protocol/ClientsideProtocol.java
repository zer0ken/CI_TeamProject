package org.protocol;

import java.util.StringTokenizer;

public class ClientsideProtocol extends Protocol {
    public static String build(Actions action, long id) {
        return action.value + "$" + id;
    }

    public static String build(Actions action, String shape) {
        return action.value + "$" + shape;
    }

    public static String build(Actions action, long id, String shape) {
        return build(action, id) + "$" + shape;
    }

    public static Command parse(String message) {
        try {
            StringTokenizer tokenizer = new StringTokenizer(message, DELIM);
            Command command = new Command();
            Actions action = Actions.valueOf(tokenizer.nextToken().toUpperCase());
            command.setAction(action);

            switch (action) {
                // add $ <shape: json string>
                case ADD -> command.setShape(tokenizer.nextToken());
                // edit $ <id: long> $ <shape: json string>
                case EDIT -> {
                    command.setId(Long.parseLong(tokenizer.nextToken()));
                    command.setShape(tokenizer.nextToken());
                }
                // remove $ <id: long>
                case REMOVE -> command.setId(Long.parseLong(tokenizer.nextToken()));
            }
            return command;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}