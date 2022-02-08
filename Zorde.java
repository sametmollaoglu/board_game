public class Zorde extends Creature {

    public Zorde(String ID, String column, String row) {
        super(ID, column, row);
    }

    public void fightToDeath(Creature ourCreature, Creature targetCreature) {
        targetCreature.HP -= this.AP;
        if (targetCreature.HP < 0) {
            targetCreature.HP = 0;
        }
        if (this.HP > targetCreature.HP) { //if the defender creature on the next step has less HP than attacker creature (ourCreature)
            this.HP -= targetCreature.HP;
            Main.board[targetCreature.column][targetCreature.row] = null;
            Main.board[this.column][this.row] = null;
            this.column = targetCreature.column;
            this.row = targetCreature.row;
            Main.board[this.column][this.row] = ourCreature;
            Main.allCreatures.remove(targetCreature);
            Main.allCalliances.remove((Calliance) targetCreature);
        } else if (this.HP < targetCreature.HP) { //if the defender creature on the next step has more HP than attacker creature (ourCreature)
            targetCreature.HP -= this.HP;
            Main.allCreatures.remove(ourCreature);
            Main.allZordes.remove((Zorde) ourCreature);
            Main.board[this.column][this.row] = null;
        } else { //if both creatures have equal HP
            Main.board[this.column][this.row] = null;
            Main.board[targetCreature.column][targetCreature.row] = null;
            Main.allCreatures.remove(ourCreature);
            Main.allCreatures.remove(targetCreature);
            Main.allCalliances.remove((Calliance) targetCreature);
            Main.allZordes.remove((Zorde) ourCreature);
        }
    }
}
