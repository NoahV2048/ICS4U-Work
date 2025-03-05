/* Noah Verdon
 * Spot It Game
 * Mar. 8, 2025
 */

import java.util.*;

public class SpotIt {
    // Public static vars
    public static int prime = 3; // prime number
    public static int score = 0; // reset score
    public static int[][] deck; // deck
    public static String ESC = "\033[0m"; // reset formatting
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Main method

        boolean quit = false;

        while (! quit) {
            // Options

            System.out.println(bold(93) + "Let's Play Spot It!" + ESC);
            System.out.printf("""
                    1. Play the Game
                    2. View Rules
                    3. View Score
                    4. Switch Items Per Card
                    5. Quit
                    \n%sOption: %s""", bold(34), ESC);
            int userInput = sc.nextInt();
            sc.nextLine();
            switch (userInput) {
                case 1:
                    playGame();
                    break;
                case 2:
                    printRules();
                    break;
                case 3:
                    System.out.println("\nYour current score: " + score + "\n");
                    try {Thread.sleep(750);} catch (InterruptedException e) {}
                    break;
                case 4:
                    do {
                        System.out.println("\nEnter a valid number one more than a prime: ");
                        prime = sc.nextInt() - 1;
                    } while (! checkPrime(prime)); // validation using prime sieve
                    break;
                case 5:
                    System.out.println("\nCome back soon!");
                    quit = true;
                    break;
                default:
                    System.out.println("Error!");
                    break;
            }
        }
        sc.close();
    }

    public static boolean checkPrime(int prime) {
        return true;
    }

    public static String col(int colorValue) {
        // Return color switch
        return "\033[" + colorValue + "m";
    }

    public static String bold(int colorValue) {
        // Return bold color switch
        return "\033[1;" + colorValue + "m";
    }

    public static void playGame() {
        // Generate a deck as a 2D array
        generateDeck(prime);

        // Choose two random indices TEMP
        int[] choices = pickCards();
        int choice1 = choices[0], choice2 = choices[1];

        // Shuffle LATER
        // deck = shuffle(deck); LATER

        // Pick cards
        // int[] card1 = deck[0], card2 = deck[1]; LATER
        int[] card1 = deck[choice1], card2 = deck[choice2];

        // Print cards
        for (int item : card1) // should rearrange items on a card before printing
            System.out.println(items[item]);
        System.out.println();
        for (int item : card2) // should rearrange items on a card before printing
            System.out.println(items[item]);

        // Compare Cards
        int commonIndex = compareCards(card1, card2);
        String commonItem = items[commonIndex];
        // System.out.println("\nCommon: " + items[commonItem]); TODO Get rid


        String userSelection;
        boolean validAbbrev = false;

        do {
            userSelection = sc.nextLine();

            if (userSelection.length() < 2) continue;

            userSelection = userSelection.substring(0, 2).toUpperCase();

            for (String item : items) {
                if (item.substring(item.length()-3, item.length()-1).equals(userSelection)) {
                    validAbbrev = true;
                    break;
                }
            }

        } while (! validAbbrev);

        if (commonItem.substring(commonItem.length()-3, commonItem.length()-1).equals(userSelection)) {
            System.out.println(col(32) + "Correct!" + ESC);
            score++;
        } else System.out.println(col(31) + "Incorrect." + ESC);

    }

    public static void printRules() {
        // Output the rules of Spot It

        System.out.println("\n-----\n"
                + "HOW TO PLAY SPOT IT\n"
                + "1. Every card has the same number of items\n"
                + "2. Every pair of cards share exactly one common item\n"
                + "3. You will be shown two cards\n"
                + "3. Select the common item between the cards to score a point\n"
                + "3. \n"
                + "-----\n");
    }

    public static void generateDeck(int primePlus) {
        // Generate a deck for a given prime+1 number

        int cap = primePlus * primePlus + primePlus + 1;

        deck = new int[cap][primePlus+1];

        int counter;
        for (int x = 0; x < cap; x++) {
            counter = 0;

            for (int y = 0; y < cap; y++) {
                if (checkMatrix(x, y, primePlus)) {
                    deck[x][counter] = y;
                    counter++;
                } // System.out.print(checkMatrix(x, y, prime) ? (y % 10) : " "); DEBUG
            } // System.out.println(); DEBUG
        }


        // for (int[] row : deck) {
        //     for (int item : row) {
        //         System.out.print(item + " ");
        //     }
        //     System.out.println();
        // }
        // System.out.println("\n\n");
    }

    public static int[] pickCards() {
        // Pick two random indices

        int choice1, choice2;

        choice1 = (int) (Math.random() * items.length); // choice1
        do {
            choice2 = (int) (Math.random() * items.length); // choice2
        } while (choice1 == choice2);

        int[] choices = {choice1, choice2};
        return choices;
    }

    public static void shuffle(int[][] deck) {
        // Shuffle a deck or rearrange items on a card

        // Deck

        for (int i = 0; i <= deck.length; i++) {

        }
    }

    public static int compareCards(int[] card1, int[] card2) {
        // Check for commonality between cards

        for (int index1 : card1) {
            for (int index2 : card2) {
                if (index1 == index2)
                    return index1;
            }
        }

        return -1; // error
    }

    public static boolean checkMatrix(int x, int y, int prime) {
        if (x == 0 && y <= prime || x <= prime && y == 0) // upper-left case
            return true;

        x--;
        y--;

        if (x + 1 == y / prime || x / prime == y + 1) // staircase case
            return true;

        x -= prime;
        y -= prime;

        if (x < 0 || y < 0)
            return false;

        if ((x + y - (x / prime) * (y / prime)) % prime == 0) // prime^2 grid case
            return true;

        return false;
    }

    // STRING ARRAY ITEMS

    // Array of 13 provinces
    public static String[] items = {
            "Newfoundland and Labrador (NL)",
            "Prince Edward Island (PE)",
            "Nova Scotia (NS)",
            "New Brunswick (NB)",
            "Quebec (QC)",
            "Ontario (ON)",
            "Manitoba (MB)",
            "Saskatchewan (SK)",
            "Alberta (AB)",
            "British Columbia (BC)",
            "Yukon (YT)",
            "Northwest Territories (NT)",
            "Nunavut (NU)"
    };

    // Array of 50 states
    public static String[] states = {
            "Alabama (AL)",
            "Alaska (AK)",
            "Arizona (AZ)",
            "Arkansas (AR)",
            "California (CA)",
            "Colorado (CO)",
            "Connecticut (CT)",
            "Delaware (DE)",
            "Florida (FL)",
            "Georgia (GA)",
            "Hawaii (HI)",
            "Idaho (ID)",
            "Illinois (IL)",
            "Indiana (IN)",
            "Iowa (IA)",
            "Kansas (KS)",
            "Kentucky (KY)",
            "Louisiana (LA)",
            "Maine (ME)",
            "Maryland (MD)",
            "Massachussets (MA)",
            "Michigan (MI)",
            "Minnesota (MN)",
            "Mississippi (MS)",
            "Missouri (MO)",
            "Montana (MT)",
            "Nebraska (NE)",
            "Nevada (NV)",
            "New Hampshire (NH)",
            "New Jersey (NJ)",
            "New Mexico (NM)",
            "New York (NY)",
            "North Carolina (NC)",
            "North Dakota (ND)",
            "Ohio (OH)",
            "Oklahoma (OK)",
            "Oregon (OR)",
            "Pennsylvania (PA)",
            "Rhode Island (RI)",
            "South Carolina (SC)",
            "South Dakota (SD)",
            "Tennessee (TN)",
            "Texas (TX)",
            "Utah (UT)",
            "Vermont (VT)",
            "Virginia (VA)",
            "Washington (WA)",
            "West Virginia (WV)",
            "Wisconsin (WI)",
            "Wyoming (WY)"
    };
}