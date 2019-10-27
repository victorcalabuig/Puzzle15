package puzzle15;


import java.util.Arrays;

/**
 * Estado del 15-puzzle.
 */
public class Estado implements Comparable<Estado>
{
    //attributos:
   private int[][] matriz;  
   private int[] posHueco;
   private Estado padre;
   private int profundidad;
   
    
/**
 * Construye un estado a partir de un array de fichas.
 * @param f Lista de las 16 fichas (15 y el hueco) del puzzle.
 */
public Estado(int...f)
{
    /*
    Aquí hay que comprobar que hay 16 fichas, y que no hay fichas repetidas. 
    */
    //throw new UnsupportedOperationException("Falta implementar");
    profundidad = 0;
    posHueco = new int[2];
    matriz = new int[4][4];
    int x = 0;
    int y = 0;
    for (int i:f){
        matriz[x][y] = i;
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
}

public Estado(Estado e)
{
    profundidad = e.profundidad + 1;
    int x = e.posHueco[0];
    int y = e.posHueco[1];
    posHueco = new int[2];
    posHueco[0] = x;
    posHueco[1] = y;
    
    padre = e;
    matriz = new int[4][4];
    for(x = 0; x<4; x++){
        for (y=0; y<4; y++){
            matriz[x][y] = e.matriz[x][y];
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
    //si fuera array: return Arrays.hashCode(array);
}

/**
 * Compara un estado con otro objeto.
 * @param obj Objeto a comparar.
 * @return {@code true} si el estado es igual al objeto.
 */
@Override public boolean equals(Object obj)
{
    /*if(obj == this){
        return true;
    */
    
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
    /*
    matriz[i][j] == i*4 +j;
    */
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
    /*
    Estado s = new Estado(this);
    mover el hueco en s
    return s;
    */
    if (this.posHueco[0] == 0){     //Si hueco en fila 0, no se puede mover 
        return null;
    }
    else{
        Estado sucesor = new Estado(this);
        int x = posHueco[0];                    
        int y = posHueco[1];
        sucesor.matriz[x][y] = sucesor.matriz[x-1][y];
        sucesor.matriz[x-1][y] = 0;     //movimiento del hueco
        sucesor.posHueco[0] = x-1;
        return sucesor;
    }
}

public Estado moverAbajo()
{
    if (this.posHueco[0] == 3){     //Si hueco en fila 3, no se puede mover 
        return null;
    }
    else{
        Estado sucesor = new Estado(this);        
        int x = posHueco[0];                    
        int y = posHueco[1];
        sucesor.matriz[x][y] = sucesor.matriz[x+1][y];
        sucesor.matriz[x+1][y] = 0;     //movimiento del hueco
        sucesor.posHueco[0] = x+1;
    
        return sucesor;
    }
}

public Estado moverIzquierda()
{
    if (this.posHueco[1] == 0){     //Si hueco en columna 0, no se puede mover 
        return null;
    }
    else{
        Estado sucesor = new Estado(this);
        int x = posHueco[0];                    
        int y = posHueco[1];
        sucesor.matriz[x][y] = sucesor.matriz[x][y-1];
        sucesor.matriz[x][y-1] = 0;     //movimiento del hueco
        sucesor.posHueco[1] = y-1;
        return sucesor;
    }
}

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





public int getValorAt(int x, int y){
    return matriz[x][y];
}



public static void intercambio(int[] hueco, int[] i, int[][] matriz){
    int x = hueco[0];
    int y = hueco[1];
    matriz[x][y] = matriz[i[0]][i[1]];
    matriz[i[0]][i[1]] = 0;
}

public int getFichasDescolocadas()
{
    return 0;
}

public int getDistanciasManhattan()
{
    return 0;
}

@Override public String toString()
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
    
    /*
    Si lo tuvieramos como array:
    for (int i = 0; i < 16; i++)
    {
        int ficha = array[i];
        
        if(ficha == 0)
            sb.append("   ");
        else
            sb.append(String.format("%3d", ficha);
        if(i%4 == 3)
            sb.append('\n');
    }
    */
}

} // Estado
