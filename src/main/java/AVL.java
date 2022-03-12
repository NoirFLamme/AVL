import static java.lang.Math.*;

class Node{
    String value;
    int height;

    Node left, right;


    Node(String number)
    {
        value = number;
        height = 1;
    }

}


class AVL {
    Node root;
    int size = 0;

    int getHeight(Node a)
    {
        if (a == null)
        {
            return 0;
        }
        return a.height;
    }

    Node rightRotation(Node current)
    {
        Node nRoot = current.left;
        current.left = nRoot.right;
        nRoot.right = current;

        current.height = max(getHeight(current.left), getHeight(current.right)) + 1;
        nRoot.height =  max(getHeight(nRoot.left), getHeight(nRoot.right)) + 1;

        return nRoot;
    }

    Node leftRotation(Node current)
    {
        Node nRoot = current.right;
        current.right = nRoot.left;
        nRoot.left = current;

        current.height = max(getHeight(current.left), getHeight(current.right)) + 1;
        nRoot.height =  max(getHeight(nRoot.left), getHeight(nRoot.right)) + 1;

        return nRoot;
    }

    void insert(String value) {
        this.root = insertAndReturnRoot(this.root, value);
    }

    private Node insertAndReturnRoot(Node node, String value)
    {
        if (node == null)
        {
            return new Node(value);
        }

        if (value.compareTo(node.value) < 0)
        {
            node.left = insertAndReturnRoot(node.left, value);
        }
        else if(value.compareTo(node.value) > 0)
        {
            node.right = insertAndReturnRoot(node.right, value);
        }
        else
            return node;

        node.height = max(getHeight(node.right), getHeight(node.left)) + 1;

        int balanceFactor = balanceFactor(node);

        if (balanceFactor > 1 && value.compareTo(node.left.value) < 0)
        {
            return rightRotation(node);
        }
        if(balanceFactor < -1 && value.compareTo(node.right.value) > 0)
        {
            return leftRotation(node);
        }
        if(balanceFactor > 1 && value.compareTo(node.left.value) > 0)
        {
            node.left = leftRotation(node.left);
            return rightRotation(node);
        }
        if(balanceFactor < -1 && value.compareTo(node.right.value) < 0)
        {
            node.right = rightRotation(node.right);
            return leftRotation(node);
        }


        return node;
    }

    int search(Node root, String value)
    {
        if (root == null)
        {
            System.out.println("NO");
            return 0;
        }

        if (value.compareTo(root.value) == 0)
        {
            System.out.println("YES");
            return 1;
        }
        else if(value.compareTo(root.value) < 0)
        {
            return search(root.left, value);
        }
        else if(value.compareTo(root.value) > 0)
        {
            return search(root.right,value);
        }

        return 0;
    }

    void getSize(Node root)
    {
        if (root != null)
        {
            this.size += 1;
            getSize(root.left);
            getSize(root.right);
        }
    }

    int balanceFactor(Node node)
    {
        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    Node delete(Node root, String value)
    {
        if(root == null)
        {
            return null;
        }

        if (value.compareTo(root.value) < 0)
        {
            root.left = delete(root.left, value);
        }
        else if(value.compareTo(root.value) > 0)
        {
            root.right = delete(root.right, value);
        }
        else
        {
            if (root.left == null || root.right == null)
            {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                else
                    root = temp;
            }
            else
            {
                Node temp = lowestNode(root.right);
                root.value = temp.value;
                root.right = delete(root.right, temp.value);
            }
        }

        if (root == null)
        {
            return root;
        }

        root.height = max(getHeight(root.right), getHeight(root.left)) + 1;

        int balanceFactor = balanceFactor(root);

        if (balanceFactor > 1 && balanceFactor(root.left) >= 0)
        {
            return rightRotation(root);
        }
        if(balanceFactor < -1 && balanceFactor(root.left) < 0)
        {
            return leftRotation(root);
        }
        if(balanceFactor > 1 && balanceFactor(root.right) <= 0)
        {
            root.left = leftRotation(root.left);
            return rightRotation(root);
        }
        if(balanceFactor < -1 && balanceFactor(root.right) > 0)
        {
            root.right = rightRotation(root.right);
            return leftRotation(root);
        }

        return root;
    }


    Node lowestNode(Node root)
    {
        Node temp = root;
        while (temp.left != null)
        {
            temp = root.left;
        }
        return temp;
    }

    public static void main(String[] args)
    {
        AVL tree = new AVL();

        tree.insert("10");
        tree.insert( "20");
        tree.insert( "30");
        tree.insert( "40");
        tree.insert( "50");
        tree.insert("25");
        int yes = tree.search(tree.root, "5");
        tree.root = tree.delete(tree.root, "30");
        System.out.println(tree.getHeight(tree.root));
    }
}
