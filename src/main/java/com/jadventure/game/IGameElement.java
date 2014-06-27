package com.jadventure.game;

public interface IGameElement {

	void accept(IGameElementVisitor visitor);

}
