package clasificador;

import java.util.ArrayList;

public enum Categorias {
//	Politica,Entretenimiento,Deportes,Ciencia/Tecnologia,Cultura,Otros;
	   
	deportes("deportes", "Deporte"),
	futbol("futbol","Deporte"),
	tenis("tenis","Deporte"),
	baloncesto("baloncesto","Deporte"),
	liga("liga","Deporte"),
	sport("sport","Deporte"),
	diariogol("diariogol","Deporte"),
	formula1("formula1","Deporte"),
	ciclismo("ciclismo","Deporte"),
	natacion("natacion","Deporte"),
	atletismo("atletismo","Deporte"),
	reality("reality","Entretenimiento"),
	tele("tele","Entretenimiento"),
	tv("tv","Entretenimiento"),
	videojuegos("videojuegos","Entretenimiento"),
	audiencia("audiencia","Entretenimiento"),
	emisora("emisora","Entretenimiento"),
	programa("programa","Entretenimiento"),
	serie("serie","Entretenimiento"),
	cadena("cadena","Entretenimiento"),
	telespectador("telespectador","Entretenimiento"),
	tve("tve","Entretenimiento"),
	telecinco("telecinco","Entretenimiento"),
	antena3("antena3","Entretenimiento"),
	cope("cope","Entretenimiento"),
	rne("rne","Entretenimiento"),
	radio("radio","Entretenimiento"),
	psoe("psoe","Politica"),
	podemos("podemos","Politica"),
	upyd("upyd","Politica"),
	rajoy("rajoy","Politica"),
	politic("politic","Politica"),
	ministro("ministro","Politica"),
	comunista("comunista","Politica"),
	debate("debate","Politica"),
	pacto("pacto","Politica"),
	eleccion("eleccion","Politica"),
	ejecutivo("ejecutivo","Politica"),
	nacionalista("nacionalista","Politica"),
	parlamento("parlamento","Politica"),
	electoral("electoral","Politica"),
	diputad("diputad","Politica"),
	socialista("socialista","Politica"),
	oposicion("oposicion","Politica"),
	tecnologi("tecnologi","Tecnologia"),
	ciencia("ciencia","Tecnologia"),
	cientific("cientific","Tecnologia"),
	csic("csic","Tecnologia"),
	digital("digital","Tecnologia"),
	electronic("electronic","Tecnologia"),
	investigacion("investigacion","Tecnologia"),
	robot("robot","Tecnologia"),
	ordenador("ordenador","Tecnologia"),
	teoria("teoria","Tecnologia"),
	sonda("sonda","Tecnologia"),
	nasa("nasa","Tecnologia"),
	laboratorio("laboratorio","Tecnologia"),
	innovacion("innovacion","Tecnologia"),
	cultura("cultura","Cultura"),
	manga ("manga","Cultura"),
	comic("comic","Cultura"),
	literatura("literatura","Cultura"),
	cine("cine", "Cultura"),
	musica("musica", "Cultura"),
	cantante("cantante", "Cultura"),
	music("music", "Cultura"),
	actor("actor", "Cultura"),
	actriz("actriz","Cultura"),
	espectaculo("espectaculo", "Cultura"),
	novela("novela", "Cultura"),
	poesia("poesia", "Cultura"),
	poet("poet", "Cultura"),
	teatro("teatro", "Cultura"),
	exposicion("exposicion", "Cultura"),
	danza("danza", "Cultura"),
	artista("artista", "Cultura"),
	festival("festival", "Cultura"),
	concierto("concierto", "Cultura"),
	actuacion("actuacion", "Cultura"),
	escritor("escritor","Cultura");
	
	
	private String categoria;
	private String subcategoria;
	
	Categorias(String subcat ,String cat)
	{
		this.categoria = cat;
		this.subcategoria=subcat;
	}

	public String getCategoria()
	{
		return this.categoria;
	}
	
	public String getSubCategoria()
	{
		return this.subcategoria;
	}

	public static ArrayList<String> getSubCategories() {
		Categorias[] cat = Categorias.values();
		ArrayList<String> subCategorias = new ArrayList<String>();;
		for(int i = 0; i < cat.length; i++)
			subCategorias.add(cat[i].getSubCategoria().toString());
		
		return subCategorias;
			
	}

}
 