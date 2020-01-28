package games.labyrinthe;

import java.io.File;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import games.labyrinthe.characters.GridlockedPlayer;

public class World extends BasicGameState {

	public final static String GAME_NAME="Labyrinthe";
	public final static String GAME_FOLDER_NAME="labyrinthe";
	public final static String DIRECTORY_SOUNDS="/sounds"+File.separator+GAME_FOLDER_NAME+File.separator;
	public final static String DIRECTORY_MUSICS="/musics"+File.separator+GAME_FOLDER_NAME+File.separator;
	public final static String DIRECTORY_IMAGES="/images"+File.separator+GAME_FOLDER_NAME+File.separator;

	private Labyrinth labyrinth;
	private MazeGenerator mazeGenerator;
	private GridlockedPlayer player;
	private int score;

	private int ID;
	private int state;

	public World(int ID) {
		this.ID = ID;
		this.state = 0;
	}

	@Override
	public int getID() {
		return this.ID;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au chargement du programme */
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à l'apparition de la page */
		if (this.state == 0) {
			this.play(container, game);
		} else if (this.state == 2) {
			this.resume(container, game);
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée à la disparition de la page */
		if (this.state == 1) {
			this.pause(container, game);
		} else if (this.state == 3) {
			this.stop(container, game);
			this.state = 0; // TODO: remove
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		/* Méthode exécutée environ 60 fois par seconde */
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			this.setState(1);
			game.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		player.update(container, game, delta);
		labyrinth.update(container, game, delta);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		/* Méthode exécutée environ 60 fois par seconde */
		context.setColor(Color.white);
		labyrinth.render(container, game, context);
		player.render(container, game, context);
	}

	public void play(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois au début du jeu */
		labyrinth = new Labyrinth(this,10,15);
		player = new GridlockedPlayer(this,0,0);
		mazeGenerator = new MazeGenerator(labyrinth);
		mazeGenerator.mazeGenrator();
}

	public void pause(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la mise en pause du jeu */
	}

	public void resume(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée lors de la reprise du jeu */
	}

	public void stop(GameContainer container, StateBasedGame game) {
		/* Méthode exécutée une unique fois à la fin du jeu */
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return this.state;
	}

	public Labyrinth getLabyrinth(){
		return labyrinth;
	}

	@Override
	public void keyPressed(int key, char c) {
		player.keyPressed(key, c);
	}

	@Override
	public void keyReleased(int key, char c) {
		player.keyReleased(key, c);
	}

	public GridlockedPlayer getPlayer(){
		return player;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}


}
