package sa;
import java.io.*;

class Value{
    float goodValue=0;
    float goodValuetemp=0;
    float badValue=0;
    float badValuetemp=0;
    float goodProb=0;
    float badProb=0;
    float goodProbtemp=0;
    float badProbtemp=0;
    float recallgood=0;
    float recallbad=0;
    float recallneutral=0;
    float goodcorrect=0;
    float badcorrect=0;
    float neutralcorrect=0;
    float goodwrong=0;
    float badwrong=0;
    float neutralwrong=0;
    int recallgoodtemp=0;
    int recallbadtemp=0;
    int recallneutraltemp=0;
    int goodcorrecttemp=0;
    int badcorrecttemp=0;
    int neutralcorrecttemp=0;
    int goodwrongtemp=0;
    int badwrongtemp=0;
    int neutralwrongtemp=0;
}

class nb2{
	float precision=0,recall=0;
	int totalreview=0;
	PAUCR p1=new PAUCR();
    void Train(Gd glb2,PAUCR pa) throws IOException{
        File f=new File("C:\\ccc\\Project\\src\\sa\\DataNew.txt");
        BufferedReader b=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        
        String s="";
        
        String[] t=new String[1000];
        PrintWriter out1=new PrintWriter(new BufferedWriter(new FileWriter("C:\\ccc\\Project\\src\\sa\\good.txt",true)));
        PrintWriter out2=new PrintWriter(new BufferedWriter(new FileWriter("C:\\ccc\\Project\\src\\sa\\bad.txt",true)));
        PrintWriter out3=new PrintWriter(new BufferedWriter(new FileWriter("C:\\ccc\\Project\\src\\sa\\neutral.txt",true)));
        while((s=b.readLine())!=null){
        	t=s.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
        
        	
        
           Pass1(glb2,t,pa);
           Pass2(glb2,t,pa);
           Pass3(glb2,t,pa);
           glb2.totalScore=glb2.positiveScore-glb2.negativeScore+glb2.oppositeSentimentScore;
            if(glb2.totalScore<0)
                {out2.println(s);}
            else if(glb2.totalScore>0)
                {out1.println(s);}
            else if(glb2.totalScore==0)
                {out3.println(s);}
        
        
            
        }
        out1.close();
        out2.close();
        out3.close();
        b.close();
    }

    String TrainSingle(Gd glb2,PAUCR pa,String str) throws IOException{
        
        String[] t=new String[1000];
        PrintWriter out1=new PrintWriter(new BufferedWriter(new FileWriter("C:\\ccc\\Project\\src\\sa\\good.txt",true)));
        PrintWriter out2=new PrintWriter(new BufferedWriter(new FileWriter("C:\\ccc\\Project\\src\\sa\\bad.txt",true)));
        PrintWriter out3=new PrintWriter(new BufferedWriter(new FileWriter("C:\\ccc\\Project\\src\\sa\\neutral.txt",true)));
        int returnvalue=-2;
        
        	t=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
          
           Pass1(glb2,t,pa);
           Pass2(glb2,t,pa);
           Pass3(glb2,t,pa);
           glb2.totalScore=glb2.positiveScore-glb2.negativeScore+glb2.oppositeSentimentScore;
            if(glb2.totalScore<0)
                {out2.println(str);returnvalue=-1;}
            else if(glb2.totalScore>0)
                {out1.println(str);returnvalue=1;}
            else if(glb2.totalScore==0)
                {out3.println(str);returnvalue=0;}
           
            
        
        out1.close();
        out2.close();
        out3.close();
        if(returnvalue==1)
        	return "g";
        else if (returnvalue==-1)
        	return "b";
        else
        	return "n";
    }

    void Pass1(Gd glb2,String[] t,PAUCR pa) throws IOException{
        int i=0,j=0,k=0,ans=0;
        String[] token2=new String[1];
        j=0; i=0;
        while(j<t.length){
            token2[0]=t[j++];
            i=pa.Compare(glb2,token2,1,0,0);
            if(i!=0){
                for(k=0;k<3 && j!=t.length;k++){
                    token2[0]=t[j++];
                    ans=pa.Compare(glb2,token2,2,1,0);
                    if(ans==0)
                        ans=pa.Compare(glb2,token2,3,1,0);
                }
            }
        }
    }

    void Pass2(Gd glb2,String[] t,PAUCR pa) throws IOException{
        pa.Compare(glb2,t,2,0,0);
        pa.Compare(glb2,t,3,0,0);
    }

    void Pass3(Gd glb2,String[] t,PAUCR pa) throws IOException{
        String []t2=new String[1000];
        int i=0,j=0;
        i=pa.Compare(glb2,t,4,0,0);
        if(i!=0){
            for(j=0;j<i;j++)
                t2[j]=t[j];
            pa.Compare(glb2,t2,2,2,0);
            pa.Compare(glb2,t2,3,2,0);
            glb2.oppositeSentimentScore=glb2.oppositeConjunctionWeight*glb2.sentimentCount/2;
        }
    }

    void Analyse(Value v,PAUCR pa) throws IOException{
    	
    	nb2 NB2=new nb2();
    	Gd glb3=new Gd();
    	PAUCR pa2=new PAUCR();
    	String trainsingleresult=new String();
    	trainsingleresult="";
    	
    	int goodflag=0,badflag=0,neutralflag=0;
    	int temptotalreview=0;
    	
        File review=new File("C:\\ccc\\Project\\src\\sa\\DataNew.txt");
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(review)));
        String str=new String();
        String[] token=new String[1000];
        
        File review2=new File("C:\\ccc\\Project\\src\\sa\\Sample.txt");
        BufferedReader br2=new BufferedReader(new InputStreamReader(new FileInputStream(review2)));
        String str2=new String();
        String[] token2=new String[1000];
        
        while((str=br.readLine())!=null){
        
        	
        	goodflag=0;
            badflag=0;
            neutralflag=0;
        	v.goodProbtemp=0;
        	v.badProbtemp=0;
        	v.goodValuetemp=0;
        	v.badValuetemp=0;
        	v.goodValue=0;
        	v.badValue=0;
        	
            token=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
            GoodValue(v,token);
            BadValue(v,token);
            
            if((v.goodValue+v.badValue) != 0){
                v.goodProb=v.goodProb + v.goodValue/(v.goodValue+v.badValue);
                v.badProb=v.badProb + v.badValue/(v.goodValue+v.badValue);
                v.goodProbtemp=v.goodProbtemp + v.goodValue/(v.goodValue+v.badValue);
                v.badProbtemp=v.badProbtemp + v.badValue/(v.goodValue+v.badValue);
                if(v.goodProbtemp>v.badProbtemp)
                	{v.recallgood=v.recallgood+1;goodflag=1;}
                else if(v.goodProbtemp<v.badProbtemp)
                	{v.recallbad=v.recallbad+1;badflag=1;}
                else if(v.goodProbtemp==v.badProbtemp)
                	{v.recallneutral=v.recallneutral+1;neutralflag=1;}
            }
            else if((v.goodValue+v.badValue)==0)
            {
            	trainsingleresult=NB2.TrainSingle(glb3,pa2,str);
            	if(trainsingleresult.equals("g"))
            	{v.recallgood=v.recallgood+1;goodflag=1;}
            	else if(trainsingleresult.equals("b"))
            	{v.recallbad=v.recallbad+1;badflag=1;}
            	else
            	{
            		v.recallneutral=v.recallneutral+1;
            		neutralflag=1;
            	}
            }
             
            str2=br2.readLine();
        
              
                token2=str2.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
                
                if(token2[0].equals("g")&&goodflag==1)
                	v.goodcorrect++;
                else if(token2[0].equals("g")&&goodflag==0)
                	v.goodwrong++;
                if(token2[0].equals("b")&&badflag==1)
                	v.badcorrect++;
                else if(token2[0].equals("b")&&badflag==0)
                	v.badwrong++;
                if(token2[0].equals("n")&&neutralflag==1)
                	v.neutralcorrect++;
                else if(token2[0].equals("n")&&neutralflag==0)
                	v.neutralwrong++;
                
                
            
            totalreview++;
        }
        
        br.close();
        br2.close();
    }

    void GoodValue(Value v,String[] t) throws IOException{
        File good=new File("C:\\ccc\\Project\\src\\sa\\good.txt");
        BufferedReader br2=new BufferedReader(new InputStreamReader(new FileInputStream(good)));
        String str2;
        String[] t2=new String[1000];
        while((str2=br2.readLine())!=null){
           t2=str2.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
           for(int i=0;i<t.length;i++){
               for(int j=0;j<t2.length;j++){
                   if(t[i].toUpperCase().equals(t2[j].toUpperCase())){
                       v.goodValue++;
                       v.goodValuetemp++;
                   }
               }
           }
        }
        br2.close();
    }

    void BadValue(Value v,String[] t) throws IOException{
        File bad=new File("C:\\ccc\\Project\\src\\sa\\bad.txt");
        BufferedReader br2=new BufferedReader(new InputStreamReader(new FileInputStream(bad)));
        String str2;
        String[] t2=new String[1000];
        while((str2=br2.readLine())!=null){
           t2=str2.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
           for(int i=0;i<t.length;i++){
               for(int j=0;j<t2.length;j++){
                   if(t[i].toUpperCase().equals(t2[j].toUpperCase())){
                   	v.badValue++;
                   	v.badValuetemp++;
                   }
                   
               }
           }
        }
        br2.close();
    }
    
    public static String[] main(String args[],PAUCR parameter) throws IOException{
        String[] re=new String[12];
        re[0]=args[0];
        Gd glb2=new Gd();
        nb2 NB=new nb2();
        Value v=new Value();
        PAUCR pa=new PAUCR();
        
        NB.Analyse(v, pa);
        if(v.goodProb == v.badProb){
            NB.Train(glb2,pa);
            NB.Analyse(v,pa);
            if(v.goodProb > v.badProb)
                re[1]="Good";
            else if(v.goodProb < v.badProb)
                re[1]="Bad";
            else if(v.goodProb == v.badProb)
                re[1]="Neutral";
        }
        else if(v.goodProb > v.badProb)
            re[1]="Good";
        else if(v.goodProb < v.badProb)
            re[1]="Bad";
        
        System.out.println("\nnbtotalreview:"+(parameter.totalreview));
        
        System.out.println("\nnbrecallgood:"+((v.recallgood/parameter.isgoodreview)*100));
        System.out.println("\nnbrecallbad:"+((v.recallbad/parameter.isbadreview)*100));
        System.out.println("\nnbrecallneutral:"+((v.recallneutral/parameter.isneutralreview)*100));
        
        System.out.println("\nnbgoodprecision:"+((v.goodcorrect/(v.goodcorrect+v.goodwrong))*100));
        System.out.println("\nnbbadprecision:"+((v.badcorrect/(v.badcorrect+v.badwrong))*100));
        System.out.println("\nnbneutralprecision:"+((v.neutralcorrect/(v.neutralcorrect+v.neutralwrong))*100));
        
        v.recallgoodtemp=(int)v.recallgood;
         v.recallbadtemp=(int)v.recallbad;
         v.recallneutraltemp=(int)v.recallneutral;
         v.goodcorrecttemp=(int)v.goodcorrect;
         v.badcorrecttemp=(int)v.badcorrect;
         v.neutralcorrecttemp=(int)v.neutralcorrect;
         v.goodwrongtemp=(int)v.goodwrong;
         v.badwrongtemp=(int)v.badwrong;
         v.neutralwrongtemp=(int)v.neutralwrong;
         
        re[2]=Float.toString(parameter.isgoodreview);
        re[3]=Float.toString(parameter.isbadreview);
        re[4]=Float.toString(parameter.isneutralreview);
        re[5]=Float.toString(parameter.totalreview);
        re[6]=Float.toString(((v.recallgood/parameter.isgoodreview)*100));
        re[7]=Float.toString(((v.recallbad/parameter.isbadreview)*100));
        re[8]=Float.toString(((v.recallneutral/parameter.isneutralreview)*100));
        re[9]=Float.toString(((v.goodcorrect/(v.goodcorrect+v.goodwrong))*100));
        re[10]=Float.toString(((v.badcorrect/(v.badcorrect+v.badwrong))*100));
        re[11]=Float.toString(((v.neutralcorrect/(v.neutralcorrect+v.neutralwrong))*100));
        
        return re;


    }
}