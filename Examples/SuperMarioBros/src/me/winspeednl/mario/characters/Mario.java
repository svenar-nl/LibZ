package me.winspeednl.mario.characters;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import me.winspeednl.libz.audio.Sound;
import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.core.Input;
import me.winspeednl.libz.entities.LibZ_Mob;
import me.winspeednl.libz.entities.LibZ_Player;
import me.winspeednl.libz.image.Sprite;
import me.winspeednl.libz.level.Level;
import me.winspeednl.libz.level.Tile;
import me.winspeednl.libz.screen.Render;
import me.winspeednl.mario.Game;
import me.winspeednl.mario.Textures;
import me.winspeednl.mario.characters.Coin;

public class Mario extends LibZ_Player {
	private Game game;
	private GameCore gc;
	private Level level;

	private int[] pixels;
	private int currSprite = 0, lastDirection = 0, targetY = 0, jumpHeight = 200, tileHopHeight = 20, deadTargetY = 0, killCombo = 0;
	private double currGravity = 0, gravity = 9.81, currJumpSpeed = 0, jumpSpeed = 10, jumpStep = 5;
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	public ArrayList<Integer> goombas = new ArrayList<Integer>();
	private boolean canMoveDown = false, canMoveUp = false, canMoveLeft = false, canMoveRight = false, isJumping = false;
	private boolean jumpKeyPressed = false, requestStopJump = false, isBig = false, isFire = false;
	private long oldMillis = 0;
	public int lives = 3;
	public boolean isDead = false, isDying = false, inputEnabled = false;
	
	ArrayList<Integer> tileTargetY = new ArrayList<Integer>();
	ArrayList<Integer> tileOriginalY = new ArrayList<Integer>();
	ArrayList<Boolean> tileGoBack = new ArrayList<Boolean>();
	ArrayList<Tile> tilesThatCouldChange = new ArrayList<Tile>();
	int tileHopMoveSpeed = 4;
	ArrayList<String> textToDraw = new ArrayList<String>();

	
	private ArrayList<Tile> tilesToHop = new ArrayList<Tile>();
	
	public Mario(Game game, GameCore gc, Level level) {
		this.game = game;
		this.gc = gc;
		this.level = level;
		this.w = 36;
		this.h = 48;
		this.moveSpeed = 5;
		this.x = 0;
		this.y = 530;
		
		Sprite
		standing = new Sprite("/characters/mario/small/standing.png", 0, 0, 36, 48),
		walk1 = new Sprite("/characters/mario/small/walk1.png", 0, 0, 45, 48),
		walk2 = new Sprite("/characters/mario/small/walk2.png", 0, 0, 45, 48),
		walk3 = new Sprite("/characters/mario/small/walk3.png", 0, 0, 45, 48),
		jump = new Sprite("/characters/mario/small/jump.png", 0, 0, 48, 48),
		die = new Sprite("/characters/mario/small/die.png", 0, 0, 42, 48),
		pole = new Sprite("/characters/mario/small/pole.png", 0, 0, 36, 48),
		duck = new Sprite("/characters/mario/small/standing.png", 0, 0, 36, 48);
		
		sprites.add(standing); // 0
		sprites.add(walk1); // 1
		sprites.add(walk2); // 2
		sprites.add(walk3); // 3
		sprites.add(jump); // 4
		sprites.add(die); // 5
		sprites.add(pole); // 6
		sprites.add(duck); // 7
		
		for (int x = 0; x < level.getWidth(); x++) {
			for (int y = 0; y < level.getHeight(); y++) {
			Tile tile = level.getTile(x, y);
			boolean tileThatCouldChange = !tile.getSprite().toLowerCase().contains("air") &&
				!tile.getSprite().toLowerCase().contains("pipe") &&
				!tile.getSprite().toLowerCase().contains("ground") &&
				!tile.getSprite().toLowerCase().contains("block2") &&
				!tile.getSprite().toLowerCase().contains("pole") &&
				!tile.getSprite().toLowerCase().contains("castle");
			
			if (tileThatCouldChange)
				tilesThatCouldChange.add(tile);
			}
		}
	}
	
	public void input(Input input) {
		if (input.isKeyPressed(KeyEvent.VK_W)) { // Jump
			if (!isJumping && !jumpKeyPressed) {
				jumpKeyPressed = true;
				jump();
				
				if (!game.audio.jumpSmall.isPlaying() && !game.audio.jumpSuper.isPlaying()) {
					if (!isBig) {
						game.audio.jumpSmall = new Sound("/audio/sfx/jump-small.wav");
						game.audio.jumpSmall.play();
					} else {
						game.audio.jumpSuper = new Sound("/audio/sfx/jump-super.wav");
						game.audio.jumpSuper.play();
					}
				}
			}
		} else {
			jumpKeyPressed = false;
		}
		
		if (input.isKeyPressed(KeyEvent.VK_S)) {
			currSprite = 7;
			canMoveDown = false;
		} else {
			if (currSprite == 7) {
				currSprite = 0;
				if (isBig)
					y -= 48;
			}
		}
		
		if (input.isKeyPressed(KeyEvent.VK_A)) {
			lastDirection = 1;
			moveX(-1);
		}
		
		if (input.isKeyPressed(KeyEvent.VK_D)) {
			lastDirection = 0;
			moveX(1);
		}
		
		boolean isWalkingSprite = (currSprite == 1 || currSprite == 2 || currSprite == 3);
		
		if (isWalkingSprite && !input.isKeyPressed(KeyEvent.VK_A) && !input.isKeyPressed(KeyEvent.VK_D) && !isJumping && !requestStopJump)
			currSprite = 0;
	}

	public void update(GameCore gc) {
		if (!isDying) {
			if (inputEnabled)
				input(gc.getInput());
			if (canMoveDown) {
				if (!isJumping || requestStopJump) {
					if (currGravity < gravity)
						currGravity += 0.4;
					y += currGravity;
				}
			} else {
				currGravity = 0;
				currJumpSpeed = 0;
			}
					
			
			checkDirections(gc);
			updateScreenOffset(gc);
	
			if (!canMoveDown) {
				if (!canMoveLeft)
					x+=moveSpeed;
				if (!canMoveRight)
					x-=moveSpeed;
			}
			
			if (canMoveDown)
				currSprite = 4;
			else {
				if (currSprite == 4 && !canMoveLeft && !canMoveRight)
					currSprite = 0;
				if (currSprite == 4 && canMoveLeft && canMoveRight)
					currSprite = 0;
			}
			
			if (isJumping && !requestStopJump)
				try{
					if (level != null) {
						Tile
						tileLeft = level.getTile((x+2)/48, (y-1)/48),
						tileRight = level.getTile((x+w-2)/48, (y-1)/48);
						
						boolean canHopLeft = !tileLeft.getSprite().toLowerCase().contains("air") &&
								!tileLeft.getSprite().toLowerCase().contains("pipe") &&
								!tileLeft.getSprite().toLowerCase().contains("ground") &&
								!tileLeft.getSprite().toLowerCase().contains("block2") &&
								!tileLeft.getSprite().toLowerCase().contains("pole") &&
								!tileLeft.getSprite().toLowerCase().contains("castle");
						
						boolean canHopRight = !tileRight.getSprite().toLowerCase().contains("air") &&
								!tileRight.getSprite().toLowerCase().contains("pipe") &&
								!tileRight.getSprite().toLowerCase().contains("ground") &&
								!tileRight.getSprite().toLowerCase().contains("block2") &&
								!tileRight.getSprite().toLowerCase().contains("pole") &&
								!tileRight.getSprite().toLowerCase().contains("castle");
						
						if (canHopLeft){
							if (!tilesToHop.contains(tileLeft)) {
								tilesToHop.add(tileLeft);
								tileTargetY.add(tileLeft.getY() - tileHopHeight);
								tileOriginalY.add(tileLeft.getY());
								tileGoBack.add(false);
								tileLeft.setY(tileLeft.getY() - 1);
							}
						}
						if (canHopRight){
							if (!tilesToHop.contains(tileRight)) {
								tilesToHop.add(tileRight);
								tileTargetY.add(tileRight.getY() - tileHopHeight);
								tileOriginalY.add(tileRight.getY());
								tileGoBack.add(false);
								tileRight.setY(tileRight.getY() - 1);
							}
						}
					}
				} catch (Exception e) {}
			if (targetY < y && isJumping)
				if (!requestStopJump)
					if (canMoveUp) {
						if (currJumpSpeed < jumpSpeed)
							currJumpSpeed += jumpStep;
						y -= currJumpSpeed;
					} else
						requestStopJump = true;
			if (targetY >= y && isJumping)
				requestStopJump = true;
			if (requestStopJump)
				if (!canMoveDown && isJumping) {
					isJumping = false;
					requestStopJump = false;
					jumpKeyPressed = false;
				}
		} else {
			currSprite = 5;
			die(gc);
		}
		
		hopTiles();
		
		if (y > gc.getHeight() && !isDying) {
			isDying = true;
		}

		if (x > 450) {
			spawnGoomba(0, 1032, 530);
			spawnGoomba(1, 2016, 530);
			spawnGoomba(2, 2544, 530);
			spawnGoomba(3, 2640, 530);
		}
		
		if (x > 3100) {
			spawnGoomba(4, 3744, 336);
			spawnGoomba(5, 3888, 144);
		}
		
		if (x > 4000) {
			spawnGoomba(6, 4560, 530);
			spawnGoomba(7, 4632, 530);
		}
		
		if (x > 5500) {
			spawnGoomba(8, 5904, 530);
			spawnGoomba(9, 5976, 530);
			spawnGoomba(10, 6072, 530);
			spawnGoomba(11, 6144, 530);
		}
		
		if (x > 7800) {
			spawnGoomba(12, 8304, 530);
			spawnGoomba(13, 8400, 530);
		}
		
		if (!canMoveDown)
			killCombo = 0;
		
		for (int i = 0; i < game.entities.size(); i++) {
			LibZ_Mob entity = (LibZ_Mob)game.entities.get(i);
			if (collides(entity.x, entity.y, entity.w, entity.h)) {
				if (entity instanceof Mushroom) {
					Mushroom mushroom = (Mushroom)entity;
					if (mushroom.canDie) {
						game.entities.remove(entity);
						pickedUpMushroom();
						textToDraw.add("1000:" + (mushroom.x - 50 - gc.getOffsetX()) + ":" + (mushroom.y - gc.getOffsetY()));
						game.score += 1000;
					}
				}
				
				if (entity instanceof FireFlower) {
					FireFlower fireFlower = (FireFlower)entity;
					if (fireFlower.canDie) {
						game.entities.remove(entity);
						pickedUpFireFlower();
						textToDraw.add("1000:" + (fireFlower.x - 50 - gc.getOffsetX()) + ":" + (fireFlower.y - gc.getOffsetY()));
						game.score += 1000;
					}
				}
				
				if (entity instanceof UP1Shroom) {
					UP1Shroom mushroom = (UP1Shroom)entity;
					if (mushroom.canDie) {
						game.entities.remove(entity);
						textToDraw.add("1UP:" + (mushroom.x - 50 - gc.getOffsetX()) + ":" + (mushroom.y - gc.getOffsetY()));
						lives++;
						
						game.audio.life1up = new Sound("/audio/sfx/1-up.wav");
						game.audio.life1up.play();
					}
				}
			}
			
			if (collides(entity.x+6, entity.y, entity.w-12, 16)) {
				if (entity instanceof Goomba) {
					Goomba goomba = (Goomba)entity;
					if (goomba.isAlive) {
						if (killCombo == 0)
							killCombo = 1;
						else
							killCombo *= 2;
						isJumping = false;
						requestStopJump = false;
						goomba.isAlive = false;
						jump();
						
						int newScore = 100 * killCombo;
						if (newScore < 6400) {
							game.score += newScore;
							textToDraw.add(newScore + ":" + (x - 50 - gc.getOffsetX()) + ":" + (y - gc.getOffsetY()));
						} else {
							lives++;
							game.score += 1000;
							textToDraw.add("1UP:" + (x - 50 - gc.getOffsetX()) + ":" + (y - gc.getOffsetY()));
							game.audio.life1up = new Sound("/audio/sfx/1-up.wav");
							game.audio.life1up.play();
						}
					}
				}
			}
			
			if (collides(entity.x, entity.y+24, entity.w, entity.h-24)) {
				// Enemies
				if (entity instanceof Goomba) {
					Goomba goomba = (Goomba)entity;
					
					if (!goomba.isTouching && goomba.isAlive) {
						goomba.isTouching = true;
						if (!isBig)
							isDying = true;
						else {
							Sprite
							standing = new Sprite("/characters/mario/small/standing.png", 0, 0, 36, 48),
							walk1 = new Sprite("/characters/mario/small/walk1.png", 0, 0, 45, 48),
							walk2 = new Sprite("/characters/mario/small/walk2.png", 0, 0, 45, 48),
							walk3 = new Sprite("/characters/mario/small/walk3.png", 0, 0, 45, 48),
							jump = new Sprite("/characters/mario/small/jump.png", 0, 0, 48, 48),
							die = new Sprite("/characters/mario/small/die.png", 0, 0, 42, 48),
							pole = new Sprite("/characters/mario/small/pole.png", 0, 0, 36, 48),
							duck = new Sprite("/characters/mario/small/standing.png", 0, 0, 36, 48);
							
							if (!isFire) {
								isBig = false;
							} else {
								isFire = false;
								standing = new Sprite("/characters/mario/big/standing.png", 0, 0, 48, 96);
								walk1 = new Sprite("/characters/mario/big/walk1.png", 0, 0, 48, 96);
								walk2 = new Sprite("/characters/mario/big/walk2.png", 0, 0, 48, 96);
								walk3 = new Sprite("/characters/mario/big/walk3.png", 0, 0, 48, 96);
								jump = new Sprite("/characters/mario/big/jump.png", 0, 0, 48, 93);
								die = new Sprite("/characters/mario/big/die.png", 0, 0, 42, 48);
								pole = new Sprite("/characters/mario/big/pole.png", 0, 0, 42, 81);
								duck = new Sprite("/characters/mario/big/duck.png", 0, 0, 48, 66);
							}
							
							
							sprites.set(0, standing); // 0
							sprites.set(1, walk1); // 1
							sprites.set(2, walk2); // 2
							sprites.set(3, walk3); // 3
							sprites.set(4, jump); // 4
							sprites.set(5, die); // 5
							sprites.set(6, pole); // 6
							sprites.set(7, duck); // 7
							
							game.audio.powerdown = new Sound("/audio/sfx/powerdown.wav");
							game.audio.powerdown.play();
						}
					}
				}
			}
		}
	}
	
	private void spawnGoomba(int goombaID, int x, int y) {
		if (!goombas.contains(goombaID)) {
			goombas.add(goombaID);
			Goomba goomba = new Goomba(game, level, x, y);
			goomba.id = goombaID;
			game.entities.add(goomba);
		}
	}

	private void pickedUpMushroom() {
		if (!isBig) {
			isBig = true;
			
			Sprite
			standing = new Sprite("/characters/mario/big/standing.png", 0, 0, 48, 96),
			walk1 = new Sprite("/characters/mario/big/walk1.png", 0, 0, 48, 96),
			walk2 = new Sprite("/characters/mario/big/walk2.png", 0, 0, 48, 96),
			walk3 = new Sprite("/characters/mario/big/walk3.png", 0, 0, 48, 96),
			jump = new Sprite("/characters/mario/big/jump.png", 0, 0, 48, 93),
			die = new Sprite("/characters/mario/big/die.png", 0, 0, 42, 48),
			pole = new Sprite("/characters/mario/big/pole.png", 0, 0, 42, 81),
			duck = new Sprite("/characters/mario/big/duck.png", 0, 0, 48, 66);
			
			sprites.set(0, standing); // 0
			sprites.set(1, walk1); // 1
			sprites.set(2, walk2); // 2
			sprites.set(3, walk3); // 3
			sprites.set(4, jump); // 4
			sprites.set(5, die); // 5
			sprites.set(6, pole); // 6
			sprites.set(7, duck); // 7
			
			y -= 48;
			
			game.audio.powerup = new Sound("/audio/sfx/powerup.wav");
			game.audio.powerup.play();
		}
	}
	
	private void pickedUpFireFlower() {
		if (!isFire) {
			isFire = true;
			
			Sprite
			standing = new Sprite("/characters/mario/fire/standing.png", 0, 0, 48, 96),
			walk1 = new Sprite("/characters/mario/fire/walk1.png", 0, 0, 48, 96),
			walk2 = new Sprite("/characters/mario/fire/walk2.png", 0, 0, 48, 96),
			walk3 = new Sprite("/characters/mario/fire/walk3.png", 0, 0, 48, 96),
			jump = new Sprite("/characters/mario/fire/jump.png", 0, 0, 48, 93),
			die = new Sprite("/characters/mario/fire/die.png", 0, 0, 42, 48),
			pole = new Sprite("/characters/mario/fire/pole.png", 0, 0, 42, 81),
			duck = new Sprite("/characters/mario/fire/duck.png", 0, 0, 48, 66);
			
			sprites.set(0, standing); // 0
			sprites.set(1, walk1); // 1
			sprites.set(2, walk2); // 2
			sprites.set(3, walk3); // 3
			sprites.set(4, jump); // 4
			sprites.set(5, die); // 5
			sprites.set(6, pole); // 6
			sprites.set(7, duck); // 7
			
			y -= 48;
		}
		game.audio.powerup = new Sound("/audio/sfx/powerup.wav");
		game.audio.powerup.play();
	}
	
	public void reset() {
		x = 0;
		y = 530;
		isDying = false;
		isDead = false;
		lives -= 1;
		gc.setOffsetX(0);
		gc.setOffsetY(0);
		game.score = 0;
		game.coins = 0;
		
		game.audio.die.stop();
		
		game.audio.ground = new Sound("/audio/music/ground.wav");
		game.audio.ground.loop(10);
		
		currSprite = 0;
		isBig = false;
		isFire = false;
		
		Sprite
		standing = new Sprite("/characters/mario/small/standing.png", 0, 0, 36, 48),
		walk1 = new Sprite("/characters/mario/small/walk1.png", 0, 0, 45, 48),
		walk2 = new Sprite("/characters/mario/small/walk2.png", 0, 0, 45, 48),
		walk3 = new Sprite("/characters/mario/small/walk3.png", 0, 0, 45, 48),
		jump = new Sprite("/characters/mario/small/jump.png", 0, 0, 48, 48),
		die = new Sprite("/characters/mario/small/die.png", 0, 0, 42, 48),
		pole = new Sprite("/characters/mario/small/pole.png", 0, 0, 36, 48),
		duck = new Sprite("/characters/mario/small/standing.png", 0, 0, 36, 48);
		
		sprites.set(0, standing); // 0
		sprites.set(1, walk1); // 1
		sprites.set(2, walk2); // 2
		sprites.set(3, walk3); // 3
		sprites.set(4, jump); // 4
		sprites.set(5, die); // 5
		sprites.set(6, pole); // 6
		sprites.set(7, duck); // 7
		
		goombas.clear();
		
		game.entities.clear();
		
		for (int i = 0; i < tilesThatCouldChange.size(); i++) {
			Tile tile = tilesThatCouldChange.get(i);
			
			if (tile.getSprite().toLowerCase().contains(new Textures().INVISIBLE.toLowerCase())) {
				tile.setSprite(new Textures().BLOCK1);
			}
			
			if (tile.getSprite().toLowerCase().contains(new Textures().MYSTERYUSED.toLowerCase())) {
				if (tile.getX() == 3024 && tile.getY() == 336) {
					tile.setSprite(new Textures().MYSTERYINVISIBLE);
				} else {
					tile.setSprite(new Textures().MYSTERY);
				}
			}
			tile.setSolid(true);
		}
		
		if (lives <= 0) {
			game.gameOver = true;
		}
	}
	
	private void die(GameCore gc) {
		if (deadTargetY == 0) {
			deadTargetY = y - 200;
			requestStopJump = false;
		}

		
		if (y > deadTargetY && !requestStopJump) {
			y -= jumpSpeed/2;
		} else {
			requestStopJump = true;
		}
		
		if (requestStopJump && y > gc.getHeight())
			isDead = true;
		else if (requestStopJump && y < gc.getHeight() + jumpSpeed)
			y += gravity;
	}
	
	private void hopTiles() {
		for (int i = 0; i < tilesToHop.size(); i++) {
			Tile tileToHop = tilesToHop.get(i);
			
			if (tileToHop.getSprite().toLowerCase().contains("mystery")) {
				if (!tileToHop.getSprite().toLowerCase().contains("used")) {
					//1008, 384 = Mushroom
					//3696, 384 = Mushroom
					//5184, 192 = Mushroom
					//3024, 336 = 1 UP
					//4800, 384 = Star
					boolean mushroomPos = (tileToHop.getX() == 1008 && tileOriginalY.get(i) == 384) || (tileToHop.getX() == 3696 && tileOriginalY.get(i) == 384) || (tileToHop.getX() == 5184 && tileOriginalY.get(i) == 192);
					boolean UP1Pos = tileToHop.getX() == 3024 && tileOriginalY.get(i) == 336;
					boolean starPos = tileToHop.getX() == 4800 && tileOriginalY.get(i) == 384;
					
					if (mushroomPos)
						if (!isBig) {
							Mushroom mushroom = new Mushroom(game, level, tileToHop.getX(), tileToHop.getY());
							game.entities.add(mushroom);
						} else {
							FireFlower fireFlower = new FireFlower(game, tileToHop.getX(), tileToHop.getY());
							game.entities.add(fireFlower);
						}
					
					if (UP1Pos) {
						UP1Shroom up1shroom = new UP1Shroom(game, level, tileToHop.getX(), tileToHop.getY());
						game.entities.add(up1shroom);
					}
					
					if (!mushroomPos && !UP1Pos && !starPos) {
						Coin coin = new Coin(game, tileToHop.getX(), tileToHop.getY());
						game.entities.add(coin);
						game.coins++;
						
						game.score += 200;
						textToDraw.add("200:" + (coin.x - 50 - gc.getOffsetX()) + ":" + (coin.y - 30 - gc.getOffsetY()));
					}
					
					tileToHop.setSprite(new Textures().MYSTERYUSED);
				}
			}

			if (!tileToHop.isSolid()) {
				if (tileToHop.getY() == tileOriginalY.get(i))
					tilesToHop.remove(i);
			} else {
				if (!tileGoBack.get(i)) {
					if (tileToHop.getY() <= tileTargetY.get(i))
						tileGoBack.set(i, true);
					else
						tileToHop.setY(tileToHop.getY() - tileHopMoveSpeed);
				} else {
					if (tileToHop.getY() >= tileOriginalY.get(i)) {
						tileToHop.setY(tileOriginalY.get(i));
						tilesToHop.remove(i);
						tileTargetY.remove(i);
						tileOriginalY.remove(i);
						tileGoBack.remove(i);
					} else {
						tileToHop.setY(tileToHop.getY() + tileHopMoveSpeed);
					}
				}
				
				if (isBig) {
					if (tileToHop.getSprite().toLowerCase().contains("block1")) {
						tileToHop.setSprite(new Textures().INVISIBLE);
						tileToHop.setSolid(false);
						game.audio.blockBreak = new Sound("/audio/sfx/breakBlock.wav");
						game.audio.blockBreak.play();
						
						int lifeTime = 30;
						String particle1 = new Textures().BLOCK1BREAK + ":" + (tileToHop.getX()) + ":" + (tileToHop.getY()) + ":" + 0 + ":" + 0 + ":" + lifeTime + ":" + 24 + ":" + 24;
						String particle2 = new Textures().BLOCK1BREAK + ":" + (tileToHop.getX() + tileToHop.getWidth() / 2) + ":" + (tileToHop.getY()) + ":" + 1 + ":" + 0 + ":" + lifeTime + ":" + 24 + ":" + 24;
						String particle3 = new Textures().BLOCK1BREAK + ":" + (tileToHop.getX()) + ":" + (tileToHop.getY() + tileToHop.getHeight() / 2) + ":" + 0 + ":" + 0 + ":" + lifeTime + ":" + 24 + ":" + 24;
						String particle4 = new Textures().BLOCK1BREAK + ":" + (tileToHop.getX() + tileToHop.getWidth() / 2) + ":" + (tileToHop.getY() + tileToHop.getHeight() / 2) + ":" + 1 + ":" + 0 + ":" + lifeTime + ":" + 24 + ":" + 24;
						
						game.particles.add(particle1);
						game.particles.add(particle2);
						game.particles.add(particle3);
						game.particles.add(particle4);
					}
				}
			}
		}
	}

	private void moveX(int dir) {
		if (dir < 0) { // Left
			if (canMoveLeft) {
				x -= moveSpeed;
			}
		}
		
		if (dir > 0) { // Right
			if (canMoveRight) {
				x += moveSpeed;
			}
		}
		
		if (System.currentTimeMillis() - oldMillis > 65) {
			oldMillis = System.currentTimeMillis();
			if (currSprite < 3)
				currSprite ++;
			else
				currSprite = 1;
		}
	}
	
	private void jump() {
		isJumping = true;
		targetY = y - jumpHeight;
	}
	
	private void checkDirections(GameCore gc) {
		try{
			if (level != null) {
				
				if (!level.getTile(x/48, (y-1)/48).isSolid() && !level.getTile((x+w)/48, (y-1)/48).isSolid())
					canMoveUp = true;
				else
					canMoveUp = false;
				
				int marge = 1;
				if (isBig)
					marge = 8;
				else
					marge = 1;
				
				if (!level.getTile(x/48, (y+h+marge)/48).isSolid() && !level.getTile((x+w)/48, (y+h+marge)/48).isSolid())
					canMoveDown = true;
				else
					canMoveDown = false;
				
				if (x > gc.getOffsetX())
					if (!level.getTile((x-1)/48, y/48).isSolid() && !level.getTile((x-1)/48, (y+h-8)/48).isSolid())
						canMoveLeft = true;
					else
						canMoveLeft = false;
				else
					canMoveLeft = false;
				
				if (!level.getTile((x+w+1)/48, y/48).isSolid() && !level.getTile((x+w+1)/48, (y+h-8)/48).isSolid())
					canMoveRight = true;
				else
					canMoveRight = false;
			}
		} catch (Exception e) {}
	}
	
	private void updateScreenOffset(GameCore gc) {
		int halfScreen = gc.getWidth() / 2;
		int halfPlayer = x + (w / 2) - gc.getOffsetX();
		
		if (halfPlayer > halfScreen)
			if (gc.getOffsetX() < (game.map.getLevel().getWidth() * game.map.getLevel().getTileSize()) - gc.getWidth())
				gc.setOffsetX(gc.getOffsetX() + (halfPlayer - halfScreen));
	}
	
	public void render(GameCore gc, Render r) {
		if (sprites.size() > 0) {
			Sprite sprite = sprites.get(currSprite);
			this.w = sprite.w;
			this.h = sprite.h;
			
			BufferedImage image = r.getImageFromArray(sprite.pixels, sprite.w, sprite.h);
			if (lastDirection == 1)
				image = r.flip(r.getImageFromArray(sprite.pixels, sprite.w, sprite.h), true, false);
			
			pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
			
			for (int x = 0; x < w; x++)
				for (int y = 0; y < h; y++)
					r.setPixel(this.x + x, this.y + y, pixels[x + y * w]);
			
			drawText(r);
		}
	}
	
	private void drawText(Render r) {
		for (int i = 0; i < textToDraw.size(); i++) {
			String text = textToDraw.get(i);
			String[] split = text.split(":");
			if (split.length == 3) {
				String newString = String.join(":", split) + ":0";
				textToDraw.set(i, newString);
			} else {
				String t = split[0];
				int x = Integer.parseInt(split[1]);
				int y = Integer.parseInt(split[2]);
				int c = Integer.parseInt(split[3]);
				
				r.drawString(t, 0xFFFFFFFF, x, y - c, new Textures().SMB);
				
				c += 1;
				String newString = t + ":" + x + ":" + y + ":" + c;
				if (c < 60)
					textToDraw.set(i, newString);
				else
					textToDraw.remove(i);
			}
		}
	}
}
