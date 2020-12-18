package com.lvbok.demo;

public class Demo1 {

    public static void main(String[] args) {
        /*Integer c = 10;
        Integer d = 10;
        Integer a = new Integer(10);
        Integer b = new Integer(10);
        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(a.equals(b));
        System.out.println(c.equals(d));*/

        /*int i = 1;
        int j = i;
        i = 2;
        System.out.println(j);

        A a = new A("1");
        A b = a;
        System.out.println(b);
        a.setName("2");
        System.out.println(b);*/

        Fibonacci f = new Fibonacci();
        f.test();
    }

    static class A {
        private String name;

        public A(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "A{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    static class Fibonacci {
        public void test() {
            int start = 1;
            int next = 1;
            System.out.print(start + "、" + next + "、");
            // 从3开始到第num个斐波那契数
            for (int i = 3; i <= 12; i++) {
                int last = start + next;
                System.out.print(last + "、");
                start = next;
                next = last;
            }
            System.out.println();
            // 通过迭代计算，效率很低
            for (int i = 1; i <= 12; i++) {
                System.out.print(getFibonacci(i) + "、");
            }
        }

        private static int getFibonacci(int index) {
            if (index == 1 || index == 2) {
                return 1;
            }
            return getFibonacci(index - 1) + getFibonacci(index - 2);
        }
    }
}
