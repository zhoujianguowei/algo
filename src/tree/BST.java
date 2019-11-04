package tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Consumer;

public class BST<Key extends Comparable<Key>, Value> {

    private class Node {

        private Key key;
        private Value value;
        private Node left, right;
        private int number;

        private Node(Key key, Value value, int number) {
            this.key = key;
            this.value = value;
            this.number = number;
        }
    }

    private Node root;

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.number;
    }

    public boolean isEmpty() { return size(root) == 0;}

    public Value get(Key key) {
        return getValue(root, key);
    }

    private Value getValue(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = x.key.compareTo(key);
        if (cmp > 0) {
            return getValue(x.left, key);
        } else if (cmp < 0) {
            return getValue(x.right, key);
        } else {
            return x.value;
        }
    }

    public void put(Key key, Value value) {
        root = putValue(root, key, value);
    }

    public Node putValue(Node x, Key key, Value value) {
        if (x == null) {
            return new Node(key, value, 1);
        }
        int cmp = x.key.compareTo(key);
        if (cmp > 0) {
            x.left = putValue(x.left, key, value);
        } else if (cmp < 0) {
            x.right = putValue(x.right, key, value);
        } else {
            x.value = value;
        }
        x.number = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Node min() {
        return  root == null ? null : min(root);
    }

    /**
    * 当n结点的left不等于null时，递归min查询n.left,否值返回n结点
    */
    private Node min(Node n) {
        return n.left == null ? n : min(n.left);
    }

    public Node max() {
        return root == null ? null : max(root);
    }

    /**
     * 当n结点的right不等于null时，递归max查询n.right,否值返回n结点
     */
    private Node max(Node n) {
        return n.right == null ? n : max(n.right);
    }

    /**
     * 小于等于key当中最大的key值
     */
    public Key floor(Key key) {
        Node n = floor(root, key);
        if (n == null) {
            return null;
        }
        return n.key;
    }

    /**
     * 如果n结点为，返回null
     * 比较cmp = key.compareTo(n.key)，如果相等返回n
     * 如果key 小于 n.key，递归查询floor（n.left）
     * 如果key 大于 n.key, 递归查询floor（n.right)，如果floor（n.right)返回为null，
     * 则表示n的右子树中不存在小于key的值，所以n结点为小于key的最大值，返回n
     */
    private Node floor(Node n, Key key) {
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp == 0) {
            return n;
        } else if (cmp < 0) {
            return floor(n.left, key);
        } else {
            Node t = floor(n.right, key);
            if (t != null) {
                return t;
            } else {
                return n;
            }
        }
    }

    /**
     * 返回大于等于key的结点当中最小的结点
     * @param key
     * @return
     */
    public Key ceil(Key key) {
        Node n = ceil(root, key);
        if (n == null) {
            return null;
        }
        return n.key;
    }

    /**
     * 如果n结点为，返回null
     * 比较cmp = key.compareTo(n.key)，如果相等返回n
     * 如果key 小于 n.key，递归查询ceil（n.left),如果ceil（n.left)返回为空，
     * 则表示n的左子树中没有大于等于key的结点，即n为大于key的最小结点，返回n
     * 如果key 大于 n.key, 递归查询（n.right)
     * @param n
     * @param key
     * @return
     */
    private Node ceil(Node n, Key key) {
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp == 0) {
            return n;
        } else if (cmp > 0) {
            return ceil(n.right, key);
        } else {
            Node t = ceil(n.left, key);
            if (t != null) {
                return t;
            }
            return n;
        }
    }

    /**
     * 返回 key 的排名，从0开始
     * @param key
     * @return
     */
    public int rank(Key key) {
        return rank(root, key);
    }

    /**
     * 如果n等于null，则返回0
     * 比较 cmp = key.compareTo(n.key)
     * 如果 cmp = 0，则返回size(n.left)
     * 如果 cmp < 0，则返回递归调用rank(n.left, key)的值
     * 如果 cmp > 0，说明key值实在n的右子树上，这时key的排名等于n左子树的数量 + n结点 + key在n右子树的排名，
     * 即返回 size(n.left) + 1 + rank(n.right, key)的值
     * @param n
     * @param key
     * @return
     */
    public int rank(Node n, Key key) {
        if (n == null) {
            return 0;
        }
        int cmp = key.compareTo(n.key);
        if (cmp == 0) {
            return size(n.left);
        } else if (cmp < 0) {
            return rank(n.left, key);
        } else {
            return size(n.left) + 1 + rank(n.right, key);
        }
    }

    /**
     * 选择排名为i的key，排名从0开始计数
     * @param i
     * @return
     */
    public Key select(int i) {
        return select(root, i).key;
    }

    /**
     * 如果n等于null，则返回null，表示在n中排名为i（i >= 0)的结点不存在
     * 比较 i == size(n.left)，如果相等，则返回n（因为排名从0开始，即某个结点的排名正好为该结点左子树的size）
     * 如果 i < size(n.left)，说明排名为i的结点在n的左子树上，返回递归调用select(n.left, i)
     * 如果 i > size(n.right), 说明排名为i的结点在n的右子树上，因为右子树上结点在n上总排名i = 左子树的数量 + n结点 + 右子树结点在右子树中的排名，
     * 即 右子树结点在右子树中的排名 = i - size(n.left) - 1，所以返回递归调用select(n.right, i - size(n) - 1)
     * @param n
     * @param i
     * @return
     */
    public Node select(Node n, int i) {
        if (n == null) {
            return null;
        }
        int ln = size(n);
        if (i < ln) {
            return select(n.left, i);
        } else if (i > ln) {
            return select(n.right, i - ln - 1);
        } else {
            return n;
        }
    }

    /**
     * 删除最大结点
     */
    public void deleteMax() {
        if (root == null) {
            return;
        }
        root = deleteMax(root);
    }

    /**
     * 删除以n为根结点的子树中的最大结点并返回剩余结点
     * 如果n的右子树为null，则说明n为该子树最大结点，返回删除n后的子树n.left
     * 如果n的右子树不为null，则递归调用deleteMax(n.right)，并把deleteMax(n.right)返回的已经删除了最大值结点的新子树作为n的右子树
     * 即 n.right = deleteMax(n.right),并更新新子树的number
     * @param n
     * @return
     */
    private Node deleteMax(Node n) {
        if (n.right == null) {
            return n.left;
        }
        n.right = deleteMax(n.right);
        n.number = size(n.right) + size(n.left) + 1;
        return n;
    }

    /**
     * 删除最小结点
     */
    public void deleteMin() {
        if (root == null) {
            return;
        }
        root = deleteMin(root);
    }

    /**
     * 删除以n为根结点的子树中的最小结点并返回剩余结点
     * 如果n的左子树为null，则说明n为该子树最小结点，返回删除n后的右子树n.right
     * 如果n的左子树不为null，则递归调用deleteMix(n.left)，并把deleteMin(n.left)返回的已经删除了最小值结点的新子树作为n的左子树
     * 即 n.left = deleteMin(n.left),并更新新子树的number
     * @param n
     * @return
     */
    private Node deleteMin(Node n) {
        if (n.left == null) {
            return n.right;
        }
        n.left = deleteMin(n.left);
        n.number = size(n.left) + size(n.right) + 1;
        return  n;
    }

    /**
     * 删除某个key
     * @param key
     */
    public void delete(Key key) {
        root = delete(root, key);
    }

    /**
     * 删除以n为根结点的树中的某个key，并返回删除之后的新子树
     * 如果n == null，则return null
     * 比较 cmp = key.compareTo(n.key)
     * 如果cmp < 0 ,则递归调用delete(n.left, key)，并把n.left赋值为删除了key之后的新子树
     * 如果cmp > 0，则递归调用delete(n.right, key)，并把n.right赋值为删除了key之后的新子树
     * 如果cmp == 0，按以下流程处理
     *      如果 n.left == null，即删除了n结点后的新子树为 n.right，返回n.right
     *      如果 n.right == null，即删除了n结点之后的新子树为 n.left，返回n.left
     *      否则需要合并左右子树，可以选取 n.right 中的最小结点作为新的根结点，并把左子树和删除了该结点的右子树拼接到该根结点上
     * 更新新子树的number
     * @param n
     * @param key
     * @return
     */
    private Node delete(Node n, Key key) {
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            n.left = delete(n.left, key);
        } else if (cmp > 0) {
            n.right = delete(n.right, key);
        } else {
            if (n.left == null) {
                return n.right;
            }
            if (n.right == null) {
                return n.left;
            }
            Node temp = n;
            n = min(n.right);
            n.right = deleteMin(temp.right);
            n.left = temp.left;
        }
        n.number = size(n.left) + size(n.right) + 1;
        return n;
    }



    /**
     * 先序遍历：在遍历过程中，父节点先于它的子节点被访问，就是先序遍历
     */
    public void DLR(Consumer<Node> consumer) {
        DLR(root, consumer);
    }

    /**
     * 先序遍历结点n
     * @param n
     * @param consumer
     */
    public void DLR(Node n, Consumer<Node> consumer) {
        if (n == null) {
            return;
        }
        consumer.accept(n);
        DLR(n.left, consumer);
        DLR(n.right, consumer);
    }

    /**
     * 中序遍历：父节点被访问的次序位于左右孩子节点之间
     * @param consumer
     */
    public void LDR(Consumer<Node> consumer) {
        LDR(root, consumer);
    }

    /**
     * 中序遍历结点n
     * @param n
     * @param consumer
     */
    public void LDR(Node n, Consumer<Node> consumer) {
        if (n == null) {
            return;
        }
        LDR(n.left, consumer);
        consumer.accept(n);
        LDR(n.right, consumer);
    }

    /**
     * 后序遍历：访问完左右孩子节点之后再访问父节点
     * @param consumer
     */
    public void LRD(Consumer<Node> consumer) {
        LRD(root, consumer);
    }

    /**
     * 后序遍历结点n
     * @param n
     * @param consumer
     */
    public void LRD(Node n, Consumer<Node> consumer) {
        if (n == null) {
            return;
        }
        LDR(n.left, consumer);
        LDR(n.right, consumer);
        consumer.accept(n);
    }


    /**
     * 层次遍历：按照从上到下、从左向右的顺序访问二叉树的每个节点
     * @param consumer
     */
    public void travLevel(Consumer<Node> consumer) {
        if (root == null) {
            return;
        }
        ArrayList<Node> list = new ArrayList<>();
        list.add(root);
        travLevel(list, consumer);
    }

    public void travLevel(ArrayList<Node> list, Consumer<Node> consumer) {
        Iterator<Node> iterator = list.iterator();
        Node temp;
        ArrayList<Node> clist = new ArrayList<>();
        while (iterator.hasNext()) {
            temp = iterator.next();
            consumer.accept(temp);
            if (temp.left != null) {
                clist.add(temp.left);
            }
            if (temp.right != null) {
                clist.add(temp.right);
            }
        }
        if (!clist.isEmpty()) {
            travLevel(clist, consumer);
        }
    }

    /**
     * 按顺序打印root
     */
    public void show() {
        LDR(root, (x) -> {
            System.out.print(x.key + " ");
        });
    }

    public Iterable<Key> keys() {
        return keys(min().key, max().key);
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        ArrayList<Key> queue = new ArrayList<Key>();
        keys(root, queue, lo ,hi);
        return queue;
    }

    private void keys(Node n, ArrayList<Key> queue, Key lo, Key hi) {
        if (n == null) {
            return;
        }
        int cmplo = n.key.compareTo(lo);
        int cmphi = n.key.compareTo(hi);
        if (cmplo > 0) {
            keys(n.left, queue, lo, hi);
        }
        if (cmplo >= 0 && cmphi <= 0) {
            queue.add(n.key);
        }
        if (cmphi < 0) {
            keys(n.right, queue, lo, hi);
        }
    }

    public static void main(String args[]) {
        BST<Integer, String> bst = new BST<Integer, String>();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String order;
        while (true) {
            order = scanner.nextLine();
            if ("add".equals(order)) {
                int num = random.nextInt(1000);
                bst.put(num, num + "");
                System.out.println("添加" + num + ":" + num);
            }
            else if ("get".equals(order)) {
                System.out.println("请输入需要获取的key");
                int key = -1;
                try {
                    key = Integer.parseInt(scanner.nextLine());
                    System.out.println("获取" + key + ":" + bst.get(key));
                }catch (Exception e) {
                    System.out.println("无效的key");
                }
            }
            else if ("remove".equals(order)) {
                System.out.println("请输入需要删除的key");
                int key = -1;
                try {
                    key = Integer.parseInt(scanner.nextLine());
                    System.out.println(key + ":" + bst.get(key));
                }catch (Exception e) {
                    System.out.println("无效的key");
                }
            }
            else if ("min".equals(order)) {
                System.out.println("min:" + bst.min().key);
            }
            else if ("max".equals(order)) {
                System.out.println("max:" + bst.max().key);
            }
            else if ("floor".equals(order)) {
                System.out.println("请输入floor的键");
                int floor;
                try {
                    floor = Integer.parseInt(scanner.nextLine());
                    System.out.println("小于等于" + floor + "的值中最大的key:" + bst.floor(floor));
                }catch (Exception e) {
                    System.out.println("无效的排名");
                }
            }
            else if ("ceil".equals(order)) {
                System.out.println("请输入ceil的键");
                int ceil;
                try {
                    ceil = Integer.parseInt(scanner.nextLine());
                    System.out.println("大于等于" + ceil + "的值中最小的key:" + bst.ceil(ceil));
                }catch (Exception e) {
                    System.out.println("无效的排名");
                }
            }
            else if ("select".equals(order)) {
                System.out.println("请输入需要选择的排名");
                int select;
                try {
                    select = Integer.parseInt(scanner.nextLine());
                    System.out.println("排名" + select + "的key:" + bst.select(select));
                }catch (Exception e) {
                    System.out.println("无效的排名");
                }
            }
            else if ("rank".equals(order)) {
                System.out.println("请输入需要查看排名的键");
                int key;
                try {
                    key = Integer.parseInt(scanner.nextLine());
                    System.out.println("键值" + key + "的排名:" + bst.rank(key));
                }catch (Exception e) {
                    System.out.println("无效的排名");
                }
            }
            else if ("deleteMax".equals(order)) {
                System.out.println("删除最大值");
                bst.deleteMax();
            }
            else if ("deleteMin".equals(order)) {
                System.out.println("删除最小值");
                bst.deleteMin();
            }
            else if ("delete".equals(order)) {
                System.out.println("请输入需要删除的键");
                int key;
                try {
                    key = Integer.parseInt(scanner.nextLine());
                    System.out.println("删除" + key);
                    bst.delete(key);
                }catch (Exception e) {
                    System.out.println("无效的排名");
                }
            }
            else if ("DLR".equals(order)) {
                System.out.print("先序遍历：" );
                bst.DLR((x) -> {
                    System.out.print(x.key + " ");
                });
                System.out.println();
            }
            else if ("LDR".equals(order)) {
                System.out.print("中序遍历：" );
                bst.LDR((x) -> {
                    System.out.print(x.key + " ");
                });
                System.out.println();
            }
            else if ("LRD".equals(order)) {
                System.out.print("后序遍历：" );
                bst.LRD((x) -> {
                    System.out.print(x.key + " ");
                });
                System.out.println();
            }
            else if ("travLevel".equals(order)) {
                System.out.print("层次遍历：" );
                bst.travLevel((x) -> {
                    System.out.print(x.key + " ");
                });
                System.out.println();
            }
            else if ("keys".equals(order)) {
                Iterable<Integer> iterable = bst.keys();
                System.out.print("按顺序获取keys：" );
                iterable.forEach((x) -> {
                    System.out.print(x + " ");
                });
                System.out.println();
            }else if ("lohi".equals(order)) {
                System.out.println("请输入low-key");
                int lo = 0;
                int hi = 0;
                try {
                    lo = Integer.parseInt(scanner.nextLine());
                    System.out.println("请输入hi-key");
                    hi = Integer.parseInt(scanner.nextLine());
                    Iterable<Integer> iterable = bst.keys(lo, hi);
                    System.out.print(lo + "到" + hi + "之间的keys：" );
                    iterable.forEach((x) -> {
                        System.out.print(x + " ");
                    });
                    System.out.println();
                }catch (Exception e) {
                    System.out.println("无效的key");
                }
            }
            bst.show();
        }
    }
}
