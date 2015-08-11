package Gui;

import generarRed.Main;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;

import trendingTopics.RecuperarInfo;
import trendingTopics.TestingGdfs;
import trendingTopics.mongoDBHandler;
import busqueda.GraficoPorHoras;
import busqueda.PopularSearch;
import busqueda.PopularTweet;
import busqueda.Search;
import busqueda.SearchPopular;
import clasificador.Clasificador;

import com.sun.mail.imap.protocol.Status;
import com.toedter.calendar.JDateChooser;




































import java.awt.Label;

import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JRadioButton;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;






public class Gui implements ActionListener{

	private JFrame frame;
	private JComboBox<String> cb_lugares; 
	private JComboBox<String> cb_lugarCP;
	private JComboBox<String> cb_tredingTopics;
	private JComboBox<String> cb_ttGraficas;
	
	//JEditorPane para el contenido enlazable.
	private JEditorPane trendingTopics;
	private JEditorPane tweets;
	private JEditorPane tt_populares;
	
	private JRadioButton rdbtnFollowers;
	private JRadioButton rdbtnRT;
	private JRadioButton rdbtnFavoritos; 
	
	
	private JEditorPane class_Deportes;
	private JEditorPane class_Entretenimiento;
	private JEditorPane class_Cultura;
	private JEditorPane class_Politica;
	private JEditorPane class_Tecnologia;
	private JEditorPane class_Otros;
	
	private ButtonGroup group;
	private JTextArea clasificador;
	private JButton btnBuscar;
	private JButton btnBuscarTweets;
	private JButton b_GenerarDatos;
	private JButton btn_populares;
	private JButton btn_muestraGrafo;
	private JButton btnGetPopulars;
	private JButton b_generarGrafica;
	private JDateChooser fecha_tt;
	private JDateChooser fecha_tweets;
	private JDateChooser fecha_populares;
	private JDateChooser dc_fechaCP;
	private String dia;
	private String mes;
	private String año;
	private JTextField searchTF;
	

	private String getMes(String mes) {
		switch(mes)
		{
		    case "jan" : return "1";
		    case "ene" : return "1";
			case "feb" : return "2";
			case "mar" : return "3";
			case "apr" : return "4";
			case "abr" : return "4";
			case "may" : return "5";
			case "jun" : return "6";
			case "jul" : return "7";
			case "aug" : return "8";
			case "ago" : return "8";
			case "sep" : return "9";
			case "oct" : return "10";
			case "nov" : return "11";
			case "dic" : return "12";
			case "dec" : return "12";
		}
		return "";
	
	}
	
	private void inicializarFecha(String newValue) {
		String fechas[] = newValue.split("-");
		if (fechas[0].startsWith("0"))
			this.dia=(String) fechas[0].subSequence(1, 2);
		else
		this.dia = fechas[0];
		
		this.mes = getMes(fechas[1]);
		this.año = fechas[2];
		
	}
	
	
	

	/**
	 * Launch the application.
	 */
	public void iniciarGui() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();	
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void crearEventoJEditorPane(JEditorPane ep){
		ep.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
		ep.addHyperlinkListener(new HyperlinkListener() {
		    public void hyperlinkUpdate(HyperlinkEvent e) {
		        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		        	if(Desktop.isDesktopSupported()) {
		        	    try {
							Desktop.getDesktop().browse(e.getURL().toURI());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (URISyntaxException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		        	}

		        }
		    }

			
		});
		ep.setEditable(false);
	}
	
	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}
	/**
	 * 
	 * @return cb_lugares JComboBox inicializado con las ciudades que reconoce Twitter.
	 */
	// Se crean dos inicializar, ya que daba error al inicializar los dos combobox con el mismo metodo
	private JComboBox<String> inicializarLugares()
	{
		
		JComboBox<String> cb_lugaresAux = new JComboBox<String>();
		cb_lugaresAux.addItem("España");
		cb_lugaresAux.addItem("Barcelona");
		cb_lugaresAux.addItem("Bilbao");
		cb_lugaresAux.addItem("Las Palmas");
		cb_lugaresAux.addItem("Madrid");
		cb_lugaresAux.addItem("Malaga");
		cb_lugaresAux.addItem("Murcia");
		cb_lugaresAux.addItem("Palma");
		cb_lugaresAux.addItem("Sevilla");
		cb_lugaresAux.addItem("Valencia");
		cb_lugaresAux.addItem("Zaragoza");
	
		
		return cb_lugaresAux;
	}
	
	private JComboBox<String> inicializarLugares2()
	{
		
		JComboBox<String> cb_lugaresAux2 = new JComboBox<String>();
		cb_lugaresAux2.addItem("España");
		cb_lugaresAux2.addItem("Barcelona");
		cb_lugaresAux2.addItem("Bilbao");
		cb_lugaresAux2.addItem("Las Palmas");
		cb_lugaresAux2.addItem("Madrid");
		cb_lugaresAux2.addItem("Malaga");
		cb_lugaresAux2.addItem("Murcia");
		cb_lugaresAux2.addItem("Palma");
		cb_lugaresAux2.addItem("Sevilla");
		cb_lugaresAux2.addItem("Valencia");
		cb_lugaresAux2.addItem("Zaragoza");
	
		
		return cb_lugaresAux2;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 956, 905);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		
		// ==================================Panel Trending Topics Declaraciones ===================================
		JPanel tt_panel = new JPanel();
		tabbedPane.addTab("Trending Topics", null, tt_panel, null);
		tt_panel.setLayout(null);
		
		cb_lugares = inicializarLugares();
		cb_lugares.setBounds(135, 31, 118, 20);
		
		tt_panel.add(cb_lugares);
		
		
		JLabel lblLugar = new JLabel("Lugar:");
		lblLugar.setHorizontalAlignment(SwingConstants.CENTER);
		lblLugar.setBounds(43, 34, 52, 14);
		lblLugar.setVerticalAlignment(SwingConstants.TOP);
		tt_panel.add(lblLugar);
		
		JLabel lblNewLabel = new JLabel("Fecha:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(313, 34, 46, 14);
		tt_panel.add(lblNewLabel);
		
		
		
		trendingTopics = new JEditorPane();
		
		
		trendingTopics.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
		
		trendingTopics.addHyperlinkListener(new HyperlinkListener() {
			    public void hyperlinkUpdate(HyperlinkEvent e) {
			        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			        	if(Desktop.isDesktopSupported()) {
			        	    try {
								Desktop.getDesktop().browse(e.getURL().toURI());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (URISyntaxException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			        	}

			        }
			    }

				
			});
		
		
		
		
		
		
		
		
		
		
		
		trendingTopics.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(trendingTopics);
		scrollPane.setLocation(30, 123);
		scrollPane.setSize(843, 297);
		
		trendingTopics.setBounds(23, 225, 613, 197);
		tt_panel.add(scrollPane);
	
		this.btnBuscar = new JButton("Buscar");

		btnBuscar.setBounds(578, 30, 89, 23);
		tt_panel.add(btnBuscar);
		
		fecha_tt = new JDateChooser();
		fecha_tt.getDateEditor().setDate(new Date());
		fecha_tt.setBounds(395, 31, 118, 20);
		tt_panel.add(fecha_tt);
		
		
		inicializarFecha(((JTextField)fecha_tt.getDateEditor().getUiComponent()).getText().toString());
		this.trendingTopics.setText(RecuperarInfo.obtenerListaTT(RecuperarInfo.obtenerCursor(dia,mes,año,"Spain")));
		
		
		//=========================Panel Tweets Declaraciones =============================================== 
		JPanel Tweet_panel = new JPanel();
		tabbedPane.addTab("Tweets", null, Tweet_panel, null);
		Tweet_panel.setLayout(null);
		
		//Contiene los tweets buscados por la palabra clave
		tweets = new JEditorPane();
		
		tweets.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
			tweets.addHyperlinkListener(new HyperlinkListener() {
			    public void hyperlinkUpdate(HyperlinkEvent e) {
			        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			        	if(Desktop.isDesktopSupported()) {
			        	    try {
								Desktop.getDesktop().browse(e.getURL().toURI());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (URISyntaxException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			        	}

			        }
			    }

				
			});
			
		
		
		
		tweets.setEditable(false);
		JScrollPane tweetsSP = new JScrollPane(tweets);
		tweetsSP.setLocation(30, 104);
		tweetsSP.setSize(868, 316);
		
		tweets.setBounds(23, 225, 613, 197);
		Tweet_panel.add(tweetsSP);
		
		JLabel lblClave = new JLabel("Clave:");
		lblClave.setHorizontalAlignment(SwingConstants.CENTER);
		lblClave.setBounds(78, 50, 46, 14);
		Tweet_panel.add(lblClave);
		
		searchTF = new JTextField();
		searchTF.setBounds(159, 47, 86, 20);
		Tweet_panel.add(searchTF);
		searchTF.setColumns(10);
		
		btnBuscarTweets = new JButton("Buscar");
		btnBuscarTweets.setBounds(619, 50, 89, 23);
		Tweet_panel.add(btnBuscarTweets);
		
		fecha_tweets = new JDateChooser();
		fecha_tweets.getDateEditor().setDate(new Date());
		fecha_tweets.setBounds(385, 50, 118, 20);
		((JTextField)fecha_tweets.getDateEditor().getUiComponent()).setText("");
		Tweet_panel.add(fecha_tweets);
		
		//==========================================Clasificador Panel ===============================
		
		JPanel classPanel = new JPanel();
		tabbedPane.addTab("Clasificador", null, classPanel, null);
		classPanel.setLayout(null);
		
		JLabel lblLugarCP = new JLabel("Lugar: ");
		lblLugarCP.setBounds(46, 47, 46, 14);
		classPanel.add(lblLugarCP);
		
		cb_lugarCP = inicializarLugares2();
		cb_lugarCP.setBounds(104, 44, 118, 20);
		classPanel.add(cb_lugarCP);
		
		dc_fechaCP = new JDateChooser();
		dc_fechaCP.getDateEditor().setDate(new Date());
		dc_fechaCP.setBounds(406, 47, 118, 20);
		((JTextField)dc_fechaCP.getDateEditor().getUiComponent()).setText("");
		classPanel.add(dc_fechaCP);
		
		Label label_fechaCP = new Label("Fecha: ");
		label_fechaCP.setBounds(327, 47, 62, 22);
		classPanel.add(label_fechaCP);
		
		b_GenerarDatos = new JButton("Clasificar");
		b_GenerarDatos.setBounds(552, 47, 105, 22);
		classPanel.add(b_GenerarDatos);
		
		
		/*====================================Clasificador ==============================*/
		
		
		
		this.class_Cultura = new JEditorPane();
		crearEventoJEditorPane(this.class_Cultura);
		
		this.class_Deportes = new JEditorPane();
		crearEventoJEditorPane(class_Deportes);
		
		this.class_Entretenimiento = new JEditorPane();
		crearEventoJEditorPane(class_Entretenimiento);
		
		this.class_Politica = new JEditorPane();
		crearEventoJEditorPane(class_Politica);
		
		this.class_Tecnologia = new JEditorPane();
		crearEventoJEditorPane(class_Tecnologia);
		
		this.class_Otros = new JEditorPane();
		crearEventoJEditorPane(class_Otros);
		
		
		class_Cultura.setText("");
		class_Deportes.setText("");
		class_Entretenimiento.setText("");
		class_Politica.setText("");
		class_Tecnologia.setText("");
		class_Otros.setText("");
		
		JScrollPane js_Cultura = new JScrollPane(class_Cultura);
		JScrollPane js_Deportes = new JScrollPane(class_Deportes);
		JScrollPane js_Entretenimiento = new JScrollPane(class_Entretenimiento);
		JScrollPane js_Politica = new JScrollPane(class_Politica);
		JScrollPane js_Tecnologia  = new JScrollPane(class_Tecnologia);
		JScrollPane js_Otros = new JScrollPane(class_Otros);
		
		js_Cultura.setLocation(30, 104);
		js_Cultura.setBounds(20, 130, 130, 300);
		
		js_Deportes.setLocation(180, 104);
		js_Deportes.setBounds(180, 130, 130, 300);
		
		js_Entretenimiento.setLocation(330, 104);
		js_Entretenimiento.setBounds(330, 130, 130, 300);
		
		js_Politica.setLocation(480, 104);
		js_Politica.setBounds(480, 130, 130, 300);
		
		js_Tecnologia.setLocation(630, 104);
		js_Tecnologia.setBounds(630, 130, 130, 300);
		
		js_Otros.setLocation(780, 104);
		js_Otros.setBounds(780, 130, 130, 300);
		
		classPanel.add(js_Cultura);
		classPanel.add(js_Deportes);
		classPanel.add(js_Entretenimiento);
		classPanel.add(js_Politica);
		classPanel.add(js_Tecnologia);
		classPanel.add(js_Otros);
		
		JLabel lblCultura = new JLabel("Cultura");
		lblCultura.setHorizontalAlignment(SwingConstants.CENTER);
		lblCultura.setBounds(20, 95, 121, 14);
		classPanel.add(lblCultura);
		
		JLabel lblDeportes = new JLabel("Deportes");
		lblDeportes.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeportes.setBounds(189, 95, 121, 14);
		classPanel.add(lblDeportes);
		
		JLabel lblEntretenimiento = new JLabel("Entretenimiento");
		lblEntretenimiento.setHorizontalAlignment(SwingConstants.CENTER);
		lblEntretenimiento.setBounds(339, 95, 121, 14);
		classPanel.add(lblEntretenimiento);
		
		JLabel lblPolitica = new JLabel("Politica");
		lblPolitica.setHorizontalAlignment(SwingConstants.CENTER);
		lblPolitica.setBounds(489, 95, 121, 14);
		classPanel.add(lblPolitica);
		
		JLabel lblTecnologia = new JLabel("Ciencia/Tecnologia");
		lblTecnologia.setHorizontalAlignment(SwingConstants.CENTER);
		lblTecnologia.setBounds(630, 95, 121, 14);
		classPanel.add(lblTecnologia);
		
		JLabel lblOtros = new JLabel("Otros");
		lblOtros.setHorizontalAlignment(SwingConstants.CENTER);
		lblOtros.setBounds(780, 95, 121, 14);
		classPanel.add(lblOtros);
		
		cb_tredingTopics = new JComboBox<String>();
		cb_tredingTopics.setBounds(181, 158, 130, 20);
		
		
		JLabel lblClavePopular = new JLabel("Trending Topic: ");
		lblClavePopular.setHorizontalAlignment(SwingConstants.CENTER);
		lblClavePopular.setBounds(59, 161, 109, 14);
		
		
		btn_populares = new JButton("Obtener TTs");
		btn_populares.setBounds(380, 36, 121, 23);
		
		
		
		
		
		this.tt_populares = new JEditorPane();
		tt_populares.setText("");
		
		tt_populares.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
			tt_populares.addHyperlinkListener(new HyperlinkListener() {
			    public void hyperlinkUpdate(HyperlinkEvent e) {
			        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			        	if(Desktop.isDesktopSupported()) {
			        	    try {
								Desktop.getDesktop().browse(e.getURL().toURI());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (URISyntaxException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			        	}

			        }
			    }

				
			});
			tt_populares.setEditable(false);
		
		
		
		
		JScrollPane js_populares = new JScrollPane(tt_populares);
		js_populares.setLocation(780, 104);
		js_populares.setBounds(53, 205, 816, 334);
		
		
		

		 btn_muestraGrafo = new JButton("Relaci\u00F3nTrending Topics");
		btn_muestraGrafo.setBounds(586, 470, 174, 23);
		classPanel.add(btn_muestraGrafo);
		
		JLabel lblNewLabel_1 = new JLabel("M\u00C1S INFORMACI\u00D3N");
		lblNewLabel_1.setBounds(400, 445, 169, 14);
		classPanel.add(lblNewLabel_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 441, 935, 2);
		classPanel.add(separator);
		
	    cb_ttGraficas = new JComboBox<String>();
		cb_ttGraficas.setBounds(104, 474, 118, 22);
		classPanel.add(cb_ttGraficas);
		
		 b_generarGrafica = new JButton("Generar Gr\u00E1fica");
		b_generarGrafica.setBounds(255, 470, 143, 23);
		classPanel.add(b_generarGrafica);
		
		
		rdbtnFavoritos = new JRadioButton("Favoritos");
		rdbtnFavoritos.setBounds(376, 157, 109, 23);
		
		
		rdbtnRT = new JRadioButton("ReTweets", true);
		rdbtnRT.setBounds(500, 157, 109, 23);
		//rdbtnRT.doClick();
		
		
		rdbtnFollowers = new JRadioButton("Followers");
		rdbtnFollowers.setBounds(622, 157, 109, 23);
		
		
		group = new ButtonGroup();
	    group.add(rdbtnFavoritos);
	    group.add(rdbtnRT);
	    group.add(rdbtnFollowers);
		
		fecha_populares = new JDateChooser();
		fecha_populares.setBounds(220, 36, 109, 20);
		
		JPanel popularPanel = new JPanel();
		popularPanel.setLayout(null);
		
		popularPanel.add(this.btn_populares);
		popularPanel.add(lblClavePopular);
		popularPanel.add(js_populares);
		popularPanel.add(cb_tredingTopics);
		popularPanel.add(rdbtnRT);
		popularPanel.add(rdbtnFollowers);
		popularPanel.add(rdbtnFavoritos);
		popularPanel.add(fecha_populares);
	
		
		
		tabbedPane.addTab("Populares", null, popularPanel, null);
		
		JLabel lblFecha = new JLabel("Fecha: ");
		lblFecha.setHorizontalAlignment(SwingConstants.CENTER);
		lblFecha.setBounds(87, 40, 81, 14);
		popularPanel.add(lblFecha);
		
		btnGetPopulars = new JButton("Populares");
		btnGetPopulars.setBounds(737, 157, 109, 23);
		popularPanel.add(btnGetPopulars);
		
		
		
		
		
		//Añadimos listeners a los botones de ambos Panel 
		

		btnBuscar.addActionListener(this);
		btnBuscarTweets.addActionListener(this);
		b_GenerarDatos.addActionListener(this);
		btn_populares.addActionListener(this);
		btn_muestraGrafo.addActionListener(this);
		btnGetPopulars.addActionListener(this);
		b_generarGrafica.addActionListener(this);
	
		
		
		
	//========================      EVENTOS     ====================================

		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()== btnBuscar) {
			
			String lugar=(String) cb_lugares.getSelectedItem();
			if(lugar.equalsIgnoreCase("Sevilla"))
				lugar = "Seville";
			else if (lugar.equalsIgnoreCase("España"))
				lugar= "Spain";
			
			inicializarFecha(((JTextField)fecha_tt.getDateEditor().getUiComponent()).getText().toString());
			
			this.trendingTopics.setText(RecuperarInfo.obtenerListaTT(RecuperarInfo.obtenerCursor(dia,mes,año,lugar)));

		}
		else if (e.getSource() == btnBuscarTweets){
			String fecha_tweets2 = "";
			if (!((JTextField)fecha_tweets.getDateEditor().getUiComponent()).getText().equalsIgnoreCase(""))
			{
					fecha_tweets2 = ((JTextField)fecha_tweets.getDateEditor().getUiComponent()).getText();
					String tratarFecha[] = fecha_tweets2.split("-");
					fecha_tweets2 = tratarFecha[2]+ "-" + getMes(tratarFecha[1]) + "-" + tratarFecha[0];
					
					
			}
					
			String busqueda = searchTF.getText();
			if(busqueda.equals(""))
				JOptionPane.showMessageDialog(null, "Introduzca la palabra clave");
			else{
				tweets.setText("");
				Search tweet_search = new Search(busqueda,100,fecha_tweets2);
				ArrayList<String> tweets_result = tweet_search.busqueda();
				Iterator<String> it = tweets_result.iterator();
				int i = 1;
				String linea = "";
				while(it.hasNext())
				{
					linea = linea + "<br>" + i + ". " +  ContenidoEnlazable.tratarTexto(it.next());
					
					i++;
				}
				
				tweets.setText(linea);
				
			}
			
			
		}
		else if (e.getSource() == b_GenerarDatos){
			
			String fecha_class = "";
			if (!((JTextField)dc_fechaCP.getDateEditor().getUiComponent()).getText().equalsIgnoreCase(""))
			{
					fecha_class = ((JTextField)dc_fechaCP.getDateEditor().getUiComponent()).getText();
					String tratarFecha[] = fecha_class.split("-");
					fecha_class = tratarFecha[2]+ "-" + getMes(tratarFecha[1]) + "-" + tratarFecha[0];
					
					
					String lugar=(String) cb_lugarCP.getSelectedItem();
					if(lugar.equalsIgnoreCase("Sevilla"))
						lugar = "Seville";
					else if (lugar.equalsIgnoreCase("España"))
						lugar= "Spain";
					
					inicializarFecha(((JTextField)dc_fechaCP.getDateEditor().getUiComponent()).getText().toString());
					
					try {
						
				      //clasificador.setText(RecuperarInfo.obtenerLinks(dia,mes,año, lugar));
						String clasificacion = Clasificador.Clasificar(dia,mes,año, lugar);
						String []clases = clasificacion.split("\n");
						String []clasi_aux;
						
						String deporte = "";
						String cultura = "";
						String entretenimiento = "";
						String tecnologia = "";
						String politica = "";
						String otros = "";
						for (int i = 0; i < clases.length; i++){
							clasi_aux = clases[i].split(":");
							
							//this.cb_tredingTopics.addItem(clasi_aux[0]);
							
							if(clasi_aux[1].contains("Deporte"))
								deporte = deporte + ContenidoEnlazable.tratarTT(clasi_aux[0]) + "<br>";
							
							if (clasi_aux[1].contains("Tecnologia"))
								tecnologia = tecnologia +  ContenidoEnlazable.tratarTT(clasi_aux[0])+ "<br>";
							
							if (clasi_aux[1].contains("Cultura"))
								cultura = cultura + ContenidoEnlazable.tratarTT(clasi_aux[0])+ "<br>";
							
							if (clasi_aux[1].contains("Entretenimiento"))
								entretenimiento = entretenimiento + ContenidoEnlazable.tratarTT(clasi_aux[0])+ "<br>";
							
							if (clasi_aux[1].contains("Politica"))
								politica = politica + ContenidoEnlazable.tratarTT(clasi_aux[0])+ "<br>";
							
							if (clasi_aux[1].contains("[]"))
								otros = otros  + ContenidoEnlazable.tratarTT(clasi_aux[0])+ "<br>";
								
						}
						
						
			           class_Cultura.setText(cultura);
			           class_Deportes.setText(deporte);
			           class_Entretenimiento.setText(entretenimiento);
			           class_Politica.setText(politica);
			           class_Tecnologia.setText(tecnologia);
			           class_Otros.setText(otros);
			            
			           // Se inicializa el combobox de los tt con gráfica
			           ArrayList<String> trendingTopics = mongoDBHandler.recuperarTTporDuracion(dia,  mes, año);
						
						Iterator<String> it = trendingTopics.iterator();
								
						while(it.hasNext()){
							String aux_tt = it.next();
							this.cb_ttGraficas.addItem(aux_tt);
						}
						
			       
						
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
					
					
			}
			
		}
		else if (e.getSource() == this.btn_populares){
		   
		
			
			inicializarFecha(((JTextField)fecha_populares.getDateEditor().getUiComponent()).getText().toString());
			
			ArrayList<String> trendingTopics = mongoDBHandler.recuperarTT(dia,  mes, año);
			
			Iterator<String> it = trendingTopics.iterator();
					
			while(it.hasNext()){
				String aux_tt = it.next();
				this.cb_tredingTopics.addItem(aux_tt);
			}
			
			
			
			
			
		}
		else if (e.getSource() == this.btn_muestraGrafo){
			
			//Generamos el Grafo.
			
			
			String fecha_class = "";
			fecha_class = ((JTextField)dc_fechaCP.getDateEditor().getUiComponent()).getText();
			String []tratarFecha = fecha_class.split("-");
			String mes = getMes(tratarFecha[1]);
			
			if ( tratarFecha[0].startsWith("0"))
				this.dia=(String)  tratarFecha[0].subSequence(1, 2);
			TestingGdfs.Generagdf(dia, mes, tratarFecha[2]);
			String fechaResultante = dia +"-" +mes+"-"+ tratarFecha[2];
			 File path =new File( fechaResultante+"Red.pdf");
	          try {
				Desktop.getDesktop().open(path);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		else if(e.getSource() == this.btnGetPopulars){
			
			String RDelegido = "";
			if(this.rdbtnFollowers.isSelected())
				RDelegido = "Followers";
			else if(this.rdbtnFavoritos.isSelected())
				RDelegido = "Favoritos";
			else if(this.rdbtnRT.isSelected())
				RDelegido = "ReTweets";
				
			
			System.out.print("a" + RDelegido);
			String fechaaux = año + "-" + mes + "-" + dia;
			String elegido = this.cb_tredingTopics.getSelectedItem().toString();
			PopularSearch populares = new PopularSearch(elegido, 1000, fechaaux, RDelegido);
			
			List<PopularTweet> ListaPopulares = populares.busqueda();
			Collections.sort(ListaPopulares);
			
			Iterator<PopularTweet> it = ListaPopulares.iterator();
			int i = 1;
			//Solo mostraremos los 10 mejores tweets obtenidos.
			String Tweets = "";
			while(it.hasNext() && i < 10){
				  PopularTweet status = it.next();
				  Tweets = Tweets + ContenidoEnlazable.tratarTweetId(status.getMyStatus().getUser().getScreenName(), status.getMyStatus().getId(),"Tweet ")+ " "+i+" " +ContenidoEnlazable.tratarTexto("@"+status.getMyStatus().getUser().getScreenName())+ "  -->  " + ContenidoEnlazable.tratarTexto(status.getMyStatus().getText()) + " FA: " +  status.getMyStatus().getFavoriteCount() + " RT: " + status.getMyStatus().getRetweetCount()+  " FO: " + status.numFollowers() + "<br><br>";		
				  i++;
			}
			
			this.tt_populares.setText(Tweets);
			
		}
		else if(e.getSource() == this.b_generarGrafica){
			String hashtag=(String) cb_ttGraficas.getSelectedItem();
			try {
				RecuperarInfo.creafichero(dia, mes, año, hashtag);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 new GraficoPorHoras(hashtag, null, null).setVisible(true);
			
		}
	}
}
