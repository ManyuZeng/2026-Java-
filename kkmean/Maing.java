import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

class Point{
    private double x;
    private double y;
    private String id;
    //point内的局部变量，Point（参数列表） 
    Point(double x,double y,String id){
        //以下三行代表初始化逻辑
        this.x=x;
        this.y=y;
        this.id=id;
    }
    //复用初始化逻辑，在其他构造方法内部接收x,y时，避免重新写this.x=x的代码
        public Point(double x,double y){this(x,y,"");    }

        double distanceTo(Point other){
            double xx=Math.pow(other.getX()-getX(),2);//getX=this.x,
            double yy=Math.pow(other.getY()-getY(),2);//用x*x会效率更高

            return Math.sqrt(xx+yy);
        }
        //getX/Y 让外部的代码读取x,y;仅可以通过这个来进行读取！
        public double getX(){return x;}//没有public：默认只能在同一个文件夹上才可以调用这个方法
        public double getY(){return y;}//有public，只有拿到Point对象，就可以调用，不论文件
        public String getId(){return id;}
        
    

    @Override
    public String toString(){
        return String.format("%s(%.2f,%.2f)",
        (id==null || id.length()==0 )?"Point":id,x,y);
    }}
        
        
    
    class Cluster{//cluster（类名）,作为小组--簇
        private int id;
        private Point centroid;
        private List<Point> points;//自动扩容数组
        public Cluster(int id,Point initialCentroid){
            this.id=id;
            this.centroid=new Point(
                    initialCentroid.getX(),
                    initialCentroid.getY());
            this.points=new ArrayList<>();  
        }
        

        public void addPoint(Point p)  {this.points.add(p);}
        //{调用了Point的方法}
    //重新算质心位置
        public void clearPoints(){
            if(this.points !=null){
                this.points.clear();
            }
        }
        public double updateCentroid(){
            if(points.isEmpty()){
                return 0.0;
            }
            double sumX=0;
            double sumY=0;
            for(Point p:points){//在points数组内一个个调用类型为point的点叫p
                sumX +=p.getX();
                sumY +=p.getY();
            }

            Point newCentroid=new Point(sumX/points.size(), sumY/points.size());
            double shiftDistance=this.centroid.distanceTo(newCentroid);
            this.centroid=newCentroid;
            return shiftDistance;//老质心与新的距离

        }
        public int getId(){return id;}
        public Point getCentroid(){return centroid;}//新质心
        public List<Point> getPoints(){return points;}
    }
        class KMeans{//构造方法必须与类名完全一致
        int k;
        int maxIterations;//最大迭代次数
        double tolerance;//收敛要求
        private List<Cluster>clusters;

        public KMeans(int k,int maxIterations,double tolerance){//public KMeans 为构造方法
            this.k=k;
            this.maxIterations=maxIterations;
            this.tolerance=tolerance;
            this.clusters=new ArrayList<>();

        }
    //核心
    public List<Cluster> fit(List<Point>dataPoints){
        

        if(dataPoints.size()<k){
            throw new IllegalArgumentException("<k了");
        }

        clusters.clear();
        for(int i=0;i<k;i++){
            clusters.add(new Cluster(i,dataPoints.get(i)));
        }

        for(int iter=0;iter<maxIterations;iter++ ){
            for(Cluster c:clusters){
                c.clearPoints();
            }
            for(Point p:dataPoints){
                Cluster nearest=findNearestCluster(p);
                nearest.addPoint(p);

            }
            double maxShift=0;
            for(Cluster c:clusters){
                maxShift=Math.max(maxShift,c.updateCentroid());

            }

            if(maxShift<tolerance){
                break;
            }
        }
        return clusters;
        
    }
    

    

    private Cluster findNearestCluster(Point p){
        Cluster nearest=clusters.get(0);
        double minDist=p.distanceTo(nearest.getCentroid());
        for(Cluster c:clusters){
            double d=p.distanceTo(nearest.getCentroid());
            if(d<minDist){
                minDist=d;
                nearest=c;
            }
        }
        return nearest;
    }
    public List<Cluster> getClusters(){return clusters;}

        }

    class PointFileReader{
        private String filePath;

        public PointFileReader(String filePath){
            this.filePath=filePath;
        }

        public List<Point> readPoints() throws FileNotFoundException{

            List<Point> points=new ArrayList<>();
            Scanner scanner =new Scanner(new File(filePath));
            int count=0;
            while(scanner.hasNextDouble()){
                double x=scanner.nextDouble();
                double y=scanner.nextDouble();
                points.add(new Point(x,y,"P"+count++));
            }
            scanner.close();
            return points;
        }
    }

    class ClusterReportWriter{
        private String outputPath;

        public ClusterReportWriter(String outputPath){
            this.outputPath=outputPath;
        }

        public void writeReport(List<Cluster>clusters)
            throws FileNotFoundException{
          PrintWriter writer=new PrintWriter(outputPath);
          writer.println("================================");
        writer.println("  K-Means 面向对象聚类分析报表");
        writer.println("================================");
        for (Cluster cluster : clusters) {
            writer.printf("簇 #%d  |  点数: %d%n",
                    cluster.getId(),
                    cluster.getPoints().size());
            writer.printf("  质心: (%.3f, %.3f)%n",
                    cluster.getCentroid().getX(),
                    cluster.getCentroid().getY());
            for (Point p : cluster.getPoints()) {
                writer.println("    " + p);
            }
            writer.println();
        }
        writer.close();      
            }

    }

    public class Maing{
        public static void main(String[] args){
            String inputFile="input_points.txt";
            String outputFile="cluster_results.txt";

            try{
               
            PointFileReader reader = new PointFileReader(inputFile);
            List<Point> points = reader.readPoints();
            System.out.println("成功加载数据点： "+points.size()+"个。");


            KMeans kmeansService=new KMeans(2,100,1e-4);
            List<Cluster> resultClussters=kmeansService.fit(points);
            System.out.println("K-means聚类完成!");
            
            ClusterReportWriter writer=new ClusterReportWriter(outputFile);
            writer.writeReport(resultClussters);
            System.out.println("结果已写入："+outputFile);
            
            }
            catch(FileNotFoundException e){
                System.err.println("文件异常："+e.getMessage());
            }
        }
    }
    



    




