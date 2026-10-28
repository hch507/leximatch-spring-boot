package org.leximatch.game.domain.policy.hint;

public final class InitialHintUtils {

    private InitialHintUtils() {
    }

    private static final char[] INITIAL_CONSONANTS = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ',
            'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
            'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ',
            'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    public static String extractInitials(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        StringBuilder result = new StringBuilder();

        for (char ch : word.toCharArray()) {
            if (ch >= 0xAC00 && ch <= 0xD7A3) {
                int index = (ch - 0xAC00) / (21 * 28);
                result.append(INITIAL_CONSONANTS[index]);
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }
}