package com.meetkiki.conrrent.deepcopy;

import com.meetkiki.conrrent.deepcopy.Person.Child;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BenchMark {

    private static List<Person> persons = new ArrayList<>();
    private static List<Object> rubbish = new ArrayList<>();
    private static int dataCount = 100000;

    public static void initData() {
        for (int i = 0; i < dataCount; i++) {
            Person person = new Person();

            person.setAge(String.valueOf(i % 100));
            person.setName(i + "name");

            List<Child> children = new ArrayList<>();
            for (int j = 0; j < (i % 10); j++) {
                Child child = new Child();
                child.setAge(String.valueOf(j % 10));
                child.setName(j + "name");
                children.add(child);
            }
            person.setChildren(children);
            persons.add(person);
        }
    }


    public static void main(String[] args) {
        initData();

        System.out.println("persons size = " + persons.size() + " === ");

        Object data1 = executeOnce(PipedDeepCopy::copy);
        rubbish.add(data1); // NOT GC

        System.out.println("PipedDeepCopy copy end \n");

        System.out.println("GsonCopy copy start  ======= ");

        Object data2 = executeOnce(GsonCopy::copy);
        rubbish.add(data2); // NOT GC

        System.out.println("GsonCopy copy end  ======= \n");


        System.out.println("MAPPER copy start  ======= ");

        Object data3 = executeOnce(PersonMapper.MAPPER::copy);
        rubbish.add(data3); // NOT GC

        System.out.println("MAPPER copy end  ======= \n");

    }

    private static Object executeOnce(Function<List<Person>, Object> call) {
        long start = System.currentTimeMillis();
        logNowMemory("start");

        Object data = call.apply(persons);

        logNowMemory("end");
        long end = System.currentTimeMillis();

        System.out.println("cost " + (end - start) + " ms ");
        return data;
    }

    private static void logNowMemory(String action) {
        System.out.println("==========" + action + " logNowMemory =============");
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memoryMXBean.getHeapMemoryUsage();
        System.out.println("INT HEAP:" + usage.getInit() / 1024 / 1024 + "Mb");
        System.out.println("MAX HEAP:" + usage.getMax() / 1024 / 1024 + "Mb");
        System.out.println("USED HEAP:" + usage.getUsed() / 1024 / 1024 + "Mb");
    }


}
