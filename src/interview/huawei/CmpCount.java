package interview.huawei;


import java.util.Scanner;

public class CmpCount {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.next();
            if (input == null || input.length() == 0) {
                System.out.println(0);
            } else {
                int maxCount = 1, tmpMaxCount = 1;
                char maxChar = input.charAt(0);
                for (int i = 1; i < input.length(); i++) {
                    if (input.charAt(i - 1) == input.charAt(i)) {
                        tmpMaxCount++;
                        if (tmpMaxCount > maxCount) {
                            maxCount = tmpMaxCount;
                            maxChar = input.charAt(i);
                        }
                    } else {
                        tmpMaxCount = 1;
                    }
                    if (tmpMaxCount == maxCount && input.charAt(i) < maxChar) {
                        maxChar = input.charAt(i);
                    }
                }
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < maxCount; i++) {
                    result.append(maxChar);
                }
                System.out.println(result);
            }
        }
    }
}
