package com.kanasuki.game.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.kanasuki.game.test.actor.DeadEnemy;
import com.kanasuki.game.test.actor.Enemy;
import com.kanasuki.game.test.actor.Environment;
import com.kanasuki.game.test.actor.Hero;
import com.kanasuki.game.test.actor.Square;
import com.kanasuki.game.test.actor.Wall;
import com.kanasuki.game.test.input.PlayerInput;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.texture.TextureManager;
import com.kanasuki.game.test.utils.WinningConditionChecker;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class GameManager {

    private static int DEFAULT_SQUARE_SIZE = 50;

    private Environment environment;
    private Group gameObjects;

    private Hero hero;
    private final Collection<Wall> walls;
    private final Collection<Enemy> enemies;
    private final Collection<DeadEnemy> deadEnemies;

    private final TextureManager textureManager;

    private final LevelConfiguration levelConfiguration;

    private Set<PlayerInput> movingCommands;

    private LinkedList<PlayerInput> actionCommands;

    private WinningConditionChecker winningConditionChecker;

    private TestGame testGame;

    private boolean gameLoosed;
    private boolean gameWon;

    private int previousDeltaX = 0;
    private int previousDeltaY = 0;

    private float timeWithoutInputProcessing = 0;
    private float timeWithoutAiTurn = 0;

    private int squareSize;

    private int score;

    public GameManager(TextureManager textureManager, LevelConfiguration levelConfiguration, TestGame testGame) {
        this.textureManager = textureManager;
        this.levelConfiguration = levelConfiguration;
        this.walls = new HashSet<>();
        this.enemies = new HashSet<>();
        this.deadEnemies = new HashSet<>();
        this.movingCommands = new HashSet<>();
        this.actionCommands = new LinkedList<>();
        this.testGame = testGame;
        this.gameLoosed = false;
        this.gameWon = false;
        this.score = 0;
    }

    public void makeTurn(float delta) {
        timeWithoutInputProcessing += delta;
        timeWithoutAiTurn += delta;

        if (!gameLoosed) {
            if (timeWithoutInputProcessing > 0.09f) {
                processInput();
                timeWithoutInputProcessing = 0f;

                if (checkLoosed()) {
                    this.gameLoosed = true;
                    return;
                }
            }

            if (timeWithoutAiTurn > 0.4f) {
                makeAiTurn();
                timeWithoutAiTurn = 0;

                if (checkLoosed()) {
                    this.gameLoosed = true;
                    return;
                }
            }
        }
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Collection<Enemy> getEnemies() {
        return enemies;
    }

    public Hero getHero() {
        return hero;
    }

    public LevelConfiguration getLevelConfiguration() {
        return levelConfiguration;
    }

    public boolean isGameLoosed() {
        return gameLoosed;
    }

    public Group createGameGroup() {
        this.squareSize = Math.min(Math.min(Gdx.graphics.getWidth()/levelConfiguration.getWidth(),
                                   Gdx.graphics.getHeight()/levelConfiguration.getHeight()), DEFAULT_SQUARE_SIZE);

        this.environment = new Environment(levelConfiguration.getWidth(), levelConfiguration.getHeight(), squareSize);
        environment.setZIndex(0);

        Texture squareTexture = textureManager.getTexture("square");
        for (int i = 0; i < environment.getSizeX(); i++) {
            for (int j = 0; j < environment.getSizeY(); j++) {
                Square square = new Square(squareTexture, i, j, environment.getSquareSize());
                environment.addActor(square);
            }
        }

        this.winningConditionChecker = new WinningConditionChecker(this);

        this.hero = new Hero(textureManager.getTexture("hero"), 4, 4, environment.getSquareSize());
        addEnemies(textureManager.getTexture("enemy"), levelConfiguration.getEnemyNumber());

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

        if (canMoveThere(fieldX, fieldY)) {
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

    public boolean checkLoosed() {
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

    public boolean isInDeadEnemy(int x, int y) {
        for (DeadEnemy deadEnemy: deadEnemies) {
            if (deadEnemy.isInField(x, y)) {
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

            if (square <= levelConfiguration.getMaxEnemySquare()) {
                iterator.remove();
                gameObjects.removeActor(enemy);
                testGame.getGameStatisticGui().increaseDeadEnemyCount();
                this.score += levelConfiguration.getMaxEnemySquare() - square + 1;
                testGame.getGameStatisticGui().increaseScore(score);
                DeadEnemy deadEnemy = new DeadEnemy(textureManager.getTexture("dead-enemy"),
                        fieldX, fieldY, environment.getSquareSize());

                deadEnemies.add(deadEnemy);
                gameObjects.addActor(deadEnemy);
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
                if (canMoveThere(fieldX, fieldY + 1) && !isInEnemy(fieldX, fieldY + 1)) {
                    MoveByAction moveByAction = new MoveByAction();
                    moveByAction.setAmount(0, squareSize);
                    //moveByAction.setDuration(0.2f);
                    actor.addAction(moveByAction);
                }
                break;
            case 1:
                if (canMoveThere(fieldX, fieldY - 1) && !isInEnemy(fieldX, fieldY - 1)) {
                    MoveByAction moveByAction = new MoveByAction();
                    moveByAction.setAmount(0, -squareSize);
                    //moveByAction.setDuration(0.2f);
                    actor.addAction(moveByAction);
                }
                break;
            case 2:
                if (canMoveThere(fieldX + 1, fieldY) && !isInEnemy(fieldX + 1, fieldY)) {
                    MoveByAction moveByAction = new MoveByAction();
                    moveByAction.setAmount(squareSize, 0);
                    //moveByAction.setDuration(0.2f);
                    actor.addAction(moveByAction);
                }
                break;
            case 3:
                if (canMoveThere(fieldX - 1, fieldY) && !isInEnemy(fieldX - 1, fieldY)) {
                    MoveByAction moveByAction = new MoveByAction();
                    moveByAction.setAmount(-squareSize, 0);
                    //moveByAction.setDuration(0.2f);
                    actor.addAction(moveByAction);
                }
                break;
        }
    }

    private boolean canMoveThere(int x, int y) {
        if (!environment.isInEnvironment(x, y)) {
            return false;
        }

        if (isInWall(x, y)) {
            return false;
        }

        if (isInDeadEnemy(x, y)) {
            return false;
        }

        return true;
    }

    private boolean isAllowedCreateObjectThere(int x, int y) {
        if (!environment.isInEnvironment(x, y)) {
            return false;
        }

        if (isInWall(x, y)) {
            return false;
        }

        if (isInEnemy(x, y)) {
            return false;
        }

        if (hero.isInField(x, y)) {
            return false;
        }

        if (isInDeadEnemy(x, y)) {
            return false;
        }

        return true;
    }

    private void createWall(int relativeX, int relativeY) {
        int squareSize = environment.getSquareSize();

        int fieldX = (int) (hero.getX() / squareSize) + relativeX;
        int fieldY = (int) (hero.getY() / squareSize) + relativeY;

        if (isAllowedCreateObjectThere(fieldX, fieldY)) {
            Wall wall = new Wall(textureManager.getTexture("wall"), fieldX, fieldY, squareSize);
            walls.add(wall);
            gameObjects.addActor(wall);

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
                return;
            }
        }
    }

    private void addEnemies(Texture texture, int number) {
        int iterations = 0;
        int enemyCount = 0;
        while (enemyCount < number && iterations < number * 10) {
            iterations++;
            int x = MathUtils.random(environment.getSizeX());
            int y = MathUtils.random(environment.getSizeY());
            if (isAllowedCreateObjectThere(x, y)) {
                enemies.add(new Enemy(texture, x, y, environment.getSquareSize()));
                enemyCount++;
            }
        }
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameEnded() {
        return gameWon || gameLoosed;
    }

    public int getScore() {
        return score;
    }
}