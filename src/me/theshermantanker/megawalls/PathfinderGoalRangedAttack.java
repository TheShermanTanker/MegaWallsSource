package me.theshermantanker.megawalls;

import net.minecraft.server.v1_7_R4.EntityInsentient;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.IRangedEntity;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.PathfinderGoal;

public class PathfinderGoalRangedAttack extends PathfinderGoal {
	
	private final EntityInsentient a;
    private final IRangedEntity b;
    private EntityLiving c;
    private int d;
    private int f;
    private int g;
    private int h;
    private float i;
    private float j;

    public PathfinderGoalRangedAttack(IRangedEntity irangedentity, int i, float f) {
        this(irangedentity, i, i, f);
    }

    public PathfinderGoalRangedAttack(IRangedEntity irangedentity, int i, int j, float f) {
        this.d = -1;
        if (!(irangedentity instanceof EntityLiving)) {
            throw new IllegalArgumentException("The Entity must implement IRangedEntity to properly function");
        } else {
            this.b = irangedentity;
            this.a = (EntityInsentient) irangedentity;
            this.g = i;
            this.h = j;
            this.i = f;
            this.j = f * f;
            this.a(3);
        }
    }

    public boolean a() {
        EntityLiving entityliving = this.a.getGoalTarget();

        if (entityliving == null) {
            return false;
        } else {
            this.c = entityliving;
            return true;
        }
    }

    public boolean b() {
        return this.a() || !this.a.getNavigation().g();
    }

    public void d() {
        this.c = null;
        this.f = 0;
        this.d = -1;
    }

    public void e() {
        double d0 = this.a.e(this.c.locX, this.c.boundingBox.b, this.c.locZ);
        boolean flag = this.a.getEntitySenses().canSee(this.c);

        if (flag) {
            ++this.f;
        } else {
            this.f = 0;
        }

        if (d0 <= (double) this.j && this.f >= 20) {
            this.a.getNavigation().h();
        }

        this.a.getControllerLook().a(this.c, 30.0F, 30.0F);
        float f;

        if (--this.d == 0) {
            if (d0 > (double) this.j || !flag) {
                return;
            }

            f = MathHelper.sqrt(d0) / this.i;
            float f1 = f;

            if (f < 0.1F) {
                f1 = 0.1F;
            }

            if (f1 > 1.0F) {
                f1 = 1.0F;
            }

            this.b.a(this.c, f1);
            this.d = MathHelper.d(f * (float) (this.h - this.g) + (float) this.g);
        } else if (this.d < 0) {
            f = MathHelper.sqrt(d0) / this.i;
            this.d = MathHelper.d(f * (float) (this.h - this.g) + (float) this.g);
        }
    }

}
