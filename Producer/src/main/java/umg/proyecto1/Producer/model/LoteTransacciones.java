package umg.proyecto1.Producer.model;
import java.util.List;

public class LoteTransacciones {
    public String loteId;
    public String fechaGeneracion;
    public List<Transaccion> transacciones;
    // Getters y Setters
	public String getLoteId() {
		return loteId;
	}
	public void setLoteId(String loteId) {
		this.loteId = loteId;
	}
	public String getFechaGeneracion() {
		return fechaGeneracion;
	}
	public void setFechaGeneracion(String fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}
	public List<Transaccion> getTransacciones() {
		return transacciones;
	}
	public void setTransacciones(List<Transaccion> transacciones) {
		this.transacciones = transacciones;
	}
    
    
    
}
