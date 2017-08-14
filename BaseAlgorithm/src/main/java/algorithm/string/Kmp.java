package algorithm.string;

public class Kmp {

    private int[] computePrefixFunc(String pattern) {
        char p[] = pattern.toCharArray();
        int next[] = new int[pattern.length() + 1];
        next[0] = -1;

        int i = 0;
        int k = -1;

        while (i < p.length) {
            if (k == -1 || p[i] == p[k]) {
                next[++i] = ++k;
            } else {
                k = next[k];
            }
        }

        for (int x = 1; x < next.length; x++)
            next[x] = 1;

        return next;
    }

    private int kmp(String target, String pattern) {
        int matchIndex = -1;

        char t[] = target.toCharArray();
        char p[] = pattern.toCharArray();

        int next[] = computePrefixFunc(pattern);

        for (int entry : next) {
            System.out.print(entry + "    ");
        }
        System.out.println();

        int i = 0;
        while (i < t.length) {
            boolean matched = false;
            int j = 0;
            while (j < p.length - 1) {
                if ((i + j < t.length) && t[i + j] == p[j]) {
                    if (j + 1 == p.length)
                        matched = true;
                    j++;
                } else {
                    i++;
                    i += next[j];
                    break;
                }
            }
            if (matched)
                matchIndex = i;
        }

        return matchIndex;
    }

    private int nativeStrMatching(String target, String pattern) {
        int matchIndex = -1;
        for (int i = 0; i < target.length(); i++) {
            boolean matched = false;
            for (int j = 0; j < pattern.length(); j++) {
                if ((i + j < target.length()) && target.charAt(i + j) == pattern.charAt(j)) {
                    if (j + 1 == pattern.length())
                        matched = true;
                } else {
                    break;
                }
            }
            if (matched)
                matchIndex = i;
        }
        return matchIndex;
    }


    public static void main(String... args) {
        String target = "abcdeabcdeabcdfee";
        String pattern = "cdf";
        Kmp kmp = new Kmp();

        int matchIndex = kmp.kmp(target, pattern);
        System.out.println(matchIndex);
        if (matchIndex != -1)
            System.out.println(String.format("pattern = [%s] pattern in target = [%s]", pattern, target.substring(matchIndex, matchIndex + pattern.length())));
    }
}
