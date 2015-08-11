package Gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContenidoEnlazable {

	public static String tratarTexto(String next) {
		next = next.replace("\n", " ");
		String args[] = next.split(" ");
		String aux = "";
		String tratado = "";
		
		Pattern pattern = Pattern.compile("(@|#)\\w+");
		
		String regex = "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" + 
	            "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" + 
	            "|mil|biz|info|mobi|name|aero|jobs|museum" + 
	            "|travel|[a-z]{2}))(:[\\d]{1,5})?" + 
	            "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" + 
	            "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
	            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" + 
	            "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + 
	            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" + 
	            "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b";
		
		Pattern linkPattern = Pattern.compile(regex);

		for(int i = 0 ; i < args.length ; i++){
			if(args[i].startsWith("@")){ // usuario
				
				Matcher matcher = pattern.matcher(args[i]);
				
				while(matcher.find()){
					
					//System.out.println(matcher.group());
					aux = "'https://twitter.com/" + matcher.group().substring(1) + "'";
					args[i] ="<a href=" + aux  + ">" + args[i] + "</a>";
				}

			}
			else if(args[i].startsWith("http")){ // Link
				Matcher matcher = linkPattern.matcher(args[i]);
				
				while(matcher.find()){
					
					//System.out.println(matcher.group());
					aux = "'" + matcher.group() + "'";
					args[i] ="<a href=" + aux  + ">" + args[i] + "</a>";
				}
			}
			else if(args[i].startsWith("#")){  //Hashtag
				Matcher matcher = pattern.matcher(args[i]);
				
				while(matcher.find()){
					
					//System.out.println(matcher.group());
					aux = "'https://twitter.com/hashtag/" + matcher.group().substring(1) + "'";
					args[i] ="<a href=" + aux  + ">" + args[i] + "</a>";
				}
			}
			tratado = tratado +  args[i] + " " ;
			
		}

		return tratado;
	}
	
	public static String tratarTT(String TT){
		//Pattern pattern = Pattern.compile("(#)?\\w+");
		String aux= "";
		String tt2 = TT;
		if(TT.startsWith("#")){
			aux = "https://twitter.com/hashtag/" + TT.substring(1) + "?src=tren";			
		}
		else{
			tt2 = tt2.replaceAll(" ","%20");
			aux = "https://twitter.com/search?q=" + tt2 + "&src=tren";
		}
		
		return /*"<a href=" +*/ aux /* + ">" + TT + "</a>"*/;
		
		
	}
	public static String tratarTweetId(String user,Long tweetId,String enlace){
		String aux="https://twitter.com/"+user+"/status/"+tweetId.toString();
		return "<a href=" + aux  + ">" + enlace + "</a>";
		
	}
}
