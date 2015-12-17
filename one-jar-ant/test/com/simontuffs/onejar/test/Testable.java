package com.simontuffs.onejar.test;

import java.lang.reflect.Method;

/**
 * This class is used to assist with JUnit testing.  It lets the one-jar-example be built
 * without any dependency on JUnit. 
 * @author simon
 *
 */
public class Testable {

    public Error cause;
    public int failures;
    public int count, skipped;
    
    public void fail(String reason) throws Error {
        cause = new Error(reason);
        System.out.println("******************************************************************************************************************");
        System.out.println("* fail: " + reason);
        System.out.println("******************************************************************************************************************");
        failures++;
    }
    
    public void runTests() throws Exception {
        Method methods[] = this.getClass().getMethods();
        for (int i=0; i<methods.length; i++) {
            Method method = methods[i];
            if (!method.getName().equals("runTests") && method.getName().startsWith("test")) {
                System.out.println("testing: " + method);
                method.invoke(this, null);
                System.out.println("------------------------------------------------------------------------------------------------------------------");
            }
        }
    }

}
