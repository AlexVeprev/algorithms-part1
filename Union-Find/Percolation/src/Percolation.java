import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int n;
	private int numOfSites;
	private int virtualTopSiteId;
	private int virtualBottomSiteId;
	private int[] field;
	private WeightedQuickUnionUF uf;

	public Percolation(int n) throws IllegalArgumentException {
		if (n <= 0) {
			throw new IllegalArgumentException("Expected n > 0");
		}

		this.n = n;
		this.numOfSites = n * n;
		this.uf = new WeightedQuickUnionUF(this.numOfSites + 2);
		this.virtualTopSiteId = this.numOfSites;
		this.virtualBottomSiteId = this.numOfSites + 1;

		this.field = new int[this.numOfSites];
		for (int i = 0; i < n * n; i++) {
			this.field[i] = 0;
		}
	}

	private int getId(int row, int col) {
		return col - 1 + (row - 1) * this.n;
	}

	public void open(int row, int col) throws IndexOutOfBoundsException {
		if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
			throw new IndexOutOfBoundsException("Expected 0 < row <= " + this.n + " and  0 < col <= " + this.n);
		}

		int id = this.getId(row, col);
		this.field[id] = 1;

		// Connect this site with opened neighbors.
		if (id < this.n) {  // top row
			uf.union(id, this.virtualTopSiteId);

			if (this.isOpen(row + 1, col)) {
				uf.union(id, this.getId(row + 1, col));
			}
		}
		else if (id >= this.n * (this.n - 1)) {  // bottom row
			uf.union(id, this.virtualBottomSiteId);

			if (this.isOpen(row - 1, col)) {
				uf.union(id, this.getId(row - 1, col));
			}
		}
		else {  // middle rows
			if (this.isOpen(row - 1, col)) {
				uf.union(id, this.getId(row - 1, col));
			}

			if (this.isOpen(row + 1, col)) {
				uf.union(id, this.getId(row + 1, col));
			}
		}

		if (id == this.n * (row - 1)) { // left column
			if (this.isOpen(row, col + 1)) {
				uf.union(id, this.getId(row, col + 1));
			}
		}
		else if (id == this.n * row - 1) { // right column
			if (this.isOpen(row, col - 1)) {
				uf.union(id, this.getId(row, col - 1));
			}
		}
		else {  // middle columns
			if (this.isOpen(row, col + 1)) {
				uf.union(id, this.getId(row, col + 1));
			}

			if (this.isOpen(row, col - 1)) {
				uf.union(id, this.getId(row, col - 1));
			}
		}
	}

	public boolean isOpen(int row, int col) throws IndexOutOfBoundsException {
		if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
			throw new IndexOutOfBoundsException("Expected 0 < row <= " + this.n + " and  0 < col <= " + this.n);
		}
		return this.field[col - 1 + (row - 1) * this.n] == 1;
	}

	public boolean isFull(int row, int col) throws IndexOutOfBoundsException {
		if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
			throw new IndexOutOfBoundsException("Expected 0 < row <= " + this.n + " and  0 < col <= " + this.n);
		}
		return this.field[col - 1 + (row - 1) * this.n] == 0;
	}

	public int numberOfOpenSites() {
		int sum = 0;

		for (int i = 0; i < this.numOfSites; i++) {
			sum += this.field[i];
		}

		return sum;
	}

	public boolean percolates() {
		return this.uf.connected(this.virtualTopSiteId, this.virtualBottomSiteId);
	}
}