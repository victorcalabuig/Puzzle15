package lib;

import java.util.*;

/**
 * Conjunto que permite obtener un elemento almacenado a partir de otro,
 * aunque no sea el mismo objeto.
 * @param <T> Tipo de los elementos almacenados en el conjunto.
 */
public class XHashSet<T> implements Iterable<T>
{
private final HashMap<T,T> map = new HashMap<>();

/**
 * Añade un elemento al conjunto. El elemento no debe existir.
 * @param e Elemento que se añade al conjunto.
 */
public void add(T e)
{
    if(map.put(e, e) != null)
        throw new AssertionError();
}

/**
 * Obtiene un elemento idéntico si existe.
 * @param e Elemento con el que se buscará un elemento idéntico.
 * @return Elemento idéntico si existe o null si no existe.
 */
public T get(T e)
{
    return map.get(e);
}

/**
 * Elimina un elemento del conjunto. El elemento debe existir.
 * @param e Elemento a eliminar.
 */
public void remove(T e)
{
    if(map.remove(e) != e)
        throw new AssertionError();
}

/**
 * Indica si el conjunto está vacío.
 * @return True si el conjunto está vacío.
 */
public boolean isEmpty()
{
    return map.isEmpty();
}

/**
 * Obtiene el tamaño de la cola.
 * @return Tamaño de la cola.
 */
public int size()
{
    return map.size();
}

/**
 * Obtiene un iterador sobre el conjunto de elementos.
 * @return iterador
 */
@Override public Iterator<T> iterator()
{
    return map.keySet().iterator();
}

} // XHashSet
