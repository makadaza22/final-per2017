package common;

import processing.core.PApplet;
import processing.core.PImage;

public class Camiseta {
	
	// Llamamos al core de processing
	private PApplet app;
	
	private int cambiar;
	private int precio;
	private int equipo;
	private int numero;
	private String nickname;
	private String propietario;
	private int x, y;
	

	//--Variables de camiseta
//	private String [] talla = new String[4]; //Estas son las unicas 4 tallas disponibles S, M, L, XL
	private PImage [] camisetas = new PImage[12];  //Aqui guardamos las imagenes de todas las camisetas
	private PImage [] thumbs = new PImage[12]; // Aqui guardamos las miniaturas para cambiar la vista de la camiseta en el editor

	private String talla;
	
	public Camiseta(PApplet app, int precio, int equipo, int numero, String nickname, String talla, String propietario, int x, int y ){
		
		this.app=app;
		this.talla=talla;
		this.precio=precio;
		this.equipo=equipo;
		this.nickname=nickname;
		this.propietario=propietario;
		this.x=x;
		this.y=y;
		this.numero=numero;
		
		
		
		//Cargamos las imágenes de las camisetas para poder pintarlas
		
		for(int i=0; i<camisetas.length; i++){
			
			camisetas[i] = app.loadImage("../data/img/" + i + ".png");
		}
		
	}
	
	public void pintar(){
		
		app.image(camisetas[cambiar], x, y);
		//app.text(nickname, 100, 100);
		
	}
	
	public void cambiar(){
		
		
	}
	
	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}
	

	public int getCambiar() {
		return cambiar;
	}

	public void setCambiar(int cambiar) {
		this.cambiar = cambiar;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getEquipo() {
		return equipo;
	}

	public void setEquipo(int equipo) {
		this.equipo = equipo;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getPropietario() {
		return propietario;
	}

	public void setPropietario(String propietario) {
		this.propietario = propietario;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

		

}
