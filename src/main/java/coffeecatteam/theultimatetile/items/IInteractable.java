package coffeecatteam.theultimatetile.items;

import coffeecatteam.theultimatetile.entities.creatures.EntityPlayer;

public interface IInteractable {

    boolean onInteracted(EntityPlayer player);
}
