package me.theshermantanker.megawalls;

import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityWitherSkull;
import net.minecraft.server.v1_7_R4.EnumDifficulty;
import net.minecraft.server.v1_7_R4.MovingObjectPosition;
import net.minecraft.server.v1_7_R4.World;

public class EntityCustomWitherSkull extends EntityWitherSkull {

    public EntityCustomWitherSkull(World world) {
        super(world);
        this.a(0.3125F, 0.3125F);
    }

    public EntityCustomWitherSkull(World world, EntityLiving entityliving, double d0, double d1, double d2) {
        super(world, entityliving, d0, d1, d2);
        this.a(0.3125F, 0.3125F);
    }
    
    @Override
    protected void a(MovingObjectPosition movingobjectposition) {
        if (!this.world.isStatic) {
            if (movingobjectposition.entity != null) {
                if (this.shooter != null) {
                    if (movingobjectposition.entity.damageEntity(DamageSource.mobAttack(this.shooter), 8.0F) && !movingobjectposition.entity.isAlive()) {
                        return;
                    }
                } else {
                    movingobjectposition.entity.damageEntity(DamageSource.MAGIC, 5.0F);
                }

                if (movingobjectposition.entity instanceof EntityLiving) {
                    byte b0 = 0;

                    if (this.world.difficulty == EnumDifficulty.NORMAL) {
                        b0 = 10;
                    } else if (this.world.difficulty == EnumDifficulty.HARD) {
                        b0 = 40;
                    }

                    if (b0 > 0) {
                        return;
                    }
                }
            }

            this.world.createExplosion(this, this.locX, this.locY, this.locZ, 1.0F, false, this.world.getGameRules().getBoolean("mobGriefing"));
            this.die();
        }
    }
}
