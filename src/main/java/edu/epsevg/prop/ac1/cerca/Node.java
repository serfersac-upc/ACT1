package edu.epsevg.prop.ac1.cerca;

import edu.epsevg.prop.ac1.cerca.heuristica.HeuristicaBasica;
import edu.epsevg.prop.ac1.model.Mapa;
import edu.epsevg.prop.ac1.model.Moviment;

/** 
 * Representa un node de l'arbre d'exploració. Li correspon un mapa (estat), i té sempre una referència
 * al node pare des del que s'ha generat. També hi podem desar la profunditat a l'arbre d'exploració i el cost acumulat.

* @author Usuari
 */
public class Node implements Comparable<Node>{
    public final Mapa estat;
    public final Node pare;
    public final Moviment accio;
    public final int depth;
    public final int g; // cost

    public Node(Mapa estat, Node pare, Moviment accio, int depth, int g) {
        this.estat = estat;
        this.pare = pare;
        this.accio = accio;
        this.depth = depth;
        this.g = g;
    }

    @Override
    public int compareTo(Node node) {
        HeuristicaBasica heur = new HeuristicaBasica();
        int f1 = this.g+heur.h(this.estat);
        int f2 = node.g+heur.h(node.estat);
        return Integer.compare(f1,f2);
    }
}
