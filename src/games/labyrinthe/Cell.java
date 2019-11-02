package games.labyrinthe;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Cell{

	private boolean northWall,southWall,westWall,eastWall;
	private int i,j;
	private boolean itsATrap;
	private boolean isFinalCell;
	private boolean giveScore;
	private Image sprite;

	public Cell (int i, int j)
	{
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
		try {
			this.sprite = new Image("images/labyrinthe/noWalls.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Cell (int i, int j, boolean east, boolean north, boolean south, boolean west){
		this.i = i;
		this.j = j;
		this.setNorthWall(north);
		this.setSouthWall(south);
		this.setEastWall(east);
		this.setWestWall(west);
		this.giveScore=false;
		this.isFinalCell=false;
		this.itsATrap=false;
		try {
			autoSetSprite();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void autoSetSprite() throws SlickException {
	// Automatically selects the right sprite
		String path_prefix = "images/labyrinthe/";

		if(itsATrap) path_prefix+="Trapped/";
		else if(giveScore) path_prefix+="Points/";
		else if(isFinalCell) path_prefix+="Exit/";

		if(eastWall){
			if(northWall){
				if(westWall) sprite = new Image(path_prefix+"deadEnd_up.png");
				else if(southWall) sprite = new Image(path_prefix+"deadEnd_right.png");
				else sprite = new Image(path_prefix+"corner_left-down.png");
			}
			else{
				if(westWall){
					if(southWall) sprite = new Image(path_prefix+"deadEnd_down.png");
					else sprite = new Image(path_prefix+"vertical.png");
				}
				else{
					if(southWall) sprite = new Image(path_prefix+"corner_left-up.png");
					else sprite = new Image(path_prefix+"tJunction_right.png");
				}
			}
		}
		else{
			if(northWall){
				if(westWall){
					if(southWall) sprite = new Image(path_prefix+"deadEnd_left.png");
					else sprite = new Image(path_prefix+"corner_right-down.png");
				}
				else{
					if(southWall) sprite = new Image(path_prefix+"horizontal.png");
					else sprite = new Image(path_prefix+"tJunction_up.png");
				}
			}
			else{
				if(westWall){
					if(southWall) sprite = new Image(path_prefix+"corner_right-up.png");
					else sprite = new Image(path_prefix+"tJunction_left.png");
				}
				else{
					if(southWall) sprite = new Image(path_prefix+"tJunction_down.png");
					else sprite = new Image(path_prefix+"noWalls.png");
				}
			}
		}

	}

	public boolean isWestWall()
	{
		return westWall;
	}

	public void setWestWall(boolean westWall)
	{
		this.westWall = westWall;
	}

	public boolean isNorthWall()
	{
		return northWall;
	}

	public void setNorthWall(boolean northWall)
	{
		this.northWall = northWall;
	}

	public boolean isSouthWall()
	{
		return southWall;
	}

	public void setSouthWall(boolean southWall)
	{
		this.southWall = southWall;
	}

	public boolean isEastWall()
	{
		return eastWall;
	}

	public void setEastWall(boolean eastWall)
	{
		this.eastWall = eastWall;
	}

	public boolean isItsATrap() {
		return itsATrap;
	}

	public void setItsATrap(boolean itsATrap) {
		this.itsATrap = itsATrap;
	}


	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException
	{
		arg2.drawImage(sprite,j*64,i*64);
	}


	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		if (World.getPlayer().getI()==this.getI() && World.getPlayer().getJ()==this.getJ()){
			if (giveScore){
				giveScore=false;
				autoSetSprite();
				World.setScore(World.getScore()+1);
			}
			if (isFinalCell){
				World.reset();
				//System.out.println("fin");
			}
			if (itsATrap){
				System.exit(0);
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
