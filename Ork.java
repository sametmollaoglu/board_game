public class Ork extends Zorde {

    public int orkHealPoints;

    public Ork(String ID, String column, String row) {
        super(ID, column, row);
        this.orkHealPoints = Constants.orkHealPoints;
        this.HP = Constants.orkHP;
        this.defaultHP = Constants.orkHP;
        this.AP = Constants.orkAP;
        this.maxMove = Constants.orkMaxMove;
    }

    public void attack() {
        //attacks all enemy creatures within a cell distance
        for (int rowIndex = this.row - 1; rowIndex <= this.row + 1; rowIndex++) { //a row range
            if (rowIndex >= 0 && rowIndex < Main.board.length) { //checks row boundary
                for (int columnIndex = this.column - 1; columnIndex <= this.column + 1; columnIndex++) { //a column range
                    if (columnIndex >= 0 && columnIndex < Main.board.length) { //checks column boundary
                        if (Main.board[columnIndex][rowIndex] != null) {
                            if (Main.board[columnIndex][rowIndex] instanceof Calliance) {
                                Main.board[columnIndex][rowIndex].HP -= this.AP;
                                if (Main.board[columnIndex][rowIndex].HP <= 0) { //checks if defender is dead
                                    Main.allCreatures.remove(Main.board[columnIndex][rowIndex]);
                                    Main.allCalliances.remove((Calliance) Main.board[columnIndex][rowIndex]);
                                    Main.board[columnIndex][rowIndex] = null;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void heal() {
        //heals all friend creatures within a cell distance
        for (int rowIndex = this.row - 1; rowIndex <= this.row + 1; rowIndex++) { //a row range
            if (rowIndex >= 0 && rowIndex < Main.board.length) { //checks row boundary
                for (int columnIndex = this.column - 1; columnIndex <= this.column + 1; columnIndex++) { //a column range
                    if (columnIndex >= 0 && columnIndex < Main.board.length) { //checks column boundary
                        if (Main.board[columnIndex][rowIndex] != null) {
                            if (Main.board[columnIndex][rowIndex] instanceof Zorde) {
                                Main.board[columnIndex][rowIndex].HP += this.orkHealPoints;
                                if (Main.board[columnIndex][rowIndex].HP > Main.board[columnIndex][rowIndex].defaultHP) {//checks if the healed friend creature exceeds its default HP
                                    Main.board[columnIndex][rowIndex].HP = Main.board[columnIndex][rowIndex].defaultHP;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
