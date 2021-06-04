package com.meetkiki.conrrent.deepcopy;

import java.io.Serializable;
import java.util.List;

public class Person implements Serializable {

    private static final long serialVersionUID = 8088192513649233159L;
    private String name;
    private String age;
    private List<Child> children;

    public static class Child implements Serializable {
        private static final long serialVersionUID = -8216376442225265456L;
        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
