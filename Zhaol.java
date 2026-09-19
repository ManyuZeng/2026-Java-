import java.util.Scanner;

public class Zhaol{
    public static void main(String[] args){
        Scanner input=new Scanner(System.in);

        System.out.print("请输入找零数目:");
        double zl=input.nextDouble();
        int zll=(int)(zl*100);
        int []miane={10000,5000,2000,1000,500,100,50,20,10};

        int len=miane.length;
        for(int i=0;i<len;i++){
            int count=zll/miane[i];

            if(count>0){
                System.out.println("面额"+miane[i]/100.0+"元"+count+"张");
            }
            zll=zll-miane[i]*count;
        }
            

    }
}
