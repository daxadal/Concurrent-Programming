package multibuffer;

public interface MultiBuffer {
	/**
	* Almacena (como ultimos) varios en el MultiBuffer. Si no hay
	* hueco para todos, el proceso se bloquea hasta que pueda introducir el resto
	*/
	public void almacenar(Producto[] producto);
	/**
	* Extrae los n primeros productos disponible. Si no hay suficientes productos el
	* proceso se bloquea hasta que se puedan extraer todos
	*/
	public Producto[] extraer(int n);
}
