package project.euler;

public class Problem9 {

	public static class Triplet {

		public final int a;
		public final int b;
		public final int c;

		public Triplet(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + a;
			result = prime * result + b;
			result = prime * result + c;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Triplet other = (Triplet) obj;
			if (a != other.a)
				return false;
			if (b != other.b)
				return false;
			if (c != other.c)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Triplet [a=" + a + ", b=" + b + ", c=" + c + "]";
		}

	}

	public static Triplet firstPythagoreanTriplet(int n) {
		for (int a = 1; a <= n / 3; a++) {
			for (int b = a + 1; b <= 2 * n / 3; b++) {
				int aSquare = (int) Math.pow(a, 2);
				int bSquare = (int) Math.pow(b, 2);
				int cSquare = aSquare + bSquare;
				int c = (int) Math.sqrt(cSquare);
				if (cSquare == Math.pow(c, 2) && a + b + c == n) {
					return new Triplet(a, b, c);
				}
			}
		}
		return null;
	}
}