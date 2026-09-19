import java.util.Random;
import java.util.Scanner;

public class Game{
    public static void main(String[] args){
        Scanner Scanner=new Scanner(System.in);
        Random random=new Random();

        int [] number={0,1,2,3,4,5,6,7,8,9,10};
        char[] yxfh={'+','-','*','/'};

        int numt1=random.nextInt(number.length);
        int num1=number[numt1];

        int numt2=random.nextInt(number.length);
        int num2=number[numt2];
    
        int car=random.nextInt(yxfh.length);  
        char yx=yxfh[car];

        System.out.println("计算："+num1+yx+num2+"=");
        if(num1==0 && yx=='/')
            System.out.println("不成立");

    }
}