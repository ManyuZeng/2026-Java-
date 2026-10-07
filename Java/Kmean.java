package Java;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kmean {
    
//模块一 距离
    public static double distance(double[]p1,double[]p2){
        double sum=0;
        for (int d=0;d<p1.length;d++){
            double dis=p1[d]-p2[d];
            sum+=dis*dis;
        }
        return Math.sqrt(sum);
    }

//模块二 深拷贝质心
    public static double[][] initializeCentroides(double[][] points, int k){
        int n=points[0].length;
        double[][] centroids=new double [k][n];

        for(int i=0;i<k;i++){
            for(int d=0;d<n;d++){
                centroids[i][d]=points[i][d];

            }
        }
        return centroids;
    }

    //模块三 簇的分配
    public static int []assignClusters(double[][] points,double[][]centroids){
        int []assignments=new int[points.length];

        for(int i=0;i<points.length;i++){
            int minIndex=0;

            double mindist =distance(points[i],centroids[0]);

            for(int j=1;j<centroids.length;j++){
                double dist= distance(points[i],centroids[j]);
                if(dist<mindist){
                    mindist=dist;
                    minIndex=j;
                }
            }
            assignments[i]=minIndex;
        }
        return assignments;
    }

    //模块四 质心的更新

    public static double[][] updateCentroids(double[][]points,int[]assignments,int k){
        int n=points[0].length;
        double[][]newCentroids=new double[k][n];
        int[] counts=new int [k];


        for(int i=0;i<points.length;i++){
            int c=assignments[i];
            for(int d=0;d<n;d++){
                newCentroids[c][d]+=points[i][d];
            }
            counts[c]++;//每一簇有多少个点
        }

        for (int j=0;j<k;j++){
            if(counts[j]>0){
                for(int d=0;d<n;d++){
                    newCentroids[j][d]/=counts[j];
                }
            }
        }
        return newCentroids;
    }


    //模块五：收敛判断
    public static boolean hasConveerged(double[][] oldC,double[][]newC,double tolerance){
        for(int i=0;i<oldC.length;i++){
            if(distance (oldC[i],newC[i])>tolerance){
                return false;
            }
        }
        return true;
        
    }

    //模块六 读取文件
    public static double[][] readpoint(String filename,int n)
    throws FileNotFoundException{
        File file =new File(filename);
        Scanner scanner=new Scanner(file);
        List<double[]> list=new ArrayList<>();

        while (scanner.hasNextDouble()){
            double []point=new double [n];
            for(int d=0;d<n;d++){
                if(scanner.hasNextDouble()){
                    point[d]=scanner.nextDouble();
                }else{
                    break;
                }
            }
            list.add(point);
        }
        scanner.close();
        return list.toArray(new double[0][]);

    }

    //模块七
    public static void writeResultsToFile(String filename,
                                          double[][] points,
                                          int[] assignments,
                                          double[][] centroids)
            throws FileNotFoundException {

        PrintWriter writer = new PrintWriter(filename);
        int k = centroids.length;
        int n = points[0].length;

        writer.println("== K-Means 聚类分析结果 ==");
        writer.println("数据点总数：" + points.length
                + " | 维度 n = " + n
                + " | 聚类簇数 K = " + k + "\n");

        for (int i = 0; i < k; i++) {
            writer.printf("【簇 %d】 质心位置: (", i);
            for (int d = 0; d < n; d++) {
                writer.printf("%.2f", centroids[i][d]);
                if (d < n - 1) writer.print(", ");
            }
            writer.println(")");

            writer.println("  包含的点:");
            int count = 0;
            for (int j = 0; j < points.length; j++) {
                if (assignments[j] == i) {
                    writer.printf("    点%d: (", j);
                    for (int d = 0; d < n; d++) {
                        writer.printf("%.2f", points[j][d]);
                        if (d < n - 1) writer.print(", ");
                    }
                    writer.println(")");
                    count++;
                }
            }
            writer.println("  簇大小: " + count + " 个点\n");
        }
        writer.close(); 
    }          
    

    public static void main(String []args){
        String inputFile = "C:/Users/35696/Desktop/input.txt";
        String outputFile="C:/Users/35696/Desktop/outout.txt";
        int n=2
        ;
        int k=2;

        try{
            double[][]points=readpoint(inputFile,n);
            System.out.println("读取成功"+points.length+"个"+n+"维数据点");
            
            double [][]centroids =initializeCentroides(points, k);
            int []assignments =new int [points.length];

            boolean converged =false;
            int iter =0,maxIter=100;

            while (!converged && iter <maxIter){
                assignments=assignClusters(points,centroids);
                double [][]newCentroids=updateCentroids(points,assignments,k);
                converged= hasConveerged(centroids, newCentroids,1e-4);
                centroids=newCentroids;
                iter++;
            }
            System.out.println("K-mean 算法在第"+iter+"次迭代后收敛。");

            writeResultsToFile(outputFile, points, assignments, centroids);
            System.out.println("结果已经写入："+outputFile);

        }catch(FileNotFoundException e){
            System.err.println("文件错误"+e.getMessage());

    }
}
}

    