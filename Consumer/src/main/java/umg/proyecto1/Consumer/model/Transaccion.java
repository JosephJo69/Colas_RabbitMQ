package umg.proyecto1.Consumer.model;

public class Transaccion {

	private String idTransaccion;
	private String Nombre;
	private String Carnet;
	private String Correo;
    public double monto;
    public String moneda;
    public String cuentaOrigen;
    public String bancoDestino;
    public Detalle detalle;
	public String getIdTransaccion() {
		return idTransaccion;
	}
	public void setIdTransaccion(String idTransaccion) {
		this.idTransaccion = idTransaccion;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getCuentaOrigen() {
		return cuentaOrigen;
	}
	public void setCuentaOrigen(String cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}
	public String getBancoDestino() {
		return bancoDestino;
	}
	public void setBancoDestino(String bancoDestino) {
		this.bancoDestino = bancoDestino;
	}
	public Detalle getDetalle() {
		return detalle;
	}
	public void setDetalle(Detalle detalle) {
		this.detalle = detalle;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getCarnet() {
		return Carnet;
	}
	public void setCarnet(String carnet) {
		Carnet = carnet;
	}
	public void setCorreo(String correo) {
		Correo = correo;
	}
	public String getCorreo() {
		return Correo;
	}
}
