/**
 * Algorithme AVL
 * Abdel Benslimane
 * Laurent Vouriot
 */

// imports
// -------
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @class Tree
 * @brief classe pour notre arbre AVL
 */
public class Tree {
    private Node root;                             //racine de l'arbre
    private Deque<Node> pile = new ArrayDeque<>(); // pile des noeuds de l'arbre

    // constructor
    // -----------
    public Tree() {this.root = null;}// racine null car au début l'arbre est vide

    /**
     * @param node : noeud que l'on veut ajouter
     * ajouter un noeud à l'arbre, si l'arbre est vide, le premier noeud ajouté devient la racine
     */
    public void add(Node node){
        if(root == null)
            root = node;
        else
            root.addSon(node);
        pile.add(node);
    }// add()

    /**
     * actualise la hauteur de tous les noeuds  de l'arbre
     */
    private void updateAllHeights() {
        int cpt = 1;
        for(Node node : pile)
            node.setHeight(node.updateHeight(cpt));
    }// updateAllHeights()

    /**
     * affichage de l'arbre
     */
    public void showTree(){root.show();}

    /**
     * @param node : noeud au quel on veut trouver le successeur
     * @return (Node) successeur
     * fonction nécéssaire à la supression d'un noeud de l'arbre
     */
    public Node successor(Node node) {
        Node current = node;
        while(current.getLeftChild() != null)
            current = current.getLeftChild();
        return current;
    }// successor()

    /**
     * @param node : noeud courant de l'abre
     * @param value : valeur que l'on veut supprimer de l'arbre
     * supprime un noeud de l'abre
     */
    public Node remove(Node node, int value) {
        // si la valeur que l'on  recherche est inferieur à celle du noeud actuelle
        // on cherche dans le sous arbre gauche
        if(value < node.getValue())
            node.setLeftChild(remove(node.getLeftChild(), value));
        // à l'inverse on regarde dans le sous arbre droit
        else if(value > node.getValue())
            node.setRightChild(remove(node.getRightChild(),value));
        else {
            // si le noeud a un fils à droite
            if(node.getLeftChild() == null) {
                Node tmp = node.getRightChild();
                node = null;
                return tmp;
            }
            // si le noeud a un fils à gauche...
            else if(node.getRightChild() == null) {
                Node tmp = node.getLeftChild();
                node = null;
                return tmp;
            }
            // si le noeud a deux enfants on cherche son successeur
            Node succ = this.successor(node.getRightChild());
            // on remplace la valeur du noeud par celle du successeur
            node.setValue(succ.getValue());
            // on supprime le successeur
            node.setRightChild(remove(node.getRightChild(),succ.getValue()));
        }
        return node;
    }// remove()

    public static void main(String[] args) {
        Tree t1 = new Tree();
        Node n1 = new Node(13);
        Node n2 = new Node(9);
        Node n3 = new Node(15);
        Node n4 = new Node(24);
        Node n5 = new Node(8);
        Node n6 = new Node(10);
        Node n7 = new Node(11);
        Node n8 = new Node(12);

        t1.add(n1);
        t1.add(n2);
        t1.add(n3);
        t1.add(n4);
        t1.add(n5);
        t1.add(n6);
        t1.add(n7);
        t1.add(n8);

        t1.updateAllHeights();

        t1.showTree();
        System.out.println();

        t1.remove(n1, 10);
        t1.updateAllHeights();

        t1.showTree();
    }
}
