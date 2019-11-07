package sort;

import tree.Heap;
import tree.structure.ComparableNode;

import java.util.Arrays;
import java.util.Random;

/**
 * 排序算法具体实现：一共包括以下排序算法，
 * 1.直接插入排序
 * 2.选择排序
 * 3.冒泡排序
 * 4.快速排序
 * 5.归并排序
 * 6.堆排序
 * 7.希尔排序
 * 8.基数排序
 */
public class SortUtils {
    /**
     * 直接插入排序，基本思想：
     * 将序列分成两个部分，一部分是有序序列，另一个是无须序列，排序的过程就是将无序序列中的节点
     * 逐步添加到有序序列中。
     *
     * @param nodes
     */
    public static void insertSort(ComparableNode[] nodes) {
        int i, j;
        for (i = 1; i < nodes.length; i++) {
            ComparableNode tmpNode = nodes[i];
            if (tmpNode.compareTo(nodes[i - 1]) < 0) {
                j = i - 1;
                while (j >= 0 && tmpNode.compareTo(nodes[j]) < 0) {
                    nodes[j + 1] = nodes[j];
                    j--;
                }
                nodes[j + 1] = tmpNode;
            }
        }
    }

    /**
     * 选择排序,基本思想：
     * 遍历数组，第一次遍历将最小的数放到数组的首位，然后第二次遍历将次小的数放到第二位，如此往复。
     *
     * @param nodes
     */
    public static void selectSort(ComparableNode[] nodes) {
        int length = nodes.length;
        int i, j;
        for (i = 0; i < length - 1; i++) {
            int minIndex = i;
            for (j = i; j < length; j++) {
                if (nodes[minIndex].compareTo(nodes[j]) > 0) {
                    minIndex = j;
                }
            }
            ComparableNode tmpNode = nodes[i];
            nodes[i] = nodes[minIndex];
            nodes[minIndex] = tmpNode;
        }
    }

    /**
     * 冒泡排序，基本思想：
     * 每次冒泡的过程，就是数据交换的过程，如果数组不是有序的，就进行交换。为了提高效率，记录上一次交换的
     * 索引序号
     *
     * @param nodes
     */
    public static void bubbleSort(ComparableNode[] nodes) {
        int i, j;
        int lastExchangedIndex = nodes.length - 1;
        for (i = 0; i < nodes.length; i++) {
            int changeIndex = -1;
            for (j = 0; j < lastExchangedIndex; j++) {
                if (nodes[j].compareTo(nodes[j + 1]) > 0) {
                    swapNodes(nodes, j, j + 1);
                    changeIndex = j;
                }
            }
            if (changeIndex == -1) {
                break;
            } else {
                lastExchangedIndex = changeIndex;
            }
        }
    }

    /**
     * 快速排序算法实现，选择的节点是随机获取，俗称种子，基本思想：
     * 将数组分成两部分，左边的数据不大于种子，右边的数据不小于种子。结果
     * 返回的是种子在实际序列中的位置
     *
     * @param nodes
     * @param i
     * @param j
     * @return
     */
    public static void quickSort(ComparableNode[] nodes, int i, int j) {
        if (i >= j) {
            return;
        }
        //标记不大于种子的数组索引
        int left = i - 1;
        Random random = new Random();
        /**
         * 种子是随机选择的,由于在迭代的过程中，种子所在索引可能会被覆盖。所以需要在迭代过策划那个中
         * 动态确定种子所在索引的位置。
         */
        int seedIndex = i + random.nextInt(j - i + 1);
        ComparableNode seed = nodes[seedIndex];
        for (int k = i; k <= j; k++) {
            if (nodes[k].compareTo(seed) <= 0) {
                left++;
                swapNodes(nodes, k, left);
                //确定种子的
                if (nodes[left].compareTo(seed) == 0) {
                    seedIndex = left;
                }
            }
        }
        swapNodes(nodes, seedIndex, left);
        quickSort(nodes, i, left - 1);
        quickSort(nodes, left + 1, j);
    }

    /**
     * 二路归并排序
     *
     * @param nodes
     * @param i
     * @param j
     */
    public static void combineSort(ComparableNode[] nodes, int i, int j) {
        if (i >= j) {
            return;
        }
        int length = j - i + 1;
        //作为临时存储
        ComparableNode[] tmpNodes = new ComparableNode[length];
        int mid = (i + j) / 2;
        combineSort(nodes, i, mid);
        combineSort(nodes, mid + 1, j);
        int m = i, n = mid + 1;
        //两个排序数组合并,合并后的内容放到临时存储tmpNodes里面
        for (int k = 0; k < length; k++) {
            /**
             * 注意判断的条件
             */
            if (n > j || (m <= mid && nodes[m].compareTo(nodes[n]) < 0)) {
                tmpNodes[k] = nodes[m];
                m++;
            } else {
                tmpNodes[k] = nodes[n];
                n++;
            }
        }
        //将临时存储获得的数据复制到原数组中
        System.arraycopy(tmpNodes, 0, nodes, i, length);
    }

    /**
     * 堆排序算法
     *
     * @param nodes
     */
    public static void heapSort(ComparableNode[] nodes) {
        Heap heap = new Heap();
        ComparableNode[] tmpNodes = Arrays.copyOf(nodes, nodes.length);
        //首先构建堆(小顶)
        heap.constructSmallHeap(nodes);
        /**
         * 逐渐将小顶堆中的元素移除，然后调整堆
         */
        for (int i = 0; i < tmpNodes.length; i++) {
            tmpNodes[i] = nodes[0];
            //用堆中的最后一个元素替换堆的根节点
            swapNodes(nodes, 0, tmpNodes.length - i - 1);
            //调整堆
            heap.putDownTree(nodes, 0, tmpNodes.length - i - 2);
        }
        System.arraycopy(tmpNodes, 0, nodes, 0, tmpNodes.length);
    }

    /***
     * 交换数组中两个节点的位置
     * @param nodes
     */
    private static void swapNodes(ComparableNode[] nodes, int i, int j) {
        ComparableNode tmpNode = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = tmpNode;
    }


    public static ComparableNode[] generateRandomNode(int n, int bound) {
        ComparableNode[] result = new ComparableNode[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            result[i] = new ComparableNode();
            result[i].val = random.nextInt(bound);
        }
        return result;
    }

    public static void printArray(ComparableNode[] nodes, int i, int j) {
        int n = nodes.length;
        assert i <= j && j < n && i >= 0;
        for (int k = i; k <= j; k++) {
            nodes[k].visit();
        }
        System.out.println("");
    }

    public static void printArray(ComparableNode[] nodes) {
        printArray(nodes, 0, nodes.length - 1);
    }

    public static void main(String[] args) {
        int n = 10, bound = 20;
        ComparableNode[] nodes = generateRandomNode(n, bound);
        ComparableNode[] copyNodes = Arrays.copyOf(nodes, n);
        System.out.println("before insert sort");
        printArray(copyNodes);
        insertSort(copyNodes);
        System.out.println("after insert sort");
        printArray(copyNodes);
        copyNodes = Arrays.copyOf(nodes, n);
        System.out.println("before select sort");
        printArray(copyNodes);
        selectSort(copyNodes);
        System.out.println("after select sort");
        printArray(copyNodes);
        copyNodes = Arrays.copyOf(nodes, n);
        System.out.println("before bubble sort");
        printArray(copyNodes);
        bubbleSort(copyNodes);
        System.out.println("after bubble sort");
        printArray(copyNodes);
        copyNodes = Arrays.copyOf(nodes, n);
        System.out.println("before quick sort");
        printArray(copyNodes);
        quickSort(copyNodes, 0, n - 1);
        System.out.println("after quick sort");
        printArray(copyNodes);
        copyNodes = Arrays.copyOf(nodes, n);
        System.out.println("before combine sort");
        printArray(copyNodes);
        combineSort(copyNodes, 0, n - 1);
        System.out.println("after combine sort");
        printArray(copyNodes);
        copyNodes = Arrays.copyOf(nodes, n);
        System.out.println("before heap sort");
        printArray(copyNodes);
        heapSort(nodes);
        System.out.println("after heap sort");
        printArray(nodes);
    }
}

