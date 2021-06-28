package com.meetkiki.conrrent.object;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.nio.ByteOrder;

public class VmTest {

    public static void main(String[] args) {
        //查看字节序
        System.out.println(ByteOrder.nativeOrder());
        //打印当前jvm信息
        System.out.println("======================================");
        /**
         * Objects are 8 bytes aligned. 所有的对象分配的字节都是8的整数倍
         */
        System.out.println(VM.current().details());

        System.out.println(ClassLayout.parseInstance(Long.MAX_VALUE).toPrintable());
    }

}