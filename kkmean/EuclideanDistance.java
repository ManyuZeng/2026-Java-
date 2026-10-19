package kkmean;


public class EuclideanDistance implements DistanceMetric{
    @Override 
    public double calulate(Point p1,Point p2){
        double dx=p1.getX()-p2.getX();
        double dy=p1.getY()-p2.getY();

        return Math.sqrt(dx*dx+dy*dy);
    }
    
}
