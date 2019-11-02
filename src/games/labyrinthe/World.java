package games.labyrinthe;

import java.io.File;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import games.labyrinthe.characters.GridlockedPlayer;
import menus.MainMenu;

public class World extends BasicGameState{

	public static int ID=1;

	public final static String GAME_NAME="Labyrinthe";
	public final static String GAME_FOLDER_NAME="labyrinthe";
	public final static String DIRECTORY_SOUNDS="sounds"+File.separator+GAME_FOLDER_NAME+File.separator;
	public final static String DIRECTORY_MUSICS="musics"+File.separator+GAME_FOLDER_NAME+File.separator;
	public final static String DIRECTORY_IMAGES="images"+File.separator+GAME_FOLDER_NAME+File.separator;

	private static Labyrinth labyrinth;
	private static MazeGenerator mazeGenerator;
	private static GridlockedPlayer player;
	private static int score;
	private static StateBasedGame game;

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		game=arg1;
	}

	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1){
		//Ici mettre tous les chargement d'image, creation de perso/decor et autre truc qui mettent du temps
		labyrinth = new Labyrinth(10,15);
		reset();
	}


	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		//Affichage
		arg2.setColor(Color.white);
		labyrinth.render(arg0, arg1, arg2);
		player.render(arg0, arg1, arg2);

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		player.update(arg0, arg1, arg2);
		World.labyrinth.update(arg0, arg1, arg2);
	}

	@Override
	public int getID() {
		return ID;
	}

	public static void reset() {
		labyrinth = new Labyrinth(10,15);
		player = new GridlockedPlayer(0,0);
		mazeGenerator = new MazeGenerator(labyrinth);
		try {
			mazeGenerator.mazeGenrator();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Labyrinth getLabyrinth(){
		return labyrinth;
	}

	@Override
	public void keyPressed(int key, char c) {
		player.keyPressed(key, c);
		if(key==Input.KEY_ESCAPE){
			game.enterState(MainMenu.ID);
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		player.keyReleased(key, c);
	}

	public static GridlockedPlayer getPlayer(){
		return player;
	}

	public static int  getScore() {
		return score;
	}

	public static void setScore(int score) {
		World.score = score;
	}


}
