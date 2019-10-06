package lib;

import java.util.*;

public class XPriorityQueueTest
{
public static void main(String args[])
{
    Comparator<Nodo> cmp = (Nodo n1, Nodo n2) -> n1.coste - n2.coste;

    XPriorityQueue<Nodo> q = new XPriorityQueue<>(cmp);
    q.add(new Nodo("tres", 3));
    q.add(new Nodo("dos",  2));
    q.add(new Nodo("uno",  1));
    System.out.println("Cola original");

    for(Nodo n : q)
        System.out.println(n);

    Nodo n1 = new Nodo("tres", 0),
         n2 = q.get(n1);

    q.remove(n2);
    q.add(n1);
    System.out.println("\nCola final");

    for(Nodo n : q)
        System.out.println(n);
}

private static class Nodo implements Comparable<Nodo>
{
    private final String estado;
    private final int coste;

    public Nodo(String estado, int coste)
    {
        this.estado = estado;
        this.coste  = coste;
    }

    @Override public int compareTo(Nodo o)
    {
        return estado.compareTo(o.estado);
    }

    @Override public boolean equals(Object obj)
    {
        if(obj == null || getClass() != obj.getClass())
            return false;
        else
            return estado.equals(((Nodo)obj).estado);
    }

    @Override public int hashCode()
    {
        return estado.hashCode();
    }

    @Override public String toString()
    {
        return estado +": "+ coste;
    }

} // Nodo

} // XPriorityQueueTest
