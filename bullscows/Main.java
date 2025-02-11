package bullscows;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static String secret;
    private static int length;
    private static final Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {

        System.out.println("Input the length of the secret code:");
        length = getLength();
        System.out.println("Input the number of possible symbols in the code:");
        int noOfLetters = getNoOfLetters();
        secret = generateSecret(length, noOfLetters);
        System.out.println(secret);
        System.out.println("Okay, let's start a game!");

        check(length);

    }

    private static void check(int length) {
        String input;
        int turn = 1;
        ArrayList<Character> cow = new ArrayList<>();
        ArrayList<Character> bull = new ArrayList<>();
        while (true) {
            bull.clear();
            cow.clear();
            System.out.printf("Turn %d %n", turn++);
            input = sc.next();
            if (input.length() != length) continue;
            for (int i = 0; i < secret.length(); i++) {
                if (secret.charAt(i) == input.charAt(i)) {
                    bull.add(input.charAt(i));
                } else if (secret.contains(String.valueOf(input.charAt(i)))) {
                    if (!cow.contains(input.charAt(i)))
                        cow.add(input.charAt(i));
                }
            }

            if (bull.size()>0 && cow.size()>0) {
                System.out.printf("Grade: %d bull(s) and %d cow(s).\n", bull.size(), cow.size());
                continue;
            }
            if (bull.isEmpty() && cow.size()>0) {
                System.out.printf("Grade: %d cow(s).\n", cow.size());
                continue;
            }
            if (bull.size()>0 && cow.isEmpty()) {
                System.out.printf("Grade: %d bull(s).\n", bull.size());
            }
            if (bull.size() == secret.length()) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }
            else {
                System.out.println("Grade: none. ");

            }
        }
    }

    private static int getLength() {
        int length=0;
        String s = "";
        while (true) {
            try{
                s = sc.nextLine();
                length = Integer.parseInt(s);
            } catch (IllegalArgumentException e){
                System.out.printf("Error: \"%s\" isn't a valid number.%n",s);
                continue;
            }
            if(length<1) {
               System.out.println("Error! Length can't be 0");
               System.exit(0);
            }
            return length;
        }

    }

    private static int getNoOfLetters() {
        int lengthSymbols = 0;
        String s="";
        while (true) {
            try{
                s = sc.nextLine();
                lengthSymbols = Integer.parseInt(s);
            } catch (IllegalArgumentException e){
                System.out.printf("Error: \"%s\" isn't a valid number.%n",s);
                continue;
            }
            if(lengthSymbols<length){
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.%n",length,lengthSymbols);
                continue;
            }
            if (lengthSymbols > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            } else {
                break;
            }
        }
        return lengthSymbols;
    }


    private static String generateSecret(int length, int noOfLetters) {

        //List<Character> digits = new ArrayList<>();
        StringBuilder digits = new StringBuilder();

        // add each unique character.
        while (digits.length() < length) {
            char nextDigit = getRandomChar(noOfLetters);
            if (!digits.toString().contains(String.valueOf(nextDigit)) && digits.length() < 10) {
                digits.append(nextDigit);
            }
        }

        System.out.print("The secret is prepared: ");
        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }
        System.out.printf(" (0-9, a-%c).%n", (char) ('a' + noOfLetters - 11));
        return digits.toString();
    }

    public static char getRandomChar(int noOfLetters) {
        Random random = new Random();
        // Total possible characters: 9 digits + letterLimit letters
        int totalCharacters = noOfLetters;

        // Generate a random number between 0 and (totalCharacters - 1)
        int randomValue = random.nextInt(totalCharacters);

        if (randomValue < 9) {
            // If the random value is between 0 and 8, return a character from '1' to '9'
            return (char) ('1' + randomValue);
        } else {
            // If the random value is between 9 and (totalCharacters - 1), return a character from 'a' to 'a + letterLimit - 1'
            return (char) ('a' + (randomValue - 9));
        }
    }

}
