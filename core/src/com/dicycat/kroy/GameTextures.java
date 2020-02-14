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

	// TRUCK_SELECT_CHANGE_1 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	// Changed truck texture to truck0, truck1, truck2, truck3 as all the different textures will be used with our
	// new selection method and defined the array list which will be used to store them
	private Texture truck0, truck1, truck2, truck3, ufo, bullet, fireStation, fireStationDead;
	private ArrayList<Texture> trucks;

	// TRUCK_SELECT_CHANGE_1 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	// NEW_FORTRESSES_1 - START OF MODIFICATION - NP STUDIOS - Alasdair Pilmore-Bedford ---------------------------
	// Added new textures add for new fortresses
	private Texture[] livingFortresses = {new Texture("cliffords tower.png"), new Texture("york minster.png"),
			new Texture("york museum.png"), new Texture ("YorkRailStationWithEvilAliens.png"),
			new Texture ("YorkHospital.png"), new Texture ("CentralHall.png")};

	private Texture[] deadFortresses = {new Texture("cliffords tower dead.png"), new Texture("york minster dead.png"),
			new Texture("york museum dead.png"), new Texture ("YorkRailStationDestoryed.png"),
			new Texture ("YorkHospitalDeaded.png"), new Texture ("CentralHallDeaded.png")};
	// NEW_FORTRESSES_1 - END OF MODIFICATION - NP STUDIOS - Alasdair Pilmore-Bedford ---------------------------

	private String[] truckAddress = {"fireTruck1.png", "fireTruck2.png", "fireTruck3.png", "fireTruck4.png"};

	public GameTextures() {
		// TRUCK_SELECT_CHANGE_2 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
		// Initialises all 4 truck texures and adds them to an array list rather than just the single texture used previously.
		truck0 = new Texture(truckAddress[0]);
		truck1 = new Texture(truckAddress[1]);
		truck2 = new Texture(truckAddress[2]);
		truck3 = new Texture(truckAddress[3]);
		trucks = new ArrayList<>(Arrays.asList(truck0, truck1, truck2, truck3));
		// TRUCK_SELECT_CHANGE_2 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----
		ufo = new Texture("ufo.png");
		bullet = new Texture("bullet.png");
		fireStation = new Texture("FireStationTemp.png");
		fireStationDead = new Texture("FireStationTempDead.png");	
	}

	// TRUCK_SELECT_CHANGE_3 - START OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	// Slightly edited the getter for the truck texture as it needs to access the arraylist trucks with the index
	// of which truck was needed, whereas previously it would only access the texture of the truck the user selected
	// before the game began.
	public Texture getTruck(int truckNum) {
		return trucks.get(truckNum);
	}
	// TRUCK_SELECT_CHANGE_13 - END OF MODIFICATION - NP STUDIOS - LUCY IVATT----
	
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
