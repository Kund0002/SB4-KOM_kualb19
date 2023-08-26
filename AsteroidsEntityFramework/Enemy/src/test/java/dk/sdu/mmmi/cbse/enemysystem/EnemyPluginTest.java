package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EnemyPluginTest {

    private EnemyPlugin pluginUnderTest;
    private GameData gameData;
    private World world;

    @Before
    public void setUp() {
        pluginUnderTest = new EnemyPlugin();
        gameData = new GameData();
        world = new World();
    }

    @Test
    public void testEnemyAddedToWorldOnStart() {
        // Ensure no enemies to start with
        assertEquals(0, world.getEntities(Enemy.class).size());

        // Call the start method
        pluginUnderTest.start(gameData, world);

        // Ensure one enemy is added to the world
        assertEquals(1, world.getEntities(Enemy.class).size());
    }

    @Test
    public void testEnemyRemovedFromWorldOnStop() {
        // Call the start method to add an enemy
        pluginUnderTest.start(gameData, world);

        // Ensure one enemy is added to the world
        assertEquals(1, world.getEntities(Enemy.class).size());

        // Call the stop method to remove the enemy
        pluginUnderTest.stop(gameData, world);

        // Ensure no enemies are left
        assertEquals(0, world.getEntities(Enemy.class).size());
    }

    // You can add more tests if needed
}

