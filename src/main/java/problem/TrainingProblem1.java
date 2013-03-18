package problem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrainingProblem1 {

    public String commonCharsNSquared(String strA, String strB) {
        List<Character> charactersList = new ArrayList<Character>(strA.length());
        Set<Character> characters = new HashSet<Character>(strA.length());
        for (char c : strA.toCharArray()) {
            if (!characters.contains(c) && strB.indexOf(c) >= 0) {
                characters.add(c);
                charactersList.add(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : charactersList) {
            sb.append(c);
        }
        return sb.toString();
    }

    public String commonCharsN(String strA, String strB) {
        List<Character> charactersList = new ArrayList<Character>(strA.length());
        Set<Character> characters = new HashSet<Character>(strA.length());
        Set<Character> charactersB = new HashSet<Character>(strB.length());
        for (char c : strB.toCharArray()) {
            charactersB.add(c);
        }
        for (char c : strA.toCharArray()) {
            if (charactersB.contains(c) && !characters.contains(c)) {
                characters.add(c);
                charactersList.add(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : charactersList) {
            sb.append(c);
        }
        return sb.toString();
    }
}
