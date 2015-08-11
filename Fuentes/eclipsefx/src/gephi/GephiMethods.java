package gephi;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeController;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.filters.api.FilterController;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.EdgeDefault;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2Builder;
import org.gephi.partition.api.Part;
import org.gephi.partition.api.Partition;
import org.gephi.partition.api.PartitionController;
import org.gephi.partition.plugin.NodeColorTransformer;
import org.gephi.preview.api.PreviewController;
import org.gephi.preview.api.PreviewModel;
import org.gephi.preview.api.PreviewProperty;
import org.gephi.preview.types.EdgeColor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.gephi.ranking.api.Ranking;
import org.gephi.ranking.api.RankingController;
import org.gephi.ranking.api.Transformer;
import org.gephi.ranking.plugin.transformer.AbstractSizeTransformer;
import org.gephi.statistics.plugin.Modularity;
import org.openide.util.Lookup;

import trendingTopics.mongoDBHandler;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
/**
 * Clase de prueba para importar y analizar archivos gdf con gephi
 * Esta sacado de las demos que vienen en el .jar con netbeans 
 * particion->https://github.com/gephi/gephi/wiki/How-to-use-partition
 * javadoc->http://gephi.org/docs/api/
 * gephi toolkit para descargar el jar->http://gephi.github.io/toolkit/
 * @author usuario_local
 *
 */
public class GephiMethods {
	private String nombreGDF = "";
	private Partition p2;
	
	public GephiMethods(String nombreGDF){
		this.nombreGDF = nombreGDF;
		
	}
	
	public Partition getParticionGrafo(){
		return p2;
	}
	
	
	public static void applyYifanHuLayout(){
		GraphModel graphModel2 = Lookup.getDefault().lookup(GraphController.class).getModel();
		YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
        layout.setGraphModel(graphModel2);
        layout.resetPropertiesValues();
        layout.setOptimalDistance(200f);
        layout.initAlgo();

        for (int i = 0; i < 300 && layout.canAlgo(); i++) {
            layout.goAlgo();
        }
        layout.endAlgo();	
	}
	@SuppressWarnings("unchecked")
	public Partition script() {
        //Init a project - and therefore a workspace
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();
        Workspace workspace = pc.getCurrentWorkspace();

        //Get models and controllers for this new workspace - will be useful later
        AttributeModel attributeModel = Lookup.getDefault().lookup(AttributeController.class).getModel();
        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        PreviewModel model = Lookup.getDefault().lookup(PreviewController.class).getModel();
        ImportController importController = Lookup.getDefault().lookup(ImportController.class);
        FilterController filterController = Lookup.getDefault().lookup(FilterController.class);
        RankingController rankingController = Lookup.getDefault().lookup(RankingController.class);

        //Import file       
        Container container;
        try {
            File file = new File(nombreGDF+".gdf");
            container = importController.importFile(file);
            container.getLoader().setEdgeDefault(EdgeDefault.UNDIRECTED);   //Force UNDIRECTED
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        //Append imported data to GraphAPI
        importController.process(container, new DefaultProcessor(), workspace);

 
        
      //  Ranking degreeRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, Ranking.DEGREE_RANKING);
        Ranking degreeRanking = rankingController.getModel().getRanking(Ranking.NODE_ELEMENT, Ranking.DEGREE_RANKING);
        AbstractSizeTransformer sizeTransformer = (AbstractSizeTransformer) rankingController.getModel().getTransformer(Ranking.NODE_ELEMENT, Transformer.RENDERABLE_SIZE);
        sizeTransformer.setMinSize(10);
        sizeTransformer.setMaxSize(50);
        rankingController.transform(degreeRanking,sizeTransformer);
        

        UndirectedGraph graph = graphModel.getUndirectedGraph();
        System.out.println("Nodes: " + graph.getNodeCount());
        System.out.println("Edges: " + graph.getEdgeCount());
        
      

        //Partition with 'source' column, which is in the data
        PartitionController partitionController = Lookup.getDefault().lookup(PartitionController.class);
  

        //Export
        ExportController ec = Lookup.getDefault().lookup(ExportController.class);
        

        //Run modularity algorithm - community detection
        Modularity modularity = new Modularity();
        modularity.execute(graphModel, attributeModel);

        //Partition with 'modularity_class', just created by Modularity algorithm
        AttributeColumn modColumn = attributeModel.getNodeTable().getColumn(Modularity.MODULARITY_CLASS);
        p2 = partitionController.buildPartition(modColumn, graph);
        System.out.println(p2.getPartsCount() + " partitions found");
      
      
        
        NodeColorTransformer nodeColorTransformer2 = new NodeColorTransformer();
        nodeColorTransformer2.randomizeColors(p2);
        partitionController.transform(p2, nodeColorTransformer2);
        
      
        YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
        layout.setGraphModel(graphModel);
        layout.resetPropertiesValues();
       
        layout.setOptimalDistance(1500f);
        
        layout.initAlgo();

        for (int i = 0; i < 500 && layout.canAlgo(); i++) {
            layout.goAlgo();
        }
        layout.endAlgo();
  
       

       
       model.getProperties().putValue(PreviewProperty.SHOW_NODE_LABELS, Boolean.TRUE);
       model.getProperties().putValue(PreviewProperty.SHOW_EDGE_LABELS, Boolean.FALSE);
       model.getProperties().putValue(PreviewProperty.SHOW_EDGES, Boolean.TRUE);
       model.getProperties().putValue(PreviewProperty.EDGE_CURVED, Boolean.FALSE);
       
       
        model.getProperties().putValue(PreviewProperty.EDGE_COLOR, new EdgeColor(Color.GRAY));
        model.getProperties().putValue(PreviewProperty.EDGE_THICKNESS, new Float(0.1f));
        model.getProperties().putValue(PreviewProperty.NODE_LABEL_FONT, model.getProperties().getFontValue(PreviewProperty.NODE_LABEL_FONT).deriveFont(6));
        
        //Export
        try {
            ec.exportFile(new File(nombreGDF+".pdf"));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        
     return p2;
}
	/**
	 * Recupera un GDF de la BBDD dependiendo de la fecha pasada por parametro.
	 * Tambien hace las invocaciones a los metodos necesarios para generar la red en formato PDF.
	 * El nombre de los archivos sera "dia-mes-annioRed.gdf"  y "dia-mes-annioRed.PDF"
	 * @param dia
	 * @param mes
	 * @param annio
	 */
	public void getRedEnPDFFormat(String dia, String mes, String annio){
		DBCursor cursor = mongoDBHandler.RecuperarGDF(dia,mes,annio);
		
		String gdf="";
	       while (cursor.hasNext()) {
	    	   DBObject tmp=cursor.next();
	    	   String gdfText = tmp.get("gdf").toString();
	    	   gdf = gdf + gdfText;
	          
	         }
		System.out.println(gdf);
		
		
		try{
		      FileWriter fichero = new FileWriter(dia+"-"+mes+"-"+annio+"Red.gdf");		      
		      fichero.write(gdf); //"\r\n"
		      fichero.close();

		    }catch(Exception ex){
		      ex.printStackTrace();
		    }
		
		
		GephiMethods p= new GephiMethods(dia+"-"+mes+"-"+annio+"Red");
		p.script();// TODO Auto-generated method stub
	}
}
