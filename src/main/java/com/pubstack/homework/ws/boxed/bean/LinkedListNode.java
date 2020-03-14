package com.pubstack.homework.ws.boxed.bean;

import java.util.Date;

public class LinkedListNode {
    private LinkedListNode previous;
    private LinkedListNode next;
    private int value;
    private String name;
    private Date date;

    public LinkedListNode(LinkedListNode p, LinkedListNode n, int v, String name, Date d) {
        this.previous = p;
        this.next = n;
        this.value = v;
        this.name = name;
        this.date = d;
    }

    public LinkedListNode getPrevious() {
        return this.previous;
    }

    public void setPrevious(LinkedListNode  previous) {
        this.previous = previous;
    }

    public LinkedListNode getNext() {
        return next;
    }

    public void setNext(LinkedListNode next) {
        this.next = next;
    }

    public String nodeFullName() {
        if (this.previous == null) {
            return name;
        }
        return this.previous.nodeFullName() + name;
    }

    public int computeAggregateValue() {
        if (this.next == null) {
            return value;
        }
        return value + this.next.computeAggregateValue();
    }
}
