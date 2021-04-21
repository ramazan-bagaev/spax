package com.kanasuki.game.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.kanasuki.game.test.actor.DeadEnemy;
import com.kanasuki.game.test.actor.Enemy;
import com.kanasuki.game.test.actor.Environment;
import com.kanasuki.game.test.actor.GameActor;
import com.kanasuki.game.test.actor.GameActorField;
import com.kanasuki.game.test.actor.GameActorManager;
import com.kanasuki.game.test.actor.Hero;
import com.kanasuki.game.test.actor.Square;
import com.kanasuki.game.test.actor.Wall;
import com.kanasuki.game.test.gui.GameStatisticGui;
import com.kanasuki.game.test.input.PlayerInput;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.texture.AnimationManager;
import com.kanasuki.game.test.texture.AnimationProfile;
import com.kanasuki.game.test.texture.TextureManager;
import com.kanasuki.game.test.utils.WinningConditionChecker;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

@Singleton
public class GameManager {

    private Environment environment;
    private Group gameObjects;

    private Hero hero;
    private final Collection<Wall> walls;
    private final Collection<Enemy> enemies;
    private final Collection<DeadEnemy> deadEnemies;

    private final Collection<GameActor> allObjects;
    private final Collection<GameActor> obstructions;

    private final TextureManager textureManager;

    private final AnimationManager animationManager;
    private final LevelConfiguration levelConfiguration;

    private Set<PlayerInput> movingCommands;

    private LinkedList<PlayerInput> actionCommands;

    private WinningConditionChecker winningConditionChecker;

    private boolean gameLost;
    private boolean gameWon;

    private int previousDeltaX = 0;
    private int previousDeltaY = 0;

    private float timeWithoutInputProcessing = 0;
    private float timeWithoutAiTurn = 0;

    private int score;

    private final GameActorField gameActorField;
    private final GameActorManager gameActorManager;

    private final GameStatisticGui gameStatisticGui;

    @Inject
    public GameManager(TextureManager textureManager, AnimationManager animationManager,
                       LevelConfiguration levelConfiguration, GameStatisticGui gameStatisticGui, Environment environment,
                       GameActorManager gameActorManager, WinningConditionChecker winningConditionChecker) {
        this.textureManager = textureManager;
        this.animationManager = animationManager;
        this.levelConfiguration = levelConfiguration;
        this.gameStatisticGui = gameStatisticGui;
        this.winningConditionChecker = winningConditionChecker;
        this.environment = environment;
        this.walls = new HashSet<>();
        this.enemies = new HashSet<>();
        this.deadEnemies = new HashSet<>();
        this.movingCommands = new HashSet<>();
        this.actionCommands = new LinkedList<>();
        this.allObjects = new HashSet<>();
        this.obstructions = new HashSet<>();
        this.gameLost = false;
        this.gameWon = false;
        this.score = 0;
        this.gameActorManager = gameActorManager;
        this.gameActorField = new GameActorField(gameActorManager, environment);
    }

    public void makeTurn(float delta) {
        timeWithoutInputProcessing += delta;
        timeWithoutAiTurn += delta;

        if (!gameLost) {
            if (timeWithoutInputProcessing > 0.09f) {
                processInput();
                timeWithoutInputProcessing = 0f;

                if (checkLost()) {
                    this.gameLost = true;
                    return;
                }
            }

            if (timeWithoutAiTurn > 0.4f) {
                makeAiTurn();
                timeWithoutAiTurn = 0;

                if (checkLost()) {
                    this.gameLost = true;
                    return;
                }
            }
        }
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public Group createGameGroup() {
        Texture squareTexture = textureManager.getTexture("square");
        for (int i = 0; i < environment.getSizeX(); i++) {
            for (int j = 0; j < environment.getSizeY(); j++) {
                Square square = new Square(squareTexture, i, j, environment.getSquareSize());
                environment.addActor(square);
            }
        }

        this.hero = new Hero(animationManager.getAnimation(AnimationProfile.HERO), 4, 4, environment.getSquareSize());
        addEnemies(animationManager.getAnimation(AnimationProfile.ENEMY), levelConfiguration.getEnemyNumber());

        this.gameObjects = new Group();
        gameObjects.addActor(hero);

        for (Actor enemy: enemies) {
            gameObjects.addActor(enemy);
        }

        for (Actor wall: walls) {
            gameObjects.addActor(wall);
        }

        gameObjects.setZIndex(1);

        Group gameGroup = new Group();
        gameGroup.addActor(environment);
        gameGroup.addActor(gameObjects);

        allObjects.addAll(walls);
        allObjects.addAll(enemies);
        allObjects.add(hero);

        obstructions.addAll(walls);

        for (GameActor gameActor: allObjects) {
            gameActorManager.addGameActor(gameActor);
        }

        return gameGroup;
    }

    public void addMovingCommand(PlayerInput playerInput) {
        movingCommands.add(playerInput);
    }

    public void removeMovingCommand(PlayerInput playerInput) {
        movingCommands.remove(playerInput);
    }

    public void addActionCommand(PlayerInput playerInput) {
        actionCommands.push(playerInput);
    }

    public void processInput() {
        if (hero.getActions().size != 0) {
            return;
        }

        if (!actionCommands.isEmpty()) {

            for (Iterator<PlayerInput> iterator = actionCommands.iterator(); iterator.hasNext(); ) {
                PlayerInput playerInput = iterator.next();
                switch (playerInput) {
                    case ACTION_UP:
                        createWall(0, 1);
                        break;
                    case ACTION_DOWN:
                        createWall(0, -1);
                        break;
                    case ACTION_LEFT:
                        createWall(-1, 0);
                        break;
                    case ACTION_RIGHT:
                        createWall(1, 0);
                        break;
                    case ACTION:
                        removeWall();
                        break;
                }
                iterator.remove();
            }
        }

        if (movingCommands == null) {
            return;
        }

        int totalX = 0;
        int totalY = 0;

        for (PlayerInput playerInput: movingCommands) {
            switch (playerInput) {

                case UP:
                    totalY += 1;
                    break;
                case DOWN:
                    totalY -= 1;
                    break;
                case LEFT:
                    totalX -= 1;
                    break;
                case RIGHT:
                    totalX += 1;
                    break;
            }
        }

        if (totalX == 0 && totalY == 0) {
            return;
        }

        int squareSize = environment.getSquareSize();

        int fieldX = (int) (hero.getX() / squareSize) + totalX;
        int fieldY = (int) (hero.getY() / squareSize) + totalY;

        this.previousDeltaX = totalX;
        this.previousDeltaY = totalY;

        if (gameActorField.isFreeToMove(fieldX, fieldY)) {
            MoveByAction action = new MoveByAction();
            action.setAmount(totalX * squareSize, totalY * squareSize);

            hero.addAction(action);
        }
    }

    public void makeAiTurn() {
        for (Enemy enemy: enemies) {
            makeAiTurn(enemy);
        }
    }

    public boolean checkLost() {
        int squareSize = environment.getSquareSize();

        int fieldX = (int) (hero.getX() / squareSize);
        int fieldY = (int) (hero.getY() / squareSize);

        return isInEnemy(fieldX, fieldY);
    }

    public boolean isInEnemy(int x, int y) {
        for (Enemy enemy: enemies) {
            if (enemy.isInField(x, y)) {
                return true;
            }
        }

        return false;
    }

    public boolean isInWall(int x, int y) {
        for (Wall wall: walls) {
            if (wall.isInField(x, y)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkWon() {
        for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext(); ) {
            Enemy enemy = iterator.next();
            int squareSize = environment.getSquareSize();

            int fieldX = (int) (enemy.getX() / squareSize);
            int fieldY = (int) (enemy.getY() / squareSize);

            int square = winningConditionChecker.checkEnemyConfinedSquare(fieldX, fieldY);
            Gdx.app.log("debug", "winning condition: square = " + square);

            if (square <= levelConfiguration.getMaxEnemySquare()) {
                iterator.remove();
                gameObjects.removeActor(enemy);
                gameStatisticGui.increaseDeadEnemyCount();
                this.score += levelConfiguration.getMaxEnemySquare() - square + 1;
                gameStatisticGui.increaseScore(score);
                DeadEnemy deadEnemy = new DeadEnemy(textureManager.getTexture("dead-enemy"),
                        fieldX, fieldY, environment.getSquareSize());

                deadEnemies.add(deadEnemy);
                gameObjects.addActor(deadEnemy);
                allObjects.add(deadEnemy);
                obstructions.add(deadEnemy);
            }
        }

        return enemies.isEmpty();
    }

    private void makeAiTurn(Actor actor) {
        int squareSize = environment.getSquareSize();
        float x = actor.getX();
        float y = actor.getY();

        int fieldX = (int) (x / squareSize);
        int fieldY = (int) (y / squareSize);

        switch (MathUtils.random(4)) {
            case 0:
                if (isFree(fieldX, fieldY + 1) && !isInEnemy(fieldX, fieldY + 1)) {
                    MoveByAction moveByAction = new MoveByAction();
                    moveByAction.setAmount(0, squareSize);
                    //moveByAction.setDuration(0.2f);
                    actor.addAction(moveByAction);
                }
                break;
            case 1:
                if (isFree(fieldX, fieldY - 1) && !isInEnemy(fieldX, fieldY - 1)) {
                    MoveByAction moveByAction = new MoveByAction();
                    moveByAction.setAmount(0, -squareSize);
                    //moveByAction.setDuration(0.2f);
                    actor.addAction(moveByAction);
                }
                break;
            case 2:
                if (isFree(fieldX + 1, fieldY) && !isInEnemy(fieldX + 1, fieldY)) {
                    MoveByAction moveByAction = new MoveByAction();
                    moveByAction.setAmount(squareSize, 0);
                    //moveByAction.setDuration(0.2f);
                    actor.addAction(moveByAction);
                }
                break;
            case 3:
                if (isFree(fieldX - 1, fieldY) && !isInEnemy(fieldX - 1, fieldY)) {
                    MoveByAction moveByAction = new MoveByAction();
                    moveByAction.setAmount(-squareSize, 0);
                    //moveByAction.setDuration(0.2f);
                    actor.addAction(moveByAction);
                }
                break;
        }
    }

    private boolean isFree(int x, int y) {
        if (!environment.isInEnvironment(x, y)) {
            return false;
        }

        return gameActorField.isFreeToMove(x, y);

        /*for (Collidable collidable: obstructions) {
            if (collidable.isInField(x, y)) {
                return false;
            }
        }*/

        //return true;
    }

    private void createWall(int relativeX, int relativeY) {
        int squareSize = environment.getSquareSize();

        int fieldX = (int) (hero.getX() / squareSize) + relativeX;
        int fieldY = (int) (hero.getY() / squareSize) + relativeY;

        if (gameActorField.isFreeToBuild(fieldX, fieldY)) {
            Wall wall = new Wall(textureManager.getTexture("wall"), fieldX, fieldY, squareSize);
            walls.add(wall);
            gameObjects.addActor(wall);
            allObjects.add(wall);
            obstructions.add(wall);
            gameActorManager.addGameActor(wall);
            if (checkWon()) {
                this.gameWon = true;
                return;
            }
        }
    }

    private void removeWall() {
        int squareSize = environment.getSquareSize();

        int x = (int) (hero.getX() / squareSize) + previousDeltaX;
        int y = (int) (hero.getY() / squareSize) + previousDeltaY;

        for (Iterator<Wall> iterator = walls.iterator(); iterator.hasNext(); ) {
            Wall wall = iterator.next();
            int fieldX = (int) (wall.getX() / squareSize);
            int fieldY = (int) (wall.getY() / squareSize);

            if (fieldX == x && fieldY == y) {
                iterator.remove();
                gameObjects.removeActor(wall);
                allObjects.remove(wall);
                obstructions.remove(wall);
                gameActorManager.removeGameActor(wall);
                return;
            }
        }
    }

    private void addEnemies(Animation<TextureAtlas.AtlasRegion> animation, int number) {
        int iterations = 0;
        int enemyCount = 0;
        while (enemyCount < number && iterations < number * 10) {
            iterations++;
            int x = MathUtils.random(environment.getSizeX());
            int y = MathUtils.random(environment.getSizeY());
            if (gameActorField.isFreeToBuild(x, y)) {
                enemies.add(new Enemy(animation, x, y, environment.getSquareSize()));
                enemyCount++;
            }
        }
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameEnded() {
        return gameWon || gameLost;
    }

    public int getScore() {
        return score;
    }
}