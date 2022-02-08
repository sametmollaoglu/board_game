public class Creature implements Comparable<Creature> {

    public String ID;
    public int column;
    public int row;
    public int AP;
    public int HP;
    public int maxMove;
    public int defaultHP;

    public Creature(String ID, String column, String row) {
        this.ID = ID;
        this.column = Integer.parseInt(column);
        this.row = Integer.parseInt(row);
    }

    @Override
    public int compareTo(Creature o) {
        return this.ID.compareTo(o.ID);
    }

    public void move(String[] moves) {
        if (moves.length != this.maxMove) {//checks the maxmove value for each creature and prints error message if necessary
            Main.output_string = Main.output_string.concat("Error : Move sequence contains wrong number of move steps. Input line ignored.\n\n");
        } else {
            boolean moved = false;
            boolean boundariesExceeded=false;
            for (int step = 0; step < moves.length; step++) { //move process to every x;y step
                int stepX = Integer.parseInt(moves[step].split(";")[0]);
                int stepY = Integer.parseInt(moves[step].split(";")[1]);
                if ((this.column + stepX < 0) || (this.column + stepX > Main.board.length) || (this.row + stepY < 0) || (this.row + stepY > Main.board.length)) {
                    boundariesExceeded=true; //checks whether boundaries are exceeded
                    break;
                } else if (Main.board[this.column + stepX][this.row + stepY] != null) {
                    Creature ourCreature = Main.board[this.column][this.row];
                    Creature targetCreature = Main.board[this.column + stepX][this.row + stepY]; //targetCreature stores the creature object in the next step
                    if (targetCreature instanceof Calliance && ourCreature instanceof Calliance) {
                        break;//if there is a friendly creature in the next step, we will stop where current location
                    } else if (targetCreature instanceof Zorde && ourCreature instanceof Zorde) {
                        break;//if there is a friendly creature in the next step, we will stop where current location
                    } else {//if there is a enemy creature in the next step, we gonna fight to death
                        moved = true;
                        //fight to death functions by using the benefits of polymorphism
                        if (ourCreature instanceof Calliance) {
                            ((Calliance) ourCreature).fightToDeath(ourCreature, targetCreature);
                        } else if (ourCreature instanceof Zorde) {
                            ((Zorde) ourCreature).fightToDeath(ourCreature, targetCreature);
                        }
                        break;
                    }
                } else {//if there is nothing in the next step
                    moved = true;
                    Creature ourCreature = Main.board[this.column][this.row];
                    if (ourCreature instanceof Ork) {
                        ((Ork) ourCreature).heal();//if ourCreature is the Ork, heal process is executed firstly
                    }
                    Main.board[this.column][this.row] = null;
                    this.column += stepX;
                    this.row += stepY;
                    Main.board[this.column][this.row] = ourCreature;
                    //there are some attack() functions here by using the benefits of polymorphism
                    if (ourCreature instanceof Human) {
                        ((Human) ourCreature).attack(step);
                    } else if (ourCreature instanceof Ork) {
                        ((Ork) ourCreature).attack();
                    } else if (ourCreature instanceof Goblin) {
                        ((Goblin) ourCreature).attack();
                    } else if (ourCreature instanceof Elf) {
                        ((Elf) ourCreature).attack(step);
                    } else if (ourCreature instanceof Troll) {
                        ((Troll) ourCreature).attack();
                    } else if (ourCreature instanceof Dwarf) {
                        ((Dwarf) ourCreature).attack();
                    }
                }
            }
            if (moved) {
                Main.printBoard();
            }
            if(boundariesExceeded){
                Main.output_string = Main.output_string.concat("Error : Game board boundaries are exceeded. Input line ignored.\n\n");
            }
        }

    }
}
