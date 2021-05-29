package com.meetkiki.conrrent.map.singleton;


public class Singleton extends AbstractSingleton {

    private String name;

    // volatile double 检查单例模式
    private static volatile Singleton INSTANCE;

    private Singleton() {
        this.name = "instance name";
    }

    public static Singleton getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                /**
                 * 如果没有volatile 那么其他 线程改变了INSTANCE的 这个线程也看不到的
                 * 第一点 保证线程间可见性
                 * 第二点 禁止指令重排序 通常情况下 new 分为三步
                 *	1.申请一块内存，其中成员变量赋默认值
                 * 	2.调用类的构造方法，给成员变量赋初始值
                 *  3.将这块内存区域赋值给栈的相应变量，简单理解就是把new出来的这个对象的地址赋值给o
                 *
                 * 如果按照这样进行下去 往往是没有问题的，问题在于jvm底层在某些时候存在“指令重排”的问题
                 * 	这三个步骤的2，3两步是可能调换的。
                 * 	假设高并发场景下，2,3步骤发生了指令重拍，线程A刚刚执行完第三步，但实际上此时的对象还没有
                 * 	初始化，属性还没完成赋值，此时是一个不完整对象，但已经不为null了，那么如果此时线程B进来判断
                 * 	对象不为null直接使用，实际上是有问题的。
                 *
                 * 所以这里需要加上volatile关键字，禁止指令重排序，从而保证线程安全。
                 */
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }


    public void clear() {
        INSTANCE = null;
        SET.clear();
        FUTURES.clear();
    }

}



