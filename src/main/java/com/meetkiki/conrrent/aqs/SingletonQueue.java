package com.meetkiki.conrrent.aqs;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

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


    public SingletonQueue(){

    }


    @Override
    public boolean add(D data) {
        Node<D> node = new Node<>(data);
        if (head == null){
            head = node;
            node.prev = tail;
        }
        tail = node;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (tail == head){
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

    }

    @Override
    public boolean offer(D d) {
        return false;
    }

    @Override
    public D remove() {
        return null;
    }

    @Override
    public D poll() {
        return null;
    }

    @Override
    public D element() {
        return null;
    }

    @Override
    public D peek() {
        return null;
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
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
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

        public Node(D data) {
            this.data = data;
        }
    }

    public Node<D> getHead() {
        return head;
    }

    public void setHead(Node<D> head) {
        this.head = head;
    }

    public Node<D> getTail() {
        return tail;
    }

    public void setTail(Node<D> tail) {
        this.tail = tail;
    }


}
