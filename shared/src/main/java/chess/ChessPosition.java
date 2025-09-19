package chess;

import java.util.*;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private final int row;
    private final int column;

    public ChessPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return column;
    }


    @Override
    public String toString() {
        return String.format("Row: %d, Column: %d", row, column);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        ChessPosition other = (ChessPosition) obj;
        return row == other.getRow() && column == other.getColumn();
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + row;
        hash = 31 * hash + column;
        return hash;
    }
}
