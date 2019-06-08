package coffeecatteam.theultimatetile.objs.items;

import coffeecatteam.theultimatetile.objs.entities.creatures.PlayerEntity;

public interface IInteractable {

    boolean onInteracted(PlayerEntity player);
}
