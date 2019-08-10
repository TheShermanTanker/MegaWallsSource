package me.theshermantanker.megawalls;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import net.minecraft.server.v1_7_R4.DamageSource;
import net.minecraft.server.v1_7_R4.Entity;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityMonster;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EnumMonsterType;
import net.minecraft.server.v1_7_R4.GenericAttributes;
import net.minecraft.server.v1_7_R4.IRangedEntity;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.MobEffect;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.World;

public class EntityCustomWither extends EntityMonster implements IRangedEntity {
	
    public EntityCustomWither(World world) {		
    	super(world);
    	this.goalSelector.a(2, new PathfinderGoalRangedAttack(this, 40, 20.0F));
        this.setHealth(this.getMaxHealth());
        this.a(0.9F, 4.0F);
        this.fireProof = true;
        this.getNavigation().e(true);
        this.b = 50;
        this.persistent = true;
        scheduler = new BukkitRunnable() {
        	
        	int seconds = 7;
        	
        	@Override
        	public void run() {
        		if(!EntityCustomWither.this.isEnraged && MegaWallsPlugin.plugin.handler.gamePhase.get(Bukkit.getWorld(EntityCustomWither.this.world.worldData.getName())) > 1) this.cancel();
        		if(!(runnable == null)) return;
        		if(EntityCustomWither.this.dead) this.cancel();
        		if(seconds == 0) {
        			Random random = new Random();
        			int skill = random.nextInt(5);
        			runnable = new EntityWitherSkillRunnable(self, skill);
        			seconds = 7;
        		} else {
        			if(runnable == null && !(EntityCustomWither.this.world.a(EntityPlayer.class, EntityCustomWither.this.boundingBox.grow(40.0D, 4.0D, 40.0D), new EntitySelectorEnemyPlayers(EntityCustomWither.this, true)).isEmpty())) {
        				seconds--;
        			}
        		}
        	}
        };
        scheduler.runTaskTimer(MegaWallsPlugin.plugin, 0L, 20L);
	}
    
    EntityCustomWither self = this;
    BukkitRunnable scheduler;
    EntityWitherSkillRunnable runnable = null;
    protected Team allies;
	private float[] bp = new float[2];
    private float[] bq = new float[2];
    private float[] br = new float[2];
    private float[] bs = new float[2];
    private int[] bt = new int[2];
    private int[] bu = new int[2];
    public boolean isEnraged = false;

    protected void c() {
        super.c();
        this.datawatcher.a(17, new Integer(0));
        this.datawatcher.a(18, new Integer(0));
        this.datawatcher.a(19, new Integer(0));
        this.datawatcher.a(20, new Integer(0));
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Invul", this.ca());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.s(nbttagcompound.getInt("Invul"));
    }

    protected String t() {
        return "mob.wither.idle";
    }

    protected String aT() {
        return "mob.wither.hurt";
    }

    protected String aU() {
        return "mob.wither.death";
    }
    
    @Override
    public boolean isTypeNotPersistent() {
    	return false;
    }
    
    @Override
    public void e() {
        this.motY *= 0.6000000238418579D;
        double d0;
        double d1;
        double d2;

        if (!this.world.isStatic && this.t(0) > 0) {
            Entity entity = this.world.getEntity(this.t(0));

            if (entity != null){

                double d3 = entity.locX - this.locX;

                d0 = entity.locZ - this.locZ;
                d1 = d3 * d3 + d0 * d0;
                if (d1 > 9.0D) {
                    d2 = (double) MathHelper.sqrt(d1);
                    
                }
            }
        }

        if (this.motX * this.motX + this.motZ * this.motZ > 0.05000000074505806D) {
            this.yaw = (float) Math.atan2(this.motZ, this.motX) * 57.295776F - 90.0F;
        }

        super.e();

        int i;

        for (i = 0; i < 2; ++i) {
            this.bs[i] = this.bq[i];
            this.br[i] = this.bp[i];
        }

        int j;

        for (i = 0; i < 2; ++i) {
            j = this.t(i + 1);
            Entity entity1 = null;

            if (j > 0) {
                entity1 = this.world.getEntity(j);
            }

            if (entity1 != null) {
                d0 = this.u(i + 1);
                d1 = this.v(i + 1);
                d2 = this.w(i + 1);
                double d4 = entity1.locX - d0;
                double d5 = entity1.locY + (double) entity1.getHeadHeight() - d1;
                double d6 = entity1.locZ - d2;
                double d7 = (double) MathHelper.sqrt(d4 * d4 + d6 * d6);
                float f = (float) (Math.atan2(d6, d4) * 180.0D / 3.1415927410125732D) - 90.0F;
                float f1 = (float) (-(Math.atan2(d5, d7) * 180.0D / 3.1415927410125732D));

                this.bp[i] = this.b(this.bp[i], f1, 40.0F);
                this.bq[i] = this.b(this.bq[i], f, 10.0F);
            } else {
                this.bq[i] = this.b(this.bq[i], this.aM, 10.0F);
            }
        }

        boolean flag = this.cb();

        for (j = 0; j < 3; ++j) {
            double d8 = this.u(j);
            double d9 = this.v(j);
            double d10 = this.w(j);

            this.world.addParticle("smoke", d8 + this.random.nextGaussian() * 0.30000001192092896D, d9 + this.random.nextGaussian() * 0.30000001192092896D, d10 + this.random.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
            if (flag && this.world.random.nextInt(4) == 0) {
                this.world.addParticle("mobSpell", d8 + this.random.nextGaussian() * 0.30000001192092896D, d9 + this.random.nextGaussian() * 0.30000001192092896D, d10 + this.random.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D);
            }
        }

        if (this.ca() > 0) {
            for (j = 0; j < 3; ++j) {
                this.world.addParticle("mobSpell", this.locX + this.random.nextGaussian() * 1.0D, this.locY + (double) (this.random.nextFloat() * 3.3F), this.locZ + this.random.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
            }
        }
    }

    protected void bn() {
        int i;
        
            super.bn();

            int j;

            for (i = 1; i < 3; ++i) {
                if (this.ticksLived >= this.bt[i - 1]) {
                    this.bt[i - 1] = this.ticksLived + 10 + this.random.nextInt(10);

                    j = this.t(i);
                    if (j > 0) {
                        Entity entity = this.world.getEntity(j);

                        if (entity != null && entity.isAlive() && this.f(entity) <= 900.0D && this.hasLineOfSight(entity)) {
                            this.bt[i - 1] = this.ticksLived + 40 + this.random.nextInt(20);
                            this.bu[i - 1] = 0;          
                        } else {
                            this.b(i, 0);
                        }
                    } else {
                    	return;
                    }
                }
            }

            if (this.getGoalTarget() != null) {
                this.b(0, this.getGoalTarget().getId());
            } else {
                this.b(0, 0);
            }
            
            List friendly = this.world.a(EntityPlayer.class, this.boundingBox.grow(40.0D, 4.0D, 40.0D), new EntitySelectorEnemyPlayers(this, false));
            for(Object entities : friendly){
            	if(entities instanceof EntityPlayer){
            		EntityPlayer player = (EntityPlayer) entities;
            		if(!player.getBukkitEntity().hasPotionEffect(PotionEffectType.REGENERATION) && this.isEnraged){
            			player.getBukkitEntity().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 2));
            		}
            	}
            }
            
    }
    
    @Override
    public void die(DamageSource damagesource){
    	String[] name = this.getCustomName().split(" ");
    	for(Object list : this.world.players){
    		EntityPlayer player = null;
    		if(list instanceof EntityPlayer){
    			player = (EntityPlayer) list;
    		}
    		player.getBukkitEntity().sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "Mega Walls" + ChatColor.GRAY + "]: " + ChatColor.YELLOW + "The " + name[0] + " " + name[1] + ChatColor.YELLOW + " has died! " + ChatColor.YELLOW + ChatColor.stripColor(name[0]) + " Players can no longer respawn!");
    	}
    	for(OfflinePlayer offlineplayer : this.allies.getPlayers()){
    		Player player = offlineplayer.getPlayer();
    		String worldName = this.world.worldData.getName();
    		DatabaseHandler handler = new DatabaseHandler();
    		player.setBedSpawnLocation(handler.getWorldData(worldName).lobby, true);
    		
    	}
    	super.die(damagesource);
    }
    
    
    
    public void setDecay(){
    	EntityCustomWither a = this;
    	new BukkitRunnable(){
    		int decayTime = 5;
    		public void run(){
    			if(decayTime == 0){
    				if(a.getHealth() - 3 > 0){
    					a.setHealth(a.getHealth() - 3);
    				} else {
    					if(a.getHealth() > 1){
    						a.setHealth(1);
    					}
    				}
    				decayTime = 5;
    			} else {
    				decayTime--;
    			}
    		}
    	}.runTaskTimer(MegaWallsPlugin.plugin, 0, 20);
    }

    public void as() {}

    public int aV() {
        if(this.cb()) {
        	return 4;
        } else {
        	return 0;
        }
    }

    private double u(int i) {
        if (i <= 0) {
            return this.locX;
        } else {
            float f = (this.aM + (float) (180 * (i - 1))) / 180.0F * 3.1415927F;
            float f1 = MathHelper.cos(f);

            return this.locX + (double) f1 * 1.3D;
        }
    }

    private double v(int i) {
        return i <= 0 ? this.locY + 3.0D : this.locY + 2.2D;
    }

    private double w(int i) {
        if (i <= 0) {
            return this.locZ;
        } else {
            float f = (this.aM + (float) (180 * (i - 1))) / 180.0F * 3.1415927F;
            float f1 = MathHelper.sin(f);

            return this.locZ + (double) f1 * 1.3D;
        }
    }

    private float b(float f, float f1, float f2) {
        float f3 = MathHelper.g(f1 - f);

        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        return f + f3;
    }

    private void a(int i, EntityLiving entityliving) {
        this.a(i, entityliving.locX, entityliving.locY + (double) entityliving.getHeadHeight() * 0.5D, entityliving.locZ, i == 0 && this.random.nextFloat() < 0.001F);
    }

    private void a(int i, double d0, double d1, double d2, boolean flag) {
        this.world.a((EntityHuman) null, 1014, (int) this.locX, (int) this.locY, (int) this.locZ, 0);
        double d3 = this.u(i);
        double d4 = this.v(i);
        double d5 = this.w(i);
        double d6 = d0 - d3;
        double d7 = d1 - d4;
        double d8 = d2 - d5;
        EntityCustomWitherSkull entitywitherskull = new EntityCustomWitherSkull(this.world, this, d6, d7, d8);

        if (flag) {
            entitywitherskull.setCharged(true);
        }

        entitywitherskull.locY = d4;
        entitywitherskull.locX = d3;
        entitywitherskull.locZ = d5;
        this.world.addEntity(entitywitherskull);
    }

    public void a(EntityLiving entityliving, float f) {
        this.a(0, entityliving);
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
    	
    	if(f > 50.0f){
    		return false;
    	} else {
    		 
    		if(damagesource.translationIndex.equals("mob") || damagesource.translationIndex.equals("player")){

                for (int i = 0; i < this.bu.length; ++i) {
                    this.bu[i] += 3;
                }
                
                if(damagesource.getEntity().getBukkitEntity() instanceof CraftPlayer){
                	CraftPlayer player = (CraftPlayer) damagesource.getEntity().getBukkitEntity();
                	if(this.allies.hasPlayer(player)){
                		return false;
                	}
                }
                
                return super.damageEntity(damagesource, f);
                
    		}
    		
    	}
    	
        return false;
    }
    
    public void heal(float f) {
    	return;
    }
    
    public int ca(){
    	return this.datawatcher.getInt(20);
    }
    
    public void s(int i){
    	this.datawatcher.watch(20, Integer.valueOf(i));
    }
    
    @Override
    protected void dropDeathLoot(boolean flag, int i) {
        return;
    }

    protected void w() {
        this.aU = 0;
    }

    protected void b(float f) {}

    public void addEffect(MobEffect mobeffect) {}

    protected boolean bk() {
        return true;
    }

    protected void aD() {
        super.aD();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(1000.0D);
        this.getAttributeInstance(GenericAttributes.d).setValue(0.6000000238418579D);
        this.getAttributeInstance(GenericAttributes.b).setValue(40.0D);
        this.getAttributeInstance(GenericAttributes.c).setValue(Double.POSITIVE_INFINITY);
    }

    public int t(int i) {
        return this.datawatcher.getInt(17 + i);
    }

    public void b(int i, int j) {
        this.datawatcher.watch(17 + i, Integer.valueOf(j));
    }

    public boolean cb() {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.UNDEAD;
    }

    public void mount(Entity entity) {
        this.vehicle = null;
    }

}