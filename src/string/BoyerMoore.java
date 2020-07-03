package string;

public class BoyerMoore {

    public static void main(String[] args) {
        String src = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam condimentum in metus at pellentesque. Fusce a libero nec sapien malesuada bibendum eu vel arcu. Ut purus velit, volutpat ut nisi sit amet, faucibus ultricies risus. Suspendisse eu est sit amet mauris pellentesque ullamcorper. Aliquam erat volutpat. Donec dignissim, felis vel malesuada varius, risus enim interdum est, a dignissim mi libero quis mauris. Praesent et porttitor arcu, vel iaculis tortor. Integer quis ex velit. Proin hendrerit sit amet ligula sit amet venenatis. Integer rhoncus, nibh sed hendrerit interdum, eros arcu aliquet nulla, in finibus quam augue eu eros. Integer iaculis, risus quis fringilla elementum, tellus quam consequat quam, at accumsan felis arcu ac sem. Nunc a mauris lorem. Integer volutpat fermentum mi, non ornare mi lobortis eget.";
        String pattern = "Integer";
        int len1 = src.length();
        int len2 = pattern.length();

        int j = len2 - 1;
        int i = j;
        while (j > 0) {
            if (src.charAt(i) == pattern.charAt(j)) {
                i--;
                j--;
            } else {

            }
        }
    }

}
