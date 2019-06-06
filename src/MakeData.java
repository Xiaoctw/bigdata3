import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class MakeData {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in=new Scanner(new File("/home/xiao/文档/大数据分析/实验3数据/web-Google.txt"));
        for (int i = 0; i < 4; i++) {
            in.nextLine();
        }
        PrintStream stream=new PrintStream(new File("/home/xiao/文档/大数据分析/实验3数据/图数据"));
        while (in.hasNext()){
            stream.println(in.nextLine());
        }
    }
}
