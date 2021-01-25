package spam;

import java.util.Objects;

public final class DecisionTree<T, R extends Enum<R>> {

	public static final class Result<R> {

		private final R resultClass;

		public Result(R resultClass) {
			this.resultClass = resultClass;
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o instanceof Result) {
				Result<R> other = (Result<R>) o;
				return Objects.equals(resultClass, other.resultClass);
			} else {
				return false;
			}
		}
	}

	public static interface Predicate<T> {
		boolean isTrue(T object);
	}

	public static interface Node<T, R> {

		boolean isLeaf();
	}

	public static class Leaf<T, R> implements Node<T, R> {
		private final R resultClass;

		public Leaf(R resultClass) {
			this.resultClass = resultClass;
		}

		public boolean isLeaf() {
			return true;
		}
	}

	public static class Condition<T, R> implements Node<T, R> {

		private final Predicate<T> predicate;
		private final Node<T, R> valid;
		private final Node<T, R> invalid;

		public Condition(Predicate<T> predicate, Node<T, R> valid, Node<T, R> invalid) {
			this.predicate = predicate;
			this.valid = valid;
			this.invalid = invalid;
		}

		private Node<T, R> check(T object) {
			return predicate.isTrue(object) ? valid : invalid;
		}

		public boolean isLeaf() {
			return false;
		}
	}

	private final Node<T, R> root;

	public DecisionTree(Node<T, R> root) {
		this.root = root;
	}

	public Result<R> classify(T object) {
		Node<T, R> node = root;
		while (node instanceof Condition) {
			node = ((Condition) node).check(object);
		}
		return new Result<R>(((Leaf<T, R>) node).resultClass);
	}
}
