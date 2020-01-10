package io.github.vampirestudios.tdg.objs.items;

import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;

public interface IInteractable {

    boolean onInteracted(PlayerEntity player);
}
