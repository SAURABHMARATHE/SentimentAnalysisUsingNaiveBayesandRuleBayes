package sa;
import java.io.*;
import rita.wordnet.RiWordnet;
import java.awt.*;
import javax.swing.*;
import java.lang.*;

class Feat{
    String f=null;
    float score=0;
}

class PaintPanel extends JPanel{
    String os0,os1,os2,os3,os4,os5,os6,os7,os8,os9,os10,os11,os12,os13,os14,os15,os16,os17;
    PAUCR temppa3=new PAUCR();
    String[] fe=new String[1000];
    int[] score2=new int[1000];
    
    PaintPanel(Feat[]ff ,String[] os){
        for(int i=0; i<5 && ff[i].f!=null; i++){
            fe[i]=ff[i].f;
            score2[i]=(int)Math.round(ff[i].score*0.4);
        }
        os0=os[0];
        os1=os[1];
        os2=os[2];
        os3=os[3];
        os4=os[4];
        os5=os[5];
        os6=os[6];
        os7=os[7];
        os8=os[8];
        os9=os[9];
        os10=os[10];
        os11=os[11];
        os12=os[12];
        os13=os[13];
        os14=os[14];
        os15=os[15];
        os16=os[16];
        os17=os[17];
    //    temppa3=temppa2;
        setBorder(BorderFactory.createLineBorder(Color.YELLOW,5));
    }
    //@Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int x1,px1,pf,recallp;
        //PAUCR p2=new PAUCR();
        
        nb2 nb=new nb2();
        Value v2=new Value();
        g.setFont(new Font(Font.DIALOG,Font.PLAIN,14));
        g.drawRoundRect(474, 34, 547, 524, 50, 50);
        g.setColor(Color.WHITE);
        g.fillRoundRect(475, 35, 545, 522, 50, 50);

        //feature
        //X-axis
        g.setColor(Color.BLACK);
        g.drawLine(570,300,995,300);
        g.drawString(">",991,305);
        g.drawString("X",980,315);
        g.drawString("Feature",750,340);
        g.drawString("0",555,305);

        //Y-axis
        g.drawLine(570,60,570,540);
        g.drawString("^",568,66);
        g.drawString("Y",557,80);
        g.drawString("v",567,543);
        g.drawString("Score",500,200);
        
        px1=535; pf=595;
        for(int i=0;score2[i]!=0;i++){
            x1=px1+75;
            g.setColor(Color.BLUE);
            g.drawString(fe[i],pf,315);
            
            if(score2[i] > 0){
                g.setColor(Color.GREEN);
              g.drawLine(565,300-score2[i]*20,575,300-score2[i]*20);
              g.fillRect(x1, 300-score2[i]*20, 10, score2[i]*20);
                g.setColor(Color.BLUE);
                if(score2[i] == 10)
                    g.drawString(Integer.toString(score2[i]),546,307-score2[i]*20);
                else
                    g.drawString(Integer.toString(score2[i]),555,307-score2[i]*20);
            }
            else if(score2[i] < 0){
                g.setColor(Color.RED);
                g.drawLine(565,300-score2[i]*20,575,300-score2[i]*20);
                g.fillRect(x1, 300, 10, -score2[i]*20);
                g.setColor(Color.BLUE);
                if(score2[i] == -10)
                    g.drawString(Integer.toString(score2[i]),542,307-score2[i]*20);
                else
                    g.drawString(Integer.toString(score2[i]),550,307-score2[i]*20);
            }
            px1=x1; pf+=75;
        }

        //overall sentiment
        g.setColor(new Color(240, 230, 140));
        g.fillOval(548, 582, 280, 90);
        g.setColor(Color.BLACK);
        g.drawOval(548, 582, 280, 90);
        g.setFont(new Font(Font.DIALOG,Font.BOLD,14));
        g.drawString("Overall sentiment",630,610);
        g.drawString("Rule-based approach: ",585,630);
        g.drawString("Naive Bayes approach: ",585,647);
        g.setColor(Color.BLUE);
        g.drawString(os0,750,630);
        g.drawString(os1,750,647);
        
        g.setColor(Color.BLACK);
        g.drawRect(840,580,560,84);
        g.setColor(new Color(240, 230, 140));
        g.fillRect(840,580,560,84);
        g.setColor(Color.BLACK);
        g.drawString("RB recallgood:",850,610);
        g.drawString("RB recallbad:",850,627);
        g.drawString("RB recallneutral:",850,644);
        g.setColor(Color.BLUE);
        g.drawString(os12,950,610);
        g.drawString(os13,950,627);
        g.drawString(os14,980,644);
        
        g.setColor(Color.BLACK);
        g.drawString("RB goodprecision:",1120,610);
        g.drawString("RB badprecision:",1120,627);
        g.drawString("RB neutralprecision:",1120,644);
        g.setColor(Color.BLUE);
        g.drawString(os15,1270,610);
        g.drawString(os16,1250,627);
        g.drawString(os17,1280,644);
        
 /*       g.setColor(Color.BLACK);
        g.drawRect(10,580,520,84);
        g.setColor(new Color(240, 230, 140));
        g.fillRect(10,580,520,84);
        
        g.setColor(Color.BLACK);
        g.drawString("NB recallgood:",20,610);
        g.drawString("NB recallbad:",20,627);
        g.drawString("NB recallneutral:",20,644);
        g.setColor(Color.BLUE);
        g.drawString(os6,150,610);
        g.drawString(os7,150,627);
        g.drawString(os8,150,644);
        
        g.setColor(Color.BLACK);
        g.drawString("NB goodprecision:",270,610);
        g.drawString("NB badprecision:",270,627);
        g.drawString("NB neutralprecision:",270,644);
        g.setColor(Color.BLUE);
        g.drawString(os9,400,610);
        g.drawString(os10,400,627);
        g.drawString(os11,430,644);*/
        
        //recall-precision
        /*g.setColor(new Color(240, 230, 140));
        g.fillOval(548, 582, 280, 90);
        g.setColor(Color.BLACK);
        g.drawOval(548, 582, 280, 90);
        g.setFont(new Font(Font.DIALOG,Font.BOLD,14));
        g.drawString("Overall sentiment",630,610);
        g.drawString("Rule-based approach: ",585,630);
        g.drawString("Naive Bayes approach: ",585,647);
        g.setColor(Color.BLUE);
        g.drawString(os1,750,630);
        g.drawString(os2,750,647);*/
        //precision, recall
        g.setColor(Color.YELLOW);
        g.fillRoundRect(57, 35, 340, 300, 50, 50);
        g.fillRoundRect(57, 376, 340, 300, 50, 50);
        g.setColor(Color.BLACK);
        g.drawRoundRect(57, 35, 340, 300, 50, 50);
        g.drawRoundRect(57, 376, 340, 300, 50, 50);
        g.drawString("Precision", 72, 60);
        g.drawString("Recall", 72, 400);
        g.setFont(new Font(Font.DIALOG,Font.PLAIN,14));        
        
        //Rule-based
        //X-axis
        g.setColor(Color.BLACK);
        g.drawLine(142, 280, 350, 280);
        g.drawString("0", 125, 280);
        g.drawString("X", 335, 295);
        g.drawString("Approach", 200, 312);
        g.drawString(">", 346, 285);
        g.setColor(Color.BLUE);
        g.drawString("RB", 180, 295);
        
        float os15temp=Float.parseFloat((os15));
        int os15temp2=(int)(os15temp);
        g.drawLine(178,280,178,(280-os15temp2));
        g.drawString(os15,178,(280-os15temp2)-2);
        
        g.setColor(Color.BLACK);
        float os16temp=Float.parseFloat((os16));
        int os16temp2=(int)(os16temp);
        g.drawLine(184,280,184,(280-os16temp2));
        g.drawString(os16,184,(280-os16temp2)-2);
        
        g.setColor(Color.GRAY);
        float os17temp=Float.parseFloat((os17));
        int os17temp2=(int)(os17temp);
        g.drawLine(190,280,190,(280-os17temp2));
        g.drawString(os17,190,(280-os17temp2)-2);
        
        g.setColor(new Color(128, 0, 0));
        g.drawString("NB", 255, 295);

        g.setColor(Color.BLUE);
        float os9temp=Float.parseFloat((os9));
        int os9temp2=(int)(os9temp);
        g.drawLine(253,280,253,(280-os9temp2));
        g.drawString(os9,253,(280-os9temp2)-2);
        
        
        g.setColor(Color.BLACK);
        float os10temp=Float.parseFloat((os10));
        int os10temp2=(int)(os10temp);
        g.drawLine(259,280,259,(280-os10temp2));
        g.drawString(os10,259,(280-os10temp2)-2);
        
        g.setColor(Color.GRAY);
        float os11temp=Float.parseFloat((os11));
        int os11temp2=(int)(os11temp);
        g.drawLine(265,280,265,(280-os11temp2));
        g.drawString(os11,265,(280-os11temp2)-2);
        
        //Y-axis
        g.setColor(Color.BLACK);
        g.drawLine(142, 80, 142, 280);
        g.drawString("Y", 130, 100);
        g.drawString("^", 140, 86);
        g.drawString("Value", 70, 165);
        g.drawString("(in %)", 70, 178);

        //Naive Bayes
        //X-axis
        g.drawLine(142, 618, 350, 618);
        g.drawString("0", 125, 618);
        g.drawString("X", 335, 633);
        g.drawString("Approach", 200, 650);
        g.drawString(">", 346, 623);
        g.setColor(Color.BLUE);
        g.drawString("RB", 180, 633);
        
        float os12temp=Float.parseFloat((os12));
        int os12temp2=(int)(os12temp);
        g.drawLine(178,618,178,(618-os12temp2));
        g.drawString(os12,178,(618-os12temp2)-2);
        
        g.setColor(Color.BLACK);
        float os13temp=Float.parseFloat((os13));
        int os13temp2=(int)(os13temp);
        g.drawLine(184,618,184,(618-os13temp2));
        g.drawString(os13,184,(618-os13temp2)-2);
        
        g.setColor(Color.GRAY);
        float os14temp=Float.parseFloat((os14));
        int os14temp2=(int)(os14temp);
        g.drawLine(190,618,190,(618-os14temp2));
        g.drawString(os14,190,(618-os14temp2)-2);
        
        
        g.setColor(new Color(128, 0, 0));
        g.drawString("NB", 255, 633);
        
        g.setColor(Color.BLUE);
        float os6temp=Float.parseFloat((os6));
        int os6temp2=(int)(os6temp);
        g.drawLine(253,618,253,(618-os6temp2));
        g.drawString(os6,253,(618-os6temp2)-2);
        
        g.setColor(Color.BLACK);
        float os7temp=Float.parseFloat((os7));
        int os7temp2=(int)(os7temp);
        g.drawLine(259,618,259,(618-os7temp2));
        g.drawString(os7,259,(618-os7temp2)-2);
        
        g.setColor(Color.GRAY);
        float os8temp=Float.parseFloat((os8));
        int os8temp2=(int)(os8temp);
        g.drawLine(265,618,265,(618-os8temp2));
        g.drawString(os8,265,(618-os8temp2)-2);
        
        //Y-axis
        g.setColor(Color.BLACK);
        g.drawLine(142, 418, 142, 618);
        g.drawString("Y", 130, 438);
        g.drawString("^", 140, 424);
        g.drawString("Value", 70, 503);
        g.drawString("(in %)", 70, 518);
        
        g.setColor(Color.GREEN);
        
        
            
    }
}

public class Feature{
    void GraphFeat(Feat[] ff,String[] os){
        PaintPanel pp;
        JFrame jfrm=new JFrame("Product Analysis using Customer Reviews");
        jfrm.setExtendedState(jfrm.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pp=new PaintPanel(ff,os);
        pp.setLayout(null);
        pp.setBackground(new Color(102,205,170));

        JTextArea ta=new JTextArea("\n                  All features\n\n   Feature\t      Score\n");
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setEditable(false);
        ta.setBorder(BorderFactory.createLineBorder(Color.YELLOW,3));
        
        for(int i=0; ff[i].f!=null && i<1000; i++){
            if(ff[i].score < 0){
                if((int)Math.round(ff[i].score*0.4) > -10)
                    ta.append("   "+ff[i].f+"\t          "+(int)Math.round(ff[i].score*0.4)+"\n");
                else
                    ta.append("   "+ff[i].f+"\t        "+(int)Math.round(ff[i].score*0.4)+"\n");
            }
            else{
                if((int)Math.round(ff[i].score*0.4) < 10)                    
                    ta.append("   "+ff[i].f+"\t            "+(int)Math.round(ff[i].score*0.4)+"\n");
                else
                    ta.append("   "+ff[i].f+"\t          "+(int)Math.round(ff[i].score*0.4)+"\n");
            }
        }
        
        JScrollPane scroll=new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(1110,125,185,330);
        pp.add(scroll);
        
        jfrm.getContentPane().add(pp);
        jfrm.setVisible(true);
    }

    void Extract(Feat[] ft,Gd glb3,RiWordnet r,PAUCR pa2) throws IOException{
        File review;
        BufferedReader br;
        String str="";
        String []token=new String[1000];
        review=new File("C:\\ccc\\Project\\src\\sa\\DataNew.txt");
        br=new BufferedReader(new InputStreamReader(new FileInputStream(review)));
        while((str=br.readLine())!=null){
            token=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
            Task1(ft,token,glb3,r,pa2);
            Task2(ft,token,glb3,r,pa2);
        }
        br.close();
    }
    
    void Task1(Feat[] ft,String[] t,Gd glb3,RiWordnet r,PAUCR pa2) throws IOException{
        File review;
        BufferedReader br;
        String str="";
        String []token=new String[100];
        int i=0,k=0,back=999,ahead=999,prev=0,next=0,p=0,flag=0,nf=0,size=0,index=0;
        String x="";
        String[] token2=new String[1];
        int[] adj=new int[1000];
        int[] fs=new int[1000];
        int[] vi=new int[1000];
        review=new File("C:\\ccc\\Project\\src\\sa\\dictwithscore.txt");
        br=new BufferedReader(new InputStreamReader(new FileInputStream(review)));
        for(i=0;i<1000;i++)
            adj[i]=-1;
        index=0;
        while((str=br.readLine())!=null){
            token=str.trim().split("\\s*(=>|,|\\.|!|#|\\(|\\)|\\/|\\s)\\s*");
            size=token.length;
            for(i=0;i<t.length;i++){
                if(vi[i]==0 && token[0].equalsIgnoreCase(t[i])){
                    vi[i]=1;
                    adj[index]=i;
                    fs[index++]=Integer.parseInt(token[size-1]);
                }
            }
        }
        br.close();
        
        for(i=0;adj[i]!=-1 && i<adj.length;i++){
            nf=0;
            for(k=adj[i];k>=0&&k!=adj[i]-4;k--){
                token2[0]=t[k];
                int e=pa2.Compare(glb3,token2,1,0,0);
                if(e!=0){
                    nf=1;
                    break;
                }
            }
             //checking noun term before this adjective
             k=adj[i]-1;
             while(k>=0){
                 back=999;
                 if(r.isNoun(t[k]) && (x=r.getBestPos(t[k])).equals("n")){
                     token2[0]=t[k];
                     int abc=pa2.Compare(glb3,token2,5,0,0);
                     if(abc!=-1){
                         //no n/a between this n and a
                         int lmn=CheckNA(t,k+1,adj[i],r);
                         if(lmn==0){
                             back=Math.abs(adj[i]-k);
                             prev=k;
                             break;
                         }
                     }
                 }
                 k--;
             }
             
             //checking noun term after this adjective
             k=adj[i]+1;
             while(k<t.length){
                 ahead=999;
                 if(r.isNoun(t[k])&&(x=r.getBestPos(t[k])).equals("n")){
                     token2[0]=t[k];
                     int abc=pa2.Compare(glb3,token2,5,0,0);
                     if(abc!=-1){
                         //no n/a between this n and a
                         int lmn=CheckNA(t,adj[i]+1,k,r);
                         if(lmn==0){
                             ahead=Math.abs(adj[i]-k);
                             next=k;
                             break;
                         }
                     }
                 }
                 k++;
             }

             //add feature
             if(back==ahead && (back!=999 && ahead!=999)){
                 flag=0;
                 for(p=0;p<1000&&ft[p].f!=null;p++){
                     if(t[prev].equalsIgnoreCase(ft[p].f)){
                         flag=1;
                         break;
                     }
                 }
                 if(flag==0){
                     switch(nf){
                         case 0: Add(ft,t[prev],fs[i]);
                                 break;
                         case 1: Add(ft,t[prev],-fs[i]);
                                 break;
                     }
                 }
                 switch(nf){
                     case 0: Add(ft,t[next],fs[i]);
                             break;
                     case 1: Add(ft,t[next],-fs[i]);
                             break;
                 }
             }

             else if(back < ahead){
                 switch(nf){
                         case 0: Add(ft,t[prev],fs[i]);
                                 break;
                         case 1: Add(ft,t[prev],-fs[i]);
                                 break;
                 }
             }

             else if(ahead < back){
                 switch(nf){
                     case 0: Add(ft,t[next],fs[i]);
                             break;
                     case 1: Add(ft,t[next],-fs[i]);
                             break;
                 }
             }
        }
    }
    
    int CheckNA(String[] t,int i,int j,RiWordnet r) throws IOException{
        for(;i<j;i++){
            if(r.isNoun(t[i]) || r.isAdjective(t[i]))
                return -1;
        }
        return 0;
    }

    void Add(Feat[] ft,String fet,float val) throws IOException{
        int i=0,k=0,flag=0;

        for(i=0;ft[i].f!=null && i<1000;i++){
            if(fet.equalsIgnoreCase(ft[i].f)){
                flag=1;
                break;
            }
        }

        switch(flag){
            case 0: for(k=0;k<100&&ft[k].f!=null;k++){}
                    ft[k].f=fet;
                    ft[k].score+=val;
                    break;
            case 1: ft[i].score+=val;
                    break;
        }
    }

    void Task2(Feat[] ft,String[] t,Gd glb3,RiWordnet r,PAUCR pa2) throws IOException{
        int i=0,j=0,k=0,q=0,s1=0,s2=0;
        String x="";
        String[] token2=new String[1];
        do{
            for(i=0;ft[i].f!=null&&i<1000;i++){}
            s1=i;
        
            for(i=0;i<t.length;i++){
                for(j=0;j<1000&&ft[j].f!=null;j++){
                    if(t[i].equalsIgnoreCase(ft[j].f)){
                        //check N before A
                        k=i-1;
                        if(k>=0){
                            q=T2(t,k,r);
                            if(q==0){
                                token2[0]=t[k];
                                q=pa2.Compare(glb3,token2,5,0,0);
                                if(q!=-1){
                                    q=LookA(ft,t[k]);
                                    if(q==0) Add(ft,t[k],ft[j].score);
                                }
                            }
                            if(k>=0 && t[k].equalsIgnoreCase("and")){
                                k--;
                                if(k>=0){
                                    q=T2(t,k,r);
                                    if(q==0){
                                        token2[0]=t[k];
                                        q=pa2.Compare(glb3,token2,5,0,0);
                                        if(q!=-1){
                                            q=LookA(ft,t[k]);
                                            if(q==0) Add(ft,t[k],ft[j].score);
                                        }
                                    }
                                }
                            }
                            if(k>=0 && (t[k].equalsIgnoreCase("the")||t[i].equalsIgnoreCase("a"))){
                                k--;
                                if(k>=0 && (t[k].equalsIgnoreCase("and"))){
                                    k--;
                                    if(k>=0){
                                        q=T2(t,k,r);
                                        if(q==0){
                                            token2[0]=t[k];
                                            q=pa2.Compare(glb3,token2,5,0,0);
                                            if(q!=-1){
                                                q=LookA(ft,t[k]);
                                                if(q==0) Add(ft,t[k],ft[j].score);
                                            }
                                        }
                                    }
                                }
                                else if(k>=0){
                                    q=T2(t,k,r);
                                    if(q==0){
                                        token2[0]=t[k];
                                        q=pa2.Compare(glb3,token2,5,0,0);
                                        if(q!=-1){
                                            q=LookA(ft,t[k]);
                                            if(q==0) Add(ft,t[k],ft[j].score);
                                        }
                                    }
                                }
                            }
                        }
                    
                        //check N after A
                        k=i+1;
                        if(k<t.length){
                            q=T2(t,k,r);
                            if(q==0){
                                token2[0]=t[k];
                                q=pa2.Compare(glb3,token2,5,0,0);
                                if(q!=-1){
                                    q=LookA(ft,t[k]);
                                    if(q==0)
                                        Add(ft,t[k],ft[j].score);
                                }
                            }
                            if(k<t.length && t[k].equalsIgnoreCase("and")){
                                k++;
                                if(k<t.length){
                                    q=T2(t,k,r);
                                    if(q==0){
                                        token2[0]=t[k];
                                        q=pa2.Compare(glb3,token2,5,0,0);
                                        if(q!=-1){
                                            q=LookA(ft,t[k]);
                                            if(q==0)
                                                Add(ft,t[k],ft[j].score);
                                        }
                                    }
                                }
                            }
                            if(k<t.length && (t[k].equalsIgnoreCase("the")||t[i].equalsIgnoreCase("a"))){
                                k++;
                                if(k<t.length){
                                    q=T2(t,k,r);
                                    if(q==0){
                                        token2[0]=t[k];
                                        q=pa2.Compare(glb3,token2,5,0,0);
                                        if(q!=-1){
                                            q=LookA(ft,t[k]);
                                            if(q==0)
                                                Add(ft,t[k],ft[j].score);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for(i=0;ft[i].f!=null&&i<1000;i++){}
            s2=i;
        }while(s1!=s2);
    }

    int T2(String[] t,int i,RiWordnet r) throws IOException{
        String x="";
        if(r.isNoun(t[i]) && (x=r.getBestPos(t[i])).equals("n"))
            return 0;
        return -1;
    }

    int LookA(Feat[] ft,String t) throws IOException{
        for(int i=0;i<1000&&ft[i].f!=null;i++){
            if(t.equalsIgnoreCase(ft[i].f))
                return -1;
        }
        return 0;
    }

    void Sort(Feat[] ff){
        int i,j;
        float temp;
        String tmp;
        for(i=0; ff[i].f!=null; i++){
            for(j=0; ff[j+1].f!=null; j++){
                if(ff[j].score < ff[j+1].score){
                    tmp = ff[j].f;
                    temp = ff[j].score;
                    ff[j].f = ff[j+1].f;
                    ff[j].score = ff[j+1].score;
                    ff[j+1].f = tmp;
                    ff[j+1].score = temp;
                }
            }
        }

        for(i=0; ff[i].f!=null; i++){
            if(ff[i].score >= 25)
                ff[i].score=25;
            else if(ff[i].score <= -25)
                ff[i].score=-25;
        }
    }

    public static void main(String args[]) throws IOException{
        PAUCR pa2=new PAUCR();
        //final PAUCR temppa2=new PAUCR();
        /*temppa2=parameter;*/
        Gd glb3=new Gd();
        final Feature feate=new Feature();
        int i=0;
        final Feat[] ft=new Feat[1000];
        try
		{
        	
		}
        catch(Exception e)
		{
        	System.out.println("Neutral Sentiment!!");
		}
        for(i=0;i<1000;i++)
            ft[i]=new Feat();
        RiWordnet r=new RiWordnet();
        feate.Extract(ft,glb3,r,pa2);
        feate.Sort(ft);
        
        final String[] os=new String[18];
        os[0]=args[0];
        os[1]=args[1];
        os[2]=args[2];
        os[3]=args[3];
        os[4]=args[4];
        os[5]=args[5];
        os[6]=args[6];
        os[7]=args[7];
        os[8]=args[8];
        os[9]=args[9];
        os[10]=args[10];
        os[11]=args[11];
        os[12]=args[12];
        os[13]=args[13];
        os[14]=args[14];
        os[15]=args[15];
        os[16]=args[16];
        os[17]=args[17];
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                feate.GraphFeat(ft,os);
            }
        });
    }
}