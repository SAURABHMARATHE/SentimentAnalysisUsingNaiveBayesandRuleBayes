package sa;
import java.io.*;
import rita.wordnet.RiWordnet;
//global declaration
class Gd3{
int positiveScore=0,negativeScore=0,sentimentCount=0,oppositeSentimentScore=0,totalScore=0;
int negationWeight=2,oppositeConjunctionWeight=-1;
float pCount=0,pShare=0,nCount=0,nShare=0;
}

class Seed{
    String[] A=new String[1000];
    String[] vas=new String[1000];
    int[] vasValue=new int[1000];
    float[] score=new float[1000];
    String[] adj=new String[1000];
}

class Feat2{
    String f="";
    float score=0;
}

public class RBNew2{
public void Mybegin() throws IOException{
FileInputStream r;
FileOutputStream f;
int i=0;
String str="";
//pre-processing customer review file
r=new FileInputStream("C:\\ccc\\Project\\src\\sa\\Sample.txt");
f=new FileOutputStream("C:\ccc\Project\src\sa\DataNew.txt");
do{
i=r.read();
	if(i!=-1)
	{
	  if(i=='.'){
             PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("C:\ccc\Project\src\sa\DataNew.txt",true)));
             str+=" "; str+="#";
             out.println(str);
             out.close(); str="";
      }
      else if(i=='\r' || i=='\n')
          str+=" ";
      else str+=(char)i;
	}
}while(i!=-1);
    if(!str.equals("")){
        PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("C:\ccc\Project\src\sa\DataNew.txt",true)));
        str+=" "; str+="#";
        out.println(str);
        out.close(); str="";
    }
f.close(); r.close();
}

void GrowAdj(Seed s,RiWordnet r) throws IOException{
    File review;
    BufferedReader br;
    String str="",x="";
    String []token=new String[100];
    int i=0,j=0,k=0;
    review=new File("C:\ccc\Project\src\sa\DataNew.txt");
    br=new BufferedReader(new InputStreamReader(new FileInputStream(review)));
    while((str=br.readLine())!=null){
        token=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
        j=0;
        while(j<token.length){
            if(r.isAdjective(token[j]) && (x=r.getBestPos(token[j])).equals("a")){
                for(k=0;s.adj[k]!=null;k++){}
                s.adj[k]=token[j];
            }
            j++;
        }
    }
    OrientationPrediction(s,r);
}

void OrientationPrediction(Seed s,RiWordnet r){
    int i=0,size1=0,size2=0;
    do{
        for(i=0;s.vas[i]!=null;i++){}
        size1=i;
        OrientationSearch(s,r);
        for(i=0;s.vas[i]!=null;i++){}
        size2=i;
    }while(size1!=size2);
}

void OrientationSearch(Seed s,RiWordnet r){
    int i=0,j=0,k=0,l=0,adjSize=0,vasSize=0;
    for(i=0;s.adj[i]!=null;i++){}
    adjSize=i;
    for(i=0;i<adjSize;i++){
        String[] syn=r.getSynonyms(s.adj[i],"a");
        if(syn!=null && syn.length>0){
            for(l=0;s.vas[l]!=null;l++){}
            vasSize=l;
            for(j=0;j<syn.length;j++){
                for(k=0;k<vasSize;k++){
                    if(syn[j].toUpperCase().equals(s.vas[k].toUpperCase())){
                        int x=LookUp(s,s.adj[i]);
                        if(x==0){
                            for(l=0;s.vas[l]!=null;l++){}
                            s.vas[l]=s.adj[i]; s.vasValue[l]=s.vasValue[k];
                        }
                    }
                }
            }
        }
        else{
            String[] a=r.getAntonyms(s.adj[i],"a");
            if(a!=null && a.length>0){
                for(l=0;s.vas[l]!=null;l++){}
                vasSize=l;
                for(j=0;j<a.length;j++){
                    for(k=0;k<vasSize;k++){
                        if(a[j].toUpperCase().equals(s.vas[k].toUpperCase())){
                            int x=LookUp(s,s.adj[i]);
                            if(x==0){
                                for(l=0;s.vas[l]!=null;l++){}
                                s.vas[l]=s.adj[i];
                                s.vasValue[l]=-s.vasValue[k];
                            }
                        }
                    }
                }
            }
        }
    }   
}

int LookUp(Seed s,String t){
    int i=0;
    for(i=0;s.vas[i]!=null;i++){
        if(t.toUpperCase().equals(s.vas[i].toUpperCase()))
            return -1;
    }
    return 0;
}

int Compare(Gd3 glb,String []t,int x,int z) throws IOException{
File m=null;
String str2="";
String []tfile=new String[1000];
BufferedReader br2;
int i=0,j=0,flag=0;
  if(x==1)
    m=new File("C:\\ccc\\Project\\src\\sa\\negation.txt");
  else if(x==2)
    m=new File("C:\\ccc\\Project\\src\\sa\\positive.txt");
  else if(x==3)
    m=new File("C:\\ccc\\Project\\src\\sa\\negative.txt");
  else if(x==4)
    m=new File("C:\\ccc\\Project\\src\\sa\\oc.txt");
  else if(x==5)
    m=new File("C:\\ccc\\Project\\src\\sa\\av.txt");

	br2=new BufferedReader(new InputStreamReader(new FileInputStream(m)));
	while((str2=br2.readLine())!=null)
	{
	  tfile=str2.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\/|\\s)\\s*");
	  for(i=0;i<t.length;i++)
	  {
	    for(j=0;j<tfile.length;j++)
	    {
            if(t[i].toUpperCase().equals(tfile[j].toUpperCase()))
            {
            flag=1;
            if(x==1 && z==0){ glb.nCount+=2;  return i+1;}
            else if(x==4 && z==0)
                glb.oppositeSentimentScore=glb.oppositeConjunctionWeight*glb.sentimentCount/2;
            else if(x==2 && z==1){ glb.positiveScore-=glb.negationWeight; return 1;}
            else if(x==3 && z==1){ glb.negativeScore-=glb.negationWeight; return 1;}
            else if(x==2 && z==0){ glb.positiveScore+=1; glb.pCount++;}
            else if(x==3 && z==0){ glb.negativeScore+=1; glb.nCount++;}
            else if(x==2 && z==2){ glb.sentimentCount+=1; return 1;}
            else if(x==3 && z==2){ glb.sentimentCount+=1; return 1;}
            else if(x==5 && z==0){ return -1;}
            }
        }
	  }//end of for
	}//end of dictionary
	if(flag==1) return 1;
	return 0;
}

int CheckNA(String[] t,int i,int j,RiWordnet r) throws IOException{
    for(;i<j;i++){
        if(r.isNoun(t[i]) || r.isAdjective(t[i])) return -1;
    }
    return 0;
}

void Task1(Seed s,String[] t,Gd3 glb,RiWordnet r) throws IOException{
    int i=0,j=0,k=0,size=0,back=999,ahead=999,prev=0,next=0,p=0,flag=0,nf=0;
    String x="";
    String[] token2=new String[1];
    for(i=0;s.vas[i]!=null;i++){}
    size=i;
    
    for(i=0;i<t.length;i++){
        back=999; ahead=999;
        for(j=0;j<size;j++){
            if(s.vas[j].toUpperCase().equals(t[i].toUpperCase())){
                nf=0;
                for(k=i-1;k>=0&&k!=i-4;k--){
                    token2[0]=t[k];
                    int e=Compare(glb,token2,1,0);
                    if(e!=0){
                        nf=1; break;
                    }
                }
                //checking noun term before this adjective
                k=i-1;
                while(k>=0){
                    back=999;
                    if(r.isNoun(t[k]) && (x=r.getBestPos(t[k])).equals("n")){
                        token2[0]=t[k];
                        int abc=Compare(glb,token2,5,0);
                        if(abc!=-1){
                            //no n/a between this n and a
                            int lmn=CheckNA(t,k+1,i,r);
                            if(lmn==0){
                                back=Math.abs(i-k);
                                prev=k; break;
                            }
                        }
                    }
                    k--;
                }
                //checking noun term after this adjective
                k=i+1;
                while(k<t.length){
                    ahead=999;
                    if(r.isNoun(t[k])&&(x=r.getBestPos(t[k])).equals("n")){
                        token2[0]=t[k];
                        int abc=Compare(glb,token2,5,0);
                        if(abc!=-1){
                            //no n/a between this n and a
                            int lmn=CheckNA(t,i+1,k,r);
                            if(lmn==0){
                                ahead=Math.abs(i-k);
                                next=k; break;
                            }
                        }
                    }
                    k++;
                }
                if(back==ahead && (back!=999 && ahead!=999)){
                        flag=0;
                        for(p=0;p<1000&&s.A[p]!=null;p++){
                            if(t[prev].toUpperCase().equals(s.A[p].toUpperCase())){
                                flag=1; break;
                            }
                        }
                        if(flag==0){
                            if(nf==1)
                                Add(s,t[prev],-s.vasValue[j]);
                            else
                                Add(s,t[prev],s.vasValue[j]);
                        }
                        if(nf==1)
                            Add(s,t[next],-s.vasValue[j]);
                        else
                            Add(s,t[next],s.vasValue[j]);
                }
                else if(back < ahead){
                    if(nf==1)
                        Add(s,t[prev],-s.vasValue[j]);
                    else
                        Add(s,t[prev],s.vasValue[j]);
                }
                else if(ahead < back){
                    if(nf==1)
                        Add(s,t[next],-s.vasValue[j]);
                    else
                        Add(s,t[next],s.vasValue[j]);
                }
            }//end of adjective match
        }//end of token
    }//end of vas
}

void Task2(Seed s,String[] t,Gd3 glb,RiWordnet r) throws IOException{
    int i=0,j=0,k=0,q=0,s1=0,s2=0;
    String x="";
    String[] token2=new String[1];
    do{
        for(i=0;i<1000&&s.A[i]!=null;i++){}
        s1=i;
        
        for(i=0;i<t.length;i++){
            for(j=0;j<1000&&s.A[j]!=null;j++){
                if(t[i].equalsIgnoreCase(s.A[j])){
                    //check N before A
                    k=i-1;
                    if(k>=0){
                        q=T2(s,t,k,r);
                        if(q==0){
                            token2[0]=t[k];
                            q=Compare(glb,token2,5,0);
                            if(q!=-1){
                                q=LookA(s,t[k]);
                                if(q==0) Add(s,t[k],s.score[j]);
                            }
                        }
                        if(k>=0 && t[k].equalsIgnoreCase("and")){
                            k--;
                            if(k>=0){
                                q=T2(s,t,k,r);
                                if(q==0){
                                    token2[0]=t[k];
                                    q=Compare(glb,token2,5,0);
                                    if(q!=-1){
                                        q=LookA(s,t[k]);
                                        if(q==0) Add(s,t[k],s.score[j]);
                                    }
                                }
                            }
                        }
                        if(k>=0 && (t[k].equalsIgnoreCase("the")||t[i].equalsIgnoreCase("a"))){
                            k--;
                            if(k>=0){
                                q=T2(s,t,k,r);
                                if(q==0){
                                    token2[0]=t[k];
                                    q=Compare(glb,token2,5,0);
                                    if(q!=-1){
                                        q=LookA(s,t[k]);
                                        if(q==0) Add(s,t[k],s.score[j]);
                                    }
                                }
                            }
                        }
                    }
                    
                    //check N after A
                    k=i+1;
                    if(k<t.length){
                        q=T2(s,t,k,r);
                        if(q==0){
                            token2[0]=t[k];
                            q=Compare(glb,token2,5,0);
                            if(q!=-1){
                                q=LookA(s,t[k]);
                                if(q==0) Add(s,t[k],s.score[j]);
                            }
                        }
                        if(k<t.length && t[k].equalsIgnoreCase("and")){
                            k++;
                            if(k<t.length){
                                q=T2(s,t,k,r);
                                if(q==0){
                                    token2[0]=t[k];
                                    q=Compare(glb,token2,5,0);
                                    if(q!=-1){
                                        q=LookA(s,t[k]);
                                        if(q==0) Add(s,t[k],s.score[j]);
                                    }
                                }
                            }
                        }
                        if(k<t.length && (t[k].equalsIgnoreCase("the")||t[i].equalsIgnoreCase("a"))){
                            k++;
                            if(k<t.length){
                                q=T2(s,t,k,r);
                                if(q==0){
                                    token2[0]=t[k];
                                    q=Compare(glb,token2,5,0);
                                    if(q!=-1){
                                        q=LookA(s,t[k]);
                                        if(q==0) Add(s,t[k],s.score[j]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for(i=0;i<1000&&s.A[i]!=null;i++){}
        s2=i;
    }while(s1!=s2);
    
}

int T2(Seed s,String[] t,int i,RiWordnet r) throws IOException{
    String x="";
    if(r.isNoun(t[i]) && (x=r.getBestPos(t[i])).equals("n"))
        return 0;
    return -1;
}

int LookA(Seed s,String t) throws IOException{
    for(int i=0;i<1000&&s.A[i]!=null;i++){
        if(t.equalsIgnoreCase(s.A[i])) return -1;
    }
    return 0;
}

void Add(Seed s,String fet,float val) throws IOException{
    int i=0,k=0,flag=0;
        for(i=0;i<1000&&s.A[i]!=null;i++){
            flag=0;
            if(fet.toUpperCase().equals(s.A[i].toUpperCase())){
                flag=1; break;
            }
        }
        if(flag==1){
            s.score[i]+=val;
        }
        else if(flag==0){
                for(k=0;k<100&&s.A[k]!=null;k++){}
                s.A[k]=fet;
                s.score[k]+=val;
        }
}

void Pass1(Gd3 glb) throws IOException{
File review;
BufferedReader br;
String str="";
String []token=new String[100];
String []token2=new String[1];
int i=0,j=0,k=0,ans=0;
review=new File("C:\\ccc\\Project\\src\\sa\\DataNew.txt");
br=new BufferedReader(new InputStreamReader(new FileInputStream(review)));
while((str=br.readLine())!=null)
{
	token=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
	j=0;
	i=0;
	while(j!=token.length)
	{
	  token2[0]=token[j++];
	  i=Compare(glb,token2,1,0);
	    if(i!=0)
	    {
            for(k=0;k<3 && j!=token.length;k++)
            {
                token2[0]=token[j++];
                ans=Compare(glb,token2,2,1);
                if(ans==0) ans=Compare(glb,token2,3,1);
            }
	    }
	}
}
}

void Pass2(Gd3 glb) throws IOException{
File review;
BufferedReader br;
String str="";
String []token=new String[1000];
int i=0;
review=new File("C:\\ccc\\Project\\src\\sa\\DataNew.txt");
br=new BufferedReader(new InputStreamReader(new FileInputStream(review)));
while((str=br.readLine())!=null)
{
	token=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
	Compare(glb,token,2,0);
	Compare(glb,token,3,0);
}
}

void Pass3(Gd3 glb) throws IOException{
File review;
BufferedReader br;
String str="";
String []token=new String[100];
String []token2=new String[1];
int j=0;
review=new File("C:\\ccc\\Project\\src\\sa\\DataNew.txt");
br=new BufferedReader(new InputStreamReader(new FileInputStream(review)));
while((str=br.readLine())!=null)
{
	token=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
	j=0;
	while(j!=token.length)
	{
	  token2[0]=token[j++];
	  Compare(glb,token2,2,2);
	  Compare(glb,token2,3,2);
	  Compare(glb,token2,4,0);
	}
}
}

void NoPattern(Seed s,RiWordnet r) throws IOException{
	File review;
	BufferedReader br;
	String str="",x="";
	String []token=new String[100];
	int i=0;
	review=new File("C:\\ccc\\Project\\src\\sa\\DataNew.txt");
	br=new BufferedReader(new InputStreamReader(new FileInputStream(review)));
	while((str=br.readLine())!=null)
	{
		token=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
		for(i=0;i<token.length;i++){
			if(token[i].toUpperCase().equals("NO")){
				if(r.isNoun(token[++i])&&(x=r.getBestPos(token[i])).equals("n")){
                    Add(s,token[i],-2);
                }
			}
		}
	}
}

void Extract(Seed s,Feat2[] ft,Gd3 glb,RiWordnet r) throws IOException{
	File review;
	BufferedReader br;
	String str="";
	String []token=new String[1000];
	review=new File("C:\\ccc\\Project\\src\\sa\\DataNew.txt");
	br=new BufferedReader(new InputStreamReader(new FileInputStream(review)));
        while((str=br.readLine())!=null){
            token=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
            Task1(s,token,glb,r);
            Task2(s,token,glb,r);
        }//end of file
}

public static void main(String args[]) throws IOException
{
Gd3 glb=new Gd3();
RBNew2 rb=new RBNew2();
rb.Mybegin(); rb.Pass1(glb); rb.Pass2(glb); rb.Pass3(glb);
//System.out.println("positiveScore= "+glb.positiveScore);
//System.out.println("negativeScore= "+glb.negativeScore);
//System.out.println("oppositeSentimentScore= "+glb.oppositeSentimentScore);
glb.totalScore=glb.positiveScore-glb.negativeScore+glb.oppositeSentimentScore;
//System.out.println("totalScore= "+glb.totalScore);
if(glb.totalScore<0) System.out.print("\n\tOverall sentiment: Negative\n");
else if(glb.totalScore==0) System.out.print("\n\tOverall sentiment: Neutral\n");
else if(glb.totalScore>0) System.out.print("\n\tOverall sentiment: Positive\n");
//rating
glb.pShare=glb.pCount/(glb.pCount+glb.nCount);
glb.nShare=glb.nCount/(glb.pCount+glb.nCount);
if(glb.nShare > glb.pShare)
  System.out.print("\tRating: 0 stars\n");
else
{
  if(glb.pShare == 1)
    System.out.print("\tRating: 5 stars\n");
  else if(glb.pShare > 0.75)
    System.out.print("\tRating: 4 stars\n");
  else if(glb.pShare > 0.50)
    System.out.print("\tRating: 3 stars\n");
  else if(glb.pShare > 0.25)
    System.out.print("\tRating: 2 stars\n");
  else if(glb.pShare > 0)
    System.out.print("\tRating: 1 stars\n");
}
System.out.println("\n");
int i=0;
Feat2[] ft=new Feat2[100];
for(i=0;i<100;i++) ft[i]=new Feat2();
Seed s=new Seed();
s.vas[0]="great"; s.vas[1]="fantastic"; s.vas[2]="nice"; s.vas[3]="cool"; s.vas[4]="bad"; s.vas[5]="dull";
s.vas[6]="good"; s.vas[7]="excellent"; s.vas[8]="big"; s.vas[9]="quality"; s.vas[10]="easy"; s.vas[11]="satisfacory";
s.vas[12]="small"; s.vas[13]="razor-sharp"; s.vas[14]="compact"; s.vas[15]="fast"; s.vas[16]="slow"; s.vas[17]="well";
s.vas[18]="love";  s.vas[19]="weak"; s.vas[20]="strong"; s.vas[21]="favorite"; s.vas[22]="favourite";
s.vas[23]="awesome"; s.vas[24]="pleased"; s.vas[25]="happy"; s.vas[26]="unhappy"; s.vas[27]="awe";
s.vas[28]="perfect";
s.vasValue[0]=1; s.vasValue[1]=1; s.vasValue[2]=1; s.vasValue[3]=1; s.vasValue[4]=-1; s.vasValue[5]=-1;
s.vasValue[6]=1; s.vasValue[7]=1; s.vasValue[8]=1; s.vasValue[9]=1; s.vasValue[10]=1; s.vasValue[11]=1;
s.vasValue[12]=1; s.vasValue[13]=1; s.vasValue[14]=1; s.vasValue[15]=1; s.vasValue[16]=-1; s.vasValue[17]=1;
s.vasValue[18]=1; s.vasValue[19]=-1; s.vasValue[20]=1; s.vasValue[21]=1; s.vasValue[22]=1; s.vasValue[23]=1;
s.vasValue[24]=1; s.vasValue[25]=1; s.vasValue[26]=-1; s.vasValue[27]=1; s.vasValue[28]=1;
RiWordnet r=new RiWordnet();
rb.GrowAdj(s,r);
rb.NoPattern(s,r);
rb.Extract(s,ft,glb,r);
System.out.print("\n");
//for(i=0;s.vas[i]!=null;i++)
//System.out.print(s.vas[i]+" ");
System.out.println("\n\tThe extracted feature(s) are:\n\tFeature\t\t\tScore");
for(i=0;i<100&&s.A[i]!=null;i++){
    System.out.println(String.format("\t%-30s %4.2f",s.A[i],s.score[i]));
}
System.out.println("\n");
}//end of main
}//end of class