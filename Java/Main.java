public class Main{
    public static void main(String[] args){
        System.out.println("    J    A     V     V    A   ");
        System.out.println("    J   A A     V   V    A A  ");
        System.out.println("J   J  AAAAA     V V    AAAAA ");
        System.out.println(" J J  A     A     V    A     A");
    
///上述是1.3

        System.out.println("a\t a^2\t a^3");
        for(int i=1;i<=4;i++)
        {
                int a1=i;
                int a2=i*i;
                int a3=i*i*i;
                System.out.println(a1 + "\t" + a2 + "\t" + a3);
        }
        ///上述是1.4

        double pi=0;
        int m=1;
        for(int i=1;i<7;i=i+2)
        {
            pi=pi+(1.0/i)*m;
            m=-m;
        }
        System.out.println(4*pi);


        pi=0;
        m=1;
        for(int i=1;i<8;i=i+2)
        {
            pi=pi+(1.0/i)*m;
            m=-m;
        }
        System.out.println(4*pi);
//上述是1.7
        }
    }