package dk.sdu.mmmi.cbse.asteroidssystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonasteroids.Asteroids;

import java.util.Random;

public class AsteroidsPlugin implements IGamePluginService {
    private Entity asteroids;

    public AsteroidsPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        Random rand = new Random();
        int random = rand.nextInt(4);

        asteroids = createAsteroid(gameData,20);
        world.addEntity(asteroids);

        for (int i = 0; i < random; i++) {
            // Add entities to the world
            asteroids = createAsteroid(gameData,20);
            world.addEntity(asteroids);
        }

    }

    private Entity createAsteroid(GameData gameData, int radius) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = (float) ((Math.random() * (gameData.getDisplayWidth() - 0)) + 0);
        float y = (float) ((Math.random() * (gameData.getDisplayHeight() - 0)) + 0);
        float radians = 3.1415f / 2;

        Entity asteroid = new Asteroids();
        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.add(new LifePart(2,0));

        asteroid.setRadius(20);
        return asteroid;
    }


    public void createSplitAsteroid(Entity asteroidHit, GameData gameData, World world){
        PositionPart otherPos = asteroidHit.getPart(PositionPart.class);
        LifePart otherLife = asteroidHit.getPart(LifePart.class);

        float radians = otherPos.getRadians();
        int radius = 0;
        float speed = 5;
        int life = otherLife.getLife() - 1;
        if (life == 1) {
            radius = 10;
            speed = (float) Math.random() * 30f + 70f;
        } else if (life <= 0) {
            world.removeEntity(asteroidHit);
        }

        Entity asteroid1 = createAsteroid(gameData,radius);

        Entity asteroid2 = createAsteroid(gameData,radius);

        world.removeEntity(asteroidHit);
        world.addEntity(asteroid1);
        world.addEntity(asteroid2);

    }



    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity asteroid : world.getEntities(Asteroids.class)) {
            world.removeEntity(asteroid);
        }
    }
}
