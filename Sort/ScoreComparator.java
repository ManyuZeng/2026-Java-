package Java.Sort;
import java.util.Comparator;


public class ScoreComparator{
    
    public static class Asc implements Comparator<Student>{ 
    @Override 
    //成绩升序排列
    public int compare(Student s1,Student s2){
        return Double.compare(s1.getScore(),s2.getScore());
    }
}


    public static class Desc  implements Comparator<Student>{
        @Override 
        //成绩降序排列
        public int compare(Student s1,Student s2){
            return Double.compare(s2.getScore(),s1.getScore());
    
    }
}
}