package umg.proyecto1.Consumer.model;


public class Detalle {
	public String nombreBeneficiario;
    public String tipoTransferencia;
    public String descripcion;
    public Referencias referencias;
    // Getters y Setters
	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}
	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}
	public String getTipoTransferencia() {
		return tipoTransferencia;
	}
	public void setTipoTransferencia(String tipoTransferencia) {
		this.tipoTransferencia = tipoTransferencia;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Referencias getReferencias() {
		return referencias;
	}
	public void setReferencias(Referencias referencias) {
		this.referencias = referencias;
	}
    
    
}
