package common;

import java.io.Serializable;


public class User implements Serializable {

	
	private static final long serialVersionUID = 1L;
	String name, password,img;
	int id;
	int equipo;

	public User(String nombre, int equipo, int id) {
		
		this.name = nombre;
		this.equipo = equipo;
		this.id=id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getEquipo() {
		return equipo;
	}

	public void setEquipo(int equipo) {
		this.equipo = equipo;
	}
	
	
	
}
