/**
 * Algorithme AVL
 * Abdel Benslimane
 * Laurent Vouriot
 */

// imports
// -------
import java.lang.Math; // Math.max(int,int)

/**
 * @class Node
 * @brief classe noeud pour nos arbres
 */
public class Node {
    private int  value;       // valeur stockée dans le noeud
    private int  height;      // hauteur du noeud
    private Node leftChild;   // fils gauche
    private Node rightChild;  // fils droit

    // constructor
    // -----------
    public Node(int value) {this.value = value; this.height = 1;}

    /**
     * @param son : Noeud fils à ajouté au noeud
     * on ajoute un fils au noeud à gauche ou à droite
     * selon la valeur de celui-ci
     */
    public void addSon(Node son) {
        // TODO : operateur ternaire ?
        if(son.getValue() > value) {
            // si il n'y pas de fils à droite on ajoute directement sinon on ajoute au noeud fils
            // de manière récursive
            if(rightChild == null)
                rightChild = son;
            else
                rightChild.addSon(son);
        }
        else {
            // idem pour noeud gauche
            if (leftChild == null)
                leftChild = son;
            else
                leftChild.addSon(son);
        }
    }// addSon()

    /**
     * @param cpt : compteur pour la hauteur
     * @return (int) la hauteur du noeud
     * met à jour la hauteur de chaque noeud. A faire après insertion d'un noeud dans l'arbre
     */
    int updateHeight(int cpt) {
        // on parcours l'abre de manière récursive jusqu'aux feuilles en incrémentant un compteur à chaque fois que l'on
        // déscend sur un neoud fils. Si il n'y a plus de de fils g/d on renvoie la valeur du compteur qui est la hauteur
        // du noeud
        if(leftChild == null && rightChild == null) {
            return cpt;
        }
        // si il n'y pas de fils à gauche mais au moins un à droite
        else if(leftChild == null) {
            cpt++;
            return rightChild.updateHeight(cpt);
        }
        // idem
        else if(rightChild == null) {
            cpt++;
            return leftChild.updateHeight(cpt);
        }
        // si il y a un fils à gauche et à droite on récupère la hauteur maximale entre les 2 sous arbres
        else {
            cpt++;
            return Math.max(leftChild.updateHeight(cpt), rightChild.updateHeight(cpt));
        }
    }// updateHeight()

    /**
     * affiche le contenu d'un noeud
     */
    public void show() {
        // affichage récursif de chaque noeud
        System.out.println("value : " + value + " height : " + height);
        if(leftChild != null)
            leftChild.show();
        if(rightChild != null)
            rightChild.show();
    }// show()

    // getters
    // -------
    public int  getValue()      {return value;}
    public Node getLeftChild()  {return leftChild;}
    public Node getRightChild() {return rightChild;}

    // setter
    // -------
    public void setHeight(int height)    {this.height = height;}
    public void setLeftChild(Node node)  {this.leftChild = node;}
    public void setRightChild(Node node) {this.rightChild = node;}
    public void setValue(int value)      {this.value = value;}

    public static void main(String[] args){
        Node n1 = new Node(13);
        Node n2 = new Node(10);
        Node n3 = new Node(20);
        Node n4 = new Node(9);
        Node n5 = new Node(19);
        Node n6 = new Node(49);

        n1.addSon(n2);
        n1.addSon(n3);
        n1.addSon(n4);
        n1.addSon(n5);
        n1.addSon(n6);

        n1.show();
    }// main()
}
