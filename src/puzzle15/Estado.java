package puzzle15;

/**
 * Estado del 15-puzzle.
 */
public class Estado implements Comparable<Estado>
{
    //attributos:
   int[][] matriz;  
   Estado padre;
   int profundidad;
   int hashCode;
   int prioridad; //???
    
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
    throw new UnsupportedOperationException("Falta implementar");
}

/**
 * Compara un estado con otro objeto.
 * @param obj Objeto a comparar.
 * @return {@code true} si el estado es igual al objeto.
 */
@Override public boolean equals(Object obj)
{
    //throw new UnsupportedOperationException("Falta implementar");
    Estado estado = (Estado) obj;
    for (int x=0; x<4; x++){
        for (int y=0; y<4; y++){
            if (this.matriz[x][y] != estado.matriz[x][y]){
                return false;
            }
        }
    }
    return true;
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
    //DUDA: que atributo determina si un estado es 'mayor' que otro? prof, prio..?
    //throw new UnsupportedOperationException("Falta implementar");
    if (this.profundidad < e.profundidad){
        return -1;
    }
    else if (this.profundidad == e.profundidad){
        return 0;
    }else{
        return 1;
    }
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

} // Estado
