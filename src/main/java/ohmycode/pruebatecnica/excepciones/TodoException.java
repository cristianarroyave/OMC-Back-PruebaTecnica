package ohmycode.pruebatecnica.excepciones;

public class TodoException extends Exception{
	
	private DatosError<?> datos;

	public TodoException(DatosError<?> datos) {
		this.datos = datos;
	}

	public TodoException(DatosError<?> datos, Throwable cause) {
		super(cause);
		this.datos = datos;
	}

	public DatosError<?> getDatos() {
		return datos;
	}	
}
