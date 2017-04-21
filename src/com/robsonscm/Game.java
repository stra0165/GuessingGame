package com.robsonscm;

import java.util.*;
import java.io.*;

public class Game {
    
    public int totalGuesses;
    
    private String input;
    private MyRandomNumber rNumber;
    
    public Game () {
        this.totalGuesses = 0;
        this.input = "";
        this.rNumber = new MyRandomNumber();
    }
    
    public short DisplayMenu() {
    
        boolean done = false;
        int selection = -1;
        Scanner inOption = new Scanner(System.in);
        
        this.clearScreen();
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║        ###  Choose from these choices   ###       ║");
        System.out.println("╠═════╦═════════════════════════════════════════════╣");
        System.out.println("║  1  ║ Easy  : Guess a number between 1 and 20     ║");
        System.out.println("╠═════╬═════════════════════════════════════════════╣");
        System.out.println("║  2  ║ Normal: Guess a number between 1 and 100    ║");
        System.out.println("╠═════╬═════════════════════════════════════════════╣");
        System.out.println("║  3  ║ Hard  : Guess a number between 1 and 1000   ║");
        System.out.println("╠═════╬═════════════════════════════════════════════╣");
        System.out.println("║  0  ║ Exit                                        ║");
        System.out.println("╚═════╩═════════════════════════════════════════════╝");
        System.out.print("> ");
    
        while (!done) {
            try {
                selection = (inOption.nextInt());

                done = (selection >= 0 && selection <= 3);
                
                if (!done) {
                    throw new Exception("Please enter 0, 1, 2 or 3: ");
                }
                
            } catch (InputMismatchException e) {
                selection = 0;
                System.err.println(inOption.next() + " was not valid input. Try again: ");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        
        return this.GameSetup( (short) selection);
    }
    
    public void clearScreen() {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                System.out.println("** cls **");
                System.out.println("\f");
                System.out.println("\f");
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
            {
                System.out.println("** clear **");
                for (int i = 0; i < 1000; i++) {
                    System.out.println();
                }
                Process proc = Runtime.getRuntime().exec("clear");
                proc.waitFor();
            }
        }
        catch (final Exception e)
        {
            for (int i = 0; i < 1000; i++) {
                System.out.println();
            }
        }
    }
    
    private short GameSetup(short option) {
        
        switch (option){
            case 0:
                return option;
            case 1:
                rNumber.SetMaximum(20);
                break;
            case 2:
                rNumber.SetMaximum(100);
                break;
            case 3:
                rNumber.SetMaximum(1000);
                break;
            default:
                this.DisplayMenu();
                return -1;
        }
        rNumber.SetMinimum(1);
        return (short) rNumber.generateRandomNumber();
    }
    
    public void PlayGame(short inNumber) {
        
        boolean right = false;
    
        Scanner inOption = new Scanner(System.in);
        
        this.clearScreen();
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║       ###  To leave the game press X   ###        ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        
        while (!right) {
            try {
                this.totalGuesses++;
    
                System.out.println("Type your guess: ");
                System.out.print("> ");
    
                short guess = 0;
                input = inOption.nextLine();
                if (input.equals("x") || input.equals("X")) {
                    break;
                } else {
                    guess = Short.parseShort(input);
                    right = (inNumber == guess);
                }
                
                if (!right) {
                    clearScreen();
                    System.out.println("╔═════════════════════════════════════════════════════════╗");
                    System.out.println(this.checkNumber(inNumber, guess));
                    System.out.println("╚═════════════════════════════════════════════════════════╝");
                }
    
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println(input + " was not valid input. Try again. ");
            }
        }
        String myNumber = Integer.toString(rNumber.currentRandomNumber);
        this.clearScreen();
        System.out.println();
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║             Thank you for playing!                ║");
        System.out.println("║            The Magic number was " + padLeftZeros(myNumber, 4) + "              ║");
    }
    
    private String checkNumber(short num, short bet) {
        
        float perc = (float) bet/num;
        return (perc > 1)
            ?
            ((perc-1 < 0.33f) ? "║ =====>>>>  So close! A little bit down!                 ║" :
             (perc-1 < 0.66f) ? "║ =====>>>>  Almost there! Guess a lower number.          ║" :
                                "║ =====>>>>  Too high!! Try miles below it!!!!            ║")
            :
             (perc   < 0.33f) ? "║ =====>>>>  So low!! Think Bigger!!!!!                   ║" :
             (perc   < 0.66f) ? "║ =====>>>>  Half-way there. A larger number would work!! ║" :
                                "║ =====>>>>  Getting Hot here! Guess a bit up!            ║";
    }
    
    public static String padLeftZeros(String str, int n) {
        return String.format("%1$" + n + "s", str);
        //.replace(' ', '0')
    }
}
