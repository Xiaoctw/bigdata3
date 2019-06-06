import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class ShortestPath {
    private Map<Integer,ShortestPathVertex> valToVec;
    private ShortestPath(String inputFile,String outputfile, int step) throws FileNotFoundException {
        valToVec=new HashMap<>();
        Master master=new Master();
       // Worker[]  workers=new Worker[5];
        List<Worker> workers=new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            workers.add(new Worker(master));
        }
        Scanner in=new Scanner(new File(inputFile));
        PrintStream stream=new PrintStream(new File(outputfile));
        //首先处理好节点信息
        while (in.hasNext()){
            int id1=in.nextInt();
            int id2=in.nextInt();
            if (!valToVec.containsKey(id1)){
                ShortestPathVertex vec=new ShortestPathVertex(id1);
                valToVec.put(id1,vec);
            }
            if (!valToVec.containsKey(id2)){
                ShortestPathVertex vec=new ShortestPathVertex(id2);
                valToVec.put(id2,vec);
            }
            valToVec.get(id1).outEdgeVerterxes.put(valToVec.get(id2),1);
            valToVec.get(id2).outEdgeVerterxes.put(valToVec.get(id1),1);//添加边
        }
        System.out.println("导入节点完成");
        valToVec.get(1).isSource=true;//定义源节点
        master.source=valToVec.get(1);
        valToVec.get(1).minDist=0;
        //把各个节点分配到不同的worker上
        for (Integer i : valToVec.keySet()) {
            ShortestPathVertex vectex=valToVec.get(i);
            Worker worker= workers.get((int) (Math.random()*5));
            vectex.worker=worker;
            worker.addVectex(vectex);
            master.vectexWorkerMap.put(vectex,worker);
        }
        System.out.println("分配计算节点完成");
        for (int i = 0; i < step; i++) {
            System.out.println("第"+i+"轮迭代,有"+Master.hasChanged+"个节点数据改变");
            master.execute();
            Master.hasChanged=0;
        }
        for (ShortestPathVertex value : valToVec.values()) {
            stream.println("节点"+value.val+",距离为:"+value.minDist);
        }
    }

    public static void main(String[] args) {
        try {
            new ShortestPath("/home/xiao/文档/大数据分析/实验3数据/图数据","/home/xiao/文档/大数据分析/实验3数据/计算距离结果",15);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
