package com.dicycat.kroy.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

/**
 * Draw a debug rectangle
 * 
 * @author Riju De
 *
 */
public class DebugRect extends DebugDraw {
	Vector2 bottomLeft;
	Vector2 dimensions;
	int lineWidth;
	Color color;

	/**
	 * @param bLeft Bottom left of the rectangle
	 * @param size Dimensions of the rectangle (Width, Length)
	 * @param width Width of the line
	 * @param colour Colour to draw
	 */
	public DebugRect(Vector2 bLeft, Vector2 size, int width, Color colour) {
		super();
		bottomLeft = bLeft;
		dimensions = size;
		lineWidth = width;
		color = colour;
	}

	@Override
	public void Draw(Matrix4 projectionMatrix)
    {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        debugRenderer.rect(bottomLeft.x, bottomLeft.y, dimensions.x, dimensions.y);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
}
