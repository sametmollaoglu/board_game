public class Dwarf extends Calliance {

    public Dwarf(String ID, String column, String row) {
        super(ID, column, row);
        this.HP = Constants.dwarfHP;
        this.defaultHP = Constants.dwarfHP;
        this.AP = Constants.dwarfAP;
        this.maxMove = Constants.dwarfMaxMove;
    }

    public void attack() {
        //attacks all enemy creatures within a cell distance in every step
        for (int rowIndex = this.row - 1; rowIndex <= this.row + 1; rowIndex++) { //a row range
            if (rowIndex >= 0 && rowIndex < Main.board.length) { //checks row boundary
                for (int columnIndex = this.column - 1; columnIndex <= this.column + 1; columnIndex++) { //a column range
                    if (columnIndex >= 0 && columnIndex < Main.board.length) { //checks column boundary
                        if (Main.board[columnIndex][rowIndex] != null) {
                            if (Main.board[columnIndex][rowIndex] instanceof Zorde) {
                                Main.board[columnIndex][rowIndex].HP -= this.AP;
                                if (Main.board[columnIndex][rowIndex].HP <= 0) { //checks if defender is dead
                                    Main.allCreatures.remove(Main.board[columnIndex][rowIndex]);
                                    Main.allZordes.remove((Zorde) Main.board[columnIndex][rowIndex]);
                                    Main.board[columnIndex][rowIndex] = null;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
