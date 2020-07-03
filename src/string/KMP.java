package string;

public class KMP {

    private static int[] buildNext(String pattern) {
        int length = pattern.length();
        int[] next = new int[length];
        next[0] = -1;

        int k = -1;
        int j = 0;
        while (j < length - 1) {
            if (k == -1 || pattern.charAt(k) == pattern.charAt(j)) {
                ++k;
                ++j;
                next[j] = k;
            } else {
                k = next[k];
            }
        }

        return next;
    }

    private static int kmp(String src, String pattern) {
        int[] next = buildNext(pattern);

        int i = 0;
        int j = 0;
        int len1 = src.length();
        int len2 = pattern.length();
        while (i < len1 && j < len2) {
            if (j == -1 || src.charAt(i) == pattern.charAt(j)) {
                ++i;
                ++j;
            } else {
                j = next[j];
            }
        }
        if (j == len2) {
            return i - len2;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        String src = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam condimentum in metus at pellentesque. Fusce a libero nec sapien malesuada bibendum eu vel arcu. Ut purus velit, volutpat ut nisi sit amet, faucibus ultricies risus. Suspendisse eu est sit amet mauris pellentesque ullamcorper. Aliquam erat volutpat. Donec dignissim, felis vel malesuada varius, risus enim interdum est, a dignissim mi libero quis mauris. Praesent et porttitor arcu, vel iaculis tortor. Integer quis ex velit. Proin hendrerit sit amet ligula sit amet venenatis. Integer rhoncus, nibh sed hendrerit interdum, eros arcu aliquet nulla, in finibus quam augue eu eros. Integer iaculis, risus quis fringilla elementum, tellus quam consequat quam, at accumsan felis arcu ac sem. Nunc a mauris lorem. Integer volutpat fermentum mi, non ornare mi lobortis eget.";
        String pattern = "Integer";
        System.out.print(kmp(src, pattern));
    }

}
