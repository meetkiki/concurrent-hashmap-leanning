package com.meetkiki.ddd;


public class HyperemiaPerson {
    private final Mouth mouth;
    private final Esophagus esophagus;
    private final Stomach stomach;
    private final Intestine intestine;

    public HyperemiaPerson() {
        this.mouth = new Mouth();
        this.esophagus = new Esophagus();
        this.stomach = new Stomach();
        this.intestine = new Intestine();
    }

    public void eat(Object food) {
        // 进食。
        Object paste = this.mouth.chew(food);       // 咀嚼形成浆糊。
        paste = this.esophagus.transfer(paste);     // 通过食道传送食物。
        Object chyme = this.stomach.fill(paste);    // 填充到胃里形成食糜。
        this.intestine.absorb(chyme);               // 在肠道里吸收营养
        // ... 成长
        // ... 便秘
    }

    // 嘴
    private static class Mouth {
        public Object chew(Object food) {
            return food;
        }
    }

    // 食道
    private static class Esophagus {
        public Object transfer(Object paste) {
            return paste;
        }
    }

    // 胃
    private static class Stomach {
        public Object fill(Object paste) {
            return paste;
        }
    }

    // 肠道
    private static class Intestine {
        public void absorb(Object chyme) {
            // absorbing...
        }
    }
}

