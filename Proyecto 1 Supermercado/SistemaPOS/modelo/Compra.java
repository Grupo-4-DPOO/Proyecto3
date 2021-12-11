package modelo;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Compra implements Serializable{

	private ArrayList<Producto> productos;
	private double valortotal;
	private int idcliente;
	private double puntosacum;
	private ArrayList<Promocion> promos;

	public Compra(int idcliente, ArrayList<Promocion> pPromos) {
		this.valortotal = 0.0;
		this.idcliente =  idcliente;
		this.puntosacum = 0.0;
		this.productos = new ArrayList<Producto>();
		this.promos = pPromos;
	}

	public void agregarProducto(Producto producto) {
		productos.add(producto);
		validarPromocion(producto);

	}
	public boolean eliminarProducto(Producto producto) {
		valortotal-= producto.getPrecio();
		puntosacum-= (producto.getPrecio()/1000.0);
		for (int i= 0; i<productos.size(); i++) {
			if (productos.get(i) == producto) {
				productos.remove(i);
				return true;
			}
		}
		return false;
	}

	public void agregarProductoPeso(Producto producto, float peso) {
		valortotal+= producto.getPrecio()* peso;
		puntosacum+= ((producto.getPrecio()*peso)/1000.0);
		productos.add(producto);
	}
	public boolean eliminarProductoPeso(Producto producto, float peso) {
		valortotal-= producto.getPrecio()* peso;
		puntosacum-= ((producto.getPrecio()*peso)/1000.0);
		for (int i= 0; i<productos.size(); i++) {
			if (productos.get(i) == producto) {
				productos.remove(i);
				return true;
			}
		}
		return false;
	}
	public ArrayList<Producto> getProductos() {
		return productos;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	public double getValortotal() {
		return valortotal;
	}

	public void setValortotal(double valortotal) {
		this.valortotal = valortotal;
	}

	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}

	public double getPuntosacum() {
		return puntosacum;
	}

	public void setPuntosacum(double puntosacum) {
		this.puntosacum = puntosacum;
	}
	public void validarPromocion(Producto producto) {
		for (int k = 0; k<promos.size();k++) {
			Promocion promo = promos.get(k);
			
			String temp;
			try {
				temp = promo.verificarPromo(producto.getIdproducto());

				System.out.println(temp);
				System.out.println(k);
				String[] resp = temp.split(",");
				if(temp.equals("") && k==promos.size()-1) {
					valortotal+= producto.getPrecio();
					puntosacum+= (producto.getPrecio()/1000.0);
					break;
				}
				else if (resp[0].equals("DCTO")) {
					float valor = (producto.getPrecio()*Float.parseFloat(resp[1]))/100;
					valortotal+= producto.getPrecio()-valor;
					puntosacum+= ((producto.getPrecio()-valor)/1000);
					break;	
				}
				else if(resp[0].equals("Multipuntos")) {
					System.out.println("entro");
					valortotal+= producto.getPrecio();
					puntosacum+= (producto.getPrecio()/1000.0)*Integer.parseInt(resp[1]);
					break;
				}
				else if(resp[0].equals("Cantmin")) {
					int contador = 0;
					for(Producto productoi: productos) {
						if(productoi == producto) {
							contador+=1;
						}
						System.out.println(contador);
					}
					if(contador>=Integer.parseInt(resp[1]) && contador<=(Integer.parseInt(resp[1])+Integer.parseInt(resp[3]))) {
						valortotal+= producto.getPrecio();
						puntosacum+= (producto.getPrecio()/1000.0);
						for(int i = 0; i<Integer.parseInt(resp[3]);i++) {
							productos.add(producto);

						}
					}
					else {
						valortotal+= producto.getPrecio();
						puntosacum+= (producto.getPrecio()/1000.0);
					}
					break;
				}
				else if (resp[0].equals("COMBO")) {
					validarCombo();
					break;
				}

			}
			catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void validarCombo() {
		String rta = "";
		int[] temporal = null;
		int[] val = new int[productos.size()];
		for(int i = 0; i<productos.size(); i++) {
			val[i] = productos.get(i).getIdproducto();
		}
		for(Promocion promo: promos) {
			rta = promo.verificarCombo(val);
			if (rta != "") {
				temporal = promo.getIdproducto();
				break;
			}
		}
		if(rta!="") {
			String[] resp = rta.split(",");
			float descuento= Float.parseFloat(resp[1]);
			for(int i = 0;i<temporal.length;i++) {
				int id = temporal[i];
				for (int j = 0; j<productos.size();j++) {
					if(productos.get(j).getIdproducto() == id) {
						float var = (productos.get(j).getPrecio()*100)/(100-descuento);
						valortotal -= var;
						puntosacum -= (var/1000);
					}
				}
			}
		}
	}

}
