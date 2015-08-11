package clasificador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.math.BigInteger;

import obtenerInformacion.DataStorage;

 /**
  * 
  * 
  * @references http://www.sanfoundry.com/java-program-rabin-karp-algorithm/
  *
  */

public class RabinKarp 

{
	private class StringInt{
		private String categoria;
		private int apariciones;
		
		public StringInt(String cate,int apar){
			this.apariciones=apar;
			this.categoria=cate;
		}
		public int  getApariciones(){
			
		return this.apariciones;
		}
		
		public String  getCategoria(){
			
			return this.categoria;
			}
		
		public boolean equals(Object o){
			StringInt aux = (StringInt)o;
			return this.categoria.equals(aux.categoria);
			
		}
		
		public String toString()
		{
			return this.categoria + " " + this.apariciones;
		}
		
		public void setApariciones(int i) {
			this.apariciones = i;
			
		}
	}
	

	/** Result List Pattern Match **/
	
	private ArrayList<StringInt> listMatches;
    /** String List  Pattern **/
    
    private ArrayList<String> pat; 
    
    /** Actual pattern **/
    
    private String actPattern;
    
    
    /**number of matches **/
    
    private int matches;
    /** pattern hash value **/    

    private long patHash;    

    /** pattern length **/

    private int M;  

    /** Large prime **/         

    private long Q; 

    /** radix **/         

    private int R;   

    /** R^(M-1) % Q **/        

    private long RM;          

 

    /** Constructor **/

    public RabinKarp(String txt, ArrayList<String> pat) 

    {
    	
    	listMatches=new ArrayList<StringInt>();
    	this.matches = 0;
        this.pat = pat;      

        R = 256;

    	
  		Iterator<String> it_subCat = pat.iterator();
  		 while(it_subCat.hasNext()){
  			actPattern = it_subCat.next();
        M = actPattern.length();

        Q = longRandomPrime();

        /** precompute R^(M-1) % Q for use in removing leading digit **/

        RM = 1;

        for (int i = 1; i <= M-1; i++)

           RM = (R * RM) % Q;

        patHash = hash(actPattern, M);

        int pos = search(txt);
        this.matches=0;
        if (pos == -1);

      //      System.out.println("\nNo Match\n");

        else{
        	Categorias miCategoria = Enum.valueOf(Categorias.class, this.actPattern);
         //   System.out.println("Pattern found at position : "+ pos+" patron: "+actPattern);
        	StringInt a = new StringInt(miCategoria.getCategoria(),pos);
        	if(listMatches.contains(a)){
        		Iterator it=listMatches.iterator();
        		while(it.hasNext()){
        			StringInt aux = (StringInt) it.next();
        			 if(aux.equals(a)){
        				 aux.setApariciones(aux.getApariciones()+a.getApariciones());
        				 break;
        			 }
        			
        		}
        	}
        	else
        		listMatches.add(a);
        		
           // this.ListMatches=this.ListMatches  + miCategoria.getCategoria()+ " " + pos + ",";
        }
  		 }
  		 

    } 
    //numero de coincidencias
	public int getMatches() {
		
		return this.matches;
	}

	//lista de patrines coincidentes
		public ArrayList<StringInt> getListMatches() {
			
			return this.listMatches;
		}
		
		public String getListMatchesToString(){
			String lista = "";
			Iterator<StringInt> it = this.listMatches.iterator();
			if(listMatches.size() > 0){
				StringInt aux = it.next();
				lista = aux.getCategoria() + "-" +aux.getApariciones();
			
				while(it.hasNext()){
					aux = it.next();
					lista = lista + ">" + aux.getCategoria() + "-" +aux.getApariciones();
				

				}
			}
			
			return lista;
		}
		
		public String getMatchesAsString(){
			String lista = "";
			Iterator<StringInt> it = this.listMatches.iterator();
			if(listMatches.size() > 0){
				StringInt aux = it.next();
				lista = aux.getCategoria() + " " + aux.getApariciones()+ "<";
				while(it.hasNext()){
					aux = it.next();
					if(it.hasNext())
						lista = lista + aux.getCategoria() + " " +  aux.getApariciones() + "<" ;
					else
						lista = lista + aux.getCategoria() + " " +  aux.getApariciones();
	
				}
			}
			
			return lista;
		}
    
    /** Compute hash **/

    private long hash(String key, int M)

    { 

        long h = 0; 

        for (int j = 0; j < M; j++) 

            h = (R * h + key.charAt(j)) % Q; 

        return h; 

    } 

    /** Funtion check **/

    private boolean check(String txt, int i) 

    {

        for (int j = 0; j < M; j++) 

            if (actPattern.charAt(j) != txt.charAt(i + j)) 

                return false; 

        return true;

    }

    /** Funtion to check for exact match**/

    private int search(String txt) 

    {
    	
  			 int N = txt.length(); 

  	        if (N < M) return N;

  	        long txtHash = hash(txt, M); 

  	        /** check for match at start **/

  	        if ((patHash == txtHash) && check(txt, 0))

  	            return 0;

  	        /** check for hash match. if hash match then check for exact match**/

  	        for (int i = M; i < N; i++) 

  	        {

  	            // Remove leading digit, add trailing digit, check for match. 

  	            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q; 

  	            txtHash = (txtHash * R + txt.charAt(i)) % Q; 

  	            // match

  	            int offset = i - M + 1;

  	            if ((patHash == txtHash) && check(txt, offset) )
                   this.matches ++;
  	               // return offset;
  	            

  	        }
  			 
            if(this.matches!=0)
            	return this.matches;

        /** no match **/

        return -1;

    }

    /** generate a random 31 bit prime **/

    private static long longRandomPrime() 

    {

        BigInteger prime = BigInteger.probablePrime(31, new Random());

        return prime.longValue();

    }

    /** Main Function **/

    public static void main(String[] args) throws IOException

    {    

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Rabin Karp Algorithm Test\n");

        System.out.println("\nEnter Text\n");

        String text = br.readLine();



        System.out.println("\nResults : \n");

        RabinKarp rk = new RabinKarp(text.toLowerCase(), Categorias.getSubCategories());  
        System.out.println(rk.getMatches()+" "+rk.getListMatches() );

    }


}
