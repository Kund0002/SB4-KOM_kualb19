package dk.sdu.mmmi.cbse.collisiondetection;

import dk.sdu.mmmi.cbse.asteroidssystem.Asteroids;
import dk.sdu.mmmi.cbse.bulletsystem.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.enemysystem.Enemy;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.asteroidssystem.AsteroidsPlugin;

import java.util.List;

public class CollisionDetection implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        // Collisions between ships and asteroids
        List<Entity> enemys = world.getEntities(Enemy.class);
        List<Entity> asteroids = world.getEntities(Asteroids.class);
        List<Entity> bullets = world.getEntities(Bullet.class);
        for (Entity enemy : enemys) {
            for (Entity asteroid : asteroids) {
                if (collides(enemy, asteroid)) {
                    hit(enemy);
                    for (Entity bullet : bullets) {
                        if (collides(bullet, asteroid)) {
                            hit(bullet);
                            hit(asteroid);
                        }
                    }
                }
            }
        }

        // Player vs. enemy bullets
        List<Entity> playerShips = world.getEntities(Player.class);
        List<Entity> enemyBullets = world.getEntities(Bullet.class);
        for (Entity playerShip : playerShips) {
            for (Entity bullet : enemyBullets) {
                if (collides(playerShip, bullet)) {
                    hit(playerShip);
                    world.removeEntity(bullet);
                }
            }
        }

        List<Entity> players = world.getEntities(Player.class);
        for (Entity player : players) {
            for (Entity asteroid : asteroids) {
                if (collides(player, asteroid)) {
                    hit(player);
                }
            }
        }


    }

    private boolean collides(Entity a, Entity b) {
        PositionPart EntA = a.getPart(PositionPart.class);
        PositionPart EntB = b.getPart(PositionPart.class);
        float distance = (float) Math.sqrt(Math.pow(EntA.getX() - EntB.getX(), 2) + Math.pow(EntA.getY() - EntB.getY(), 2));
        return distance <= (EntA.getRadians() + EntB.getRadians());
    }

    private void split(Asteroids asteroids, AsteroidsPlugin asteroidsPlugin, GameData gameData, World world) {
        // Implement this to create two smaller asteroids from a larger one
        asteroidsPlugin.createSplitAsteroid(asteroids, gameData, world);
    }

    private void hit(Entity entity) {
        LifePart lifePart = entity.getPart(LifePart.class);
        lifePart.setIsHit(true);
    }
}

