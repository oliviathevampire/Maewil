package coffeecatteam.theultimatetile.inventory.items;

import coffeecatteam.theultimatetile.entities.player.EntityPlayer;

public interface IInteractable {

    boolean onInteracted(EntityPlayer player);
}
