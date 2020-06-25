package puzzle15;

import java.util.*;
import lib.*;

/**
 * Implementación de varios algoritmos de búsqueda para resolver el 15-puzzle.
 */
public class Main
{

/**
 * Tiempo de ejecución.
 */
private static long milisInicio;

/**
 * Número de nodos (estados del 15-puzzle) que se han explorado. 
 */
private static int  nodosExplorados;

/**
 * Array de funciones lambda que representan los 4 movimientos posibles (arriba,
 * izquierda y derecha.
 */
private static final Operador[] movimientos = {
    e -> e.moverArriba(),
    e -> e.moverAbajo(),
    e -> e.moverDerecha(),
    e -> e.moverIzquierda()
};

/**
 * Heurística que se basa en el número de fichas descolocadas que hay en el puzzle
 * para saber como de cerca está un estaado del estado objetivo.
 */
private static final Heuristica descolocadas = e -> e.getFichasDescolocadas();

/**
 * Heurísitca que se basa en las distancias Manhattan para determinar como de cerca 
 * está un estado del estado objetivo.
 */
private static final Heuristica manhattan = e -> e.getDistanciasManhattan();

/**
 * Puzzles de prueba, ordenados por la profundidad de su solución.
 */
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

/**
 * Busca el estado objetivo realizando una búsqueda primero en anchura.
 * @param inicial Estado inicial del puzzle.
 * @return Devuelve el estado objetivo, si este es alcanzable desde el estado
 * inicial. Sino lo es, devuelve null.
 */
private static Estado busquedaAnchura(Estado inicial)
{
    HashSet<Estado> repetidos = new HashSet<>(); 
    LinkedList<Estado> abiertos = new LinkedList<>();
    abiertos.add(inicial);
    
    nodosExplorados = 0;
    while(!abiertos.isEmpty()){
        Estado actual = abiertos.removeFirst();
        nodosExplorados++;
        if(actual.esObjetivo()){
            return actual;
        }
        
        for(Operador o : movimientos){
            Estado sucesor = o.run(actual);
            if(sucesor != null && repetidos.add(sucesor)){                
                abiertos.add(sucesor);
            }       
        }
    }
    return null;
   
}   

/**
 * Comprueba si un estado ya ha sido explorado. También considera como estados
 * no repetidos aquellos que, aunque ya hayand sido explorados, tengan una 
 * profundidad inferior (esto es importante en las búsquedas en profundidad).
 * 
 * @param repetidos Set de estados repetidos.
 * @param sucesor Estado a comprobar.
 * @return True o false si el estado es repetido o no.
 */
private static boolean esRepetido(XHashSet<Estado> repetidos, Estado sucesor)
{
    Estado r = repetidos.get(sucesor);
    if(r == null){
        repetidos.add(sucesor);
        return false;
    }else if(r.getProfundidad() <= sucesor.getProfundidad()) {
        return true;
    }else{
        repetidos.remove(r);
        repetidos.add(sucesor);
        return false;
    }
}

/**
 * Busca el estado objetivo realizando una búsqueda primero en profundidad.
 * @param inicial Estado inicial del puzzle.
 * @return Devuelve el estado objetivo, si este es alcanzable desde el estado
 * inicial. Sino lo es, devuelve null.
 */
private static Estado busquedaProfundidad(Estado inicial, int limite)
{
    XHashSet<Estado> repetidos = new XHashSet<>();
    Stack<Estado> abiertos = new Stack<>();
    abiertos.add(inicial);
    while(!abiertos.isEmpty()){
        Estado actual = abiertos.pop();
        nodosExplorados++;
        if(actual.esObjetivo())
            return actual;
        
        if(actual.getProfundidad() < limite){
            for(Operador o : movimientos){
                Estado sucesor = o.run(actual);                                
            
                if(sucesor != null && !esRepetido(repetidos, sucesor)){
                    abiertos.add(sucesor);  
                }
            }
        }    
    }  
    return null;    
}

/**
 * Busca el estado objetivo realizando una búsqueda de profundidad iterativa.
 * @param inicial Estado inicial del puzzle.
 * @return Estado objetivo, si este es alcanzable desde el estado
 * inicial. Sino lo es, devuelve null.
 */
private static Estado busquedaProfundidadIterativa(Estado inicial, int limite)
{
    int profundidad = 0;
    while(profundidad < limite){
       Estado solucion = busquedaProfundidad(inicial, profundidad);
       if(solucion != null)
           return solucion;
       else
           profundidad++;
    }
    return null;
}

/**
 * Busca el estado objetivo realizando una búsqueda con la heurística Descolocadas.
 * @param inicial Estado inicial del puzzle.
 * @return Estado objetivo, si este es alcanzable desde el estado
 * inicial. Sino lo es, devuelve null.
 */
private static Estado busquedaHeuristicaDescolocadas(Estado inicial)
{
    return busquedaHeuristica(inicial, descolocadas);
}

/**
 * Busca el estado objetivo realizando una búsqueda con la heurística Manhattan.
 * @param inicial Estado inicial del puzzle.
 * @return Estado objetivo, si este es alcanzable desde el estado
 * inicial. Sino lo es, devuelve null.
 */
private static Estado busquedaHeuristicaManhattan(Estado inicial)
{
    return busquedaHeuristica(inicial, manhattan);
}

/**
 * Este método es un método genérico para buscar el estado objetivo a través de 
 * cualquier heurística. Lo utilizan los métodos de heurísitcas Descolocadaas y 
 * Manhattan.
 * @param inicial Estado inicial del puzzle.
 * @param f Heurística utilizada en la búsqueda.
 * @return Estado objetivo, si este es alcanzable desde el estado
 * inicial. Sino lo es, devuelve null.
 */
private static Estado busquedaHeuristica(Estado inicial, Heuristica f)
{
    XHashSet<Estado> repetidos = new XHashSet<>();
    XPriorityQueue<Estado> abiertos = new XPriorityQueue<>(f);
    abiertos.add(inicial);
    nodosExplorados = 0;
    
    while(!abiertos.isEmpty()){
        Estado actual = abiertos.poll();
        nodosExplorados++;
        repetidos.add(actual);

        if(actual.esObjetivo()){
            return actual;
        }
        
        for(Operador o : movimientos){
            Estado sucesor = o.run(actual);
            if(sucesor != null){
                tratarRepetidos(f, abiertos, repetidos, sucesor);
            }
        }
    }

    return null;
}

/**
 * Realiza el tratamiento de nodos repetidos para la búsqueda heurística, 
 * considerando el coste real hasta el nodo n (profundidad del estado) y el
 * coste estimado hasta el objetivo (depende de la heurísitca utilizada). 
 * De este modo, aunque un estado ya haya sido explorado, si el nuevo coste 
 * real hasta el objetivo es inferior, se tratará este nodo como nuevo. Los nodos
 * nuevos se añaden a abiertos.
 * 
 * @param f Heurística utilizada.
 * @param abiertos Set de estados abiertos (generados pero no explorados aún).
 * @param repetidos Set de estados explorados.
 * @param sucesor Estado a tratar.
 */
private static void tratarRepetidos(
        Heuristica f, XPriorityQueue<Estado> abiertos,
        XHashSet<Estado> repetidos, Estado sucesor)
{
    Estado a = abiertos.get(sucesor),
           r = repetidos.get(sucesor);

    if(a != null){ //si ya está en abiertos
        if(f.compare(sucesor, a) < 0){ 
            abiertos.remove(a);
            abiertos.add(sucesor);
        }
    }else if(r != null){ //si está en repetidos
        if(f.compare(sucesor, r) < 0){ 
        	//reabrir estado
            abiertos.add(sucesor); 
            repetidos.remove(r);
        }
    }else{ //no está en abiertos ni en repetidos
        abiertos.add(sucesor);
    }
}

/**
 * Imprime la solución al puzzle, junto con datos sobre ejecución como nodos 
 * explorados o tiempo. 
 * @param algoritmo Nombre del algoritmo de búsqueda utilizado.
 * @param e Estado solución (objetivo).
 */
private static void printSolucion(String algoritmo, Estado e)
{
    long milisTotal = System.currentTimeMillis() - milisInicio;
    System.out.println("\n"+ algoritmo);
    printTraza(e);

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
    System.out.println(); 
}

/**
 * 
 * @param e
 */
private static void printTraza(Estado e){
    if(e != null)
    {
        if(e.getPadre() == null){ //se trata del nodo inicial
            System.out.println("------------");   
            System.out.println(e);
        }
        else {
            printTraza(e.getPadre());
                }
        System.out.println("------------");
        System.out.println(e);
    }
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
