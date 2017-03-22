import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static int BLOCKED_SITE = 0;
    private static int OPEN_SITE = 1;
    private static int FULL_SITE = 2;
    private static boolean allowRecursion = false;

    private int n;
    private int numOfSites;
    private int virtualTopSiteId;
    private int virtualBottomSiteId;
    private int[] field;
    private WeightedQuickUnionUF uf;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Expected n > 0");
        }

        this.n = n;
        numOfSites = n * n;
        uf = new WeightedQuickUnionUF(numOfSites + 2);
        virtualTopSiteId = numOfSites;
        virtualBottomSiteId = numOfSites + 1;

        field = new int[numOfSites];
        for (int i = 0; i < numOfSites; i++) {
            field[i] = BLOCKED_SITE;
        }
    }

    private int getId(int row, int col) {
        return col - 1 + (row - 1) * n;
    }

    private boolean isSiteValid(int row, int col) {
        return row > 0 && row <= n && col > 0 && col <= n;
    }

    /**
     * Make site full if any nearby is full.
     * If the site is full then make full nearby sites recursively.
     *
     * @param row - site row;
     * @param col - site column;
     */
    private void makeFull(int row, int col) {
        int[] rowShifts = {0, 0, 1, -1};
        int[] colShifts = {1, -1, 0, 0};
        int neibRow;
        int neibCol;

        if (field[getId(row, col)] != FULL_SITE) {
            for (int i = 0; i < rowShifts.length; i++) {
                neibRow = row + rowShifts[i];
                neibCol = col + colShifts[i];

                if (isSiteValid(neibRow, neibCol) && isFull(neibRow, neibCol)) {
                    field[getId(row, col)] = FULL_SITE;
                    break;
                }
            }

            if (field[getId(row, col)] != FULL_SITE) {
                return;
            }
        }

        for (int i = 0; i < rowShifts.length; i++) {
            neibRow = row + rowShifts[i];
            neibCol = col + colShifts[i];

            if (isSiteValid(neibRow, neibCol) && isOpen(neibRow, neibCol) && !isFull(neibRow, neibCol)) {
                field[getId(neibRow, neibCol)] = FULL_SITE;
                makeFull(neibRow, neibCol);
            }
        }
    }

    public void open(int row, int col) {
        if (!isSiteValid(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        if (isOpen(row, col)) {
            return;
        }

        int id = getId(row, col);
        field[id] = OPEN_SITE;

        // Connect this site with opened neighbors.

        if (numOfSites == 1) {
            uf.union(id, virtualTopSiteId);
            uf.union(id, virtualBottomSiteId);
            field[id] = FULL_SITE;
            return;
        }

        if (row == 1) {  // top row
            uf.union(id, virtualTopSiteId);
            field[id] = FULL_SITE;

            if (isOpen(row + 1, col)) {
                uf.union(id, getId(row + 1, col));
            }
        }
        else if (row == n) {  // bottom row
            uf.union(id, virtualBottomSiteId);

            if (isOpen(row - 1, col)) {
                uf.union(id, getId(row - 1, col));
            }
        }
        else {  // middle rows
            if (isOpen(row - 1, col)) {
                uf.union(id, getId(row - 1, col));
            }

            if (isOpen(row + 1, col)) {
                uf.union(id, getId(row + 1, col));
            }
        }

        if (col == 1) { // left column
            if (isOpen(row, col + 1)) {
                uf.union(id, getId(row, col + 1));
            }
        }
        else if (col == n) { // right column
            if (isOpen(row, col - 1)) {
                uf.union(id, getId(row, col - 1));
            }
        }
        else {  // middle columns
            if (isOpen(row, col + 1)) {
                uf.union(id, getId(row, col + 1));
            }

            if (isOpen(row, col - 1)) {
                uf.union(id, getId(row, col - 1));
            }
        }

        if (allowRecursion) {
            makeFull(row, col);
        }
    }

    public boolean isOpen(int row, int col) {
        if (!isSiteValid(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        return field[getId(row, col)] == OPEN_SITE || field[getId(row, col)] == FULL_SITE;
    }

    public boolean isFull(int row, int col) {
        if (!isSiteValid(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        if (allowRecursion) {
            return field[getId(row, col)] == FULL_SITE;
        }
        else {
            return uf.connected(getId(row, col), virtualTopSiteId);
        }
    }

    public int numberOfOpenSites() {
        int sum = 0;

        for (int i = 0; i < numOfSites; i++) {
            if (field[i] == FULL_SITE || field[i] == OPEN_SITE) {
                sum++;
            }
        }

        return sum;
    }

    public boolean percolates() {
        return uf.connected(virtualTopSiteId, virtualBottomSiteId);
    }
}
