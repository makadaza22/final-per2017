import java.io.File;

import processing.core.PApplet;
import processing.data.XML;

//Esta clase se encarga de controlar el XML de los usuarios
public class XMLusers {
	private PApplet app;

	//Creamos una variable XML
	private XML users;

	public XMLusers(PApplet app) {
		this.app = app;
		
		//Buscamos el path donde este o vayamos a guardar el XML
		File datos = new File("../data/users.xml");
		
		
		//Si el XML no existe lo creamos, si existe solo lo cargamos.
		if (datos.exists()) {
			users = app.loadXML("../data/users.xml");
		} else {
			users = app.parseXML("<users></users>");
			app.saveXML(users, "../data/users.xml");
		}
	}
	
	
	//Metodo para añadir usuarios
	public boolean addUser(String user, int equipo, int id) {
		
		//Booleanos
		boolean exist = false;
		boolean add = false;
		
		//Si el XML tiene datos lo leemos
		if (users.hasChildren()) {
			
			//Leemos los datos con el TAG user
			XML[] children = users.getChildren("user");
			
			for (int i = 0; i < children.length; i++) {
				
				//Si hay una coincidencia entre los nombres se le considera repetido y se le marca como que ya existe
				if (children[i].getString("user").equals(user)) {
					exist = true; // el usuario existe
					break;
				}
			}
		}
		
		//Si no exise lo creamos
		if (!exist) {
			XML child = app.parseXML("<user  user=\"" + user + "\" equipo=\"" + equipo + "\" id=\"" + id + "\"></user>");
			users.addChild(child);
			app.saveXML(users, "../data/users.xml"); //Guardamos
			add = true; //Ponemos en true el booleano que nos deja saber que ha sido añadido
		}

		return add;
	}
	

}
