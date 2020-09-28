/**
 * Algorithme AVL
 * Abdel Benslimane
 * Laurent Vouriot
 */

// imports
// -------
import java.lang.Math; // Math.max(int,int)
import java.sql.SQLOutput;

/**
 * @class Node
 * classe noeud pour nos arbres
 */
public class Node {
    private int  value;       // valeur stockée dans le noeud
    private int  height;      // hauteur du noeud
    private Node leftChild;   // fils gauche
    private Node rightChild;  // fils droit
    private Node father;      // père du noeud

    // constructor
    // -----------
    public Node(int value) {
        this.value = value;
        this.height = 1;
    }

    /**
     * @param son : Noeud fils à ajouté au noeud
     * on ajoute un fils au noeud à gauche ou à droite
     * selon la valeur de celui-ci
     */
    public void addSon(Node son) {
        if(son.getValue() > value) {
            // si il n'y pas de fils à droite on ajoute directement sinon on ajoute au noeud fils
            // de manière récursive
            if(rightChild == null) {
                rightChild = son;
                // si le noeud est une feuille on peut garder son père
                son.setFather(this);
                // après chaque insertion on met à jour les hauteurs des noeuds
                son.updateHeight();
            }
            else
                rightChild.addSon(son);
        }
        else {
            // idem pour noeud gauche
            if (leftChild == null) {
                leftChild = son;
                son.setFather(this);
                son.updateHeight();
            }
            else
                leftChild.addSon(son);
        }
    }// addSon()

    /**
     * actualise la hauteur d'un noeud et de ses pères
     */
    void updateHeight() {
        // si il y a un changement de hauteur dans un  noeud on regarde si il est déséquilibré.
        if (getHeight() != calcHeight()) {
            if (unbalanced())
                // TODO : trouver dans quel cas de déséquilibre on est
                System.out.println(this.getValue() + " unbalanced !");
        }
        // si pas de déséquilibre on met à jour la hauteur
        setHeight(calcHeight());
        // si le noeud a un père (donc pas la racine), on met à jour la hauteur de on père
        if(getFather() != null) {
            System.out.println(getValue() + " father value : " + getFather().getValue());
            getFather().updateHeight();
        }
    }// updateHeight()

    /**
     * @return (int) la hauteur du noeud
     * calcule la hauteur d'un noeud.
     */
    int calcHeight() {
        // si il n'y a pas  de fils gauche ou droit c'est une feuille donc h = 1
        if(leftChild == null && rightChild == null)
            return 1;
        // si il n'y pas de fils à gauche mais au moins un à droite
        else if(leftChild == null)
             return rightChild.calcHeight() + 1;
        // idem pour le sous arbre droit
        else if(rightChild == null)
            return leftChild.calcHeight() + 1;
        // si il y a un fils à gauche et à droite on récupère la hauteur maximale entre les 2 sous arbres
        else
            return Math.max(leftChild.calcHeight(), rightChild.calcHeight()) + 1;
    }// calcHeight()

    /**
     * affiche le contenu d'un noeud
     */
    public void show() {
        // affichage récursif de chaque noeud (infix)
        System.out.println("value : " + value + " height : " + height);
        if(leftChild != null)
            leftChild.show();
        if(rightChild != null)
            rightChild.show();
    }// show()

    /**
     * @return (bool) : vrai si l'arbre/sous-arbre est déséquilibré
     * verifie si le noeud est déséquilibré
     */
    public boolean unbalanced(){
        // TODO : belek et commentaires
        // si pas de fils g/d c'est une feuille il ne peut  pas être déséquilibré
        if(this.getRightChild() == null && this.getLeftChild() == null)
            return false;
        // si il n'a pas de fils gauche on vérifie que la hauteur ne dépasse pas 1
        if(this.getLeftChild() == null)
            return this.getRightChild().getHeight() > 1;
        // si pas de fils droit idem
        else if(this.getRightChild() == null)
            return this.getLeftChild().getHeight() > 1;
        // si il y a un fils g et d on verifie que la difference entre les ne dépasse pas 1
        else
            return Math.abs(this.getLeftChild().getHeight() - this.getRightChild().getHeight()) > 1;
    }// unbalanced()

    // getters
    // -------
    public int  getValue()      {return value;}
    public Node getLeftChild()  {return leftChild;}
    public Node getRightChild() {return rightChild;}
    public int  getHeight()     {return height;}
    public Node getFather()     {return father;}

    // setters
    // -------
    public void setHeight(int height)    {this.height = height;}
    public void setLeftChild(Node node)  {this.leftChild = node;}
    public void setRightChild(Node node) {this.rightChild = node;}
    public void setValue(int value)      {this.value = value;}
    public void setFather(Node father)   {this.father = father;}

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
