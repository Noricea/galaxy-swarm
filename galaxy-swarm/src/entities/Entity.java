package entities;

import models.TexturedModel;

public class Entity {

	private TexturedModel model;

	public Entity(TexturedModel model) {
		this.model = model;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}
}
