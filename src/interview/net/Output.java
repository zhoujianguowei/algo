package interview.net;

import java.util.Scanner;

public class Output {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int numA = scanner.nextInt();
            int numB = scanner.nextInt();
            assert numA <= numB;
            for (int i = numA; i <= numB; i++) {
                if (i % 15 == 0) {
                    System.out.println("foobar");
                } else if (i % 3 == 0) {
                    System.out.println("foo");
                } else if (i % 5 == 0) {
                    System.out.println("bar");
                } else {
                    System.out.println(i);
                }
            }

        }
    }
}
