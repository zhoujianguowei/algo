package sort;

import com.bjzhou.assist.entity.Page;
import org.assertj.core.api.ComparableAssert;
import tree.Heap;
import tree.structure.ComparableNode;
import tree.structure.LinkNode;
import tree.structure.QueNode;

import java.util.*;

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
 * 9.计数排序
 * 10.桶排序
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

    /**
     * 希尔排序。希尔排序是直接插入排序的改进版本，
     * 本质上是一个多阶段的直接插入排序，通过设置一些列的排序步长，每次迭代过程就是使得相同步长的分组数据
     * 变成有序，逐步改善序列的有序性。
     * note：最后一个步长必须是1
     *
     * @param nodes
     * @param steps
     */
    public static void shellSort(ComparableNode[] nodes, int[] steps) {
        int n = nodes.length, stepLength = steps.length;
        assert stepLength > 0 && steps[stepLength - 1] == 1 : "最后一个步长必须是1";
        int i, j;
        for (i = 0; i < stepLength; i++) {
            int di = steps[i];
            for (j = 0; j < di; j++) {
                int k = di + j;
                while (k < n) {
                    if (k < n && nodes[k].compareTo(nodes[k - di]) < 0) {
                        int m = k - di;
                        ComparableNode tmpNode = nodes[k];
                        while (m >= j && tmpNode.compareTo(nodes[m]) < 0) {
                            nodes[m + di] = nodes[m];
                            m -= di;
                        }
                        nodes[m + di] = tmpNode;
                    }
                    k += di;
                }
            }
        }


    }

    /***
     * 基数排序的代码实现，基数排序基本思想：
     * 基本排序是一种非比较的排序算法，针对于多个关键词进行排序。每一轮迭代是针对于特定位置的关键字进行排序，
     * 一轮迭代过程主要包括两个步骤：分配和收集。
     * 分配：按照该轮迭代的关键词进行排序后，将其中元素放到指定的队列中，队列的个数就是基数r(整型的十进制就是10)。
     * 收集：分配完成之后，将分配后的所有队列连接起来，形成下一轮的输入序列。
     * 如此往复，直到最后一轮，迭代结束。算法复杂度是O(r(n+d)),其中r是迭代的次数，对于整型来说就是最大值的位数。
     * @param nodes
     */
    public static void radixSort(ComparableNode<Integer>[] nodes) {
        int i;
        //每次迭代的输入队列，区分正负数
        QueNode<ComparableNode<Integer>> nonNegWq = new QueNode<>();
        int nonNegCnt = 0;
        int negCnt = 0;
        QueNode<ComparableNode<Integer>> negWq = new QueNode<>();
        for (i = 0; i < nodes.length; i++) {
            LinkNode<ComparableNode<Integer>> linkNode = new LinkNode<>(nodes[i]);
            if (nodes[i].val >= 0) {
                nonNegWq.enq(linkNode);
                nonNegCnt++;
            } else {
                negWq.enq(linkNode);
                negCnt++;
            }
        }
        QueNode<ComparableNode<Integer>> resultQ = doRadixSort(nonNegWq);
        //将数据元素复制到数组中去,对于非负数是从小到大排列
        i = nodes.length - nonNegCnt;
        while (!resultQ.isEmpty()) {
            nodes[i++] = resultQ.deq().getVal();
        }
        //对于负数，则是从大到小排列
        resultQ = doRadixSort(negWq);
        i = negCnt - 1;
        while (!resultQ.isEmpty()) {
            nodes[i--] = resultQ.deq().getVal();
        }
    }

    /**
     * 基数排序的分配和收集过程代码实现
     *
     * @param wq
     * @return
     */
    public static QueNode<ComparableNode<Integer>> doRadixSort(QueNode<ComparableNode<Integer>> wq) {
        //10个队列，表明是10进制形式的排序
        List<QueNode<ComparableNode<Integer>>> queNodeList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            queNodeList.add(new QueNode<ComparableNode<Integer>>());
        }
        int ite = 0;
        while (true) {
            int maxIndex = 0;
            /**
             * 分配
             */
            while (!wq.isEmpty()) {
                int remain = (int) Math.pow(10, ite);
                LinkNode<ComparableNode<Integer>> linkNode = wq.deq();
                ComparableNode<Integer> node = linkNode.getVal();
                int index = Math.abs((node.val / remain) % 10);
                queNodeList.get(index).enq(linkNode);
                maxIndex = Math.max(maxIndex, index);
            }
            /**
             * 收集
             */
            for (int i = 0; i < queNodeList.size(); i++) {
                QueNode<ComparableNode<Integer>> queNode = queNodeList.get(i);
                if (queNode.isEmpty()) {
                    continue;
                }
                if (wq.isEmpty()) {
                    wq.front = queNode.front;
                    wq.tail = queNode.tail;
                } else {
                    wq.tail.next = queNode.front;
                    wq.tail = queNode.tail;
                }
                /**
                 * 需要将队列进行重置，不能清空（否则会破坏列表的链接关系）
                 */
                queNodeList.set(i, new QueNode<ComparableNode<Integer>>());
            }
            if (maxIndex == 0) {
                break;
            }
            ite++;
        }
        return wq;
    }

    /**
     * 计数排序算法，基本思想：
     * 计数排序不是一个基于比较的排序算法，是将元素的值与其所在数组索引位置进行关联的
     * 一种算法，该算法的时间复杂度是O(n)，适用于数据范伟波动不大的数据。
     * note:注意桶的范围的计算方式，以及桶的大小设置。（桶的数量越多，覆盖的范围越小，桶内排序代价越小）
     * @param nodes
     */
    public static void countSort(ComparableNode<Integer>[] nodes) {
        ComparableNode<Integer> lowerBound = nodes[0];
        ComparableNode<Integer> upperBound = nodes[0];
        for (ComparableNode<Integer> node : nodes) {
            if (node.compareTo(lowerBound) < 0) {
                lowerBound = node;
            }
            if (node.compareTo(upperBound) > 0) {
                upperBound = node;
            }
        }
        //计算所需的映射数组空间
        ArrayList<ArrayList<ComparableNode<Integer>>> list = new ArrayList<>(upperBound.val - lowerBound.val + 1);
        int lowerVal = lowerBound.val;
        int upperVal = upperBound.val;
        //初始化双重链表
        for (int i = 0; i < upperVal - lowerVal + 1; i++) {
            list.add(new ArrayList<ComparableNode<Integer>>());
        }
        for (ComparableNode<Integer> node : nodes) {
            List<ComparableNode<Integer>> partition = list.get(node.val - lowerVal);
            partition.add(node);
        }
        //将排好序的内容复制到数组中
        int i = 0;
        for (List<ComparableNode<Integer>> partition : list) {
            if (!partition.isEmpty()) {
                for (ComparableNode<Integer> node : partition) {
                    nodes[i++] = node;
                }
            }
        }
    }

    /**
     * 桶排序，基本思想：
     * 根据排序数据的范围，生成n+1个平均间隔的桶。然后根据排序元素的内容计算得到
     * 元素在桶中的具体位置，最后对所有的桶进行排序。
     * @param nodes
     */
    public static void bucketSort(ComparableNode<Integer>[] nodes) {
        int lowerVal = nodes[0].val, upperVal = lowerVal;
        for (ComparableNode<Integer> node : nodes) {
            if (node.val < lowerVal) {
                lowerVal = node.val;
            }
            if (node.val > upperVal) {
                upperVal = node.val;
            }
        }
        int bucketSize = (int) Math.sqrt(nodes.length);
        List<ArrayList<ComparableNode<Integer>>> bucketList = new ArrayList<>(bucketSize);
        for (int i = 0; i < bucketSize; i++) {
            bucketList.add(new ArrayList<ComparableNode<Integer>>());
        }
        //每个桶覆盖的数据范围(左闭右开)
        float bucketWidth = (upperVal - lowerVal) * 1.0f / (bucketSize-1);
        //将元素放到指定的桶里
        for (ComparableNode<Integer> node : nodes) {
            int index = (int) ((node.val - lowerVal) / bucketWidth);
            bucketList.get(index).add(node);
        }
        int j = 0;
        //对每个桶的元素进行排序
        for (int i = 0; i < bucketSize; i++) {
            List<ComparableNode<Integer>> bucket = bucketList.get(i);
            if (!bucket.isEmpty()) {
                ComparableNode<Integer>[] ba = new ComparableNode[bucket.size()];
                bucket.toArray(ba);
                combineSort(ba, 0, ba.length - 1);
                System.arraycopy(ba, 0, nodes, j, bucket.size());
//                bucket=Arrays.asList(ba);
//                bucketList.set(i, (ArrayList<ComparableNode<Integer>>) bucket);
            }
            j += bucket.size();
        }

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


    public static ComparableNode[] generateRandomNode(int n, int lowerBound, int upperBound) {
        ComparableNode[] result = new ComparableNode[n];
        assert upperBound >= lowerBound;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            result[i] = new ComparableNode();
            result[i].val = lowerBound + random.nextInt(upperBound - lowerBound + 1);
        }
        return result;
    }

    public static void printArray(ComparableNode[] nodes, int i, int j) {

//        int n = nodes.length;
//        assert i <= j && j < n && i >= 0;
//        for (int k = i; k <= j; k++) {
//            nodes[k].visit();
//        }
//        System.out.println("");
    }

    public static void printArray(ComparableNode[] nodes) {
        printArray(nodes, 0, nodes.length - 1);
    }

    /**
     * 测试10中排序算法
     */
    public static void test() {
        int n = 1000000, lowerBound = -9999, upperBound = 20000;
        int randomIndex = new Random().nextInt(n);
        ComparableNode refNode = null;
        ComparableNode[] nodes = generateRandomNode(n, lowerBound, upperBound);
        ComparableNode[] copyNodes = Arrays.copyOf(nodes, n);
        long bench = System.currentTimeMillis();
        //堆排序
        System.out.println("before heap sort");
        printArray(copyNodes);
        bench = System.currentTimeMillis();
        heapSort(copyNodes);
        System.out.println("after heap sort");
        System.out.println("cost time:" + (System.currentTimeMillis() - bench) + "ms");
        refNode = copyNodes[randomIndex];
        printArray(copyNodes);
        System.out.println("");

//        //直接插入排序
//        copyNodes = Arrays.copyOf(nodes, n);
//        System.out.println("before insert sort");
//        printArray(copyNodes);
//        bench = System.currentTimeMillis();
//        insertSort(copyNodes);
//        System.out.println("after insert sort");
//        assert (refNode.compareTo(copyNodes[randomIndex]) == 0);
//        System.out.println("cost time:" + (System.currentTimeMillis() - bench) + "ms");
//        printArray(copyNodes);
//        System.out.println("");
//
//        //选择排序
//        copyNodes = Arrays.copyOf(nodes, n);
//        System.out.println("before select sort");
//        printArray(copyNodes);
//        bench = System.currentTimeMillis();
//        selectSort(copyNodes);
//        assert (refNode.compareTo(copyNodes[randomIndex]) == 0);
//        System.out.println("after select sort");
//        System.out.println("cost time:" + (System.currentTimeMillis() - bench) + "ms");
//        printArray(copyNodes);
//        System.out.println("");
//
//        //冒泡排序
//        copyNodes = Arrays.copyOf(nodes, n);
//        System.out.println("before bubble sort");
//        printArray(copyNodes);
//        bench = System.currentTimeMillis();
//        bubbleSort(copyNodes);
//        assert (refNode.compareTo(copyNodes[randomIndex]) == 0);
//        System.out.println("after bubble sort");
//        System.out.println("cost time:" + (System.currentTimeMillis() - bench) + "ms");
//        printArray(copyNodes);
//        System.out.println("");
//
//        //快速排序
//        copyNodes = Arrays.copyOf(nodes, n);
//        System.out.println("before quick sort");
//        printArray(copyNodes);
//        bench = System.currentTimeMillis();
//        quickSort(copyNodes, 0, n - 1);
//        assert (refNode.compareTo(copyNodes[randomIndex]) == 0);
//        System.out.println("after quick sort");
//        System.out.println("cost time:" + (System.currentTimeMillis() - bench) + "ms");
//        printArray(copyNodes);
//        System.out.println("");
//
//
//        //希尔排序
//        System.out.println("before shell sort");
//        copyNodes = Arrays.copyOf(nodes, n);
//        printArray(copyNodes);
//        int[] steps = new int[]{5, 3, 1};
//        bench = System.currentTimeMillis();
//        shellSort(copyNodes, steps);
//        assert (refNode.compareTo(copyNodes[randomIndex]) == 0);
//        System.out.println("after shell sort");
//        System.out.println("cost time:" + (System.currentTimeMillis() - bench) + "ms");
//        printArray(copyNodes);
//        System.out.println("");
//
//        //基数排序算法实现
//        System.out.println("radix sort before");
//        copyNodes = Arrays.copyOf(nodes, n);
//        printArray(copyNodes);
//        bench = System.currentTimeMillis();
//        radixSort(copyNodes);
//        assert refNode.compareTo(copyNodes[randomIndex]) == 0;
//        System.out.println("system sort after");
//        System.out.println("cost time:" + (System.currentTimeMillis() - bench) + "ms");
//        printArray(copyNodes);
//        System.out.println("");

        //二路归并排序
        copyNodes = Arrays.copyOf(nodes, n);
        System.out.println("before combine sort");
        printArray(copyNodes);
        bench = System.currentTimeMillis();
        combineSort(copyNodes, 0, n - 1);
        System.out.println("after combine sort");
        assert (refNode.compareTo(copyNodes[randomIndex]) == 0);
        System.out.println("cost time:" + (System.currentTimeMillis() - bench) + "ms");
        printArray(copyNodes);
        System.out.println("");

        //计数排序算法实现
        System.out.println("count sort before");
        copyNodes = Arrays.copyOf(nodes, n);
        printArray(copyNodes);
        bench = System.currentTimeMillis();
        countSort(copyNodes);
        assert refNode.compareTo(copyNodes[randomIndex]) == 0;
        System.out.println("count sort after");
        System.out.println("cost time:" + (System.currentTimeMillis() - bench) + "ms");
        printArray(copyNodes);
        System.out.println("");

        //桶排序算法实现
        System.out.println("bucket sort before");
        copyNodes = Arrays.copyOf(nodes, n);
        printArray(copyNodes);
        bench = System.currentTimeMillis();
        bucketSort(copyNodes);
        assert refNode.compareTo(copyNodes[randomIndex]) == 0;
        System.out.println("bucket sort after");
        System.out.println("cost time:" + (System.currentTimeMillis() - bench) + "ms");
        printArray(copyNodes);
        System.out.println("");

        //系统自带的排序
        System.out.println("system sort before");
        copyNodes = Arrays.copyOf(nodes, n);
        printArray(copyNodes);
        bench = System.currentTimeMillis();
        Arrays.sort(copyNodes);
        assert (refNode.compareTo(copyNodes[randomIndex]) == 0);
        System.out.println("system sort after");
        System.out.println("cost time:" + (System.currentTimeMillis() - bench) + "ms");
        printArray(copyNodes);
        System.out.println("");
    }
    private static class DebugInfo{
        public DebugInfo(){
            System.out.println("debug test");
        }

        @Override
        public String toString() {
            return "DEBUG TO STRING";
        }
        public void printInfo(){
            System.out.println("print debug info");
        }
    }

    public static void main(String[] args) {
        LinkedHashMap<String,Integer> linkedHashMap=new LinkedHashMap<>();
        linkedHashMap.put("hello",1);
        linkedHashMap.put("good",2);
        linkedHashMap.put("nid",4);
        Set<Map.Entry<String,Integer>> set=linkedHashMap.entrySet();
        Iterator<Map.Entry<String,Integer>> iterator= set.iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Integer> entry=iterator.next();
            System.out.println("key:"+entry.getKey()+",val:"+entry.getValue());
        }

//        test();
//        int i = -4;
//        System.out.printf("%-10d %32s\n", i, Integer.toBinaryString(i));
//        i >>>= 1;  // 无符号右移1位
//        System.out.printf("%-10d %32s\n", i, Integer.toBinaryString(i));
//        i >>>= 1;
//        System.out.printf("%-10d %32s\n", i, Integer.toBinaryString(i));
        /*StringBuilder builderX=new StringBuilder("[");
        StringBuilder builderY=new StringBuilder("[");
        StringBuilder combineBuilderX=new StringBuilder("[");
        StringBuilder combineBuilderY=new StringBuilder("[");
        for(int i=0,k=10000;i<50;i++,k+=10000) {
            System.out.println("排序"+k+"个数时间消耗");
            //测试二路归并排序和基数排序
            ComparableNode<Integer>[] nodes = generateRandomNode(k,-10000,10000);
            ComparableNode<Integer>[] copyNodes=Arrays.copyOf(nodes,k);
            long bench=System.currentTimeMillis();
            combineSort(copyNodes,0,k-1);
            System.out.println("二路归并时间:"+(System.currentTimeMillis()-bench));
            combineBuilderX.append(k).append(" ");
            combineBuilderY.append(System.currentTimeMillis()-bench).append(" ");
            copyNodes=Arrays.copyOf(nodes,k);
            bench=System.currentTimeMillis();
            radixSort(nodes);
            System.out.println("基数排序时间:"+(System.currentTimeMillis()-bench));
            builderX.append(k).append(" ");
            builderY.append(System.currentTimeMillis()-bench).append(" ");
        }
        builderX.append("];");
        builderY.append("];");
        combineBuilderX.append("];");
        combineBuilderY.append("];");
        System.out.println(builderX);
        System.out.println(builderY);
        System.out.println(combineBuilderX);
        System.out.println(combineBuilderY);*/
    }
}

