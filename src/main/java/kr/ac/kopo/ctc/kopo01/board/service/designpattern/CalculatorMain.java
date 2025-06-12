package kr.ac.kopo.ctc.kopo01.board.service.designpattern;

interface  Func {
    int func(int a, int b);
}

class  Calculator {
    private Func listener;

    public Calculator(Func listener) {
        this.listener = listener;
    }

    public int funcTemplate(int a, int b) {
        System.out.println("공통 전처리");
        int ret = listener.func(a,b);
        System.out.println("공통 후처리");
        return ret;

    }
}

class AddFunc implements Func{
    @Override
    public int func(int a, int b) {
        return a + b;
    }
}

class SubFunc implements Func{
    @Override
    public  int func(int a, int b){
        return a - b;
    }
}

public class CalculatorMain {
    public static  void main(String[] args) {
        Calculator addCalc = new Calculator(new AddFunc());
        int r1 = addCalc.funcTemplate(1, 2);

        Calculator subCalc = new Calculator(new SubFunc());
        int r2 = subCalc.funcTemplate(1, 2);

        Calculator mulCalc = new Calculator((a,b) -> a * b);
        int r3 = mulCalc.funcTemplate(3, 5);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);


    }

}