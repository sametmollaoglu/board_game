public class Elf extends Calliance {

    public int elfRangedAP;

    public Elf(String ID, String column, String row) {
        super(ID, column, row);
        this.HP = Constants.elfHP;
        this.defaultHP = Constants.elfHP;
        this.AP = Constants.elfAP;
        this.maxMove = Constants.elfMaxMove;
        this.elfRangedAP = Constants.elfRangedAP;
    }

    public void attack(int step) {
        //attacks all enemy creatures within a cell distance in every step except the last step, makes ranged attack in the last step
        if (step == this.maxMove - 1) {
            rangedAttack();
        } else {
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

    public void rangedAttack() { //rangedAttack function is same as attack() but ranges are 2 cells instead of 1
        for (int rowIndex = this.row - 2; rowIndex <= this.row + 2; rowIndex++) {
            if (rowIndex >= 0 && rowIndex < Main.board.length) {
                for (int columnIndex = this.column - 2; columnIndex <= this.column + 2; columnIndex++) {
                    if (columnIndex >= 0 && columnIndex < Main.board.length) {
                        if (Main.board[columnIndex][rowIndex] != null) {
                            if (Main.board[columnIndex][rowIndex] instanceof Zorde) {
                                Main.board[columnIndex][rowIndex].HP -= this.elfRangedAP;
                                if (Main.board[columnIndex][rowIndex].HP <= 0) {
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
