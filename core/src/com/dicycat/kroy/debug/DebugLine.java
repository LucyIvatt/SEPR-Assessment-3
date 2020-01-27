package com.dicycat.kroy.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

/**
 * Draws a line debug
 * 
 * @author Riju De
 *
 */
public class DebugLine extends DebugDraw {
	Vector2 start;
	Vector2 end;
	int lineWidth;
	Color color;

	/**
	 * @param a Start point of the line
	 * @param b End point of the line
	 * @param width	Width of the line
	 * @param colour Colour to draw
	 */
	public DebugLine(Vector2 a, Vector2 b, int width, Color colour) {
		super();
		start = a;
		end = b;
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
        debugRenderer.line(start, end);
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
}
