package src;
import java.io.*;
class PDemo {
    public static void main(String args[])
            throws IOException
    {
        String expstr;

        BufferedReader br = new
                BufferedReader(new InputStreamReader(System.in));
        Parser p = new Parser();
        System.out.println("Enter an empty expression to stop.");

        for(;;) {
            System.out.print("Enter Expression: ");
            expstr = br.readLine(); //BufferedReader를 사용해서 읽어들임
            if(expstr.equals("")) break; //빈 값이 들어오면 break;
            try {
                System.out.println("Result : " + p.evaluate(expstr)); //p.evaluate를 호출해서 연산 시작
                System.out.println();
            } catch(ParserException exc) {
                System.out.println(exc);
            }
        }
    }
}
