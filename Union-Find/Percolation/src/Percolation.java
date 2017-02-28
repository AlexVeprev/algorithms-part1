import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int n;
	private boolean[] field;
	
	public Percolation(int n) throws IllegalArgumentException {
		if (n <= 0) {
			throw new IllegalArgumentException("Expected n > 0");
		}

		this.n = n;
		
		this.field = new boolean[n * n];
		for (int i = 0; i < n * n; i++) {
			this.field[i] = false;
		}
	}

	public void open(int row, int col) throws IndexOutOfBoundsException {
		if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
			throw new IndexOutOfBoundsException("Expected 0 < row <= " + this.n + " and  0 < col <= " + this.n);
		}
		
		this.field[col - 1 + (row - 1) * this.n] = true;
	}
	
	public boolean isOpen(int row, int col) throws IndexOutOfBoundsException {
		if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
			throw new IndexOutOfBoundsException("Expected 0 < row <= " + this.n + " and  0 < col <= " + this.n);
		}
		return this.field[col - 1 + (row - 1) * this.n];
	}
	
	public boolean isFull(int row, int col) throws IndexOutOfBoundsException {
		if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
			throw new IndexOutOfBoundsException("Expected 0 < row <= " + this.n + " and  0 < col <= " + this.n);
		}
		return !this.field[col - 1 + (row - 1) * this.n];
	}
	
	public int numberOfOpenSites() {
		return 0;
	}
	
	public boolean percolates() {
		return false;
	}
	   
	public static void main(String[] args) {
		Percolation p = new Percolation(5);
		p.open(5, 5);
		p.open(1, 5);
		p.open(5, 1);
		p.open(1, 1);
		p.open(1, 1);

		System.out.println(p.isFull(1, 1));
		System.out.println(p.isOpen(1, 1));
		System.out.println(p.isFull(2, 2));
		System.out.println(p.isOpen(2, 2));
	}

}
