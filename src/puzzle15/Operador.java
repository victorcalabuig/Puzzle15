package puzzle15;

/**
 * Operador de cambio de estado del 15-puzzle.
 */
public interface Operador
{
    Estado run(Estado e);
}
