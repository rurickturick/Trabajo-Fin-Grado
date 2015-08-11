package Gui;
import java.awt.*;
import java.awt.geom.Rectangle2D;


	

	/**
	 *
	 * @author usuario_local
	 */
	   

public class PantallaCarga {
	   
	   
	    static SplashScreen mySplash;          
	    static Graphics2D splashGraphics;
	    static Rectangle2D.Double splashTextArea;      
	    static Rectangle2D.Double splashProgressArea; 
	    static Font font;
	   
	       private  void appInit(){
	        for (int i = 1; i <= 100; i++){
	            int pctDone = i ;  
	            splashProgress(pctDone);
	            try{
	                Thread.sleep(15);             // Espera 0.01 s
	            }
	            catch (InterruptedException ex){
	                break;
	            }
	        }
	    }

	    private  void splashInit() {
	        mySplash = SplashScreen.getSplashScreen();
	        if (mySplash != null){
	            Dimension ssDim = mySplash.getSize();
	            int height = ssDim.height;
	            int width = ssDim.width;
	            splashProgressArea = new Rectangle2D.Double(width * .30, height*.92, width*.4, 12 );

	            // create the Graphics environment for drawing status info
	            splashGraphics = mySplash.createGraphics();
	            splashProgress(0);
	        }
	    }
	 
	    private void splashProgress(int pct){
	        if (mySplash != null && mySplash.isVisible()){
	            splashGraphics.setPaint(Color.LIGHT_GRAY);
	            splashGraphics.fill(splashProgressArea);

	            // draw an outline
	            splashGraphics.setPaint(Color.BLUE);
	            splashGraphics.draw(splashProgressArea);

	            // Calculate the width corresponding to the correct percentage
	            int x = (int) splashProgressArea.getMinX();
	            int y = (int) splashProgressArea.getMinY();
	            int wid = (int) splashProgressArea.getWidth();
	            int hgt = (int) splashProgressArea.getHeight();

	            int doneWidth = Math.round(pct*wid/100.f);
	            doneWidth = Math.max(0, Math.min(doneWidth, wid-1));  // limit 0-width

	            // fill the done part one pixel smaller than the outline
	            splashGraphics.setPaint(Color.CYAN);
	            splashGraphics.fillRect(x, y+1, doneWidth, hgt-1);

	            mySplash.update();
	        }
	    }
	   
	    public void iniciarCarga() {
	        splashInit();
	        appInit();
	        if (mySplash != null)
	            mySplash.close();
	        //MainWindow window = new MainWindow();
	    }
	   
	

}
