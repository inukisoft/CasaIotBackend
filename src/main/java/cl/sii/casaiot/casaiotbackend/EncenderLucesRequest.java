package cl.sii.casaiot.casaiotbackend;

public class EncenderLucesRequest {
	
	private String jerarquia;
	private boolean encendida;
	public String getJerarquia() {
		return jerarquia;
	}
	public void setJerarquia(String jerarquia) {
		this.jerarquia = jerarquia;
	}
	public boolean isEncendida() {
		return encendida;
	}
	public void setEncendida(boolean encendida) {
		this.encendida = encendida;
	} 
	
}
