package com.kanasuki.game.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.kanasuki.game.test.actor.*;
import com.kanasuki.game.test.gui.GameStatisticGui;
import com.kanasuki.game.test.input.PlayerInput;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.management.action.ActionManager;
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
    private final ActionManager actionManager;

    private Hero hero;

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
                       GameActorManager gameActorManager, WinningConditionChecker winningConditionChecker, ActionManager actionManager) {
        this.textureManager = textureManager;
        this.animationManager = animationManager;
        this.levelConfiguration = levelConfiguration;
        this.gameStatisticGui = gameStatisticGui;
        this.winningConditionChecker = winningConditionChecker;
        this.environment = environment;
        this.actionManager = actionManager;
        this.movingCommands = new HashSet<>();
        this.actionCommands = new LinkedList<>();
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

            actionManager.act(delta);

            if (timeWithoutAiTurn > 0.4f) {
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

        gameActorManager.getGameGroup().addActor(environment);

        this.hero = new Hero(animationManager.getAnimation(AnimationProfile.HERO), 4, 4, environment.getSquareSize());
        addEnemies(animationManager.getAnimation(AnimationProfile.ENEMY), levelConfiguration.getEnemyNumber());

        gameActorManager.addGameActor(hero);

        return gameActorManager.getGameGroup();
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

        if (gameActorField.isFreeToMove(fieldX, fieldY, hero.getType())) {
            MoveByAction action = new MoveByAction();
            action.setAmount(totalX * squareSize, totalY * squareSize);

            hero.addAction(action);
        }
    }

    public boolean checkLost() {
        int squareSize = environment.getSquareSize();

        int fieldX = (int) (hero.getX() / squareSize);
        int fieldY = (int) (hero.getY() / squareSize);

        return gameActorManager.isActorType(fieldX, fieldY, ActorType.ENEMY);
    }

    private boolean checkWon() {
        Collection<GameActor> onDelete = new HashSet<>();

        for (GameActor gameActor: gameActorManager.getGameActors()) {
            if (gameActor.getType() != ActorType.ENEMY) {
                continue;
            }

            int squareSize = environment.getSquareSize();

            int fieldX = (int) (gameActor.getX() / squareSize);
            int fieldY = (int) (gameActor.getY() / squareSize);

            int square = winningConditionChecker.checkEnemyConfinedSquare(fieldX, fieldY);
            Gdx.app.log("debug", "winning condition: square = " + square);

            if (square <= levelConfiguration.getMaxEnemySquare()) {
                onDelete.add(gameActor);
                gameStatisticGui.increaseDeadEnemyCount();
                this.score += levelConfiguration.getMaxEnemySquare() - square + 1;
                gameStatisticGui.increaseScore(score);
            }
        }
        Gdx.app.log("debug", "to delete " + onDelete.size());

        for (GameActor gameActor: onDelete) {
            gameActorManager.removeGameActor(gameActor);

            int squareSize = environment.getSquareSize();

            int fieldX = (int) (gameActor.getX() / squareSize);
            int fieldY = (int) (gameActor.getY() / squareSize);

            DyingEnemy dyingEnemy = new DyingEnemy(animationManager.getAnimation(AnimationProfile.DYING_ENEMY),
                    fieldX, fieldY, environment.getSquareSize());

            gameActorManager.addGameActor(dyingEnemy);
        }

        return gameActorManager.getEnemyAmount() == 0;
    }

    private void createWall(int relativeX, int relativeY) {
        int squareSize = environment.getSquareSize();

        int fieldX = (int) (hero.getX() / squareSize) + relativeX;
        int fieldY = (int) (hero.getY() / squareSize) + relativeY;

        if (gameActorField.isFreeToBuild(fieldX, fieldY)) {
            Wall wall = new Wall(textureManager.getTexture("wall"), fieldX, fieldY, squareSize);
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

        GameActor gameActor = gameActorManager.getGameActor(x, y);

        if (gameActor.getType() == ActorType.OBSTRUCTION_REMOVABLE) {
            //gameActorManager.addGameActor(new DestroyingWall(animationManager.getAnimation(AnimationProfile.WALL_DESTRUCTION), x, y, squareSize));
            gameActorManager.removeGameActor(gameActor);
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
                Enemy enemy = new Enemy(animation, x, y, environment.getSquareSize());
                gameActorManager.addGameActor(enemy);
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