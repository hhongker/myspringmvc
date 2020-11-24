package com.xiaorui.myspringmvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author: hhr
 * @description:
 * @create: 2020-01-05 12:44
 **/
public class DispatcherServlet extends HttpServlet {

    private HashMap<String,String> realClassName = new HashMap<String, String>();
    private HashMap<String,Object> controllObj = new HashMap<String, Object>();
    private HashMap<Object,HashMap<String,ControllerMethodContent>> controllMethod = new HashMap<Object, HashMap<String, ControllerMethodContent>>();



    private String getUrl(HttpServletRequest request){
        String url = request.getRequestURI();
        return url.substring(url.indexOf("/")+1,url.indexOf("."));
    }
    private void loadControllerConfig(){
        try {
            Properties properties = new Properties();
            properties.load(
                    Thread
                            .currentThread()
                            .getContextClassLoader()
                            .getResourceAsStream("findController.properties")
            );
            Enumeration en = properties.propertyNames();
            while (en.hasMoreElements()){
                String key = (String) en.nextElement();
                String value = properties.getProperty(key);
                realClassName.put(key,value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValue(Class parameterType,Object[] obj,int i, String requestValue) throws IllegalAccessException, InstantiationException {

        if(parameterType == int.class || parameterType == Integer.class){
            obj[i] = new Integer(requestValue);
        }else if (parameterType == String.class || parameterType ==  char.class){
            obj[i] = requestValue;
        } else if (parameterType == float.class || parameterType == Float.class){
            obj[i] = new Float(requestValue);
        }else if (parameterType == double.class || parameterType == Double.class){
            obj[i] = new Double(requestValue);
        }else if (parameterType == byte.class || parameterType == Byte.class){
            obj[i] = new Byte(requestValue);
        }else if (parameterType == boolean.class || parameterType == Boolean.class){
            obj[i] = new Boolean(requestValue);
        }else if (parameterType == long.class || parameterType == Long.class){
            obj[i] = new Long(requestValue);
        }else if (parameterType == short.class || parameterType == Short.class){
            obj[i] = new Short(requestValue);
        }
    }

    private void setValue(Field field,String requestValue, Object o) throws IllegalAccessException, InstantiationException {
        Class parameterType = field.getType();

        if(parameterType == int.class || parameterType == Integer.class){
            int value = Integer.parseInt(requestValue);
            field.set(o,value);
        }else if (parameterType == String.class || parameterType ==  char.class){
            field.set(o,requestValue);
        } else if (parameterType == float.class || parameterType == Float.class){
            Float value = Float.parseFloat(requestValue);
            field.set(o,value);
        }else if (parameterType == double.class || parameterType == Double.class){
            Double value = Double.parseDouble(requestValue);
            field.set(o,value);
        }else if (parameterType == byte.class || parameterType == Byte.class){
            Byte value = Byte.parseByte(requestValue);
            field.set(o,value);
        }else if (parameterType == boolean.class || parameterType == Boolean.class){
            Boolean value = Boolean.parseBoolean(requestValue);
            field.set(o,value);
        }else if (parameterType == long.class || parameterType == Long.class){
            Long value = Long.parseLong(requestValue);
            field.set(o,value);
        }else if (parameterType == short.class || parameterType == Short.class){
            Short value = Short.parseShort(requestValue);
            field.set(o,value);
        }
    }

    private List getRequestList(HttpServletRequest request){
        Enumeration en = request.getParameterNames();
        ArrayList<String> arrayList = new ArrayList();
        while (en.hasMoreElements()){
            String key = (String) en.nextElement();
            String value = request.getParameter(key);
            arrayList.add(value);
        }
        return arrayList;
    }

    private Object[] dIForMethod(Method method,HttpServletRequest request) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Parameter[] params = method.getParameters();
        Object[] objects = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Parameter parameter = params[i];

            Param param = parameter.getAnnotation(Param.class);
            Class parameterType = parameter.getType();
            if (param != null){//如果参数前面的注解不为空，则证明是散花值
                String requestValue = request.getParameter(param.value());
                setValue(parameterType,objects,i,requestValue);
            }else {//说明了参数是引用值
                if (parameterType.isArray()) {//数组
                    List arrayList = getRequestList(request);
                    String[] arr = new String[arrayList.size()];
                    for (int j = 0; j < arrayList.size(); j++) {
                        arr[j] = (String) arrayList.get(j);
                    }
                    objects[i] = arr;
                } else {
                    Object parameterObj = parameterType.newInstance();
                    if (parameterObj instanceof Map) {//Map
                        Enumeration en = request.getParameterNames();
                        HashMap hashMap = (HashMap) parameterObj;
                        while (en.hasMoreElements()) {
                            String key = (String) en.nextElement();
                            String vlaue = request.getParameter(key);
                            hashMap.put(key, vlaue);
                        }
                        objects[i] = hashMap;
                    } else if (parameterObj instanceof List) {
                        List arrayList = getRequestList(request);
                        objects[i] = arrayList;
                    } else if (parameterObj instanceof Object) {//对象
                        Field[] fields = parameterObj.getClass().getDeclaredFields();
                        for (int j = 0; j < fields.length; j++) {
                            Field field = fields[j];
                            field.setAccessible(true);
                            String value = request.getParameter(field.getName());
                            setValue(field, value, parameterObj);
                        }
                        objects[i] = parameterObj;
                    }
                }
            }
        }
        return objects;
    }


    private PlaceCod[] getPlaceCodByMethod(Method method){
        PlaceCode placeCode = method.getAnnotation(PlaceCode.class);

        if (placeCode == null || placeCode.value().length == 0) return null;
        else return placeCode.value();
    }
    private void loadMethodsForClass(Object obj) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method[] methods = obj.getClass().getDeclaredMethods();
        HashMap hashMap = new HashMap();
        ControllerMethodContent methodContent = null;
        for (int i = 0; i < methods.length; i ++){
            PlaceCod[] placeCods = getPlaceCodByMethod(methods[i]);
            methodContent = new ControllerMethodContent(methods[i],placeCods);
            hashMap.put(methods[i].getName(),methodContent);
        }
        controllMethod.put(obj,hashMap);
    }

    private Object getObj(String url,HttpServletRequest request){
        Object obj = controllObj.get(url);
        if (obj == null){
            try {
                String className = realClassName.get(url);
                if(className == null)throw new IndexNotFoundExeption("404：没有找到类名，请检查配置和请求名是否一致");
                else {
                    obj = Class.forName(className).newInstance();
                    controllObj.put(url,obj);
                    this.loadMethodsForClass(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }



    private void forwardOrSendRedirect(String methodReturn,HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        if (methodReturn.contains("redirect:")){
            response.sendRedirect(methodReturn.replace("redirect:",""));
        }else {
            request.getRequestDispatcher(methodReturn).forward(request,response);
        }
    }


    private Method suchMethod(Class clazz,String methodName,Class... paramType){
        Method method = null;
        if (clazz ==  Object.class) return null;
        try {
            method = clazz.getMethod(methodName,paramType);
        } catch (NoSuchMethodException e) {
            suchMethod(clazz.getSuperclass(),methodName,paramType);
        }
        return method;
    }
    private void saveValueCode(Object object,HashMap<String, String> hashMap) throws InvocationTargetException, IllegalAccessException {
        Method method = suchMethod(object.getClass(),"setAttribute",String.class,Object.class);
        if (method != null){
            for (String s : hashMap.keySet()) {
                String value = hashMap.get(s);
                method.invoke(object,s,value);
            }
        }
//        HttpServletRequest request = null;
//        HttpSession session = null;
//        ServletContext application = null;


//        if (object instanceof HttpServletRequest){
//            request = (HttpServletRequest)object;
//            for (String s : hashMap.keySet()) {
//                String value = hashMap.get(s);
//                request.setAttribute(s,value);
//            }
//        } else if (object instanceof HttpSession) {
//            session = (HttpSession) object;
//            for (String s : hashMap.keySet()) {
//                String value = hashMap.get(s);
//                session.setAttribute(s,value);
//            }
//        } else if (object instanceof ServletContext){
//            application = (ServletContext) object;
//            for (String s : hashMap.keySet()) {
//                String value = hashMap.get(s);
//                application.setAttribute(s,value);
//            }
//        }
    }

    private void saveValueToCode(PlaceCod[] placeCods,HttpServletRequest request,HashMap<String, String> hashMap) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        for (PlaceCod placeCod : placeCods) {
            switch (placeCod){
                case request:this.saveValueCode(request,hashMap);
//                    for (String s : hashMap.keySet()) {
//                        String value = hashMap.get(s);
//                        request.setAttribute(s,value);
//                    }
                    break;
                case session:this.saveValueCode(request.getSession(),hashMap);
//                    for (String s : hashMap.keySet()) {
//                        String value = hashMap.get(s);
//                        request.getSession().setAttribute(s,value);
//                    }
                    break;
                case application:this.saveValueCode(getServletContext(),hashMap);
//                    for (String s : hashMap.keySet()) {
//                        String value = hashMap.get(s);
//                        getServletContext().setAttribute(s,value);
//                    }
                    break;
            }
        }
    }

    private void forwardOrSendRedirect(ControllerMethodContent methodContent,Object methodReturn,HttpServletResponse response,HttpServletRequest request) throws IllegalAccessException, InstantiationException, IOException, ServletException, NoSuchMethodException, InvocationTargetException {
        if (!(methodReturn == null || "null".equals(methodReturn) || "".equals(methodReturn))){
            if (methodReturn instanceof  String)forwardOrSendRedirect((String) methodReturn,request,response);
            else if (methodReturn instanceof ForwarContent){
                ForwarContent forwarContent  = (ForwarContent) methodReturn;
                HashMap<String, String> hashMap = forwarContent.getCopeAttribute();
                PlaceCod[] placeCod = methodContent.getPlaceCods();
                if (placeCod != null && placeCod.length != 0){
                    saveValueToCode(placeCod,request,hashMap);
                }
                forwardOrSendRedirect(forwarContent.getMethodReturn(),request,response);
            }
        }
    }


    @Override
    public void init(){
        loadControllerConfig();
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String url = getUrl(request);

            Object obj = this.getObj(url, request);

            String methodName = request.getParameter("method");

            ControllerMethodContent methodContent = controllMethod.get(obj).get(methodName);
            Method method = methodContent.getMethod();
            Object[] params = dIForMethod(method,request);

            Object methodReturn;
            if (params.length == 0 || params == null){
                methodReturn = method.invoke(obj);
            }else {
                methodReturn = method.invoke(obj,params);
            }

            forwardOrSendRedirect(methodContent,methodReturn,response,request);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
