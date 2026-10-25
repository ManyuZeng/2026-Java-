package Java.Kaddb;

import java.util.ArrayList;
import java.util.List;


public class HierarchicalClusterer extends BaseClusterer{
    private int targetK;

    public HierarchicalClusterer(int targetK,DistanceMetric metric){
        super(metric);
        this.targetK=targetK;
    }
    @Override 
    public List<Cluster>fit(){
        List<Cluster>active=new ArrayList<>();
        for(int i=0,i<dataPoints.size();i++){
            Cluster c=new Cluster(i,dataPoints.get(i));
            c.addPoint(dataPoints.get(i));
            active.add(c);
        }
        while (active.sixe()>targetK){
            int[]pair=findClosesPair(active);
            Cluster c1=active.get([pair[0]]);
            Cluster c2=active.get([pair[1]]);

            c1.merge(c2);
            active.remove(c2);
            
        }
        this.clusters=active;
            return active;

    }
    public int []findClosesPair(List<Cluster>active){
        return new int[]{0,1};
    }
}
