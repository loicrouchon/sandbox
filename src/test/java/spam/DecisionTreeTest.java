package spam;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import spam.DecisionTree.Condition;
import spam.DecisionTree.Leaf;
import spam.DecisionTree.Node;
import spam.DecisionTree.Predicate;
import spam.DecisionTree.Result;

public class DecisionTreeTest {

	private static enum Classes {
		LEGITIMATE, SPAM;
	}

	private static final class EMail {
		public boolean fromBlacklistedSender = false;
		public boolean senderIsAKnownContact = false;
		public boolean containsSpamFrequentWords = false;

		public EMail(boolean fromBlacklistedSender, boolean senderIsAKnownContact, boolean containsSpamFrequentWords) {
			this.fromBlacklistedSender = fromBlacklistedSender;
			this.senderIsAKnownContact = senderIsAKnownContact;
			this.containsSpamFrequentWords = containsSpamFrequentWords;
		}
	}

	private final Leaf<EMail, Classes> legitimateLeaf = new Leaf<>(Classes.LEGITIMATE);
	private final Leaf<EMail, Classes> spamLeaf = new Leaf<>(Classes.SPAM);
	private Result<Classes> legitimate = new Result<>(Classes.LEGITIMATE);
	private Result<Classes> spam = new Result<>(Classes.SPAM);

	private Predicate<EMail> fromBlackListedSender = new Predicate<EMail>() {

		@Override
		public boolean isTrue(EMail email) {
			return email.fromBlacklistedSender;
		}
	};
	private Predicate<EMail> senderIsAKnownContact = new Predicate<EMail>() {

		@Override
		public boolean isTrue(EMail email) {
			return email.senderIsAKnownContact;
		}
	};
	private Predicate<EMail> containsSpamFrequentWords = new Predicate<EMail>() {

		@Override
		public boolean isTrue(EMail email) {
			return email.containsSpamFrequentWords;
		}
	};

	@Test
	public void emailClassificationWithoutConditions() {
		DecisionTree<EMail, Classes> tree = new DecisionTree<>(legitimateLeaf);

		assertThat(tree.classify(new EMail(false, false, false)), is(legitimate));
		assertThat(tree.classify(new EMail(false, false, true)), is(legitimate));
		assertThat(tree.classify(new EMail(false, true, false)), is(legitimate));
		assertThat(tree.classify(new EMail(false, true, true)), is(legitimate));
		assertThat(tree.classify(new EMail(true, false, false)), is(legitimate));
		assertThat(tree.classify(new EMail(true, false, true)), is(legitimate));
		assertThat(tree.classify(new EMail(true, true, false)), is(legitimate));
		assertThat(tree.classify(new EMail(true, true, true)), is(legitimate));
	}

	@Test
	public void emailClassificationWithOneCondition() {
		Node<EMail, Classes> root = new Condition<>(fromBlackListedSender, spamLeaf, legitimateLeaf);
		DecisionTree<EMail, Classes> tree = new DecisionTree<>(root);

		assertThat(tree.classify(new EMail(false, false, false)), is(legitimate));
		assertThat(tree.classify(new EMail(false, false, true)), is(legitimate));
		assertThat(tree.classify(new EMail(false, true, false)), is(legitimate));
		assertThat(tree.classify(new EMail(false, true, true)), is(legitimate));
		assertThat(tree.classify(new EMail(true, false, false)), is(spam));
		assertThat(tree.classify(new EMail(true, false, true)), is(spam));
		assertThat(tree.classify(new EMail(true, true, false)), is(spam));
		assertThat(tree.classify(new EMail(true, true, true)), is(spam));
	}

	@Test
	public void emailClassificationWithMultipleConditions() {
		Node<EMail, Classes> root = new Condition<>(fromBlackListedSender, spamLeaf, new Condition<>(
		        senderIsAKnownContact, legitimateLeaf, new Condition<>(containsSpamFrequentWords, spamLeaf,
		                legitimateLeaf)));
		DecisionTree<EMail, Classes> tree = new DecisionTree<>(root);

		assertThat(tree.classify(new EMail(false, false, false)), is(legitimate));
		assertThat(tree.classify(new EMail(false, false, true)), is(spam));
		assertThat(tree.classify(new EMail(false, true, false)), is(legitimate));
		assertThat(tree.classify(new EMail(false, true, true)), is(legitimate));
		assertThat(tree.classify(new EMail(true, false, false)), is(spam));
		assertThat(tree.classify(new EMail(true, false, true)), is(spam));
		assertThat(tree.classify(new EMail(true, true, false)), is(spam));
		assertThat(tree.classify(new EMail(true, true, true)), is(spam));
	}
}
