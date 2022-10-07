package ohmycode.pruebatecnica.excepciones;

public class DatosError<T>{

	private ErroresDeServicio error;
	private String mensaje;
	private T objeto;
	
	public DatosError(ErroresDeServicio error, String mensaje) {
		this.error = error;
		this.mensaje = mensaje;
	}
	
	public DatosError(ErroresDeServicio error, String mensaje, T objeto) {
		this.error = error;
		this.mensaje = mensaje;
		this.objeto = objeto;
	}
	public ErroresDeServicio getError() {
		return error;
	}
	public void setError(ErroresDeServicio error) {
		this.error = error;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public T getObjeto() {
		return objeto;
	}
	public void setObjeto(T objeto) {
		this.objeto = objeto;
	}
	@Override
	public String toString() {
		return String.format("DatosError [error=%s, mensaje=%s, objeto=%s]", error, mensaje, objeto);
	}
}
