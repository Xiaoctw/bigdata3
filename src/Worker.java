import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Worker extends Node {
    Master master;//从属的主节点
    int numVectex;//保存的图节点的个数
    List<Vectex> vectexList;//保存当前节点中存储的图节点
    List<Message1> message1sFromVec;//保存每一轮计算过程当中,从计算节点返回来的message值
    private Map<Vectex,List<Message1>> map=new HashMap<>();//保留收到的每个消息对应的图节点
    public Worker(Master master,List<Vectex> vectexList) {
        this.master=master;
        this.vectexList = vectexList;
        numVectex=vectexList.size();
        message1sFromVec=new ArrayList<>();
    }

    public Worker(Master master) {
        this.master=master;
        vectexList=new ArrayList<>();
        numVectex=0;
        message1sFromVec=new ArrayList<>();
    }

    /**
     * 向一个
     * @param vectex
     */
    public void addVectex(Vectex vectex){
        if (vectexList==null){
            vectexList=new ArrayList<>();
        }
        vectexList.add(vectex);
        numVectex++;
    }

    /**
     * 收到了信息,将信息传输到自己的所有图节点当中
     * 这些信息都是从主节点传过来的
     * @param list message信息
     */
    void receive(List<Message1> list){
        for (Message1 message1 : list) {
            if(map.containsKey(message1.getDest())){
                map.get(message1.getDest()).add(message1);
            }else {
                map.put(message1.getDest(),new ArrayList<>());
                map.get(message1.getDest()).add(message1);
            }
        }
    }

    void execute(){
        for (Vectex vectex : map.keySet()) {
            vectex.compute(map.get(vectex));//进行执行操作
        }
        map.clear();//消息要及时清空
        send();//一定要及时发送消息,用于下一轮计算
    }

    /**
     * 这里是把从图中节点计算得来的消息发送出去
     * 把消息添加到总的链表里
     */
    public void send(){
        master.message1sRec.addAll(message1sFromVec);
        message1sFromVec.clear();
    }
}