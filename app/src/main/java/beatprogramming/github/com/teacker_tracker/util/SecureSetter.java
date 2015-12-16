package beatprogramming.github.com.teacker_tracker.util;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by malkomich on 16/12/15.
 */
public class SecureSetter {

    private static final String SET_ID_METHOD = "setId";

    /**
     * Sets an attribute of the DTO, whose setter is inaccessible.
     *
     * @param dto
     *            Data Transfer Object of our domain
     * @param methodName
     *            Setter method name
     * @param type
     *            Type of the attribute to set
     * @param value
     *            Value of the attribute to set
     */
    public static <T> void setAttribute(Serializable dto, String methodName, Class<T> type,
                                        T value) {

        try {
            Method setIdMethod = findMethod(dto.getClass(), methodName, type);
            setIdMethod.setAccessible(true);
            setIdMethod.invoke(dto, value);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Sets the ID attribute of the DTO, whose setter is inaccessible.
     *
     * @param dto
     *            Data Transfer Object of our domain
     * @param value
     *            Value of the ID to set
     */
    public static void setId(Serializable dto, Integer value) {

        setAttribute(dto, SET_ID_METHOD, Integer.class, value);

    }

    /**
     * Retrieves the method instance we want to access.
     *
     * @param dtoClass
     *            DTO specific class
     * @param methodName
     *            Setter method name
     * @param type
     *            Type of the attribute to set
     * @return Setter method
     */
    private static Method findMethod(Class<?> dtoClass, String methodName, Class<?> type) {

        Method method = null;
        try {
            method = dtoClass.getDeclaredMethod(methodName, type);
        } catch (NoSuchMethodException e) {
            System.out.println(e);
        }

        return method;
    }
}
