package problem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryMaskResolver {

    public List<String> resolve(String mask) {
        List<StringBuilder> builders = new ArrayList<StringBuilder>();
        builders.add(new StringBuilder());
        for (char character : mask.toCharArray()) {
            if (character == '?') {
                List<StringBuilder> clones = clone(builders);
                appendCharacter(builders, '0');
                appendCharacter(clones, '1');
                builders.addAll(clones);
            } else {
                appendCharacter(builders, character);
            }
        }
        List<String> results = new ArrayList<String>(builders.size());
        for (StringBuilder builder : builders) {
            results.add(builder.toString());
        }
        return results;
    }

    private List<StringBuilder> clone(List<StringBuilder> original) {
        // return original.stream().map((StringBuilder builder) -> new StringBuilder(builder)).collect(Collectors.toList());
        List<StringBuilder> clones = new ArrayList<StringBuilder>(original.size());
        for (StringBuilder builder : original) {
            clones.add(new StringBuilder(builder));
        }
        return clones;
    }

    private void appendCharacter(List<StringBuilder> builders, Character character) {
        for (StringBuilder builder : builders) {
            builder.append(character);
        }
    }
}