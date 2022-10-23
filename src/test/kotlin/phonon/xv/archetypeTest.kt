/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package phonon.xv.test.core

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertEquals
import kotlin.test.fail

import java.util.EnumSet
import phonon.xv.component.*
import phonon.xv.core.*


public class ArchetypeTest {

    /**
     * Test iterating an archetype
     */
    @Test
    fun resize() {
        // manually insert archetypes
        val components = ComponentsStorage()
        components.archetypes.add(ArchetypeStorage(
            EnumSet.of(
                VehicleComponentType.LAND_MOVEMENT_CONTROLS,
                VehicleComponentType.TRANSFORM,
            ),
            16,
        ))
        components.archetypes.add(ArchetypeStorage(
            EnumSet.of(
                VehicleComponentType.FUEL,
                VehicleComponentType.LAND_MOVEMENT_CONTROLS,
                VehicleComponentType.TRANSFORM,
            ),
            16,
        ))
        components.archetypes.add(ArchetypeStorage(
            EnumSet.of(
                VehicleComponentType.FUEL,
                VehicleComponentType.TRANSFORM,
            ),
            16,
        ))

        // no health component
        val iterHealth = ComponentTuple1.query<HealthComponent>(components)
        assertEquals(false, iterHealth.hasNext())

        for ( (_, _, _) in ComponentTuple2.query<LandMovementControlsComponent, TransformComponent>(components) ) {
            fail("ComponentTuple Iterator should not have any elements")
        }

        // manually insert fuel components, then test iter across archetypes
        components.archetypes[1].fuel!!.add(FuelComponent(1.0, 1.0))
        components.archetypes[1].fuel!!.add(FuelComponent(2.0, 2.0))
        components.archetypes[2].fuel!!.add(FuelComponent(3.0, 3.0))

        val fuelExpected = listOf(1.0, 2.0, 3.0)

        for ( (i, v) in ComponentTuple1.query<FuelComponent>(components).withIndex() ) {
            val (_, fuel) = v
            assertEquals(fuelExpected[i], fuel.current)
        }
    }

}