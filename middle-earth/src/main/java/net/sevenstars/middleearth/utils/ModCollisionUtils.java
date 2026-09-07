package net.sevenstars.middleearth.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.Direction;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ModCollisionUtils {
    // Default ray length used for collision detection
    public static final double DEFAULT_RAY_LENGTH = 0.2;

    // Offset applied to the origin of the rays to ensure they are slightly outside the bounding box
    public static final double ORIGIN_OFFSET = 0.01;

    // Angle spread for rayCasting, which controls how much the rays deviate from the center of the face
    public static final double ANGLE_SPREAD = 0.1;

    /**
     * Check for block collisions from multiple directions (all 6 faces)
     * Generates rays from all faces of the bounding box to detect collisions.
     *
     * @param world         the world in which the collision occurs
     * @param box           the bounding box of the entity or projectile
     * @param velocity      the velocity of the entity/projectile
     * @param contextEntity the entity that is performing the collision check (used for collision context)
     * @param onCollision   a callback function to handle the hit result if a collision occurs
     * @return true if any collision is detected, false otherwise
     */
    public static boolean checkBlockFanCollision(
            Level world,
            AABB box,
            Vec3 velocity,
            Entity contextEntity,
            Consumer<HitResult> onCollision) {
        // If the velocity is zero but the entity might still be colliding with something, check its position
        if (velocity.lengthSqr() == 0 && !world.noCollision(contextEntity)) {
            return true; // Handle the collision, or do whatever logic is necessary for a stationary entity
        }

        // Generate rays for all 6 faces of the bounding box
        List<Pair<Vec3, Vec3>> rays = getFanRaysFromBoxFaces(box, velocity);

        // Check each ray for collisions
        for (Pair<Vec3, Vec3> ray : rays) {
            Vec3 origin = ray.getFirst();
            Vec3 dir = ray.getSecond();
            Vec3 end = origin.add(dir.scale(DEFAULT_RAY_LENGTH));

            // Perform the raycast to check for a collision
            HitResult hit = world.clip(new ClipContext(origin,
                    end,
                    ClipContext.Block.OUTLINE,
                    // We use OUTLINE shape for detecting blocks
                    ClipContext.Fluid.NONE,
                    // No fluid handling
                    contextEntity));

            // If a collision is detected, invoke the callback and return true
            if (hit.getType() != HitResult.Type.MISS) {
                onCollision.accept(hit);  // Call the onCollision callback with the hit result
                return true;
            }
        }

        return false;  // Return false if no collision is detected
    }

    /**
     * Generate rays from all six faces of the bounding box for collision detection.
     *
     * @param box      the bounding box of the entity/projectile
     * @param velocity the velocity of the entity/projectile
     * @return a list of rays to check for collisions
     */
    public static List<Pair<Vec3, Vec3>> getFanRaysFromBoxFaces(AABB box, Vec3 velocity) {
        List<Pair<Vec3, Vec3>> rays = new ArrayList<>();

        // Loop through all 6 directions (faces)
        for (Direction faceDir : Direction.values()) {
            rays.addAll(getFaceRays(box, faceDir, velocity));  // Generate rays for each face
        }

        return rays;
    }

    /**
     * Generate rays from a single face with an angle spreading.
     *
     * @param box      the bounding box of the entity/projectile
     * @param faceDir  the direction of the face to generate rays from
     * @param velocity the velocity of the entity/projectile
     * @return a list of rays originating from the specified face
     */
    private static List<Pair<Vec3, Vec3>> getFaceRays(
            AABB box,
            Direction faceDir,
            Vec3 velocity) {
        Vec3 normal = Vec3.atLowerCornerOf(faceDir.getUnitVec3i()).normalize();
        Vec3 center = box.getCenter();
        double minX = box.minX, minY = box.minY, minZ = box.minZ;
        double maxX = box.maxX, maxY = box.maxY, maxZ = box.maxZ;

        // Choose the corner points and center on the face
        List<Vec3> facePoints = getFacePoints(faceDir, minX, minY, minZ, maxX, maxY, maxZ, center);

        List<Pair<Vec3, Vec3>> rays = new ArrayList<>();

        // Generate rays by applying a slight outward offset and angle bias
        for (Vec3 point : facePoints) {
            Vec3 origin = point.add(normal.scale(ORIGIN_OFFSET));  // Apply outward offset
            Vec3 diagonalBias = point.subtract(center).normalize().scale(ANGLE_SPREAD);  // Apply angle bias
            Vec3 direction = velocity.normalize().add(diagonalBias).normalize();  // Final ray direction

            rays.add(Pair.of(origin, direction));  // Add the ray to the list
        }

        return rays;
    }

    /**
     * Generate points on the face of the box based on the direction of the face.
     * This method returns the corners and center of each face, based on the direction of the face.
     *
     * @param faceDir the direction of the face (X, Y, or Z axis)
     * @param minX    minimum X coordinate of the box
     * @param minY    minimum Y coordinate of the box
     * @param minZ    minimum Z coordinate of the box
     * @param maxX    maximum X coordinate of the box
     * @param maxY    maximum Y coordinate of the box
     * @param maxZ    maximum Z coordinate of the box
     * @param center  the center of the box
     * @return a list of points on the face of the box
     */
    private static List<Vec3> getFacePoints(
            Direction faceDir,
            double minX,
            double minY,
            double minZ,
            double maxX,
            double maxY,
            double maxZ,
            Vec3 center) {
        return switch (faceDir.getAxis()) {
            case X -> // X-axis (East-West direction)
                    List.of(new Vec3(faceDir == Direction.EAST ? maxX : minX, minY, minZ),
                            new Vec3(faceDir == Direction.EAST ? maxX : minX, minY, maxZ),
                            new Vec3(faceDir == Direction.EAST ? maxX : minX, maxY, minZ),
                            new Vec3(faceDir == Direction.EAST ? maxX : minX, maxY, maxZ),
                            new Vec3(faceDir == Direction.EAST ? maxX : minX, center.y, center.z));
            case Y -> // Y-axis (Up-Down direction)
                    List.of(new Vec3(minX, faceDir == Direction.UP ? maxY : minY, minZ),
                            new Vec3(maxX, faceDir == Direction.UP ? maxY : minY, minZ),
                            new Vec3(minX, faceDir == Direction.UP ? maxY : minY, maxZ),
                            new Vec3(maxX, faceDir == Direction.UP ? maxY : minY, maxZ),
                            new Vec3(center.x, faceDir == Direction.UP ? maxY : minY, center.z));
            case Z -> // Z-axis (North-South direction)
                    List.of(new Vec3(minX, minY, faceDir == Direction.SOUTH ? maxZ : minZ),
                            new Vec3(maxX, minY, faceDir == Direction.SOUTH ? maxZ : minZ),
                            new Vec3(minX, maxY, faceDir == Direction.SOUTH ? maxZ : minZ),
                            new Vec3(maxX, maxY, faceDir == Direction.SOUTH ? maxZ : minZ),
                            new Vec3(center.x,
                                    center.y,
                                    faceDir == Direction.SOUTH ? maxZ : minZ));
        };
    }
}
