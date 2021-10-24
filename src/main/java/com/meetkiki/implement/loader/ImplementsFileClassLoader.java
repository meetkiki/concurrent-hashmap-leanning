package com.meetkiki.implement.loader;

import com.meetkiki.implement.loader.classes.InterfaceA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 获取接口的所有实现类 理论上也可以用来获取类的所有子类
 * 查询路径有限制，只局限于接口所在模块下，比如pandora-gateway,而非整个pandora（会递归搜索该文件夹下所以的实现类）
 * 路径中不可含中文，否则会异常。若要支持中文路径，需对该模块代码中url.getPath() 返回值进行urldecode.
 */
public class ImplementsFileClassLoader {

    private static final Logger LOG = LoggerFactory.getLogger(ImplementsFileClassLoader.class);

    public static void main(String[] args) throws Exception {
        final List<Class<? extends InterfaceA>> classList = getAllClassByInterface(InterfaceA.class);

        for (Class<? extends InterfaceA> clz : classList) {
            final InterfaceA instance = clz.newInstance();
            instance.test();
        }
    }

    public static <T> List<Class<? extends T>> getAllClassByInterface(Class<T> clazz) {
        // 判断是否是一个接口
        if (clazz.isInterface()) {
            try {
                final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                List<Class<?>> allClass = getAllClass(clazz.getPackage().getName(), classLoader);
                /**
                 * 循环判断路径下的所有类是否实现了指定的接口 并且排除接口类自己
                 */
                return getAllImpl(clazz, allClass);
            } catch (Exception e) {
                LOG.error("出现异常{}", e.getMessage());
                throw new RuntimeException("出现异常" + e.getMessage());
            }
        }
        return Collections.emptyList();
    }

    private static <T> List<Class<? extends T>> getAllImpl(Class<T> clazz,List<Class<?>> allClass) {
        List<Class<? extends T>> result = new ArrayList<>();
        for (Class<?> clz : allClass) {
            /**
             * 判断是不是同一个接口
             */
            // isAssignableFrom: 判定此 Class 对象所表示的类或接口与指定的 Class
            // 参数所表示的类或接口是否相同，或是否是其超类或超接口
            if (clazz.isAssignableFrom(clz)) {
                if (!clazz.equals(clz)) {
                    // 自身并不加进去
                    result.add((Class<T>) clz);
                }
            }
        }
        LOG.info("class list size :" + result.size());
        return result;
    }

    /**
     * 从一个指定路径下查找所有的类
     *
     * @param packageName
     */
    private static ArrayList<Class<?>> getAllClass(String packageName, ClassLoader classLoader) {
        LOG.info("packageName to search：" + packageName);
        List<String>
                classNameList = getClassName(packageName);
        ArrayList<Class<?>> list = new ArrayList<>();
        for (String className : classNameList) {
            try {
                list.add(Class.forName(className, false, classLoader));
            } catch (ClassNotFoundException e) {
                LOG.error("load class from name failed:" + className + e.getMessage());
                throw new RuntimeException("load class from name failed:" + className + e.getMessage());
            }
        }
        LOG.info("find list size :" + list.size());
        return list;
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName 包名
     * @return 类的完整名称
     */
    private static List<String> getClassName(String packageName) {
        List<String> fileNames = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");
        URL url = classLoader.getResource(packagePath);
        if (url != null) {
            String type = url.getProtocol();
            LOG.debug("file type : " + type);
            if (type.equals("file")) {
                String fileSearchPath = url.getPath();
                LOG.debug("fileSearchPath: " + fileSearchPath);
                fileSearchPath = fileSearchPath.substring(0, fileSearchPath.indexOf("/classes"));
                LOG.debug("fileSearchPath: " + fileSearchPath);
                fileNames = getClassNameByFile(fileSearchPath);
            } else if (type.equals("jar")) {
                try {
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    JarFile jarFile = jarURLConnection.getJarFile();
                    fileNames = getClassNameByJar(jarFile);
                } catch (java.io.IOException e) {
                    throw new RuntimeException("open Package URL failed：" + e.getMessage());
                }
            } else {
                throw new RuntimeException("file system not support! cannot load MsgProcessor!");
            }
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类
     *
     * @param filePath 文件路径
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String filePath) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        if (childFiles == null) {
            return myClassName;
        }
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getClassNameByFile(childFile.getPath()));
            } else {
                String childFilePath = childFile.getPath();
                if (childFilePath.endsWith(".class")) {
                    childFilePath =
                            childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                }
            }
        }

        return myClassName;
    }

    /**
     * 从jar获取某包下所有类
     *
     * @return 类的完整名称
     */
    private static List<String> getClassNameByJar(JarFile jarFile) {
        List<String> result = new ArrayList<>();
        try {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String entryName = jarEntry.getName();
                //LOG.info("entrys jarfile:"+entryName);
                if (entryName.endsWith(".class")) {
                    result.add(entryName.replace("/", ".").substring(0, entryName.lastIndexOf(".")));
                }
            }
        } catch (Exception e) {
            LOG.error("发生异常:" + e.getMessage());
            throw new RuntimeException("发生异常:" + e.getMessage());
        }
        return result;
    }
}
