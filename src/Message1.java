class Message1 {
    private Vectex dest;//代表消息发往的目的地
    private int val;//代表传入的值

    Message1(Vectex dest) {
        this.dest = dest;
    }

    int getVal() {
        return val;
    }

    void setVal(int val) {
        this.val = val;
    }

    Vectex getDest() {
        return dest;
    }
}