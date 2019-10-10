package puzzle15;

import java.util.Arrays;

/**
 * Estado del 15-puzzle.
 */
public class Estado implements Comparable<Estado>
{
    //attributos:
   private int[][] matriz;  
   private Estado padre;
   private int profundidad;
   private int hashCode;
   private int prioridad; //???
    
/**
 * Construye un estado a partir de un array de fichas.
 * @param f Lista de las 16 fichas (15 y el hueco) del puzzle.
 */
public Estado(int...f)
{
    //throw new UnsupportedOperationException("Falta implementar");
    matriz = new int[4][4];
    int x = 0;
    int y = 0;
    for (int i:f){
        matriz[x][y] = i;
        if (y == 3){
            x++;
            y = 0;
        }else{
            y++;
        }
    }
}

/**
 * Devuelve el estado padre.
 * @return estado padre
 */
public Estado getPadre()
{
    //throw new UnsupportedOperationException("Falta implementar");
    return padre;
}

/**
 * Devuelve la profundidad del estado.
 * @return profundidad
 */
public int getProfundidad()
{
    //throw new UnsupportedOperationException("Falta implementar");
    return profundidad;
}

/**
 * Calcula el código hash.
 * @return Código hash.
 */
@Override public int hashCode()
{
    return Arrays.deepHashCode(matriz);
}

/**
 * Compara un estado con otro objeto.
 * @param obj Objeto a comparar.
 * @return {@code true} si el estado es igual al objeto.
 */
@Override public boolean equals(Object obj)
{
    if(!(obj instanceof Estado))
        return false;
    
    //throw new UnsupportedOperationException("Falta implementar");
    Estado estado = (Estado) obj;
    return compareTo(estado) == 0;
}

/**
 * Compara un estado con otro.
 * <p>Aparentemente esta comparación no tiene sentido,
 * pero es necesaria para utilizar la cola de prioridad.
 * @param e estado
 * @return un número negativo, cero, o positivo si este estado
 *         es menor, igual, o mayour que el estado indicado.
 */
@Override public int compareTo(Estado e)
{
    for (int x=0; x<4; x++){
        for (int y=0; y<4; y++){
            int r = Integer.compare(matriz[x][y], e.matriz[x][y]);
            
            if(r != 0)
                return r;
        }
    }
    
    return 0;
}

/**
 * Comprueba si el estado es el objetivo.
 * @return {@code true} si el estado es el objetivo.
 */
public boolean esObjetivo()
{
    //throw new UnsupportedOperationException("Falta implementar");
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

public Estado moverArriba()
{
    return null;
}

public Estado moverAbajo()
{
    return null;
}

} // Estado
