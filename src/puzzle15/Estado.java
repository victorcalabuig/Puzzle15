package puzzle15;

import java.util.*;

/**
 * Estado del 15-puzzle. Representa la disposicióñn del puzzle en un instante 
 * dado.
 */
public class Estado implements Comparable<Estado>
{
	/**
	 * Matriz 4x4 de enteros (0->15) que almacena el número de cada ficha y su 
	 * posición actual en el puzzle. 
	 */
	private int[][] matriz; 
	
	/**
	 * Array de dos posiciones que almacena las coordenadas x-y (filas-columnas) 
	 * actuales del hueco del puzzle, que se representa en la matriz con el 
	 * número 0.
	 */
    private int[] posHueco;
    
    /**
     * Estado anterior del puzzle, antes del último movimiento.
     */
    private Estado padre;
    
    /**
     * Indica la profundidad, refiriendose esta al número de movimientos totales
     * desde el estado inicial (profundidad 0) hasta el actual. 
     */
    private int profundidad;
   
    
/**
 * Constructor que genera un estado nuevo a partir de un array de fichas.
 * 
 * @param f Lista de las 16 fichas (15 y el hueco) del puzzle.
 */
public Estado(int...f)
{
	//para controlar fichas repetidas
    LinkedList<String> fichas = new LinkedList<>(); 
    for(int i = 0; i < 16; i++) fichas.add(String.valueOf(i));
    
    profundidad = 0;
    posHueco = new int[2];
    matriz = new int[4][4];
    
    //coordenadas para rellenar la matriz de fichas (x->fila; y->columna)
    int x = 0; 
    int y = 0;
    
    // cada ficha de f se intenta extraer de la lista enlazada fichas. 
    // Si se extrae es correcta, sino, es repetida o fuera del rango 0-15. 
    for (int i:f)
    {
        String num = String.valueOf(i);
        if (fichas.indexOf(num) != -1){ //ficha correcta
            fichas.remove(num);
        }else{
            if(fichas.isEmpty())
                throw new UnsupportedOperationException(
                        "Puzzle incorrecto: Más de 15 fichas");
            else
                throw new UnsupportedOperationException(
                        "Puzzle incorrecto: Hay fichas repetidas");
        }
        
        matriz[x][y] = i; //insertar ficha y comprobar si es el hueco (0)
        if (i == 0){
            posHueco[0] = x;
            posHueco[1] = y;
        }
        if (y == 3){
            x++;
            y = 0;
        }else{
            y++;
        }
    }
    if(!fichas.isEmpty())   //Si no se han extraido las 15 fichas (+hueco)
        throw new UnsupportedOperationException("Puzzle incorrecto: "
                + "faltan " + fichas.size() + " fichas.");
}

/**
 * Constructor que genera un nuevo estado a partir de un estado previo/padre.
 * El estado nuevo es idéntico al padre, excepto en la profundidad, que aumenta
 * en 1. Este estado luego será modificado más adelante para representar un 
 * movimiento.
 * 
 * @param e Estado padre.
 */
public Estado(Estado e)
{
    profundidad = e.profundidad + 1;
    padre = e;
    posHueco = new int[2];
    posHueco[0] = e.posHueco[0];
    posHueco[1] = e.posHueco[1];
    
    matriz = new int[4][4];
    for(int x = 0; x<4; x++){
        for (int y=0; y<4; y++){
            matriz[x][y] = e.matriz[x][y];
        }
    } 
}

/**
 * Devuelve el estado padre.
 * 
 * @return estado padre
 */
public Estado getPadre()
{
    return padre;
}

/**
 * Devuelve la profundidad del estado.
 * 
 * @return profundidad
 */
public int getProfundidad()
{
    return profundidad;
}

/**
 * Calcula el código hash de un estado, utilizando para ello la matriz 4x4 
 * que lo representa.
 * 
 * @return Código hash.
 */
@Override 
public int hashCode()
{
    return Arrays.deepHashCode(matriz);
    //si fuera array: return Arrays.hashCode(array);
}

/**
 * Comprueba si dos estados son iguales. Dos estaados son iguales si sus 
 * matrices de fichas son iguales. 
 * 
 * @param obj Estado a comparar.
 * @return {@code true} Si los dos estados son iguales.
 */
@Override 
public boolean equals(Object obj)
{
    if(!(obj instanceof Estado))
        return false;
    Estado estado = (Estado) obj;
    return compareTo(estado) == 0;
}

/**
 * Compara un estado con otro.
 * <p>Aparentemente esta comparación no tiene sentido, pero es necesaria 
 * para utilizar la cola de prioridad.
 * 
 * @param e Estado a comparar
 * @return un número negativo, cero, o positivo si este estadoes menor, 
 * igual, o mayor que el estado indicado.
 */
@Override 
public int compareTo(Estado e)
{
    for (int x=0; x<4; x++){
        for (int y=0; y<4; y++){
            int r = Integer.compare(matriz[x][y], e.matriz[x][y]);
            if(r != 0) return r;
        }
    }
    return 0;
}

/**
 * Comprueba si el estado es el objetivo (solución del puzzle).
 * 
 * @return {@code true} Si el estado es el objetivo final.
 */
public boolean esObjetivo()
{
    int val = 0;
    for (int x=0; x<4; x++){
        for (int y=0; y<4; y++){
            if (this.matriz[x][y] != val){
                return false;
            }
            val++;
        }
    }
    return true;        
}

/**
 * Mueve la ficha 0 (el hueco) a la fila superior, si se puede. 
 * 
 * @return El estado resultante tras el movimiento o null si el movimiento
 * no puede realizarse.
 */
public Estado moverArriba()
{
    if (this.posHueco[0] == 0){ //Si hueco en fila 0, no se puede mover 
        return null;
    }
    else{
        Estado sucesor = new Estado(this);
        int x = posHueco[0];                    
        int y = posHueco[1];
        sucesor.matriz[x][y] = sucesor.matriz[x-1][y];
        sucesor.matriz[x-1][y] = 0;  
        sucesor.posHueco[0] = x-1;
        return sucesor;
    }
}

/**
 * Mueve la ficha 0 (el hueco) a la fila inferior, si se puede. 
 * 
 * @return El estado resultante tras el movimiento o null si el movimiento
 * no puede realizarse.
 */
public Estado moverAbajo()
{
    if (this.posHueco[0] == 3){ //Si hueco en fila 3, no se puede mover 
        return null;
    }
    else{
        Estado sucesor = new Estado(this);        
        int x = posHueco[0];                    
        int y = posHueco[1];
        sucesor.matriz[x][y] = sucesor.matriz[x+1][y];
        sucesor.matriz[x+1][y] = 0;    
        sucesor.posHueco[0] = x+1;
        return sucesor;
    }
}

/**
 * Mueve la ficha 0 (el hueco) a la columna izquierda, si se puede. 
 * 
 * @return El estado resultante tras el movimiento o null si el movimiento
 * no puede realizarse.
 */
public Estado moverIzquierda()
{
    if (this.posHueco[1] == 0){ //Si hueco en columna 0, no se puede mover 
        return null;
    }
    else{
        Estado sucesor = new Estado(this);
        int x = posHueco[0];                    
        int y = posHueco[1];
        sucesor.matriz[x][y] = sucesor.matriz[x][y-1];
        sucesor.matriz[x][y-1] = 0; 
        sucesor.posHueco[1] = y-1;
        return sucesor;
    }
}

/**
 * Mueve la ficha 0 (el hueco) a la columna derecha, si se puede. 
 * 
 * @return El estado resultante tras el movimiento o null si el movimiento
 * no puede realizarse.
 */
public Estado moverDerecha()
{
    if (this.posHueco[1] == 3){     //Si hueco en columna 3, no se puede mover 
        return null;
    }
    else{
        Estado sucesor = new Estado(this);
        int[] posNueva = { posHueco[0], posHueco[1]+1 };
        intercambio(posHueco, posNueva, sucesor.matriz);
        sucesor.posHueco[1] = posHueco[1]+1;
        return sucesor;
    }
}

/**
 * Devuelve la ficha del puzzle indicada por las coordenadas. 
 * 
 * @param x Fila en la que se encuentra la ficha.
 * @param y Columna en la que se encuentra la ficha.
 * @return Número de la ficha. 
 */
public int getValorAt(int x, int y){
    return matriz[x][y];
}

/**
 * Intercambia la posición del hueco (ficha 0) por la posición indicada. 
 * 
 * @param hueco Posición inicial del hueco.
 * @param i Posición a la que se desea mover el hueco.
 * @param matriz Matriz del estado del puzzle.
 */
public static void intercambio(int[] hueco, int[] i, int[][] matriz){
    int x = hueco[0];
    int y = hueco[1];
    matriz[x][y] = matriz[i[0]][i[1]];
    matriz[i[0]][i[1]] = 0;
}

/**
 * Cuenta el número de fichas descolocadas que hay en el puzzle. Cualquier 
 * ficha que esté en una posición de la matriz distinta a la posición en la que
 * estára en la solución final se considera descolocada. Este método lo utiliza 
 * la heurística de fichas descolocadas para saber cómo de cerca está un puzzle
 * de la solución final.
 * 
 * @return Número de fichas descolocadas.
 */
public int getFichasDescolocadas()
{
    int descolocadas = 0;
    int val = 0;
    for(int x = 0; x < 4; x++){
        for(int y = 0; y < 4; y++){
            if(val != this.getValorAt(x,y))
                descolocadas++;
            val++;
        }
    }
    return descolocadas;
}

/**
 * Calcula la suma de las distancias que tiene que recorrer cada ficha del 
 * puzzle para situarse en la posición de la solución final. No se tiene en 
 * cuenta el hueco (ficha 0) para que la heurística sea optimista. Este 
 * método lo utiliza la heurística de distancias Manhattan. 
 * 
 * @return Suma de distancias.
 */
public int getDistanciasManhattan()
{
    int distancia = 0;
    int correcta = 0;
    for(int x = 0; x < 4; x++){
        for(int y = 0; y < 4; y++){
            if(matriz[x][y] != correcta && correcta != 0){
                int[] posActual = {x,y};
                int[] posFicha = encontrar(correcta);
                distancia += getDistancia(posActual, posFicha);
            }
            correcta++;
        }
    }
    return distancia;
}

/**
 * Devuelve la las coordenadas (fila,columna) de la ficha indicada.
 * 
 * @param ficha Ficha a buscar. 
 * @return Array de dos posiciones con la fila y la columna.
 */
private int[] encontrar(int ficha)
{
    int[] pos = new int[2];
    for(int x = 0; x < 4; x++){
        for(int y = 0; y < 4; y++){
            if(matriz[x][y] == ficha){
                pos[0]=x;
                pos[1]=y;
                break;
            }  
        }
    }
    return pos;
}

/**
 * Calcula la distancia entre dos fichas, que se representan por su posición
 * en la matriz del puzzle. 
 * 
 * @param a Coordenadas (fila columna) de la ficha a
 * @param b Coordenadas de la ficha b.
 * @return Distancia entre las dos fichas.
 */
private int getDistancia(int[] a, int[] b){
    int distX = Math.abs(a[0] - b[0]);
    int distY = Math.abs(a[1] - b[1]);
    return distX + distY;
}

/**
 * Genera una representación String de un estado, utilizando su matriz de 
 * fichas (el hueco ya no se representa con un 0, sino con 3 espacios en blanco.
 */
@Override 
public String toString()
{
    StringBuilder sb = new StringBuilder();
    
    for(int x=0; x<4; x++){
        for(int y=0; y<4; y++){
            int ficha = matriz[x][y];
            
            if(ficha == 0)
                sb.append("   ");
            else
                sb.append(String.format("%3d", ficha));
        }
        
        sb.append('\n');
    }
    return sb.toString();
}

} // Estado
