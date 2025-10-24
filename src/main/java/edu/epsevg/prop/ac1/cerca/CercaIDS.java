package edu.epsevg.prop.ac1.cerca;


import edu.epsevg.prop.ac1.model.*;
import edu.epsevg.prop.ac1.resultat.ResultatCerca;

import java.util.*;


public class CercaIDS extends Cerca {
    public CercaIDS(boolean usarLNT) { super(usarLNT); }

    @Override
    public void ferCerca(Mapa inicial, ResultatCerca rc) {
        int limit = 0;
        Node solucio;
        boolean trobat = false;

        while (!trobat) {
            Set<Mapa> visitats = new HashSet<>();
            Node arrel = new Node(inicial, null, null, 0, 0);

            solucio = dfsLimitat(arrel, limit, rc, visitats);
            if (solucio != null) {
                rc.setCami(reconstruirCami(solucio));
                trobat = true;
            }
            limit++;
        }
    }
    private Node dfsLimitat(Node node, int limit, ResultatCerca rc, Set<Mapa> visitats) {
        rc.incNodesExplorats();

        if (node.estat.esMeta()) return node;
        if (node.depth == limit) {
            rc.incNodesTallats();
            return null;
        }

        if (usarLNT) {
            if (visitats.contains(node.estat)) {
                rc.incNodesTallats();
                return null;
            }
            visitats.add(node.estat);
        }

        for (Moviment m : node.estat.getAccionsPossibles()) {
            Mapa nouEstat = node.estat.mou(m);
            if (!usarLNT && estaEnBranca(node, nouEstat)) {
                rc.incNodesTallats();
                continue;
            }

            Node fill = new Node(nouEstat, node, m, node.depth + 1, node.g + 1);
            Node sol = dfsLimitat(fill, limit, rc, visitats);
            if (sol != null) return sol;
        }

        return null;
    }

    private boolean estaEnBranca(Node node, Mapa estat) {
        Node actual = node;
        while (actual != null) {
            if (actual.estat.equals(estat)) return true;
            actual = actual.pare;
        }
        return false;
    }
}
