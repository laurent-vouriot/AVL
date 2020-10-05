/**
 * Algorithme AVL
 * Abdel Benslimane
 * Laurent Vouriot
 */

// imports
// -------
import java.lang.Math; // Math.max(int,int)
import java.sql.SQLOutput;
import java.util.EventListener;

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

    //------------------------------------------------------------------------------------------------------------------
    // Methodes
    //------------------------------------------------------------------------------------------------------------------

    /**
     * @param son : Noeud fils à ajouté au noeud
     * on ajoute un fils au noeud à gauche ou à droite
     * selon la valeur de celui-ci
     */
    public void addSon(Node son) {
        if(son.getValue() > getValue()) {
            // si il n'y pas de fils à droite on ajoute directement sinon on ajoute au noeud fils
            // de manière récursive
            if(getRightChild() == null) {
                setRightChild(son);
                // si le noeud est une feuille on peut garder son père
                son.setFather(this);
                // après chaque insertion on met à jour les hauteurs des noeuds
                son.updateHeight();
            }
            else
                getRightChild().addSon(son);
        }
        else {
            // idem pour noeud gauche
            if (getLeftChild() == null) {
                setLeftChild(son);
                son.setFather(this);
                son.updateHeight();
            }
            else
                getLeftChild().addSon(son);
        }
    }// addSon()

    /**
     * @return (int) la hauteur du noeud
     * calcule la hauteur d'un noeud.
     */
    int calcHeight() {
        // si il n'y a pas  de fils gauche ou droit c'est une feuille donc h = 1
        if(getLeftChild()== null && getRightChild() == null)
            return 1;
            // si il n'y pas de fils à gauche mais au moins un à droite
        else if(getLeftChild() == null)
            return getRightChild().calcHeight() + 1;
            // idem pour le sous arbre droit
        else if(getRightChild() == null)
            return getLeftChild().calcHeight() + 1;
            // si il y a un fils à gauche et à droite on récupère la hauteur maximale entre les 2 sous arbres
        else
            return Math.max(getLeftChild().calcHeight(), getRightChild().calcHeight()) + 1;
    }// calcHeight()

    /**
     * @return (bool) : vrai si l'arbre/sous-arbre est déséquilibré
     * verifie si le noeud est déséquilibré
     */
    public boolean unbalanced(){
        // si pas de fils g/d c'est une feuille il ne peut  pas être déséquilibré
        if(this.getRightChild() == null && this.getLeftChild() == null)
            return false;
        // si il n'a pas de fils gauche on vérifie que la hauteur de son fils droit ne dépasse pas 1
        if(this.getLeftChild() == null)
            return this.getRightChild().getHeight() > 1;
            // si pas de fils droit idem
        else if(this.getRightChild() == null)
            return this.getLeftChild().getHeight() > 1;
        else
            // si il y a un fils g et d on verifie que la difference entre les ne dépasse pas 1
            return Math.abs(this.getLeftChild().getHeight() - this.getRightChild().getHeight()) > 1;
    }// unbalanced()

    /**
     * actualise la hauteur d'un noeud et de ses pères
     */
    void updateHeight() {
        // si il y a un changement de hauteur dans un  noeud on regarde si il est déséquilibré.
        if (getHeight() != calcHeight()) {
            if (unbalanced())
                // si oui on appel la méthode qui va déterminer le cas de déséquilibre puis rééquilibrer
                balance();
        }
        // si pas de déséquilibre on met à jour la hauteur
        setHeight(calcHeight());
        // si le noeud a un père (donc pas la racine), on met à jour la hauteur de on père
        if(getFather() != null) {
            getFather().updateHeight();
        }
    }// updateHeight()

    //------------------------------------------------------------------------------------------------------------------
    // rééquilibrage
    //------------------------------------------------------------------------------------------------------------------

    /**
     * cas de rééquilibrage gauche interne
     */
    void leftInternal() {
        System.out.println("leftInternal " + this.getValue());

        // on récupère la nouvelle racine
        Node newRoot = this.getLeftChild().getRightChild();
        newRoot.setFather(null);

        // si la nouvelle racine a un fils droit
        if(newRoot.getRightChild() != null) {
            // le fils droit de la nouvelle racine devient le fils droit du fils gauche de l'ancienne racine (ouf!)
            this.getLeftChild().setRightChild(newRoot.getRightChild());
            // penser à mettre à jour le père
            newRoot.getLeftChild().setFather(this.getLeftChild());
        }
        else
            // sinon  on met rien
            this.getLeftChild().setRightChild(null);

        // le fils gauche de la nouvelle racine est le fils gauche de l'ancienne racine
        newRoot.setLeftChild(this.getLeftChild());
        this.getLeftChild().setFather(newRoot);

        // dans ce cas là il faut mettre jour imméditamement la hauteur du fils gauche de la nouvelle racine
        newRoot.getLeftChild().setHeight(newRoot.getLeftChild().calcHeight());

        // si la racine a un fils droit
        if(newRoot.getRightChild() != null) {
            // ce fils sera le fils droit de l'ancienne racine
            this.setLeftChild(newRoot.getRightChild());
            newRoot.getRightChild().setFather(this);
        }
        else
            // sinon on met rien
            this.setLeftChild(null);

        // l'ancienne racine devient le fils droit de la nouvelle racine
        newRoot.setRightChild(this);
        // met à jour le père...
        this.setFather(newRoot);
    }// leftInternal()

    /**
     * cas de déséquilibre gauche externe
     */
    void leftExternal() {
        System.out.println("leftExternal " + this.getValue());

        // on récupère la nouvelle racine
        Node newRoot = getLeftChild();
        newRoot.setFather(null);

        // si cette nouvelle racine a un fils droit
        if(newRoot.getRightChild() != null) {
            // on met ce noeud en fils gauche de l'ancienne racine
            this.setLeftChild(newRoot.getRightChild());
            // on met à jour le père du noeud
            newRoot.getRightChild().setFather(this);
        }
        else
            // sinon on met rien
            this.setLeftChild(null);

        // le fils droit de la nouvelle racine est l'ancienne racine
        newRoot.setRightChild(this);
        // on met à jour le père de l'ancienne racine...
        this.setFather(newRoot);
    }// leftExternal()

    //
    // les cas droite externe et droite interne sont symetriques aux cas gauche externe et gauche interne
    //

    /**
     * cas de rééquilibrage droite externe
     */
    void rightExternal() {
        System.out.println("rightExternal " + this.getValue());
        Node newRoot = this.getRightChild();
        newRoot.setFather(null);

        if(newRoot.getLeftChild() != null) {
            this.setRightChild(newRoot.getLeftChild());
            newRoot.getLeftChild().setFather(this);
        }
        else {
            this.setRightChild(null);
        }
        newRoot.setLeftChild(this);
        this.setFather(newRoot);
    }// rightExternal()

    /**
     * cas de rééquilibrage droite interne
     */
    void rightInternal() {
        Node newRoot = this.getRightChild().getLeftChild();
        newRoot.setFather(null);

        if(newRoot.getLeftChild() != null) {
            this.getRightChild().setLeftChild(newRoot.getLeftChild());
            newRoot.getRightChild().setFather(this.getRightChild());
        }
        else
            this.getRightChild().setLeftChild(null);

        newRoot.setRightChild(this.getRightChild());
        this.getRightChild().setFather(newRoot);

        newRoot.getRightChild().setHeight(newRoot.getRightChild().calcHeight());

        if(newRoot.getLeftChild() != null) {
            this.setRightChild(newRoot.getLeftChild());
            newRoot.getLeftChild().setFather(this);
        }
        else
            this.setRightChild(null);

        newRoot.setLeftChild(this);
        this.setFather(newRoot);
    }// rightInternal()

    /**
     * return (bool) : renvoie vrai si le sous arbre droit est plus grand faux sinon (donc le sous arbre gauche)
     */
    boolean leftOrRight() {
        // si il n'y pas  de fils gauche ou droit la hauteur est à zéro
        int leftChildHeight =  0;
        int rightChildHeight = 0;

        if(getLeftChild() != null)
            leftChildHeight = getLeftChild().getHeight();
        if(getRightChild() != null)
            rightChildHeight = getRightChild().getHeight();

        return rightChildHeight > leftChildHeight;
    }// leftOrRight()

    /**
     * Vérifie dans quel cas de déséquilibre
     */
    void balance() {
        // si le sous arbre droit est plus grand que le sous arbre gauche
        if(leftOrRight()) {                                               //droite
            if(getRightChild() != null && getRightChild().leftOrRight())  //externe
                rightExternal();
            else
                rightInternal();                                          //interne
        }
        // si le sous arbre gauche est plus grand que le sous arbre droit
        else {                                                            //gauche
            if (getLeftChild() != null && getLeftChild().leftOrRight())   //externe
                leftInternal();
            else
                leftExternal();                                           //interne
        }

    } //balance()

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

    //------------------------------------------------------------------------------------------------------------------
    // getters
    //------------------------------------------------------------------------------------------------------------------
    public int  getValue()      {return value;}
    public Node getLeftChild()  {return leftChild;}
    public Node getRightChild() {return rightChild;}
    public int  getHeight()     {return height;}
    public Node getFather()     {return father;}

    //------------------------------------------------------------------------------------------------------------------
    // setters
    // -----------------------------------------------------------------------------------------------------------------
    public void setHeight(int height)    {this.height = height;}
    public void setLeftChild(Node node)  {this.leftChild = node;}
    public void setRightChild(Node node) {this.rightChild = node;}
    public void setValue(int value)      {this.value = value;}
    public void setFather(Node father)   {this.father = father;}

    // DEBUG
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
