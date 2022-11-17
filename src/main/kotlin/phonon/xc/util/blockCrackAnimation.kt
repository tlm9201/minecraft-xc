/**
 * Packet handler for block cracking.
 */

package phonon.xc.util.blockCrackAnimation


import java.util.concurrent.ThreadLocalRandom
import org.bukkit.World
import org.bukkit.Location
import phonon.xc.nms.blockcrack.broadcastBlockCrackAnimation

/**
 * Block location to create block cracking animation,
 * for where bullets hit.
 */
public data class BlockCrackAnimation(val world: World, val x: Int, val y: Int, val z: Int)

/**
 * Send packet for block breaking at location
 */
public class TaskBroadcastBlockCrackAnimations(
    val animations: ArrayList<BlockCrackAnimation>,
): Runnable {
    override fun run() {
        val random = ThreadLocalRandom.current()

        for ( block in animations ) {
            // generate random entity id for entity breaking block
            val entityId = random.nextInt(Integer.MAX_VALUE)
            val breakStage = random.nextInt(4) + 1
            // val packet = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION, false)
            // packet.getBlockPositionModifier().write(0, BlockPosition(
            //     block.x,
            //     block.y,
            //     block.z,
            // ))
            // packet.getIntegers().write(0, entityId)
            // packet.getIntegers().write(1, breakStage)

            // TODO: optimize to only broadcast to players in range...right now doing whole server!
            block.world.broadcastBlockCrackAnimation(entityId, block.x, block.y, block.z, breakStage)
            
            // OLD
            // val loc = Location(block.world, block.x.toDouble(), block.y.toDouble(), block.z.toDouble())
            // protocolManager.broadcastServerPacket(packet, loc, 64)
        }
    }
}