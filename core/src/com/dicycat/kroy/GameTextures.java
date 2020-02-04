package com.dicycat.kroy;

import com.badlogic.gdx.graphics.Texture;

/**
 * Stores textures for classes to reference.
 * This means multiple of the same sprite use the same reference.
 * Because of this, render calls are reduced.
 * 
 * @author Riju De
 *
 */
public class GameTextures {
	private Texture truck, ufo, bullet, fireStation, fireStationDead;
	// NEW_FORTRESSES - START OF MODIFICATION - NP STUDIOS - Alasdair Pilmore-Bedford ---------------------------
	// Added new textures add for new fortresses
	private Texture[] livingFortresses = {new Texture("cliffords tower.png"), new Texture("york minster.png"),
			new Texture("york museum.png"), new Texture ("YorkRailStationWithEvilAliens.png"),
			new Texture ("YorkHospital.png"), new Texture ("CentralHall.png")};

	private Texture[] deadFortresses = {new Texture("cliffords tower dead.png"), new Texture("york minster dead.png"),
			new Texture("york museum dead.png"), new Texture ("YorkRailStationDestoryed.png"),
			new Texture ("YorkHospitalDeaded.png"), new Texture ("CentralHallDeaded.png")};

	// NEW_FORTRESSES - END OF MODIFICATION - NP STUDIOS - Alasdair Pilmore-Bedford ---------------------------
	private String[] truckAddress = {"fireTruck1.png", "fireTruck2.png", "fireTruck3.png", "fireTruck4.png"};
	
	
	/**
	 * @param truckNum Which truck texture to get
	 */
	public GameTextures(int truckNum) {
		truck = new Texture(truckAddress[truckNum]);
		ufo = new Texture("ufo.png");
		bullet = new Texture("bullet.png");
		fireStation = new Texture("FireStationTemp.png");
		fireStationDead = new Texture("FireStationTempDead.png");	
	}
	
	public Texture getTruck() {
		return truck;
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
