package test;



import com.xiaorui.domain.AB;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author: hhr
 * @description:
 * @create: 2020-01-05 13:58
 **/
public class main {

    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
//        try {
//            Test1 test1 = (Test1) Class.forName("test.Test1").newInstance();
//            Method method = test1.getClass().getDeclaredMethod("method1", String.class, String.class);
//            Parameter[] params = method.getParameters();
//
//            for (int i = 0; i < params.length; i++) {
//                Parameter param = params[i];
//                System.out.println(param);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Properties properties = new Properties();
//        properties.load(
//                Thread.currentThread().getContextClassLoader().getResourceAsStream("findController.properties")
//        );
//        Enumeration en = properties.propertyNames();
//        while (en.hasMoreElements()){
//            System.out.println(en.nextElement());
//        }
//        while (en.hasMoreElements()){
//            System.out.println(en.nextElement());
//        }

//        Constructor constructor = int.class.getConstructor(String.class);
//        System.out.println(constructor.newInstance("123"));

//        Object[] objects = new Object[10];
//        for (int i = 0; i < 9; i ++){
//            add(objects,i);
//        }
//        for (int i = 0; i < objects.length; i++) {
//            Object object = objects[i];
//            System.out.println(object);
//        }
//
//    }
//    private static void add(Object[] o, int i){
//       o[i] = i;

//        AB ab = new AB();
//        Object[] objects = new Object[]{String.class,int.class};
//        Object ab1 = ab.getClass().getConstructor();


//        String  a ="";
//        a.getClass().newInstance();
//        Object o = new AB();
//        System.out.println(o.toString());
//        switch (null){
//
//        }

//        int c = 0;
//        test(c);
//        System.out.println(c);


//        Test1 test1 = new Test1();
//        Test2 test2 = new Test1();
//        test2.method1("11","32");
//
//        Class.forName("test.Test3").getDeclaredMethod("method1", String.class, String.class).invoke(test2,"123","23");
        System.out.println(Object.class == Object.class);

    }

//    public static void test(Object obj){
//        int a = (int) obj;
//        a = 1;
//
//    }
}

