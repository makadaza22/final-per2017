import java.io.File;

import processing.core.PApplet;
import processing.data.XML;

//Esta clase se encarga de controlar el XML de los usuarios
public class XMLcamiseta {
	private PApplet app;

	//Creamos una variable XML
	private XML camisetas;

	public XMLcamiseta(PApplet app) {
		this.app = app;
		
		//Buscamos el path donde este o vayamos a guardar el XML
		File datos = new File("../data/camisetas.xml");
		
		
		//Si el XML no existe lo creamos, si existe solo lo cargamos.
		if (datos.exists()) {
			camisetas = app.loadXML("../data/camisetas.xml");
		} else {
			camisetas = app.parseXML("<camisetas></camisetas>");
			app.saveXML(camisetas, "../data/camisetas.xml");
		}
	}
	
	
	//Metodo para anadir camisetas
	public boolean addCamiseta(PApplet app, int precio, int equipo, int number, String nickname, String[] talla, String propietario) {
		
		//Booleanos
		boolean exist = false;
		boolean add = false;
		
		//Si el XML tiene datos lo leemos
		if (camisetas.hasChildren()) {
			
			//Leemos los datos con el TAG camiseta
			XML[] children = camisetas.getChildren("user");
			
			for (int i = 0; i < children.length; i++) {
				
				//Si hay una coincidencia entre los nombres se le considera repetido y se le marca como que ya existe
				if (children[i].getString("camiseta").equals(camisetas)) {
					exist = true; // el usuario existe
					break;
				}
			}
		}
		
		//Si no exise lo creamos
		if (!exist) {
			XML child = app.parseXML("<camiseta  precio=\"" + precio + "\" equipo=\"" + equipo +  "\" numero=\"" + number +  "\" nickname=\"" + nickname + "\"  "
					+ "talla=\"" + talla + "\" propietario=\"" + propietario + "\"></camiseta>");
			camisetas.addChild(child);
			app.saveXML(camisetas, "../data/camisetas.xml"); //Guardamos
			add = true; //Ponemos en true el booleano que nos deja saber que ha sido añadido
		}

		return add;
	}
	

}
