/*
 * Created on May 20, 2005
 *
 */
package com.simontuffs.onejar.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author simon
 *
 */
public class Invoker {
    
    public static Object get(Object obj, String field) throws Exception {
        Field f = obj.getClass().getField(field);
        return f.get(obj);
    }

    public static void set(Object obj, String field, Object value) throws Exception {
        Field f = obj.getClass().getField(field);
        f.set(obj, value);
    }

    public static Object invoke(Class cls, String method) throws Exception {
        Method m = cls.getMethod(method, null);
        return m.invoke(null, null);
    }

    public static Object invoke(Class cls, String method, Class sig[], Object args[]) throws Exception {
        Method m = cls.getMethod(method, sig);
        return m.invoke(null, args);
    }
    
    public static Object invoke(Object obj, String method) throws Exception {
        return invoke(obj, method, null, null);
    }
    
    public static Object invoke(Object obj, String method, Class sig[], Object args[]) throws Exception {
        Method m = obj.getClass().getMethod(method, sig);
        Object result = m.invoke(obj, args);
        Error cause = Invoker.getCause(obj);
        if (cause != null) {
            Error e = new Error(cause.getMessage());
            e.setStackTrace(cause.getStackTrace());
            throw e;
        }
        return result;
    }
    
    public static Error getCause(Object object) throws Exception {
        Error cause = (Error)get(object, "cause");
        set(object, "cause", null);
        return cause;
    }

    public static class Result {
        public List out = new ArrayList(), err = new ArrayList();
        public int status;
        public String command;
        public void connect(InputStream is, final List list) {
            final BufferedReader br = new BufferedReader(new InputStreamReader(is));
            new Thread() {
                public void run() {
                    String line = null;
                    try {
                        while ((line = br.readLine()) != null) {
                            list.add(line);
                        }
                    } catch (IOException iox) {
                        // done.
                    }
                }
            }.start();
        }
        public String toString() {
            String NL = "\n";
            StringBuffer result = new StringBuffer();
            result.append("command=" + command + NL);
            result.append("status=" + status + NL);
            result.append("***** out *****" + NL);
            for (int i=0; i<out.size(); i++) { 
                result.append(out.get(i) + NL);
            }
            result.append("***** err *****" + NL);
            for (int i=0; i<err.size(); i++) { 
                result.append(err.get(i) + NL);
            }
            return result.toString();
        }
    }
    
    public static Result run(String commandline) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(commandline);
        Result result = new Result();
        result.command = commandline;
        result.connect(p.getInputStream(), result.out);
        result.connect(p.getErrorStream(), result.err);
        result.status = p.waitFor();
        return result;
    }

}
