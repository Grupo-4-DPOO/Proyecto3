package modelo;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Cajero implements Serializable{

	private SistemaPOS sistema;
	private Compra compra;
	private Cliente cliente;

	
	public Cajero (SistemaPOS sistema) {
		this.cliente = null;
		this.compra = null;
		this.sistema = sistema;
	}
	public void iniciarCompra(int cedula, ArrayList<Promocion> promos) {
		compra = new Compra(cedula, promos);
		cliente = sistema.encontrarCliente(cedula);		
	}
	public String finalizarCompra() {
	Factura factura = new Factura(compra.getProductos().size(), compra.getValortotal(), compra.getPuntosacum(), compra.getIdcliente(), compra.getProductos(), 0.0);
	cliente.agregarFactura(factura);
	cliente.SumarPuntos(compra.getPuntosacum());
	sistema.agregarFactura(factura);
	sistema.registrarVenta(compra.getValortotal());
	this.cliente = null;
	this.compra = null;
	return factura.getResumen();
	}
	public String agregarProductoCompra(int idproducto) {
		String rta= " fto ";
		Producto elemento = sistema.encontrarProductoAVender(idproducto);
		if (elemento != null) {
			compra.agregarProducto(elemento);
			elemento.eliminardisponibles();
			rta= "producto agregado" +elemento.toString() +"fto"+ elemento.getRutaimagen();
		}
		return rta;
	}
	public String finalizarcomprapuntos() {
		double puntosusados = 0.0;
		if(cliente.getPuntos()>0 && compra.getValortotal()>0) {
			double puntosclie = cliente.getPuntos()*15;
			if(puntosclie <= compra.getValortotal()) {
				puntosusados = cliente.getPuntos();
				cliente.setPuntos(0.0);
			}
			else {
				double puntosausar = compra.getValortotal()/15;
				puntosusados = puntosausar;
				cliente.setPuntos(cliente.getPuntos()-puntosusados);		
			}
		}
		Factura factura = new Factura(compra.getProductos().size(), compra.getValortotal(), compra.getPuntosacum(), compra.getIdcliente(), compra.getProductos(), puntosusados);
		cliente.agregarFactura(factura);
		cliente.SumarPuntos(compra.getPuntosacum());
		sistema.agregarFactura(factura);
		sistema.registrarVenta(compra.getValortotal());
		this.cliente = null;
		this.compra = null;
		return factura.getResumen();
	}
	
	public String agregarProductosPeso(int idproducto, float peso) {
		String rta= " fto ";
		Producto elemento = sistema.encontrarProductoAVender(idproducto);
		if (elemento != null) {
			compra.agregarProductoPeso(elemento, peso);
			elemento.eliminardisponibles();
			rta= "producto agregado"+elemento.toString()+ "fto"+ elemento.getRutaimagen();
		}
		return rta;
	}
	
	public String eliminarProductosPeso(int idproducto, float peso) {
		String reta= " fto ";
		Producto elemento = sistema.encontrarProductoAVender(idproducto);
		if (elemento != null) {
			boolean rta = compra.eliminarProductoPeso(elemento, peso);
			if (rta == true) {
				elemento.eliminardisponibles();
				reta= "producto eliminado"+elemento.toString()+ "fto"+ elemento.getRutaimagen();
			}
		}
		return reta;
	}
	public String elimiarProductoCompra(int idproducto) {
		String reta= " fto ";
		Producto elemento = sistema. encontrarProductoAElminar(idproducto);
		if (elemento != null) {
			boolean rta = compra.eliminarProducto(elemento);
			if (rta == true) {
			elemento.agregardisponibles();
			reta= "producto eliminado" + elemento.toString()+"fto"+ elemento.getRutaimagen();
			}
		}
		return reta;
	}
}
