package experimentations;

import java.util.InputMismatchException;

/**
 * @author philippklein
 * @version 1.0
 * @since 09.14.2021
 **/

public class alphametic {

    // All the characters in v1, v2 and solution
    private String characters = "";
    // The numbers assigned to the characters
    private String numbers = "";

    public alphametic(String variable1, String variable2, String solution) {
        addVariableToChars(variable1);
        addVariableToChars(variable2);
        addVariableToChars(solution);
        distributeNumbers();
        calculate(variable1,variable2,solution);
    }

    private void calculate(String v1, String v2, String solution) {
        while( getVariableNumbers(v1) + getVariableNumbers(v2) != getVariableNumbers(solution) ) {
            increaseNumbers(v1,v2,solution);
        }
        System.out.println("Solution for the riddle (" + v1 + " + " + v2 + " = " + solution + "):");
        System.out.println( "V1 : " + getVariableNumbers( v1 ) );
        System.out.println( "V2 : " + getVariableNumbers( v2 ) );
        System.out.println( "Solution : " + getVariableNumbers( solution ) );
        System.out.println("");
    }

    // As long there are duplicate digits or the first letters are 0, the number will be increased by 1

    private void increaseNumbers(String v1, String v2, String solution) {
        int i = Integer.parseInt(numbers);
        ++i;
        numbers = "" + i;
        while ( isFirstLetterZero(v1,v2,solution) | existDuplicateDigits() ) {
            ++i;
            numbers = "" + i;
        }
    }

    // Before the calculation can begin, the numbers have to be distributed to the characters.
    // The max. amount of characters this riddle can have is 10 (0 -> 9). If there are more, the riddle is not solvable.

    private void distributeNumbers() {
        if ( characters.length() > 10 ) throw new InputMismatchException("The riddle is not solvable! Too many characters.");
        for ( byte i = 0 ; i < characters.length() ; ++i ) {
            if ( i == 9 ) {
                numbers += "" + 0;
            } else {
                numbers += "" + ( i + 1 );
            }
        }
    }

    private void addVariableToChars(String s) {
        for ( byte i = 0 ; i < s.length() ; ++i ) {
            if ( !charAlreadyExists( s.charAt( i ) ) ) {
                characters += "" + s.charAt(i);
            }
        }
    }

    // Because the digits and characters have the same positioning in the String, so that every character has
    // a specific number tied to it, the distribution is very easy. It is similar to a two-dimensional array.

    private int getVariableNumbers(String s) {
        String result = "";
        for ( byte i = 0 ; i < s.length() ; ++i ) {
            result += "" + numbers.charAt( characters.indexOf( s.charAt(i) ) );
        }
        return Integer.parseInt(result);
    }

    private boolean charAlreadyExists(char c) {
        return characters.indexOf(c) != -1;
    }

    // If duplicate digits exist, then the indexOf call will return the second position of that char, from
    // the index of the already found character, if it's -1 then there are no duplicate digits.

    private boolean existDuplicateDigits() {
        for ( byte i = 0 ; i < 10 ; ++i ) {
            if (  numbers.indexOf( "" + i,  numbers.indexOf( "" + i ) + 1 ) != -1 ) {
                return true;
            }
        }
        return false;
    }

    // VERY IMPORTANT: the rules of the riddle state, that the first letter of the variables != 0
    // The for-loop goes through each character and checks if it is the first Letter, if so, check if
    // the number assigned to it is zero. If that is the case the numbers have to be increased.

    private boolean isFirstLetterZero(String v1, String v2, String solution) {
        for ( byte i = 0 ; i < characters.length() ; ++i ) {
            if ( isCharFirstLetter( characters.charAt( i ), v1, v2, solution) & numbers.charAt( i ) == '0' ) {
                return true;
            }
        }
        return false;
    }

    // Checks if the char is the first letter in the first-, second variable and the solution

    private boolean isCharFirstLetter(char c, String v1, String v2, String solution) {
        return v1.charAt(0) == c || v2.charAt(0) == c || solution.charAt(0) == c;
    }

}
