package com.meetkiki.ddd;

public class AnemiaPerson {
    private Object nutrition;

    public void set(Object nutrition) {
        this.nutrition = nutrition;
    }
}

class AnemiaPersonService {
    public static void main(String[] args) {
        AnemiaPerson person = new AnemiaPerson();
        // 进食。
        Mouth mouth = new Mouth();
        Esophagus esophagus = new Esophagus();
        Stomach stomach = new Stomach();
        Intestine intestine = new Intestine();

        Object paste = mouth.chew(new Object());       // 咀嚼形成浆糊。
        paste = esophagus.transfer(paste);     // 通过食道传送食物。
        Object chyme = stomach.fill(paste);    // 填充到胃里形成食糜。
        Object nutrition = intestine.absorb(chyme);               // 在肠道里吸收营养

        person.set(nutrition);
        // ... 成长
        // person.growing();
        // ... 便秘
        // person....();
    }
}

// 嘴
class Mouth {
    public Object chew(Object food) {
        return food;
    }
}

// 食道
class Esophagus {
    public Object transfer(Object paste) {
        return paste;
    }
}

// 胃
class Stomach {
    public Object fill(Object paste) {
        return paste;
    }
}

// 肠道
class Intestine {
    public Object absorb(Object chyme) {
        // absorbing...
        return chyme;
    }
}

