import java.util.*;

public class Master extends Node{
    private int step;//超级轮的个数
    List<Message1> message1sRec;
    ShortestPathVertex source;//源节点
    Map<Vectex,Worker> vectexWorkerMap;//保留每个图节点位于哪一个工作节点当中
    static int hasChanged;
    public Master() {
        hasChanged=0;
        vectexWorkerMap=new HashMap<>();
        step=0;
        message1sRec=new ArrayList<>();
    }

    /**
     * 这个相当于是进行一步执行操作,超级轮的个数会递增,收到上一次来的消息,进行
     * 消息发送,把对应的消息发送到对应的节点中
     */
    protected void execute(){
        if (step==0){//初始操作
            for (Vectex vectex : source.outEdgeVerterxes.keySet()) {
                Message1 message1=new Message1(vectex);
                message1.setVal(source.outEdgeVerterxes.get(vectex));
//                vectexWorkerMap.get(vectex).receive(Collections.singletonList(message1));
                message1sRec.add(message1);
            }
        }
            Map<Worker, List<Message1>> map = new HashMap<>();
            for (Message1 message1 : message1sRec) {//对从上一轮收到的信息进行处理操作
                Worker worker = vectexWorkerMap.get(message1.getDest());
                if (!map.containsKey(worker)) {
                    map.put(worker, new ArrayList<>());
                }
                map.get(worker).add(message1);
            }

        for (Worker worker : map.keySet()) {//接受操作
            worker.receive(map.get(worker));
        }
        message1sRec.clear();//消息及时清空,注意消息何时清空
        for (Worker worker : map.keySet()) {//执行操作,这个与接受操作分开进行
            worker.execute();
        }
        step++;
    }
}
