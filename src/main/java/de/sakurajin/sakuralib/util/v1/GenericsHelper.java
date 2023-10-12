package de.sakurajin.sakuralib.util.v1;

/**
 * A helper class to work around some of the annoyances of java generics.
 */
public class GenericsHelper {
    /**
     * Check if a class is a number.
     * This is needed since getClass().equals(Number.class) is always false.
     * @param classToCheck The class to check.
     * @return True if the class is a number. false otherwise.
     */
    static public boolean isNumber(Class<?> classToCheck){
        if(classToCheck.equals(Byte.class)) return true;
        if(classToCheck.equals(Short.class)) return true;
        if(classToCheck.equals(Integer.class)) return true;
        if(classToCheck.equals(Long.class)) return true;
        if(classToCheck.equals(Float.class)) return true;
        if(classToCheck.equals(Double.class)) return true;
        return false;
    }

    /**
     * Check if an object is a number.
     * This is a short version for isNumber(objectToCheck.getClass())
     * @param objectToCheck The object to check.
     * @return True if the object is a number. false otherwise.
     */
    static public boolean isNumber(Object objectToCheck){
        return isNumber(objectToCheck.getClass());
    }
}
