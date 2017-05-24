import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import common.Camiseta;
import common.Equipo;
import common.User;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Logica {

	private PApplet app;
	private ArrayList<Client> clients;
	
	private User user;
	private Camiseta cam;
	private Equipo team;
	

	private int pantalla, x1, x2, y;
	private int cant_clientes;
	private boolean iniciar;

	// --UI Elements
	private PImage fondoUno;
	private PImage fondoDos;
	private PImage logoAdidas;

	private PFont bodyFont;
	private PFont titleFont;
	private float variable;

	// --Variables de usuario
	private String user1; // Para pintar el nombre del usuario en el input de
							// arriba
	private String user2; // Para pintar el nombre del usuario en el input de
							// arriba

	// --Variables de equipo
	private int[] equipos = new int[6]; // Cada equipo tiene un int asociado
										// para identificarlos
	private PImage[] escudosEquiposG = new PImage[6]; // Aqui guardamos las
														// imagenes GRANDES de
														// los escudos de los
														// equipos que vamos a
														// usar en el selector
														// de equipos
	private PImage[] escudosEquiposM = new PImage[6]; // Aqui van los escudos de
														// los equipos que vemos
														// en el editor de
														// camisetas
	private String nombreEquipo;

	private String[] talla = new String[4];

	private boolean girarCamiseta; // Este es un booleano que me activa cambiar
									// las vistas de la camiseta
	private int ladoCamiseta; // Este es el int con el que cambio la vista de la
								// camiseta
	private int precio; // Valor global de la camiseta
	private int number; // Numero que elije el usuario
	private String nickname; // Este es el nombre que pone el usuario en la
								// camiseta

	// OJO CON ESTA VARIABLE VAMOS A RECIBIR LA INFORMACION DESDE ANDROID PARA
	// EJECUTAR TODO ACÁ
	String mensaje;

	public Logica(PApplet app) {

		this.app = app;

		// Iniciamos las variables
		clients = new ArrayList<Client>();

		// UI Elements
		pantalla = 0;
		x1 = 210;
		y = 150;
		iniciar = false;

		// Cargamos todas las imágenes y fuentes que vamos a usar en las
		// interfaces
		fondoUno = app.loadImage("../data/img/mainbg_blue.png");
		fondoDos = app.loadImage("../data/img/bg_selector.png");
		logoAdidas = app.loadImage("../data/img/logo.png");
		// bodyFont = app.loadFont("../data/fonts/bariol-regular.vlw");

		// Los escudos van aqui:
		// Grandes del selector

		escudosEquiposG[0] = app.loadImage("../data/img/lreal.png");
		escudosEquiposG[1] = app.loadImage("../data/img/lmilan.png");
		escudosEquiposG[2] = app.loadImage("../data/img/ljuve.png");
		escudosEquiposG[3] = app.loadImage("../data/img/lchelsea.png");
		escudosEquiposG[4] = app.loadImage("../data/img/lmanchester.png");
		escudosEquiposG[5] = app.loadImage("../data/img/lbayern.png");

		// los pequeños 48 X 48 PX

		escudosEquiposM[0] = app.loadImage("../data/img/real.png");
		escudosEquiposM[1] = app.loadImage("../data/img/milan.png");
		escudosEquiposM[2] = app.loadImage("../data/img/juve.png");
		escudosEquiposM[3] = app.loadImage("../data/img/chelsea.png");
		escudosEquiposM[4] = app.loadImage("../data/img/manchester.png");
		escudosEquiposM[5] = app.loadImage("../data/img/bayern.png");

		// Tallas camisetas es variable
		talla[0] = "S";
		talla[1] = "M";
		talla[2] = "L";
		talla[3] = "XL";

		// Cambiar todos estos valores por los reales de cada usuario
		user1 = "cambiar";
		user2 = "cambiar";
		precio = 114;
		number = 10;
		nombreEquipo = "";

	}
	
	public void setClientes(int cant_clientes){
		
		this.cant_clientes=cant_clientes;
		System.out.println("------------------------->:" + cant_clientes);
	}

	public void ejecutar() {

		// System.out.println("hello logica");

		switch (pantalla) {

		case 0: // Loading -- esperando por conexión de usuarios (visible desde
				// que el servidor esta corriendo)
			app.image(fondoUno, 0, 0);

			// ------------------------ PROGRESS BAR / WAITING EFFECT

			app.noFill();
			app.stroke(255, 100); // Pintar el trazo blanco con opacidad 100
			app.strokeWeight(10); // Grosor del trado a 10px
			app.strokeCap(app.PROJECT); // Borde del trazo cuadrado
			app.arc(app.width / 2, app.height / 2 + 75, 100, 100, 0, 2 * app.PI);

			// Sacamos la matrix
			app.pushMatrix(); //
			app.translate(app.width / 2, app.height / 2 + 75);

			app.rotate(variable); // Lo hacemos rotar
			app.stroke(255, 197, 20); // Pintamos el Trado blanco con 190 de
										// opacidad
			app.arc(0, 0, 100, 100, 0, (float) (2 * app.PI / 5));

			app.popMatrix(); // cerramos la matrix
			variable += 0.08; // Angulo que rotará el arco

			app.textSize(13); // Ponemos el ancho de texto en 20px
			app.text("Waiting for partners...", 490, 500);

			// ------------------------ FIN PROGRESS BAR / WAITING EFFECT

			//System.out.println("Clientes: " + clients.size());
			
			if(cant_clientes==2){
				
				pantalla=1;
				
			}

			break;

		case 1: // Pantalla de bienvenida... Selector de equipos
			app.image(fondoDos, 0, 0);

			// Aqui comienza la magia, el usuario elige su equipo favorito!

			app.image(escudosEquiposG[0], 187, 279);
			app.image(escudosEquiposG[1], 315, 279);
			app.image(escudosEquiposG[2], 435, 279);
			app.image(escudosEquiposG[3], 550, 279);
			app.image(escudosEquiposG[4], 700, 279);
			app.image(escudosEquiposG[5], 833, 279);

			for (int i = 0; i < equipos.length; i++) {

				if (equipos[i] == 0) {
					nombreEquipo = "Real Madrid";
				}
				if (equipos[i] == 1) {
					nombreEquipo = "A.C. Milan";
				}
				if (equipos[i] == 2) {
					nombreEquipo = "Juventus F.C.";
				}
				if (equipos[i] == 3) {
					nombreEquipo = "Chelsea F.C.";
				}
				if (equipos[i] == 4) {
					nombreEquipo = "Manchester United";
				}
				if (equipos[i] == 5) {
					nombreEquipo = "F.C. Bayern Munchen";
				}

			}

			

			// Condicion para pasar de esta pantalla de inicio al editor de
			// camisetas....

			if (cant_clientes==2) {

				// En este pedazo también debemos igualar las variables de los
				// usuarios que han llegado... para traer
				// toda esa info al editor y saber qué camiseta debemos cargar
				// dependiendo del equipo seleccionado
				pantalla = 2;

			}

			break;

		case 2: // Editor de camisetas

			editor();

			// Las camisetas si las cargamos aquí....
			// Traemos el nombre y el int del equipo, lo validamos y las
			// pintamos... y los datos personalizables se rellenan con
			// lo que quiera poner el usuario.

			for (int i = 0; i < equipos.length; i++) {

				if (equipos[i] == 0) {
					nombreEquipo = "Real Madrid";
				}
				if (equipos[i] == 1) {
					nombreEquipo = "A.C. Milan";
				}
				if (equipos[i] == 2) {
					nombreEquipo = "Juventus F.C.";
				}
				if (equipos[i] == 3) {
					nombreEquipo = "Chelsea F.C.";
				}
				if (equipos[i] == 4) {
					nombreEquipo = "Manchester United";
				}
				if (equipos[i] == 5) {
					nombreEquipo = "F.C. Bayern Munchen";
				}

			} // Fin asignacion nombres

			cam = new Camiseta(app, precio, equipos[0], number, nickname, talla[0], null, x1, y);
			cam.pintar();

			// Aqui hacemos el cambio del numero y ojo que el color del texto
			// cambia de acuerdo a la camiseta...

			if (nombreEquipo == "Real Madrid") {
				app.fill(10, 31, 64);
			} else {
				app.fill(255);
			}
			app.textSize(94);
			app.text(number, x1 + 70, y + 160);

			break;

		case 3: // Fin de la experiencia -- envío del codigo promocional...
			break;

		}// Fin pantallas

	}

	public void click() {

		if (pantalla == 1) {

			//if(app.mouseX && app){
				
			}
			// condiciones del click sobre los equipos...
			// Aqui de acuerdo al equipo que seleccionen, se cambia la variable
			// int equipo para que reconozca el nombre del equipo
			// y cargue el escudo correspondiente. ESTA CONDICION APLICA PARA
			// AMBOS USUARIOS, cada uno debe elegir un equipo.

		//}

		if (pantalla == 2) {

			// condiciones del click sobre el editor de camisetas...

		}

	}

	// Como la interfaz es tan compleja, meterlo en el switch a pie arriba se
	// nos vuelve muy confuso, entonces aquí editamos todo y en el
	// switch pantalla se hace visible unicamente.

	public void editor() {

		app.image(fondoUno, 0, 0);
		app.noStroke();
		app.fill(23, 95);
		app.rect(0, 0, 1100, 88);

		app.fill(23, 42, 76);

		// Logo adidas
		app.image(logoAdidas, 500, 20);

		// Input logos equipos
		app.rect(0, 0, 183, 88);
		app.rect(917, 0, 183, 88);

		// Esto es variable--- debe cambiar a medida que cambian los equipos!!
		// Equipo Usuaio 1
		app.image(escudosEquiposM[0], 21, 21);
		app.fill(255);
		app.textSize(16);
		app.text(nombreEquipo, 65, 51);
		// Equipo Usuaio 2
		app.image(escudosEquiposM[5], 1030, 21);

		// Inputs nombres de usuario
		app.fill(255);
		app.textSize(15);
		app.text("user 1", 325, 30);
		app.text("user 2", 713, 30);

		app.textSize(20);
		app.text(user1, 285, 60);
		app.text(user2, 285, 60);

		app.fill(255, 50);
		app.rect(272, 41, 151, 27);
		app.rect(670, 41, 151, 27);

		// ------Pricing

		// Lado usuario 1:
		app.fill(255);
		app.textSize(18);
		app.text("PRICE", 33, 135);
		app.textSize(40);
		app.text("$" + precio, 33, 182);

		// Lado usuario 2:
		app.fill(255);
		app.textSize(18);
		app.text("PRICE", 1020, 135);
		app.textSize(40);
		app.text("$" + precio, 1000, 182);

		// ------Number

		// Lado usuario 1:
		app.fill(255, 197, 0);
		app.rect(0, 209, 135, 32);
		app.fill(255);
		app.textSize(18);
		app.text("NUMBER", 33, 231);
		app.textSize(40);
		app.text(number, 33, 291);

		// Lado usuario 2:
		app.fill(255, 197, 0);
		app.rect(965, 209, 135, 32);
		app.fill(255);
		app.textSize(18);
		app.text("NUMBER", 1000, 231);
		app.textSize(40);
		app.text(number, 1028, 291);

		// ------Size

		// Lado usuario 1:

		app.fill(23, 42, 76);
		app.rect(0, 311, 135, 32);
		app.fill(255);
		app.textSize(18);
		app.text("SIZE", 33, 333);
		app.textSize(40);
		app.text("S", 35, 393); // Aqui traemos el valor real de la talla desde
								// la clase camiseta

		// Lado usuario 2:

		app.fill(23, 42, 76);
		app.rect(965, 311, 135, 32);
		app.fill(255);
		app.textSize(18);
		app.text("SIZE", 1030, 333);
		app.textSize(40);
		app.text("M", 1034, 393); // Aqui traemos el valor real de la talla
									// desde la clase camiseta

		// ------ Vistas camiseta

		// Lado usuario 1:
		app.textSize(18);
		app.text("view", 33, 440);
		app.stroke(255);
		app.strokeWeight(1);
		app.noFill();
		app.rect(33, 465, 67, 67);
		app.rect(100, 465, 67, 67);

		// Lado usuario 2:
		app.textSize(18);
		app.text("view", 1030, 440);
		app.stroke(255);
		app.strokeWeight(1);
		app.noFill();
		app.rect(933, 465, 67, 67);
		app.rect(1000, 465, 67, 67);

		// ------Boton guardar

		// Lado usuario 1:

		app.noStroke();
		app.fill(255, 197, 0);
		app.rect(277, 548, 139, 45);
		app.fill(255);
		app.textSize(22);
		app.text("SAVE", 317, 579);

		// Lado usuario 2:

		app.noStroke();
		app.fill(255, 197, 0);
		app.rect(679, 548, 139, 45);
		app.fill(255);
		app.textSize(22);
		app.text("SAVE", 719, 579);

	}

}
