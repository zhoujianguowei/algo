package interview.net;

import java.util.Scanner;
import java.util.Stack;

public class RadixTransform {
    public static long valueOf(String s, int radix) {
        int i = 0;
        long result = 0;
        boolean isNegative = false;
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            i++;
            if (s.charAt(0) == '-') {
                isNegative = true;
            }
        }
        for (; i < s.length(); i++) {
            int digit = Character.digit(s.charAt(i), radix);
            result *= radix;
            result -= digit;
        }
        return isNegative ? result : -result;
    }

    static String parseVal(long num, int radix) {
        Stack<Long> st = new Stack<>();
        long tmp = num;
        boolean isNegative = false;
        if (num < 0) {
            isNegative = true;
        }
        while (tmp != 0) {
            long val = tmp % radix;
            if (val < 0) {
                st.push(-val);
            } else {
                st.push(val);
            }
            tmp /= radix;
        }
        StringBuilder result = new StringBuilder();
        if (isNegative) {
            result.append("-");
        }
        while (!st.isEmpty()) {
            result.append(st.pop());
        }
        return result.length() == 0 ? "0" : result.toString();

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int fromBase = scanner.nextInt();
            String s = scanner.next();
            int toBase = scanner.nextInt();
            System.out.println(parseVal(valueOf(s, fromBase), toBase));
        }
    }
}
