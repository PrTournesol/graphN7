package test;

public class test {

    public  static void main (String args[]){
        Integer a=3;
        System.out.println(a);
        test obj=new test();
        obj.teste(a);
        System.out.println(a);
    }

    public void test(){}
    public void teste(Integer b){
        b=5;
        System.out.println(b);

    }
}
