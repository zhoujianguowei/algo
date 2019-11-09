package interview.net;

import java.util.*;

/**
 * 题目描述
 * 为了找到自己满意的工作，牛牛收集了每种工作的难度和报酬。牛牛选工作的标准是在难度不超过自身能力值的情况下，牛牛选择报酬最高的工作。在牛牛选定了自己的工作后，牛牛的小伙伴们来找牛牛帮忙选工作，牛牛依然使用自己的标准来帮助小伙伴们。牛牛的小伙伴太多了，于是他只好把这个任务交给了你。
 * 输入描述:
 * 每个输入包含一个测试用例。
 * 每个测试用例的第一行包含两个正整数，分别表示工作的数量N(N<=100000)和小伙伴的数量M(M<=100000)。
 * 接下来的N行每行包含两个正整数，分别表示该项工作的难度Di(Di<=1000000000)和报酬Pi(Pi<=1000000000)。
 * 接下来的一行包含M个正整数，分别表示M个小伙伴的能力值Ai(Ai<=1000000000)。
 * 保证不存在两项工作的报酬相同。
 * 输出描述:
 * 对于每个小伙伴，在单独的一行输出一个正整数表示他能得到的最高报酬。一个工作可以被多个人选择。
 * 示例1
 * 输入
 * 复制
 * 3 3
 * 1 100
 * 10 1000
 * 1000000000 1001
 * 9 10 1000000000
 * 输出
 * 复制
 * 100
 * 1000
 * 1001
 */
public class LookForWork {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int m = scanner.nextInt();
            int n = scanner.nextInt();
            Map<Integer, Integer> work2PayMap = new HashMap<>();
            for (int i = 0; i < m; i++) {
                int work = scanner.nextInt();
                int pay = scanner.nextInt();
                Integer payVal = work2PayMap.get(work);
                if (payVal == null || pay > payVal) {
                    work2PayMap.put(work, pay);
                }
            }
            int[] ability = new int[n];
            Map<Integer, Set<Integer>> ability2OrderSetMap = new HashMap<>();
            int[] payResult = new int[n];
            Arrays.fill(payResult, 0);
            for (int i = 0; i < n; i++) {
                ability[i] = scanner.nextInt();
                Set<Integer> orderSet = ability2OrderSetMap.get(ability[i]);
                if (orderSet == null) {
                    orderSet = new HashSet<>();
                    ability2OrderSetMap.put(ability[i], orderSet);
                }
                orderSet.add(i);
            }
            int maxPay = 0;
            List<Integer> workList = new ArrayList<>(work2PayMap.keySet());
            Collections.sort(workList);
            List<Integer> abilityList = new ArrayList<>(ability2OrderSetMap.keySet());
            Collections.sort(abilityList);
            int i, j = 0;
            for (i = 0; i < workList.size(); i++) {
                int work = workList.get(i);
                while (j < abilityList.size() && abilityList.get(j) < work) {
                    Set<Integer> orderSet = ability2OrderSetMap.get(abilityList.get(j));
                    Iterator<Integer> iterator = orderSet.iterator();
                    while (iterator.hasNext()) {
                        payResult[iterator.next()] = maxPay;
                    }
                    j++;
                }
                if (j == abilityList.size()) {
                    break;
                }
                maxPay = Math.max(maxPay, work2PayMap.get(work));
            }
            while (j < abilityList.size()) {
                Set<Integer> orderSet = ability2OrderSetMap.get(abilityList.get(j));
                Iterator<Integer> iterator = orderSet.iterator();
                while (iterator.hasNext()) {
                    payResult[iterator.next()] = maxPay;
                }
                j++;
            }
            for (i = 0; i < payResult.length; i++) {
                System.out.println(payResult[i]);
            }
        }
    }
}
