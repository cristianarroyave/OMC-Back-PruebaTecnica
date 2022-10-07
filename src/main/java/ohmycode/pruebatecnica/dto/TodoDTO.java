package ohmycode.pruebatecnica.dto;

public class TodoDTO {

	private String titulo;
	
	private boolean completado;
	
	private Integer usuario;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return String.format("TodoDTO [titulo=%s, completado=%s, usuario=%s]", titulo, completado, usuario);
	}
}
