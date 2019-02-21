package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.theultimatetile.objs.entities.creatures.EntityPlayer;

public interface IInteractable {

    boolean onInteracted(EntityPlayer player);
}
