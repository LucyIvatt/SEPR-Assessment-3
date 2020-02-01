package com.dicycat.kroy;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Stores textures for classes to reference.
 * This means multiple of the same sprite use the same reference.
 * Because of this, render calls are reduced.
 * 
 * @author Riju De
 *
 */
public class GameTextures {
	private Texture truck0, truck1, truck2, truck3, ufo, bullet, fireStation, fireStationDead;
	private ArrayList<Texture> trucks;
	private Texture[] livingFortresses = {new Texture("cliffords tower.png"), new Texture("york minster.png"), new Texture("york museum.png")};
	private Texture[] deadFortresses = {new Texture("cliffords tower dead.png"), new Texture("york minster dead.png"), new Texture("york museum dead.png")};
	private String[] truckAddress = {"fireTruck1.png", "fireTruck2.png", "fireTruck3.png", "fireTruck4.png"};
	
	
	/**
	 */
	public GameTextures() {
		truck0 = new Texture(truckAddress[0]);
		truck1 = new Texture(truckAddress[1]);
		truck2 = new Texture(truckAddress[2]);
		truck3 = new Texture(truckAddress[3]);
		trucks = new ArrayList<>(Arrays.asList(truck0, truck1, truck2, truck3));
		ufo = new Texture("ufo.png");
		bullet = new Texture("bullet.png");
		fireStation = new Texture("FireStationTemp.png");
		fireStationDead = new Texture("FireStationTempDead.png");	
	}
	
	public Texture getTruck(int truckNum) {
		return trucks.get(truckNum);
	}
	
	public Texture getUFO() {
		return ufo;
	}
	
	public Texture getBullet() {
		return bullet;
	}
	
	public Texture getFortress(int fortress) {
		return livingFortresses[fortress];
	}
	
	public Texture getDeadFortress(int fortress) {
		return deadFortresses[fortress];
	}
	
	public Texture getFireStation() {
		return fireStation;
	}
	public Texture getFireStationDead() {
		return fireStationDead;
	}
}
