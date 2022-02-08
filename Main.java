import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static Creature[][] board; //[column][row]
    static ArrayList<Creature> allCreatures = new ArrayList<>(); //stores all Calliances and Zordes
    static ArrayList<Calliance> allCalliances = new ArrayList<>(); //stores all Calliances
    static ArrayList<Zorde> allZordes = new ArrayList<>(); //stores all Zordes

    static String output_string = "";    //All outputs are summed up in this variable to print to output.txt file

    public static void main(String[] args) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(args[0]));
            String txtLine;
            while ((txtLine = file.readLine()) != null) {
                switch (txtLine) {
                    case "BOARD":
                        if ((txtLine = file.readLine()) != null) { //the board is initialized here
                            board = new Creature[Integer.parseInt(txtLine.split("x")[0])][Integer.parseInt(txtLine.split("x")[1])];
                        }
                        break;
                    case "CALLIANCE": //Calliance objects are defined and stored in the 2-D board array in here ,iterately
                        while ((txtLine = file.readLine()) != null) {
                            String[] callianceLine = txtLine.split(" ");
                            Calliance temp = null;
                            switch (callianceLine[0]) {
                                case "HUMAN":
                                    temp = new Human(callianceLine[1], callianceLine[2], callianceLine[3]);
                                    break;
                                case "ELF":
                                    temp = new Elf(callianceLine[1], callianceLine[2], callianceLine[3]);
                                    break;
                                case "DWARF":
                                    temp = new Dwarf(callianceLine[1], callianceLine[2], callianceLine[3]);
                                    break;
                            }
                            if (temp != null) {
                                allCreatures.add(temp);
                                allCalliances.add(temp);
                                board[temp.column][temp.row] = temp;
                            } else {
                                break;
                            }
                        }
                    case "ZORDE": //Zorde objects are defined and stored in the 2-D board array in here ,iterately
                        while ((txtLine = file.readLine()) != null) {
                            String[] zordeLine = txtLine.split(" ");
                            Zorde temp = null;
                            switch (zordeLine[0]) {
                                case "ORK":
                                    temp = new Ork(zordeLine[1], zordeLine[2], zordeLine[3]);
                                    break;
                                case "TROLL":
                                    temp = new Troll(zordeLine[1], zordeLine[2], zordeLine[3]);
                                    break;
                                case "GOBLIN":
                                    temp = new Goblin(zordeLine[1], zordeLine[2], zordeLine[3]);
                                    break;
                            }
                            if (temp != null) {
                                allCreatures.add(temp);
                                allZordes.add(temp);
                                board[temp.column][temp.row] = temp;
                            }
                        }
                }
            }
            file.close();
            Collections.sort(allCreatures); //allCreatures arraylist is sorted to display in alphabetical order
            printBoard();
        } catch (IOException a) {
            System.out.print(args[0] + " file cannot be read.\n");
        }

        try {
            BufferedReader file = new BufferedReader(new FileReader(args[1]));
            String txtLine;
            while ((txtLine = file.readLine()) != null) {
                String[] moveLine = txtLine.split(" "); //stores id and all the steps (2 elements)
                ArrayList<String> tempArraylist = new ArrayList<>(); //temp arraylist store every moves temporarily
                for (int f = 0; f < moveLine[1].split(";").length; f += 2) {
                    tempArraylist.add(moveLine[1].split(";")[f] + ";" + moveLine[1].split(";")[f + 1]);
                }
                String[] moves = tempArraylist.toArray(new String[0]); //moves array stores elements of tempArraylist to be used in move() function, as [1;1, 0;-1, ...etc]
                for (Creature moving_character : allCreatures) {
                    if (moving_character.ID.equals(moveLine[0])) {
                        moving_character.move(moves); //every command line is executed in here by calling move() function
                        break;
                    }
                }
                if(allCalliances.size()==0 ||allZordes.size()==0){//checks whether the game is over
                    Main.output_string = Main.output_string.concat("\nGame Finished\n");
                    Main.output_string = Main.output_string.concat(allCalliances.size()==0 ? "Zorde Wins\n" : "Calliance Wins\n");
                    break;
                }
            }
            file.close();
        } catch (IOException a) {
            System.out.print(args[1] + " file cannot be read.\n");
        }
        try {//this try catch block create output text file
            PrintWriter writer = new PrintWriter(args[2], "UTF-8");
            writer.print(output_string);
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public static void printBoard() {
        for (int r = 0; r < board.length + 1; r++) {
            Main.output_string = Main.output_string.concat(r != board.length ? "**" : "**\n");
        }
        for (int i = 0; i < board.length; i++) {
            Main.output_string = Main.output_string.concat("*");
            for (Creature[] creatures : board) {
                Main.output_string = Main.output_string.concat(creatures[i] != null ? creatures[i].ID : "  ");
            }
            Main.output_string = Main.output_string.concat("*\n");
        }
        for (int r = 0; r < board.length + 1; r++) {
            Main.output_string = Main.output_string.concat(r != board.length ? "**" : "**\n\n");
        }
        for (Creature a : allCreatures) {
            Main.output_string = Main.output_string.concat(a.ID + "\t" + a.HP + "\t(" + a.defaultHP + ")\n");
        }
        Main.output_string = Main.output_string.concat("\n");
    }
}