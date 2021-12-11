package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class SistemaPOS implements Serializable{

	private Cajero cajero;
	private Caja caja;
	private ArrayList<Cliente> cliente;
	private ArrayList<Factura> factura;
	private ArrayList<Producto> producto;
	private ArrayList<Promocion> promos;

	public SistemaPOS() {
		this.caja = new Caja();
		this.cliente = new ArrayList<Cliente>();
		this.factura = new ArrayList<Factura>();
		this.producto = new ArrayList<Producto>();
		this.promos = new ArrayList<Promocion>();
		this.cajero = new Cajero(this);
	}

	public Cliente encontrarCliente(int cedula) {
		for (int i=0; i<cliente.size(); i++) {
			if (cliente.get(i).getCedula() == cedula) {
				return cliente.get(i);
			}
		}
		System.out.println("No se encontro el cliente, registrese, se usara el cliente invitado");
		return new Cliente(cedula, null, cedula, null, null, null, null);
	}

	public void registrarVenta(double valortotal) {
		caja.ingresarVenta(valortotal);		
	}
	public void agregarFactura(Factura factura) {
		this.factura.add(factura);
	}
	public Producto encontrarProductoAVender(int idproducto) {
		Producto rta = null;
		for (int i=0; i<producto.size(); i++) {
			if (producto.get(i).getIdproducto() == idproducto && producto.get(i).getDisponibles()>0) {
				rta =  producto.get(i);
			}
		}
		return rta;
	}
	public Producto encontrarProductoAElminar(int idproducto) {
		Producto rta = null;
		for (int i=0; i<producto.size(); i++) {
			if (producto.get(i).getIdproducto() == idproducto) {
				rta =  producto.get(i);
			}
		}
		return rta;

	}
	public void agregarClienteNuevo(int cedula, String nombre, int telefono, String email, String sexo, String estadocivil, String situalab) {
		boolean presente = false;
		System.out.println("entro");
		if(presente== false) {
			Cliente usuario = new Cliente(cedula, nombre, telefono, email, sexo, estadocivil, situalab);
			this.cliente.add(usuario);
		}
		else{
			System.out.println("El cliente ya se encuentra registrado");
		}
	}

	public void agregarProducto(Producto producto) {
		int idproducto = producto.getIdproducto();
		Producto temp = encontrarProductoAVender(idproducto);
		if (temp == null) {
			this.producto.add(producto);
		}
		else {
			temp.setDisponibles(temp.getDisponibles()+producto.getDisponibles());
		}
	}
	@SuppressWarnings("resource")
	public void cargarpromo(String archivoPromos) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader (archivoPromos));
		String linea = br.readLine();   // Leer la linea con el primer ProductoMenu en el archivo   
		// Un ProductoMenu tiene la forma: nombre;precioBase
		while (linea != null) // Cuando se llegue al final del archivo, linea tendra el valor null
		{
			// Separar los valores que estan en la linea por el caracter ';'
			String[] partes = linea.split(";");

			String[] idproducto = partes[0].split(",");
			int[] ids= new int[idproducto.length];
			for(int i = 0; i<idproducto.length;i++) {
				String var = idproducto[i];
				ids[i] = Integer.parseInt(var.replace("\"", ""));
				
			}

			String nombre = partes[1];
			String tipopromo = partes[2];
			String fechai = partes[7];
			String fechaf = partes[8];
			float descuento = Float.parseFloat(partes[3]);
			int cantmin = Integer.parseInt(partes[4]);
			int cantreg = Integer.parseInt(partes[5]);
			int multipoints = Integer.parseInt(partes[6]);

			Promocion promocion = new Promocion(ids, nombre, tipopromo, descuento, fechai, fechaf, cantmin, cantreg, multipoints);
			promos.add(promocion);
			linea = br.readLine(); // leer la proxima linea en el archivo con un nuevo ProductoMenu 

		}
		br.close();
	}


	//acciones de venta, llamada a cajero quien se encargara
	public void iniciarCompra(int cedula) {
		cajero.iniciarCompra(cedula, promos);

	}
	public String finalizarCompra() {
		return cajero.finalizarCompra();
	}
	public String finalizarCompraPuntos() {
		return cajero.finalizarcomprapuntos();
	}
	public String agregarProductoCompra(int idproducto) {
		return cajero.agregarProductoCompra(idproducto);
	}

	public String agregarProductosPeso(int idproducto, float peso) {
		return cajero.agregarProductosPeso(idproducto, peso);
	}

	public String eliminarProductosPeso(int idproducto, float peso) {
		return cajero.eliminarProductosPeso(idproducto, peso);
	}
	public String elimiarProductoCompra(int idproducto) {
		return cajero.elimiarProductoCompra(idproducto);
	}






	public Cajero getCajero() {
		return cajero;
	}

	public void setCajero(Cajero cajero) {
		this.cajero = cajero;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public ArrayList<Cliente> getCliente() {
		return cliente;
	}

	public void setCliente(ArrayList<Cliente> cliente) {
		this.cliente = cliente;
	}

	public ArrayList<Factura> getFactura() {
		return factura;
	}

	public void setFactura(ArrayList<Factura> factura) {
		this.factura = factura;
	}

	public ArrayList<Producto> getProducto() {
		return producto;
	}

	public void setProducto(ArrayList<Producto> producto) {
		this.producto = producto;
	}

}
