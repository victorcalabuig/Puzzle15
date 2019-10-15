package puzzle15;

import java.util.HashMap;

/**
 * Implementación de varios algoritmos para resolver el 15-puzzle.
 */
public class Main
{
private static long milisInicio;
private static int  nodosExplorados;

private static final Estado puzzles[] = {
    // Los comentario indican el indice y la profundidad de la solución.
    new Estado(1,2,3,7, 8,4,5,6, 9,10,0,11, 12,13,14,15), //  0: 10
    new Estado(1,2,3,7, 8,4,5,6, 12,9,10,11, 13,14,15,0), //  1: 12
    new Estado(1,2,3,7, 8,4,5,6, 0,10,11,15, 12,13,9,14), //  2: 18
    new Estado(1,2,3,7, 8,4,5,6, 10,11,15,14, 9,12,13,0), //  3: 20
    new Estado(1,2,3,7, 8,4,5,6, 13,12,0,9, 14,15,11,10), //  4: 22
    new Estado(1,2,3,7, 8,4,5,6, 11,15,0,14, 10,12,13,9), //  5: 28
    new Estado(1,2,3,7, 8,4,5,6, 11,15,14,13, 10,0,9,12), //  6: 30
    new Estado(1,2,3,7, 8,4,5,6, 0,14,13,12, 15,11,10,9), //  7: 32
    new Estado(1,2,3,7, 8,4,5,6, 15,14,9,13, 11,10,12,0), //  8: 36
    new Estado(1,2,3,7, 15,8,4,5, 14,9,6,13, 11,0,10,12), //  9: 44
    new Estado(1,2,3,4, 5,6,7,8, 9,10,11,12, 13,15,14,0), // 10: 44
    new Estado(0,2,1,3, 4,5,6,7, 8,9,10,11, 12,13,14,15)  // 11: imposible
};

private static final Operador[] movimientos = {
    e -> e.moverArriba(),
    e -> e.moverAbajo(),
    e -> e.moverDerecha(),
    e -> e.moverIzquierda()
};

private static Estado busquedaAnchura(Estado inicial)
{
    
    Estado actual = inicial;
    Estado ultimo = inicial;
    //HashMap<T,T> cerrados;
    while (!actual.esObjetivo()){
        //cerrados.add(actual)
        for(Operador m : movimientos)
        {
            Estado sucesor = m.run(actual);
            if(cerrados.get(sucesor) == null){  //si no es repetido, encolamos al final
                ultimo.encolar(sucesor);
                ultimo = sucesor;
                actual = actual.getNext();
            }
        }   
    }
    return actual;
    
    
    
    
    
    
    
    //throw new UnsupportedOperationException("Falta implementar");
    
}

private static Estado busquedaProfundidad(Estado inicial, int limite)
{
    throw new UnsupportedOperationException("Falta implementar");
}

private static Estado busquedaProfundidadIterativa(Estado inicial, int limite)
{
    throw new UnsupportedOperationException("Falta implementar");
}

private static Estado busquedaHeuristicaDescolocadas(Estado inicial)
{
    throw new UnsupportedOperationException("Falta implementar");
}

private static Estado busquedaHeuristicaManhattan(Estado inicial)
{
    throw new UnsupportedOperationException("Falta implementar");
}

private static void printSolucion(String algoritmo, Estado e)
{
    long milisTotal = System.currentTimeMillis() - milisInicio;
    System.out.println("\n"+ algoritmo);

    if(e == null)
    {
        System.out.println("No se ha encontrado la solución.");
    }
    else
    {
        System.out.println(algoritmo);
        System.out.println("     Profundidad: "+ e.getProfundidad());
    }

    System.out.println("Nodos explorados: "+ nodosExplorados);
    System.out.println("    Milisegundos: "+ milisTotal);
}

public static void main(String args[])
{
    String algoritmos[] = {
        "Búsqueda en anchura",
        "Búsqueda en profundidad",
        "Búsqueda en profundidad iterativa",
        "Búsqueda con heurística de fichas descolocadas",
        "Búsqueda con heurística de distancias Manhattan"
    };

    if(args.length != 2)
    {
        System.out.println("\n PARÁMETROS: algoritmo puzzle\n");

        for(int i = 0; i < algoritmos.length; i++)
            System.out.println("algoritmo "+ i +": "+ algoritmos[i]);

        System.out.println("     puzzle: del 0 al 9\n");
        System.exit(0);
    }

    int iAlg    = Integer.parseInt(args[0]),
        iPuzzle = Integer.parseInt(args[1]);

    if(iPuzzle < 0 || iPuzzle > 11)
        throw new RuntimeException("Puzzle erróneo: "+ iPuzzle);

    Estado inicial = puzzles[iPuzzle],
           result  = null;

    milisInicio = System.currentTimeMillis();

    switch(iAlg)
    {
        case 0:
            result = busquedaAnchura(inicial);
            break;
        case 1:
            result = busquedaProfundidad(inicial, 30);
            break;
        case 2:
            result = busquedaProfundidadIterativa(inicial, 30);
            break;
        case 3:
            result = busquedaHeuristicaDescolocadas(inicial);
            break;
        case 4:
            result = busquedaHeuristicaManhattan(inicial);
            break;
        default:
            throw new RuntimeException("Algoritmo erróneo: "+ iAlg);
    }

    printSolucion(algoritmos[iAlg], result);
}

} // Main
