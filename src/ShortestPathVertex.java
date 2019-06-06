import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortestPathVertex implements Vectex {

    int val;
    boolean isSource;//是否是源节点
    int minDist;//保存到达该节点的最短路径是多少
    Worker worker;//该计算节点属于哪一个工作节点
    //List<Vectex> outEdgeVerterxes;//保存与该节点邻接的顶点
    Map<Vectex,Integer> outEdgeVerterxes;//保存邻接的顶点和距离
    public ShortestPathVertex(boolean isSource) {
        this.isSource = isSource;
        minDist=Integer.MAX_VALUE;
        outEdgeVerterxes=new HashMap<>();
    }

    public ShortestPathVertex(int val) {//默认的情况下不是源节点
        this.val=val;
        this.isSource=false;
        minDist=Integer.MAX_VALUE;
        outEdgeVerterxes=new HashMap<>();
    }

    @Override
    public void compute(List<Message1> message1s) {
        int minVal=isSource?0:Integer.MAX_VALUE;
        for (Message1 message : message1s) {
            minVal=Math.min(minVal,message.getVal());
        }
        if(minVal<minDist){//如果距离发生改变,那么进行这步运算
            minDist=minVal;
            Master.hasChanged++;
            for (var verterx : outEdgeVerterxes.keySet()) {
                sendMessage(verterx,minVal+outEdgeVerterxes.get(verterx));
            }
        }
        voteToHalt();
    }

//    @Override
//    public int getSuperStep() {
//        return superStep;
//    }

    /**
     * 代表停机操作
     * 现在就先什么也不干
     */
    @Override
    public void voteToHalt() {

    }

    /**
     * 代表发送一个消息
     * @param vectex 代表发往的顶点
     * @param val 代表消息传递的值的大小
     */
    @Override
    public void sendMessage(Vectex vectex, int val) {
        Message1 message=new Message1(vectex);
        message.setVal(val);
        //这里还应该添加一步,在对应的worker中汇总message
        worker.message1sFromVec.add(message);
    }
}
