package project.euler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Problem79Graph implements Problem79 {

	private static final class NodeDependencyComparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			return Integer.compare(o1.dependsOf.size(), o2.dependsOf.size());
		}
	}

	private static final class Dependency {
		int position;
		final Node node;

		public Dependency(int position, Node node) {
			this.position = position;
			this.node = node;
		}

		@Override
		public int hashCode() {
			return Objects.hash(position, node);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			Dependency other = (Dependency) obj;
			return Objects.equals(position, other.position) && Objects.equals(node, other.node);
		}

		@Override
		public String toString() {
			return position + ":'" + node.value + "'";
		}
	}

	private static final class Node {
		final Character value;
		final List<List<Dependency>> dependsOf = new LinkedList<>();
		final List<Node> toDepends = new LinkedList<>();

		public Node(Character value) {
			this.value = value;
		}

		public void addDependencies(List<Dependency> dependencies) {
			dependsOf.add(dependencies);
			for (Dependency d : dependencies) {
				d.node.toDepends.add(this);
			}
		}

		@Override
		public int hashCode() {
			return Objects.hash(value, value);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			return Objects.equals(value, other.value);
		}

		@Override
		public String toString() {
			return "'" + value + "'" + " has dependencies on " + dependsOf;
		}
	}

	@Override
	public String shortestPasscode(String... codes) {
		List<Node> nodes = buildDependencyGraph(codes);
		StringBuilder result = new StringBuilder();
		while (!nodes.isEmpty()) {
			sortByNbOfDependencies(nodes);
			feedResultBuffer(nodes, result);
		}
		return result.toString();
	}

	private static void feedResultBuffer(List<Node> nodes, StringBuilder result) {
		Node node = nodes.get(0);
		result.append(node.value);
		Iterator<Node> it = node.toDepends.iterator();
		while (it.hasNext()) {
			Node dependentNode = it.next();
			releaseDependentNodeDependencies(node, dependentNode);
			if (!isStillInDependentNodeDependencies(node, dependentNode)) {
				it.remove();
			}
		}
		if (node.dependsOf.isEmpty()) {
			nodes.remove(0);
		}
	}

	private static void releaseDependentNodeDependencies(Node node, Node dependentNode) {
		Iterator<List<Dependency>> dependenciesIt = dependentNode.dependsOf.iterator();
		while (dependenciesIt.hasNext()) {
			List<Dependency> dependencies = dependenciesIt.next();
			if (dependencies.get(0).node.equals(node)) {
				dependencies.remove(0);
				if (dependencies.isEmpty()) {
					dependenciesIt.remove();
				} else {
					for (Dependency dependency : dependencies) {
						dependency.position--;
					}
				}
			}
		}
	}

	private static boolean isStillInDependentNodeDependencies(Node node, Node dependentNode) {
		for (List<Dependency> dependencies : dependentNode.dependsOf) {
			for (Dependency dependency : dependencies) {
				if (node.equals(dependency.node)) {
					return true;
				}
			}
		}
		return false;
	}

	private static void sortByNbOfDependencies(List<Node> nodes) {
		Collections.sort(nodes, new NodeDependencyComparator());
	}

	private static List<Node> buildDependencyGraph(String[] codes) {
		Map<Character, Node> nodes = new HashMap<>();
		for (String code : new HashSet<>(Arrays.asList(codes))) {
			char[] chars = code.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				Character c = chars[i];
				Node node = nodes.get(c);
				if (node == null) {
					node = new Node(c);
					nodes.put(c, node);
				}
				LinkedList<Dependency> deps = new LinkedList<>();
				for (int j = 0; j < i; j++) {
					deps.add(new Dependency(j, nodes.get(chars[j])));
				}
				if (!deps.isEmpty()) {
					node.addDependencies(deps);
				}
			}
		}
		return new ArrayList<>(nodes.values());
	}
}