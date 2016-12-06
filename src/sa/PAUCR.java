package sa;
import java.io.*;


class Gd{
    int positiveScore=0,negativeScore=0,sentimentCount=0,oppositeSentimentScore=0,totalScore=0;
    int negationWeight=2,oppositeConjunctionWeight=-1;
    float pCount=0,pShare=0,nCount=0,nShare=0;
    int positiveScoretemp[]=new int[100000];
	int negativeScoretemp[]=new int[100000];
	int sentimentCounttemp[]=new int[100000];
	int oppositeSentimentScoretemp[]=new int[100000];
	int totalScoretemp[]=new int[100000];
    int negationWeighttemp[]=new int[100000];
    int oppositeConjunctionWeighttemp[]=new int[100000];
    int reviewvalue[]=new int[100000];
    float recallgood=0;
    float recallbad=0;
    float recallneutral=0; 
    float goodcorrect=0;
    float badcorrect=0;
    float neutralcorrect=0;
    float goodwrong=0;
    float badwrong=0;
    float neutralwrong=0;
}

public class PAUCR{
	int isgoodreview=0,isbadreview=0,isneutralreview=0,totalreview=0;
    void Mybegin() throws IOException{
        FileInputStream r;
        FileOutputStream f;
        int i;
        String str="";
        r=new FileInputStream("C:\\ccc\\Project\\src\\sa\\Sample.txt");
        f=new FileOutputStream("C:\\ccc\\Project\\src\\sa\\DataNew.txt");
        f.close();
        PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("C:\\ccc\\Project\\src\\sa\\DataNew.txt",true)));
        
        
        //pre-processing customer review file
        int tempflag=0;
        do{
        	
            i=r.read();
            if(i!=-1){
            	
            	if(i=='g'&&tempflag==0)
        		{isgoodreview++;   }
        	else if(i=='b'&&tempflag==0)
        		{isbadreview++;   }
        	else if(i=='n'&&tempflag==0)
        		{isneutralreview++; }
        	
            	  if(i=='.'||i=='?'||i=='!'){
                    str+=" "; str+="#";
                    out.print(str);
                    tempflag=0;
                 
                    str="";
                }
      
                else if((i=='g'&&tempflag==0)||(i=='b'&&tempflag==0)||(i=='n'&&tempflag==0))
                  {str+=""; tempflag=1;}
                else if(i=='\n')
                {str=str+"";}
                else if(i!='\n')
                    str+=(char)i;
            	
              
            }
        }while(i!=-1);
      
        out.close();
        r.close();
        System.out.println("good:"+isgoodreview);
        System.out.println("bad:"+isbadreview);
        System.out.println("neutral:"+isneutralreview);
    }

    int Compare(Gd glb,String []t,int x,int z,int index) throws IOException{
        File m=null;
        String str2="";
        String []tfile=new String[100000];
        BufferedReader br2;
        int i=0,flag=0;

        switch(x){
            case 1: m=new File("C:\\ccc\\Project\\src\\sa\\negation.txt");
                    break;
            case 2: m=new File("C:\\ccc\\Project\\src\\sa\\positive.txt");
                    break;
            case 3: m=new File("C:\\ccc\\Project\\src\\sa\\negative.txt");
                    break;
            case 4: m=new File("C:\\ccc\\Project\\src\\sa\\oc.txt");
                    break;
            case 5: m=new File("C:\\ccc\\Project\\src\\sa\\av.txt");
                    break;
        }
        br2=new BufferedReader(new InputStreamReader(new FileInputStream(m)));

        while((str2=br2.readLine())!=null){
            tfile=str2.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\/|\\s)\\s*");
            for(i=0;i<t.length;i++){
                if(t[i]!=null && t[i].equalsIgnoreCase(tfile[0])){
                    flag=1;
                    switch(x){
                        case 1: br2.close();
                                glb.nCount+=2;
                                return i+1;
                        case 2: switch(z){
                                    case 0: glb.positiveScore+=1;
                                            glb.positiveScoretemp[index]+=1;
                                            glb.pCount++;
                                            break;
                                    case 1: br2.close();
                                            glb.positiveScore-=glb.negationWeight;
                                            glb.positiveScoretemp[index]-=glb.negationWeighttemp[index];
                                            return 1;
                                    case 2: br2.close();
                                            glb.sentimentCount+=1;
                                            glb.sentimentCounttemp[index]+=1;
                                            return 1;
                                }
                                break;
                        case 3: switch(z){
                                    case 0: glb.negativeScore+=1;
                                    		glb.negativeScoretemp[index]+=1;
                                            glb.nCount++;
                                            break;
                                    case 1: br2.close();
                                            glb.negativeScore-=glb.negationWeight;
                                            glb.negativeScoretemp[index]-=glb.negationWeighttemp[index];
                                            return 1;
                                    case 2: br2.close();
                                            glb.sentimentCount+=1;
                                            glb.sentimentCounttemp[index]+=1;
                                            return 1;
                                }
                                break;
                        case 4: br2.close();
                                return i;
                        case 5: br2.close();
                                return -1;
                    }
                }
            }
        }
        br2.close();
        if(flag==1)
            return 1;
        return 0;
    }

    void Pass1(Gd glb) throws IOException{
        File review;
        BufferedReader br;
        String str="";
        String []token=new String[100000];
        String []token2=new String[1];
        int i=0,j=0,k=0,ans=0,index=0;
        review=new File("C:\\ccc\\Project\\src\\sa\\DataNew.txt");
        br=new BufferedReader(new InputStreamReader(new FileInputStream(review)));
        
        File review2=new File("C:\\ccc\\Project\\src\\sa\\Sample.txt");
        BufferedReader br2=new BufferedReader(new InputStreamReader(new FileInputStream(review2)));
        String str2=new String();
        String[] tokentemp=new String[100000];
        
        while((str=br.readLine())!=null){
      
            token=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
            j=0; i=0;
            while(j<token.length){
                token2[0]=token[j++];
                i=Compare(glb,token2,1,0,index);
                if(i!=0){
                    for(k=0;k<3 && j<token.length;k++){
                        token2[0]=token[j++];
                        ans=Compare(glb,token2,2,1,index);
                        if(ans==0)
                            ans=Compare(glb,token2,3,1,index);
                    }
                }
            }
            
            str2=br2.readLine();
      
          
            tokentemp=str2.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
            
            if(tokentemp[0].equals("g"))
            	{glb.reviewvalue[index]=1;
            	
            	}
            else if(tokentemp[0].equals("b"))
            	{glb.reviewvalue[index]=-1;
            	}
            else if(tokentemp[0].equals("n"))
            	{glb.reviewvalue[index]=0;
            	}
            
            index++;
            totalreview++;
        }
        br.close();
        br2.close();
    }

    void Pass2(Gd glb) throws IOException{
    	int index=0;
        File review;
        BufferedReader br;
        String str="";
        String []token=new String[100000];
        review=new File("C:\\ccc\\Project\\src\\sa\\DataNew.txt");
        br=new BufferedReader(new InputStreamReader(new FileInputStream(review)));
        while((str=br.readLine())!=null){
            token=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
            Compare(glb,token,2,0,index);
            Compare(glb,token,3,0,index);
            index++;
        }
        br.close();
    }

    void Pass3(Gd glb) throws IOException{
        File review;
        BufferedReader br;
        String str="";
        String []token=new String[100000];
        String []t2=new String[100000];
        int i=0,j=0,index=0;
        review=new File("C:\\ccc\\Project\\src\\sa\\DataNew.txt");
        br=new BufferedReader(new InputStreamReader(new FileInputStream(review)));
        while((str=br.readLine())!=null){
            token=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
            i=Compare(glb,token,4,0,index);
            if(i!=0){
                for(j=0;j<i;j++)
                    t2[j]=token[j];
               
                Compare(glb,t2,2,2,index);
                Compare(glb,t2,3,2,index);
                glb.oppositeSentimentScore=glb.oppositeConjunctionWeight*glb.sentimentCount/2;
            }
            index++;
        }
        br.close();
    }

    public static void main(String args[]) throws IOException{
        Gd glb=new Gd();
        int i=0;
        for(i=0;i<100000;i++)
        {
        	glb.positiveScoretemp[i]=0;
            glb.negativeScoretemp[i]=0;
            glb.sentimentCounttemp[i]=0;
            glb.oppositeSentimentScoretemp[i]=0;
        	glb.totalScoretemp[i]=0;
        	glb.negationWeighttemp[i]=2;
        	glb.oppositeConjunctionWeighttemp[i]=-1;
        	glb.reviewvalue[i]=-2;
        }
        final PAUCR rb=new PAUCR();
        PAUCR parameter=new PAUCR();
        parameter=rb;
        String[] res=new String[2];
        String[] re=new String[12];
        String[] re2=new String[20];
        rb.Mybegin();
        rb.Pass1(glb);
        rb.Pass2(glb);
        rb.Pass3(glb);
        
        
        glb.totalScore=glb.positiveScore-glb.negativeScore+glb.oppositeSentimentScore;
        
        if(glb.totalScore<0)
            res[0]="Negative";
        else if(glb.totalScore==0)
            res[0]="Neutral";
        else if(glb.totalScore>0)
            res[0]="Positive";
        System.out.println("\nrbtotalreview:"+parameter.totalreview);
        
        for(i=0;i<parameter.totalreview;i++)
        {
        	glb.totalScoretemp[i]=glb.positiveScoretemp[i]-glb.negativeScoretemp[i]+glb.oppositeSentimentScoretemp[i];
        	if(glb.totalScoretemp[i]<0)
                {
        		glb.recallbad++;
                if(glb.reviewvalue[i]==-1)
                	glb.badcorrect++;
                else
                	glb.badwrong++;
                }
            else if(glb.totalScoretemp[i]==0)
                {
            	glb.recallneutral++;
            	if(glb.reviewvalue[i]==0)
                	glb.neutralcorrect++;
                else
                	glb.neutralwrong++;
                }
            else if(glb.totalScoretemp[i]>0)
                {
            	glb.recallgood++;
            	if(glb.reviewvalue[i]==1)
                	glb.goodcorrect++;
                else
                	glb.goodwrong++;
                }
        }
        
        
        System.out.println("\nrbrecallgood:"+((glb.recallgood/parameter.isgoodreview)*100));
        System.out.println("\nrbrecallbad:"+((glb.recallbad/parameter.isbadreview)*100));
        System.out.println("\nrbrecallneutral:"+((glb.recallneutral/parameter.isneutralreview)*100));
        
        System.out.println("\nrbgoodprecision:"+((glb.goodcorrect/(glb.goodcorrect+glb.goodwrong))*100));
        System.out.println("\nrbbadprecision:"+((glb.badcorrect/(glb.badcorrect+glb.badwrong))*100));
        System.out.println("\nrbneutralprecision:"+((glb.neutralcorrect/(glb.neutralcorrect+glb.neutralwrong))*100)); 
        
        re=nb2.main(res,rb);
        
        for(int j=0;j<12;j++)
        re2[j]=re[j];
       
        re2[12]=Float.toString((glb.recallgood/parameter.isgoodreview)*100);
        re2[13]=Float.toString((glb.recallbad/parameter.isbadreview)*100);
        re2[14]=Float.toString((glb.recallneutral/parameter.isneutralreview)*100);
        re2[15]=Float.toString((glb.goodcorrect/(glb.goodcorrect+glb.goodwrong))*100);
        re2[16]=Float.toString((glb.badcorrect/(glb.badcorrect+glb.badwrong))*100);
        re2[17]=Float.toString((glb.neutralcorrect/(glb.neutralcorrect+glb.neutralwrong))*100);
        sa.Feature.main(re2);
  
    }
}