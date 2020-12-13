package org.legitzxdevelopment.util;

import java.util.Scanner;

/**
 * Contributor(s): Luciano K
 * Description: Used for miscellaneous methods
 */
public class Util {
    /**
     * Method that allows for easy data capture
     * @param prompt        Prompt for input
     * @param type          Type of input (Restricted to: String, Int, and Boolean)
     * @param <T>           Generic Type
     * @return              User Input
     */
    public < T > T getUserInput(String prompt, Class<T> type) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);

        if(type == String.class) {
            String input = scanner.nextLine();
            return type.cast(input);
        }

        if(type == Integer.class) {
            int input = scanner.nextInt();
            return type.cast(input);
        }

        if(type == Boolean.class) {
            boolean input = scanner.nextBoolean();
            return type.cast(input);
        }

        return null;
    }

    /**
     * Clears the command prompt
     */
    public void clearWindow() {
        // Not implemented yet
    }
}
