package modelo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Promocion implements Serializable{
	private int[] idproducto;
	private String nombre;
	private String tipopromo;
	private float descuento;
	private	String fechai;
	private	String fechaf;
	private int cantmin;
	private int cantreg;
	private int multipoints;



	public Promocion(int[] idproducto, String nombre, String tipopromo, float descuento, String fechai, String fechaf, int cantmin, int cantreg, int multipoints) {
		this.idproducto = idproducto;
		this.nombre = nombre;
		this.tipopromo = tipopromo;
		this.descuento = descuento;
		this.fechai = fechai;
		this.fechaf = fechaf;
		this.cantmin = cantmin;
		this.cantreg = cantreg;
		this.multipoints = multipoints;
	}
	public String verificarPromo(int pidproducto) throws ParseException {
		String rta= "";
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy"); 
		Date fechain = format.parse(fechai);
		Date fechafin = format.parse(fechaf);
		Date fecha = new Date();
		if (fechain.compareTo(fecha)<0 && fechafin.compareTo(fecha)>0) {
			System.out.println(tipopromo);
			for(int i = 0; i<idproducto.length;i++) {
				if(idproducto[i]==pidproducto) {
					if(tipopromo.equals("DCTO")) {
						rta = "DCTO," + descuento;
					}
					else if(tipopromo.equals("PROM")) {
						rta = "Cantmin," + cantmin + ",Cantregaladas," + cantreg;
					}
					else if(tipopromo.equals("COMB")){
						rta = "VerCombo";
					}
					else if (tipopromo.equals("Multipuntos")) {
						rta= "Multipuntos," + multipoints;
					}
				}
			}
		}
		return rta;
	}
	public String verificarCombo(int[] idproductos) {
		String rta = "";
		int cantprod = 0;
		for (int i = 0; i<idproductos.length;i++) {
			for(int j = 0; j<idproducto.length; i++) {
				if(idproductos[i]==idproducto[j]) {
					cantprod++;
				}
			}
		}
		if(cantprod == idproducto.length) {
			rta= "ComboValido," + descuento;
		}
		return rta;
	}
	public int[] getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(int[] idproducto) {
		this.idproducto = idproducto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipopromo() {
		return tipopromo;
	}
	public void setTipopromo(String tipopromo) {
		this.tipopromo = tipopromo;
	}
	public float getDescuento() {
		return descuento;
	}
	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}
	public String getFechai() {
		return fechai;
	}
	public void setFechai(String fechai) {
		this.fechai = fechai;
	}
	public String getFechaf() {
		return fechaf;
	}
	public void setFechaf(String fechaf) {
		this.fechaf = fechaf;
	}
	public int getCantmin() {
		return cantmin;
	}
	public void setCantmin(int cantmin) {
		this.cantmin = cantmin;
	}
	public int getCantreg() {
		return cantreg;
	}
	public void setCantreg(int cantreg) {
		this.cantreg = cantreg;
	}
	public int getMultipoints() {
		return multipoints;
	}
	public void setMultipoints(int multipoints) {
		this.multipoints = multipoints;
	}

}
