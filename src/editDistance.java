import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class editDistance {

		static int[][] D=new int[10][10];//D[i,j]
		static String[] bestCur=new String[10];//store each step state
		static ArrayList bestStep=new ArrayList();
		
		static String[] operation=new String[10];//store each step's operation
		static int[] reJndex=new int[10];//store the best flip position
		static Integer[][] reCi=new Integer[10][10];//store after flip, the different char's position
	public int read() throws IOException {
		return 0;
	}

	public static void main(String[] args) {		
	
	/**********************************************************************************/
		//public String input()
		String str="";//initialize input strings		
		try {
			File file=new File("src/strings.txt");
			FileInputStream fis=new FileInputStream(file);
			BufferedInputStream bip=new BufferedInputStream(fis);			
		int length;	
		
		
			for (int i=0;i<file.length();i++){
				length=bip.read();
				str+=(char)length;						
			}	
			//System.out.println(str);	
			bip.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	//split the input file into two strings	
	String[] strS=str.split("\n");
	System.out.println("After Split: "+strS[0]+strS[1]);
	String strP=strS[0].substring(2,strS[0].length());	
	String strT=strS[1].substring(2,strS[1].length());
	int lenT=strT.length();
	
	
	//System.out.println(lenT);
	//System.out.println("Pattern: "+strP);
	//System.out.println("Target: "+strT);		
	/**********************************************************************************/
	bestCur[0]=strP;
	int i,j;
	for (i=0;i<strT.length();i++)
	{
		/*
		for (j=1;j<strT.length();j++)
		{	D[i][0]=i;
			D[0][j]=j;
		
			D[i][j]=DEvaluate(strP,strT,i,j);
			System.out.print(D[i][j]);
		}
		*/
		//System.out.print("\r\n"+strP.charAt(i));
		D[i][i]=DEvaluate(strP,strT,i,i);
		
	}
	
	for (int outi=0;outi<strT.length();outi++)
	{
		System.out.print(D[outi][outi]);		
	}
	
	
	//System.out.print("\r\n"+D[2][2]);	
	//System.out.print("\r\n"+strP.substring(0, 1));	
	//System.out.print("\r\n"+strT.substring(1, 3));
	//System.out.print(operation[]);
	System.out.print("\r\n");
	String[] show=displaySeq(strP, strT, strT.length());
	
	for(int ishow=0;ishow<show.length&&show[ishow]!=null;ishow++)
	{
		//System.out.println(show[ishow]);
	}
	
	}
	/**********************************************************************************/
	/**********************************************************************************/
	//Compare
	 public static Boolean isEqual(String str1, String str2)
	 {		Boolean B=false; 
			int diff;
			diff=str1.compareTo(str2);
			if (diff==0) B=true;
			return B;			
	 }
	 /**********************************************************************************/
	//Flip
	 public static String flip(String inputstr)
	 {
		String reverse="";
		for (int i=inputstr.length()-1;i>=0;i--)
		{
			reverse+=inputstr.substring(i,i+1);
		}
		return reverse;
	 }
	 
	/**********************************************************************************/
	 public static int DEvaluate(String P, String T,int i,int j)
	 {//this function is used to compute D[i-1,j-1] and D[i,j-d]
		 //pointer points the current char in P
		 int Dij;
		 int D1,D2;
		 D1=299792458;
		 D2=299792458;
		 int differInSub=0;
		 String fl;
		 String reFl=new String();
		 reFl="";
		 String[] subs=new String[T.length()];
		 subs[0]=T;
		 
		 
		
		 if(P.charAt(0)==T.charAt(0))//Basic case D(0,0)
		 {
			 D[0][0]= 0;
		 }
		 else 
		 {
			 D[0][0]=1;
		 }			 
/**********************************************************************************/ 
		if (i>0)
		{			
			if(P.charAt(i)==T.charAt(i))
		 {
			 D1=D[i-1][j-1];//there is only one different char in each string.
		 }
		 else 
		 	{
			 D1=D[i-1][j-1]+1;			 
			 }	
		}
		else		
		{
			D1=D[0][0];
		}		 
/**********************************************************************************/ 
		 
		 for (int jndex=0;jndex<j;jndex++)//Here, a fliping is needed, and a follow up substitute is called.
		 {
			 fl=flip(T.substring(jndex, j+1));//Flip a part of the target string 
			 
/**********************************************************************************/
			 //After flipping, there still are some different char in the substring.
			 //Count out how many different char.
			 for (int ci=0;ci<fl.length();ci++)
			 {
				 if(ci==0)
				 {
					  if(P.substring(jndex, j+1).charAt(ci)!=fl.charAt(ci))
					  {
						  differInSub++;
						  reCi[jndex][ci]=ci;//record the position where difference happened
					  }
					  differInSub=differInSub-1;//Since when ci=0, D[0][0] will inference the "D2=D[jndex][jndex]+differInSub+1;", I subtract 1 here.
				 }
				 else 
				 {
					  if(P.substring(jndex, j+1).charAt(ci)!=fl.charAt(ci))
						 {
							 differInSub++;							 //Find how many difference after flipping
						  reCi[jndex][ci]=ci;//record the position where difference happened
						  }
				 }
				
			 }
			 
			 //compare each D
				if (D[jndex][jndex]+differInSub<=D2)
				{			
					D2=D[jndex][jndex]+differInSub+1;
					reJndex[i]=jndex;	//record the position where fliping happened
				}				 
				differInSub=0;			 
		 }
/**********************************************************************************/
		 //Compare D1 with D2, and return the min one
		 if (D1>D2)
		 {
			 Dij=D2;// flipping will be performed
			 
			 operation[i]="D2";
		/**********************************************************************************/
		 }
	/**********************************************************************************/

		 else
		 {
			 Dij=D1;//substitution will be performed,but not display now
			 operation[i]="D1";
		 }		 
		 /**********************************************************************************/
		
		 return Dij;
	 }
	 
	 
	 public static String substitute(String p,String t, int pos)
	 {
		 String aftersub="";
		 return aftersub=t.substring(0, pos)+p.substring(pos,pos+1)+t.substring(pos+1);
	 }
	 public static String[] displaySeq(String p,String t,int lengthT)
	 {
		 int count=0;
		 for(int countdown=lengthT-1;countdown>=0;)
		 {
			 if (operation[countdown]=="D1")
			 {
				 bestStep.add(countdown);
				 countdown--;
				 count++;
			 }
			 else
			 {
				 bestStep.add(countdown);
				 countdown=reJndex[countdown]-1;
				 count++;
			 }
		 }
		 
		 int[] state =new int[2];
		 int d=0;
		 Iterator step=bestStep.iterator();
			while(step.hasNext())
			{
				//System.out.println((int)show.next());
				state[d]=(int)step.next();
				d++;
			}
			
			d=0;
			for(int dr=0;dr<state.length;dr++)
			{
				if(operation[state[d]]=="D1")
				{
					bestCur[dr]=substitute(p,t, state[d-1]);//sub
					System.out.println("sub: "+bestCur[dr]);
					d++;
				}
				
				else
				{
					bestCur[dr]=flip(t.substring(reJndex[state[d]]));//flip+sub
					System.out.println("flip: "+bestCur[dr]);
					
				int differInSub=0;
					/*************************************************/
					 //Count out how many different char.
					 for (int ci=0;ci<bestCur[dr].length();ci++)
					 {
						
						if(ci==0)
						 {
							  if(p.substring(jndex, j+1).charAt(ci)!=p.charAt(ci))
							  {
								  differInSub++;
								  //record the position where difference happened
							  }
							  differInSub=differInSub-1;//Since when ci=0, D[0][0] will inference the "D2=D[jndex][jndex]+differInSub+1;", I subtract 1 here.
						 }
						 else 
						 {
							  if(P.substring(jndex, j+1).charAt(ci)!=bestCur[dr].charAt(ci))
								 {
									 differInSub++;							 //Find how many difference after flipping
								  //record the position where difference happened
								  }
						 }
						
					 }
					
					 /*************************************************/	
					
					
					for (int reCic=0;reCic<reCi[state[d]].length;reCic++)
					{ 
						//System.out.println(reCi[state[d]-1][0]);
						
						if (reCi[state[d]][reCic]!=null)
						{
							bestCur[dr]=substitute(p,bestCur[dr], reCi[state[d]][reCic]);
							System.out.println("flipsub:" +bestCur[dr]);
							
						}
						
					}
				}
			}
			
			
		return bestCur;
	 }
	 
}
