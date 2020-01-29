package games.labyrinthe;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import app.AppLoader;

public class Cell{

	private World world;
	private boolean northWall,southWall,westWall,eastWall;
	private int i,j;
	private boolean itsATrap;
	private boolean isFinalCell;
	private boolean giveScore;
	private Image sprite;

	public Cell(World world, int i, int j) {
		this.world = world;
		this.i = i;
		this.j = j;
		this.setNorthWall(true);
		this.setSouthWall(true);
		this.setEastWall(true);
		this.setWestWall(true);
		this.giveScore=false;
		this.isFinalCell=false;
		this.itsATrap=false;
		//TODO LIGNE A CORRIGER PLUS TARD
		this.sprite = AppLoader.loadPicture("/images/labyrinthe/noWalls.png");
	}

	public Cell(int i, int j, boolean east, boolean north, boolean south, boolean west) {
		this.i = i;
		this.j = j;
		this.setNorthWall(north);
		this.setSouthWall(south);
		this.setEastWall(east);
		this.setWestWall(west);
		this.giveScore=false;
		this.isFinalCell=false;
		this.itsATrap=false;
		autoSetSprite();
	}

	public void autoSetSprite() {
	// Automatically selects the right sprite
		String path_prefix = "/images/labyrinthe/";

		if(itsATrap) path_prefix+="Trapped/";
		else if(giveScore) path_prefix+="Points/";
		else if(isFinalCell) path_prefix+="Exit/";

		if(eastWall){
			if(northWall){
				if(westWall) sprite = AppLoader.loadPicture(path_prefix+"deadEnd_up.png");
				else if(southWall) sprite = AppLoader.loadPicture(path_prefix+"deadEnd_right.png");
				else sprite = AppLoader.loadPicture(path_prefix+"corner_left-down.png");
			}
			else{
				if(westWall){
					if(southWall) sprite = AppLoader.loadPicture(path_prefix+"deadEnd_down.png");
					else sprite = AppLoader.loadPicture(path_prefix+"vertical.png");
				}
				else{
					if(southWall) sprite = AppLoader.loadPicture(path_prefix+"corner_left-up.png");
					else sprite = AppLoader.loadPicture(path_prefix+"tJunction_right.png");
				}
			}
		}
		else{
			if(northWall){
				if(westWall){
					if(southWall) sprite = AppLoader.loadPicture(path_prefix+"deadEnd_left.png");
					else sprite = AppLoader.loadPicture(path_prefix+"corner_right-down.png");
				}
				else{
					if(southWall) sprite = AppLoader.loadPicture(path_prefix+"horizontal.png");
					else sprite = AppLoader.loadPicture(path_prefix+"tJunction_up.png");
				}
			}
			else{
				if(westWall){
					if(southWall) sprite = AppLoader.loadPicture(path_prefix+"corner_right-up.png");
					else sprite = AppLoader.loadPicture(path_prefix+"tJunction_left.png");
				}
				else{
					if(southWall) sprite = AppLoader.loadPicture(path_prefix+"tJunction_down.png");
					else sprite = AppLoader.loadPicture(path_prefix+"noWalls.png");
				}
			}
		}
	}

	public boolean isWestWall() {
		return westWall;
	}

	public void setWestWall(boolean westWall) {
		this.westWall = westWall;
	}

	public boolean isNorthWall() {
		return northWall;
	}

	public void setNorthWall(boolean northWall) {
		this.northWall = northWall;
	}

	public boolean isSouthWall() {
		return southWall;
	}

	public void setSouthWall(boolean southWall) {
		this.southWall = southWall;
	}

	public boolean isEastWall() {
		return eastWall;
	}

	public void setEastWall(boolean eastWall) {
		this.eastWall = eastWall;
	}

	public boolean isItsATrap() {
		return itsATrap;
	}

	public void setItsATrap(boolean itsATrap) {
		this.itsATrap = itsATrap;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics context) {
		context.drawImage(sprite,j*64,i*64);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (world.getPlayer().getI()==this.getI() && world.getPlayer().getJ()==this.getJ()){
			if (giveScore){
				giveScore=false;
				autoSetSprite();
				world.setScore(world.getScore()+1);
			}
			if (isFinalCell){
				world.setState(3);
				game.enterState(world.getID());
				//System.out.println("fin");
			}
			if (itsATrap){
				world.setState(3);
				game.enterState(1 /* Choice */, new FadeOutTransition(), new FadeInTransition());
			}
		}
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public void setI(int i) {
		this.i = i;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}

	public boolean isFinalCell() {
		return isFinalCell;
	}

	public void setFinalCell(boolean isFinalCell) {
		this.isFinalCell = isFinalCell;
	}

	public boolean isGiveScore() {
		return giveScore;
	}

	public void setGiveScore(boolean giveScore) {
		this.giveScore = giveScore;
	}

}
