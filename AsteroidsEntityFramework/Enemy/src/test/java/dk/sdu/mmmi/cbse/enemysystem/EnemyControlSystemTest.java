package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EnemyControlSystemTest {

    private EnemyControlSystem systemTest;
    private EnemyPlugin plugin;
    private GameData gameData;
    private World world;

    @Before
    public void setUp() {
        systemTest = new EnemyControlSystem();
        plugin = new EnemyPlugin();
        gameData = new GameData();
        world = new World();
    }

    @Test
    public void testEnemyLifeDecrementAndRemoval() {
        // Ensure no enemies to start with
        assertEquals(0, world.getEntities(Enemy.class).size());

        // Call the start method of the plugin
        plugin.start(gameData, world);

        List<Entity> enemys = world.getEntities(Enemy.class);
        for (Entity enemy : enemys) {
            LifePart lifePart = new LifePart(1, 1);  // Giving enemy 3 lives for the test
            enemy.add(lifePart);
            world.addEntity(enemy);

            systemTest.process(gameData, world);

            assertEquals(1, world.getEntities(Enemy.class).size());

            lifePart.setIsHit(true);




            assertTrue(world.getEntities(Enemy.class).contains(enemy));
        }

        // Setup an enemy entity


        // Process once


        // Expect the life to be reduced by 1 and not removed from the world


        // Process two more times


        // Expect the enemy to be removed after its life reaches 0

    }

    // You can add more tests like for movement, bullet creation, etc.
}
