package coffeecatteam.theultimatetile.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The {@link ApiInternal} annotation marks anything that is API-internal and
 * that is discouraged from being used directly inside of reasonable
 * modifications or other applications. Due to this not being enforced in any
 * way, this is simply a request.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiInternal {

}