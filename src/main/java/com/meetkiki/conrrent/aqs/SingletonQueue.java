package com.meetkiki.conrrent.aqs;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

import static java.lang.reflect.Array.newInstance;

public class SingletonQueue<D> implements Queue<D> {

    /**
     * 头结点
     */
    private Node<D> head;

    /**
     * 尾结点
     */
    private Node<D> tail;


    private int size;


    public SingletonQueue() {
    }


    @Override
    public boolean add(D data) {
        Node<D> node = new Node<>(data);
        Node<D> prev = tail;
        if (head == null) {
            // init cas
            head = node;

            // cas
            tail = node;
        } else {
            node.prev = prev;

            // cas
            tail = node;

            prev.next = node;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (tail == head) {
            return false;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends D> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean offer(D d) {
        return add(d);
    }

    @Override
    public D remove() {
        return null;
    }

    @Override
    public D poll() {
        if (head == null) {
            return null;
        }
        Node<D> temp = head;
        head = temp.next;
        if (head != null) {
            head.prev = null;
        }
        size--;
        return temp.data;
    }

    @Override
    public D element() {
        return head.data;
    }

    @Override
    public D peek() {
        return head.data;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<D> iterator() {
        return new Itr<>(head);
    }

    static class Itr<D> implements Iterator<D>{

        private Node<D> node;

        public Itr(Node<D> head) {
            this.node = head;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public D next() {
            Node<D> temp = node;
            node = temp.next;
            return temp.data;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        Node<Object> node = (Node<Object>) head;
        int i = 0;
        while (node != null){
            result[i++] = node.data;
            node = node.next;
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            a = (T[]) newInstance(a.getClass().getComponentType(), size);
        Object[] result = a;
        Node<T> node = (Node<T>) head;
        int i = 0;
        while (node != null){
            result[i++] = node.data;
            node = node.next;
        }
        return (T[]) result;
    }


    static final class Node<D> {
        /**
         * 前置节点
         */
        private Node<D> prev;
        /**
         * 后置指针
         */
        private Node<D> next;

        private D data;

        public Node() {
        }

        public Node(D data) {
            this.data = data;
        }
    }


}
