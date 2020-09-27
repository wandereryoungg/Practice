package com.young.practice.classloaderUtil;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * Function:
 * Project:JNILoadJar
 * Date:2020/8/25
 * Created by xiaojun .
 */

public class ObjectUtils {
    private final static String CONSTRUCTOR = "<init>";
    public static Field findField(Class<?> clazz, String fieldName) {
        String fullFieldName = clazz.getName() + '#' + fieldName;

        try {
            Field field = findFieldRecursiveImpl(clazz, fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldError(fullFieldName);
        }
    }
    public static Object getObjectField(Object obj, String fieldName) {
        try {
            Field field = findFieldRecursiveImpl(obj.getClass(), fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            Log.e("clong","getObjectField error:"+e.toString());
            return null;
        }
    }

    private static Field findFieldRecursiveImpl(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            while (true) {
                clazz = clazz.getSuperclass();
                if (clazz == null || clazz.equals(Object.class))
                    break;

                try {
                    return clazz.getDeclaredField(fieldName);
                } catch (NoSuchFieldException ignored) {}
            }
            throw e;
        }
    }
    /** Sets the value of an object field in the given object instance. A class reference is not sufficient! See also {@link #findField}. */
    public static void setObjectField(Object obj, String fieldName, Object value) {
        try {
            findField(obj.getClass(), fieldName).set(obj, value);
        } catch (IllegalAccessException e) {
            // should not happen
            throw new IllegalAccessError(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public static Method findMethodExact(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        String fullMethodName = clazz.getName() + '#' + methodName;
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(fullMethodName);
        }
    }
    public static final ClassLoader BOOTCLASSLOADER = ClassLoader.getSystemClassLoader();

    public static Class<?> findClass(String className, ClassLoader classLoader) {
        if (classLoader == null)
            classLoader = BOOTCLASSLOADER;
        try {
            return ClassUtils.getClass(classLoader, className, false);
        } catch (ClassNotFoundException e) {
            Log.e("clong","findClass error:"+e.toString());
            return null;
        }
    }
    private static String getObjectClass(int index, String paramSig) {
        String objectClass = null;

        String subParam = paramSig.substring(index + 1);
        objectClass = subParam.split(";")[0].replace('/', '.');

        return objectClass;
    }

    private static String getArrayClass(int index, String paramSig) {
        int count = 0;
        StringBuilder arrayClassBuilder = new StringBuilder("");

        while (paramSig.charAt(index) == '[') {
            count++;
            index++;
            arrayClassBuilder.append('[');
        }

        if (paramSig.charAt(index) == 'L') {
            String subParam = paramSig.substring(index);
            arrayClassBuilder.append(subParam.split(";")[0].replace('/', '.'));
            arrayClassBuilder.append(";");
        } else {
            arrayClassBuilder.append(paramSig.charAt(index));
        }

        return arrayClassBuilder.toString();
    }
    public static Member getMethod(String className, String methodName, String paramSig, ClassLoader targetClassLoader, ClassLoader hookClassLoader, ClassLoader forwardClassLoader) {
        if(className.isEmpty() || methodName.isEmpty()) {
            return null;
        }

        int index = 0;
        ArrayList<Class> paramsArray = new ArrayList<Class>();
        while(index < paramSig.length()) {
            switch (paramSig.charAt(index)) {
                case 'B': // byte
                    paramsArray.add(byte.class);
                    break;
                case 'C': // char
                    paramsArray.add(char.class);
                    break;
                case 'D': // double
                    paramsArray.add(double.class);
                    break;
                case 'F': // float
                    paramsArray.add(float.class);
                    break;
                case 'I': // int
                    paramsArray.add(int.class);
                    break;
                case 'J': // long
                    paramsArray.add(long.class);
                    break;
                case 'S': // short
                    paramsArray.add(short.class);
                    break;
                case 'Z': // boolean
                    paramsArray.add(boolean.class);
                    break;
                case 'L':
                    try {
                        String objectClass = getObjectClass(index,paramSig);

                        if(hookClassLoader != null) {
                            paramsArray.add(Class.forName(objectClass,true,hookClassLoader));
                        }else if(forwardClassLoader != null) {
                            paramsArray.add(Class.forName(objectClass,true,forwardClassLoader));
                        }else if(targetClassLoader != null) {
                            paramsArray.add(Class.forName(objectClass,true,targetClassLoader));
                        }else {
                            paramsArray.add(Class.forName(objectClass));
                        }

                        index += objectClass.length() + 1;
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case '[':
                    try {
                        String arrayClass = getArrayClass(index,paramSig);

                        if(hookClassLoader != null) {
                            paramsArray.add(Class.forName(arrayClass,true,hookClassLoader));
                        }else if(forwardClassLoader != null) {
                            paramsArray.add(Class.forName(arrayClass,true,forwardClassLoader));
                        }else if(targetClassLoader != null) {
                            paramsArray.add(Class.forName(arrayClass,true,targetClassLoader));
                        }else {
                            paramsArray.add(Class.forName(arrayClass));
                        }

                        index += arrayClass.length() - 1;
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            index++;
        }

        Class[] params = new Class[paramsArray.size()];
        for(int i = 0; i < paramsArray.size(); i++) {
            params[i] = paramsArray.get(i);
        }

        Member method = null;
        try {
            if(hookClassLoader != null) {
                if(CONSTRUCTOR.equals(methodName)) {
                    method = Class.forName(className,true,hookClassLoader).getDeclaredConstructor(params);
                }else {
                    method = Class.forName(className,true,hookClassLoader).getDeclaredMethod(methodName,params);
                }
            }else if(forwardClassLoader != null) {
                if(CONSTRUCTOR.equals(methodName)) {
                    method = Class.forName(className,true,forwardClassLoader).getDeclaredConstructor(params);
                }else {
                    method = Class.forName(className,true,forwardClassLoader).getDeclaredMethod(methodName,params);
                }
            }else if(targetClassLoader != null) {
                if(CONSTRUCTOR.equals(methodName)) {
                    method = Class.forName(className,true,targetClassLoader).getDeclaredConstructor(params);
                }else {
                    method = Class.forName(className,true,targetClassLoader).getDeclaredMethod(methodName,params);
//                    method = ObjectUtils.findClass(className,targetClassLoader).getDeclaredMethod(methodName,params);
                }
            }else {
                if(CONSTRUCTOR.equals(methodName)) {
                    method = Class.forName(className).getDeclaredConstructor(params);
                }else {
                    method = Class.forName(className).getDeclaredMethod(methodName,params);
                }
            }
        }catch (Exception e) {
            Log.e("clong",e.toString());
            e.printStackTrace();
        }
        return method;
    }


    public static void setStaticObjectField(Class<?> clazz, String fieldName, Object value) {
        try {
            findField(clazz, fieldName).set((Object)null, value);
        } catch (IllegalAccessException var4) {
            throw new IllegalAccessError(var4.getMessage());
        } catch (IllegalArgumentException var5) {
            throw var5;
        }
    }

}
