import java.util.List;

public interface Vectex{
//    int superStep = 0;//记录超级轮
    void compute(List<Message1> message1s);
//    int getSuperStep();//记录超级轮的个数
    void voteToHalt();//设置为停机状态
    void sendMessage(Vectex vectex,int val);
}