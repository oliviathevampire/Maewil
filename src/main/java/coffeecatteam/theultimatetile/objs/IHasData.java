package coffeecatteam.theultimatetile.objs;

/**
 * @author CoffeeCatRailway
 * Created: 22/02/2019
 */
public interface IHasData<E> {

    String getId();

    E newCopy();

    <T extends E> T newCopy(T obj);
}
