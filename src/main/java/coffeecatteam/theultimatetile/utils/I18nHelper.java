package coffeecatteam.theultimatetile.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * @author pepijn
 */
public class I18nHelper {
    public static String m(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
    
    public static String m(Enum<?> e) {
        try {
            return ENUM_BUNDLES.get(e.getDeclaringClass()).getString(e.name());
        } catch (NullPointerException ex) {
            ResourceBundle rb = ResourceBundle.getBundle("coffeecatteam.theultimatetile.resources." + e.getDeclaringClass().getSimpleName());
            ENUM_BUNDLES.put(e.getClass(), rb);
            return rb.getString(e.name());
        }
    }
    
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("coffeecatteam.theultimatetile.resources.strings");
    private static final Map<Class<? extends Enum>, ResourceBundle> ENUM_BUNDLES = new HashMap<>();
}