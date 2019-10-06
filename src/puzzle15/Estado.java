package puzzle15;

/**
 * Estado del 15-puzzle.
 */
public class Estado implements Comparable<Estado>
{
/**
 * Construye un estado a partir de un array de fichas.
 * @param f Lista de las 16 fichas (15 y el hueco) del puzzle.
 */
public Estado(int...f)
{
    throw new UnsupportedOperationException("Falta implementar");
}

/**
 * Devuelve el estado padre.
 * @return estado padre
 */
public Estado getPadre()
{
    throw new UnsupportedOperationException("Falta implementar");
}

/**
 * Devuelve la profundidad del estado.
 * @return profundidad
 */
public int getProfundidad()
{
    throw new UnsupportedOperationException("Falta implementar");
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
    throw new UnsupportedOperationException("Falta implementar");
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
    throw new UnsupportedOperationException("Falta implementar");
}

/**
 * Comprueba si el estado es el objetivo.
 * @return {@code true} si el estado es el objetivo.
 */
public boolean esObjetivo()
{
    throw new UnsupportedOperationException("Falta implementar");
}

} // Estado
