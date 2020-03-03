package io.github.vampirestudios.tdg.objs;

import io.github.vampirestudios.tdg.objs.entities.creatures.PlayerEntity;
import org.mini2Dx.miniscript.core.GameFuture;

public class MoveFuture extends GameFuture {
	private final PlayerEntity gameCharacter;
	private final float targetX, targetY;

	public MoveFuture(PlayerEntity gameCharacter, float targetX, float targetY) {
		super();
		this.gameCharacter = gameCharacter;
		this.targetX = targetX;
		this.targetY = targetY;
	}

	@Override
	protected boolean update(float delta) {
		if(gameCharacter.getX() < targetX) {
			gameCharacter.setX(gameCharacter.getX() + gameCharacter.getSpeed());
		} else if(gameCharacter.getX() > targetX) {
			gameCharacter.setX(gameCharacter.getX() - gameCharacter.getSpeed());
		}
		if(gameCharacter.getY() < targetY) {
			gameCharacter.setY(gameCharacter.getY() + gameCharacter.getSpeed());
		} else if(gameCharacter.getY() > targetY) {
			gameCharacter.setY(gameCharacter.getY() - gameCharacter.getSpeed());
		}
		return gameCharacter.getX() == targetX && gameCharacter.getY() == targetY;
	}

	@Override
	protected void onFutureSkipped() {}

	@Override
	protected void onScriptSkipped() {}
}