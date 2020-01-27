package com.dicycat.kroy.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

/**
 * Draws a circle debug
 * 
 * @author Riju De
 *
 */
public class DebugCircle extends DebugDraw {
	Vector2 position;
	float radius;
	int lineWidth;
	Color color;

	/**
	 * @param pos Position to draw at
	 * @param rad Radius of the circle
	 * @param width Width of the line
	 * @param colour Colour to draw
	 */
	public DebugCircle(Vector2 pos, float rad, int width, Color colour) {
		super();
		position = pos;
		radius = rad;
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
        debugRenderer.circle(position.x, position.y, radius);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
}
