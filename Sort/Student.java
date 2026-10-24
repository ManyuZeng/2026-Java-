package Java.Sort;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class Student implements Comparable<Student>{
    
    private String name;
    private int id;
    private double score;

    public Student(String name,int id,double score){
        this.name=name;
        this.id=id;
        this.score=score;

    }

    @Override //藏调用
    public int compareTo(Student other){

        return Integer.compare(this.id,other.id);

    }

    public String getName(){return name;}
    public double getScore(){return score;}

    @Override 
    public String toString(){
        return String .format("[%d] %s: %.1f",id,name,score);
    }

    //===========测试=======run 运行点击，debug（以调试的方式运行）
    public static void main(String[]args){
        List<Student>list=new ArrayList<>();

        list.add(new Student("小明",2025009345,85.5));
        list.add(new Student("小黑",2025003678,45));
        list.add(new Student("小肖",2025008123,90.0));


        Collections.sort(list);

        System .out.println("==========排序后（学号）升序");
        for(Student s:list){
            System .out.println(s);
        }
        //成绩降序排列
        Collections.sort(list,new ScoreComparator.Desc());
        System .out.println("=================降序成绩");
        for(Student s:list){
            System .out.println(s);
       
    }


}
}
    
