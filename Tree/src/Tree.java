/**
 * Algorithme AVL
 * Abdel Benslimane
 * Laurent Vouriot
 */

// imports
// -------
import java.awt.desktop.SystemSleepEvent;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @class Tree
 * @brief classe pour notre arbre AVL
 */
public class Tree {
    private Node root;                             // racine de l'arbre
    private Deque<Node> pile = new ArrayDeque<>(); // pile des noeuds de l'arbre

    // constructor
    // -----------
    public Tree() {this.root = null;}// racine null car au début l'arbre est vide

    /**
     * @param node : noeud que l'on veut ajouter
     * ajouter un noeud à l'arbre, si l'arbre est vide, le premier noeud ajouté devient la racine
     */
    public void add(Node node){
        if(getRoot() == null)
            setRoot(node);
        else {
            updateRoot();
            getRoot().addSon(node);
        }
        pile.add(node);
    }// add()

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
        node.updateHeight();
        return node;
    }// remove()

    public void updateRoot(){
        Node root = getRoot();
        while(root.getFather() != null)
            root = root.getFather();
        setRoot(root);
    }// updateRoot()

    //------------------------------------------------------------------------------------------------------------------
    // getter
    //------------------------------------------------------------------------------------------------------------------
    public Node getRoot() {return root;}

    //------------------------------------------------------------------------------------------------------------------
    // setter
    //------------------------------------------------------------------------------------------------------------------
    public void setRoot(Node node) {this.root = node;}

    // DEBUG
    public static void main(String[] args) {
        Tree t1 = new Tree();

        Node n1 = new Node(50);
        Node n2 = new Node(17);
        Node n3 = new Node(9);
        Node n4 = new Node(14);
        Node n5 = new Node(12);
        Node n6 = new Node(76);
        Node n7 = new Node(65);
        Node n8 = new Node(70);
        Node n9 = new Node(80);

        t1.add(n1);
        t1.add(n2);
        t1.add(n3);
        t1.add(n4);
        t1.add(n5);
        t1.add(n6);/*
        t1.add(n7);
        t1.add(n8);
        t1.add(n9);*/

//        for (int i = 0; i < 5; i++)
//            t1.add(new Node(i + 1));

        t1.showTree();
    }
}
