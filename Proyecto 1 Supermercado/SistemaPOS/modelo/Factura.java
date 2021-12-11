package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("serial")
public class Factura implements Serializable{

	private int cantidadart;
	private double valortotal;
	private double puntosacum;
	private int idcliente;
	private ArrayList<Producto> productos;
	private String resumen;
	private String informacion;
	
	public Factura(int cantidadart, double valortotal, double puntosacum, int idcliente, ArrayList<Producto> productos, double puntosusados) {
		this.cantidadart = cantidadart;
		this.valortotal = valortotal;
		this.puntosacum = puntosacum;
		this.idcliente = idcliente;
		this.productos = productos;
		if(puntosusados==0) {
			this.informacion = "";
		}
		else {
			this.informacion = darInformacion(puntosusados);
		}
		resumen = generarFactura();
		
	}
	
	public String generarFactura() {
		Date fecha = new Date();
		String rta = "Fecha : " + fecha.toString() + "\n IdCliente: " + Integer.toString(idcliente);
		for(Producto producto:productos) {
			rta += "\n Id: " + Integer.toString(producto.getIdproducto()) + " Nombre: " + producto.getNombre() + " Precio unitario:" 
		+ Float.toString(producto.getPrecio() )+ "Precio por unidad: " + Float.toString(producto.getPreciounidad()) + "/" + producto.getUnidadmed();	
		}
		//Se aproximaron los puntos al valor mas cercano pero estos puntos no se pierden
		rta+= "\n El valor total de la factura es : " + Double.toString(valortotal) + "\n Puntos Acumulados: " + Math.round(puntosacum) + informacion;
		return rta;
	}
	

	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public int getCantidadart() {
		return cantidadart;
	}

	public void setCantidadart(int cantidadart) {
		this.cantidadart = cantidadart;
	}

	public double getValortotal() {
		return valortotal;
	}

	public void setValortotal(double valortotal) {
		this.valortotal = valortotal;
	}

	public double getPuntosacum() {
		return puntosacum;
	}

	public void setPuntosacum(double puntosacum) {
		this.puntosacum = puntosacum;
	}

	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}
	public String darInformacion(double puntosusados) {
		
		return "\n El monto total de esta transaccion es: " + this.valortotal + " que se pagaran de la siguiente forma: " + Math.round(puntosusados) + " puntos y " + Math.round((this.valortotal-(puntosusados*15))) + " a pagar por el usuario \n Se reciben en caja: " + Math.round((this.valortotal-(puntosusados*15))) ;
	}
	
	

}
