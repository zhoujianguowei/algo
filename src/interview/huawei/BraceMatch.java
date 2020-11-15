package interview.huawei;

import java.util.Scanner;
import java.util.Stack;

public class BraceMatch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            int maxLength = 0;
            if (str == null || str.length() <= 1) {
                System.out.println(0);
            } else {
                int i, j, len = str.length();
                for (i = 0; i < len - 1; i++) {
                    for (j = i + 1; j < len; j++) {
                        if (isLegalExpression(str.substring(i, j + 1))) {
                            maxLength = Math.max(maxLength, j - i + 1);
                        }
                    }
                }
            }
            System.out.println(maxLength);
        }
    }

    /**
     * 判断是否是合法的表达式
     *
     * @param substring
     * @return
     */
    private static boolean isLegalExpression(String substring) {
        assert substring.length() > 0;
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < substring.length(); i++) {
            Character ch = substring.charAt(i);
            if (ch == '(') {
                st.push(ch);
            } else if (ch == ')') {
                if (st.isEmpty()) {
                    return false;
                } else {
                    st.pop();
                }
            } else {
                return false;
            }
        }
        return st.isEmpty() ? true : false;
    }

}
