package edu.epsevg.prop.ac1.cerca;
 
import edu.epsevg.prop.ac1.model.*;
import edu.epsevg.prop.ac1.resultat.ResultatCerca;

import java.util.*;

public class CercaDFS extends Cerca {
    public CercaDFS(boolean usarLNT) { super(usarLNT); }

    @Override
    public void ferCerca(Mapa inicial, ResultatCerca rc) {
        Node arrel = new Node(inicial, null, null, 0, 0);
        Set<Mapa> visitats = new HashSet<>();

        Node solucio = dfs(arrel, rc, visitats);
        if (solucio != null) {
            rc.setCami(reconstruirCami(solucio));
        }
    }
    
    
    private Node dfs(Node node, ResultatCerca rc, Set<Mapa> visitats) {
        rc.incNodesExplorats();

        if (node.estat.esMeta()) return node;

        if (usarLNT) {
            // Si ja hem visitat aquest estat a menor profunditat, el tallem
            if (visitats.contains(node.estat)) {
                rc.incNodesTallats();
                return null;
            }
            visitats.add(node.estat);
        }

        for (Moviment m : node.estat.getAccionsPossibles()) {
            Mapa nouEstat = node.estat.mou(m);
            Node fill = new Node(nouEstat, node, m, node.depth + 1, node.g + 1);

            if (!usarLNT && estaEnBranca(node, nouEstat)) {
                rc.incNodesTallats();
                continue;
            }

            Node sol = dfs(fill, rc, visitats);
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
