/*
 * Created on 31 May, 2014
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package sa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import rita.wordnet.RiWordnet;

/**
 * @author saurabhm
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Entity {

	   public static void main(String args[]) throws IOException{
        PAUCR pa2=new PAUCR();
        
        Gd glb3=new Gd();
        File review2=new File("C:\\ccc\\Project\\src\\sa\\Sample.txt");
        BufferedReader br2=new BufferedReader(new InputStreamReader(new FileInputStream(review2)));
        String str2=new String();
        String[] token2=new String[1000];
        String[] entities=new String[100000];
        RiWordnet r=new RiWordnet();
         boolean value1,value2,value3,value4;
        
        String str=new String();
        str=" ";
        int index=0;
        
        while((str2=br2.readLine())!=null)
        {
        token2=str2.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
                
                for(int i=0;i<token2.length;i++)
                {
                	value1=r.isAdjective(token2[i]);
                	value2=r.isAdverb(token2[i]);
                	value3=r.isVerb(token2[i]);
                	value4=r.isNoun(token2[i]);
                	
                if(value4==true)
                {
                	//str=String.valueOf(r.getBestPos(token2[i]));
                	
                	/*if(str!=null)
                	{
                		System.out.println("\n"+str);
                		entities[index]=String.valueOf(token2[i]);
                		index++;
                	}*/
                	entities[index]=String.valueOf(token2[i]);
                }
                	//str=" ";
    		
                }
        }

        for(int i=0;i<index;i++)
        	System.out.println("\n"+entities[i]);
 
	   }
	   
}
