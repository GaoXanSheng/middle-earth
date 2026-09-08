package net.sevenstars.middleearth.entity.npcs;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.entity.TrackedDataHandlerRegistryME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollEntity;
import net.sevenstars.middleearth.entity.goals.*;
import net.sevenstars.middleearth.entity.npcs.data.NpcData;
import net.sevenstars.middleearth.entity.npcs.data.NpcInitializationData;
import net.sevenstars.middleearth.entity.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcEntityInitializer;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcSpawnEggHelper;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcRenderedPart;
import net.sevenstars.middleearth.entity.spider.Pouncer;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaEntity;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.sevenstars.middleearth.entity.spider.spawn.SpawnOfShelobEntity;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.npc_types.data.LootData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.utils.ItemTagsME;
import net.sevenstars.middleearth.utils.SpawnUtil;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class NpcEntity extends PathfinderMob implements EquipmentUser, CrossbowAttackMob {
    public static class KeyStrings {
        public static final String DATA = "NpcData";
        public static final String INITIALIZATION_DATA = "InitializationData";
        public static final String TEXTURE_DATA = "TextureData";
        public static final String IS_FIGHTING = "IsFighting";
    }
    // [TrackedDatas]
    private static final EntityDataAccessor<NpcData> NPC_DATA;
    private static final EntityDataAccessor<NpcInitializationData> NPC_INITIALIZATION_DATA;
    private static final EntityDataAccessor<NpcTextureData> NPC_TEXTURE_DATA;
    private static final EntityDataAccessor<Boolean> IS_FIGHTING;

    private static final EntityDataAccessor<Integer> USING_ITEM = SynchedEntityData.defineId(NpcEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CROSSBOW_CHARGING = SynchedEntityData.defineId(NpcEntity.class, EntityDataSerializers.BOOLEAN);

    private final CustomBowAttackGoal bowAttackGoal = new CustomBowAttackGoal<>(this, 1.0, 20, 16.0F);
    private final NpcCrossBowAttackGoal crossBowAttackGoal = new NpcCrossBowAttackGoal<>(this, 1.0, 11.0F);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2, false) {
        @Override
        public void stop() {
            super.stop();
            NpcEntity.this.setAggressive(false);
        }

        @Override
        public void start() {
            super.start();
            NpcEntity.this.setAggressive(true);
        }
    };

    public final AnimationState walkingState = new AnimationState();
    public final AnimationState idleState = new AnimationState();
    public final AnimationState aimingState = new AnimationState();
    public final AnimationState attackState = new AnimationState();
    public final AnimationState swingState = new AnimationState();

    public NpcEntity(EntityType<NpcEntity> entityType, Level world) {
        super(entityType, world);
        // Client-side preview entities are never networked, so they keep vanilla's "not networked" sentinel;
        // networked NPCs get their real id assigned right after construction by the spawn packet handler.
        if (world.isClientSide()) {
            this.setId(-1);
        }
        this.updateAttackType();
        this.navigation.setCanOpenDoors(true);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData entityData) {
        this.saveSpawnReason(spawnReason);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    // [IsFighting]
    public void setFighting(boolean state){
        this.entityData.set(IS_FIGHTING, state);
    }
    public boolean getFighting(){
        return this.entityData.get(IS_FIGHTING);
    }
    // [IsBlocking] // TODO
    public void setBlocking(boolean state){
        //this.dataTracker.set(IS_FIGHTING, state);
    }
    public boolean getBlocking(){
        return false; //this.dataTracker.get(IS_FIGHTING);
    }
    // [NpcTextureData]
    public void saveNpcTextureData(NpcTextureData npcTextureData) {
        this.entityData.set(NPC_TEXTURE_DATA, npcTextureData);
    }
    public NpcTextureData retrieveNpcTextureData() {
        return this.entityData.get(NPC_TEXTURE_DATA);
    }
    public boolean hasTextureData(){
        return retrieveNpcTextureData().get(NpcRenderedPart.BODY) != null;
    }
    public boolean shouldRefreshVisuals() {
        return this.retrieveNpcTextureData().needToBeRefreshed();
    }
    // [NpcInitializationData]
    public void saveNpcInitializationData(NpcInitializationData npcInitializationData) {
        this.entityData.set(NPC_INITIALIZATION_DATA, npcInitializationData);
    }
    public NpcInitializationData retrieveNpcInitializationData() {
        return this.entityData.get(NPC_INITIALIZATION_DATA);
    }
    public void prepare() {
        NpcInitializationData npcInitializationData = retrieveNpcInitializationData();
        boolean result = npcInitializationData.tryToInitialize(this);
        if(result)
            resetInitializationData();
    }

    public void resetInitializationData() {
        this.entityData.set(NPC_INITIALIZATION_DATA, new NpcInitializationData());
    }
    // [NpcTypeIdentifier]
    public void prepareNpcIdentifier(Identifier npcTypeIdentifier){
        NpcInitializationData newNpcInitializationData = this.retrieveNpcInitializationData().withType(npcTypeIdentifier);
        this.saveNpcInitializationData(newNpcInitializationData);
    }
    // [NpcData]
    public void saveNpcData(NpcData npcData) {
        this.entityData.set(NPC_DATA, npcData);
    }
    public NpcData retrieveNpcData() {
        return this.entityData.get(NPC_DATA);
    }
    // [NpcType]
    public NpcType getNpcType(){
        return retrieveNpcData().getNpcType();
    }
    public void saveNpcType(Holder<NpcType> npcType){
        NpcData newNpcData = this.retrieveNpcData().withType(npcType);
        this.saveNpcData(newNpcData);
    }
    // [Loot Data]
    public LootData retrieveLootData(){
        NpcType npcType = retrieveNpcData().getNpcType();
        if(npcType == null)
            return null;
        return npcType.getLootData();
    }
    // [Category]
    public EntityCategories getNpcCategory() {
        return retrieveNpcData().getCategory();
    }
    public void saveCategory(EntityCategories category) {
        NpcData newNpcData = this.retrieveNpcData().withCategory(category);
        this.saveNpcData(newNpcData);
    }
    // [SpawnReason]
    private void saveSpawnReason(EntitySpawnReason spawnReason) {
        NpcData newNpcData = this.retrieveNpcData().withSpawnReason(spawnReason);
        this.saveNpcData(newNpcData);
    }
    public EntitySpawnReason getSpawnReason() {
        return this.retrieveNpcData().getSpawnReason();
    }
    // [StructureManager]
    public void assignStructureManager(StructureManagerBlockEntity blockEntity){
        NpcData newNpcData = this.retrieveNpcData().withStructureManagerPos(blockEntity.getBlockPos());
        this.saveNpcData(newNpcData);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.ATTACK_DAMAGE, 1.0);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new TargetPlayerDiplomacyGoal(this));
        this.targetSelector.addGoal(4, new NpcDoorInteractGoal(this, true));
        this.targetSelector.addGoal(5, new TargetNPCDiplomacyGoal(this));
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, SnowTrollEntity.class, true));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, SpawnOfShelobEntity.class, true));
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, ShelobiteScuttlerEntity.class, true));
        this.targetSelector.addGoal(9, new NearestAttackableTargetGoal<>(this, ShelobiteLarvaEntity.class, true));
        this.targetSelector.addGoal(10, new NearestAttackableTargetGoal<>(this, AbstractHorse.class, true));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(NPC_INITIALIZATION_DATA, new NpcInitializationData());
        builder.define(NPC_DATA, new NpcData());
        builder.define(NPC_TEXTURE_DATA, new NpcTextureData());
        builder.define(IS_FIGHTING, false);
        builder.define(CROSSBOW_CHARGING, false);
        builder.define(USING_ITEM, 0);
    }

    @Override
    public void saveWithoutId(ValueOutput view) {
        super.saveWithoutId(view);
        addAdditionalSaveData(view);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput view) {
        super.addAdditionalSaveData(view);
        view.store(KeyStrings.DATA, NpcData.CODEC, this.retrieveNpcData());
        view.store(KeyStrings.INITIALIZATION_DATA, NpcInitializationData.CODEC, this.retrieveNpcInitializationData());
        view.store(KeyStrings.TEXTURE_DATA, NpcTextureData.CODEC, this.retrieveNpcTextureData());
        view.store(KeyStrings.IS_FIGHTING, Codec.BOOL, this.getFighting());
    }

    @Override
    public void load(ValueInput view) {
        super.load(view);
        readAdditionalSaveData(view);
    }

    @Override
    public void readAdditionalSaveData(ValueInput view) {
        super.readAdditionalSaveData(view);
        NpcData foundNpcData = view.read(KeyStrings.DATA, NpcData.CODEC).orElse(new NpcData());
        NpcInitializationData foundNpcInitializationData = view.read(KeyStrings.INITIALIZATION_DATA, NpcInitializationData.CODEC).orElse(new NpcInitializationData());
        NpcTextureData foundNpcTextureData = view.read(KeyStrings.TEXTURE_DATA, NpcTextureData.CODEC).orElse(new NpcTextureData());
        boolean isFighting = view.read(KeyStrings.IS_FIGHTING, Codec.BOOL).orElse(false);

        String npcDataId = view.read("NpcDataId", Codec.STRING).orElse(null);
        if(npcDataId != null){
            foundNpcInitializationData = foundNpcInitializationData.withType(MiddleEarth.fetchId(npcDataId));
            foundNpcData = new NpcData();
            foundNpcTextureData = new  NpcTextureData();
            isFighting = false;
        }

        this.entityData.set(NPC_DATA, foundNpcData);
        this.entityData.set(NPC_INITIALIZATION_DATA, foundNpcInitializationData);
        this.entityData.set(NPC_TEXTURE_DATA, foundNpcTextureData);
        this.entityData.set(IS_FIGHTING, isFighting);

        foundNpcInitializationData.tryToInitialize(this);

        this.updateAttackType();
    }

    @Override
    public boolean canUseNonMeleeWeapon(ItemStack weapon) {
        return true;
    }

    @Override
    public @Nullable ItemStack getPickResult() {
        if(getNpcType() == null)
            return ItemStack.EMPTY;
        return NpcSpawnEggHelper.getSpawnEgg(level(), getNpcType().getId());
    }
    public void updateAttackType() {
        if (this.level() != null && !this.level().isClientSide()) {
            this.goalSelector.removeGoal(this.meleeAttackGoal);
            this.goalSelector.removeGoal(this.bowAttackGoal);
            ItemStack itemStack = this.getMainHandItem();
            if (itemStack.is(Items.BOW) || itemStack.is(ItemTagsME.BOW)) {
                int i = 30;
                if (this.level().getDifficulty() != Difficulty.HARD) {
                    i = 20;
                }
                this.bowAttackGoal.setAttackInterval(i);
                this.goalSelector.addGoal(4, this.bowAttackGoal);
            } if (itemStack.is(Items.CROSSBOW) || itemStack.is(ItemTagsME.CROSSBOW)) {
                this.goalSelector.addGoal(4, this.crossBowAttackGoal);
            } else {
                this.goalSelector.addGoal(4, this.meleeAttackGoal);
            }
        }
    }

    public void updateTargetGoals() {
        //if (this.getWorld() != null && !this.getWorld().isClient) {
        //    if(getFaction().getDisposition() == DispositionType.EVIL) {
        //        this.targetSelector.add(10, new ActiveTargetGoal<>(this, BroadhoofGoatEntity.class, true));
        //        this.targetSelector.add(10, new ActiveTargetGoal<>(this, GreatHornEntity.class, true));
        //    } else {
        //        this.targetSelector.add(5, new ActiveTargetGoal<>(this, CaveTrollEntity.class, true));
        //        this.targetSelector.add(5, new ActiveTargetGoal<>(this, StoneTrollEntity.class, true));
        //        this.targetSelector.add(6, new ActiveTargetGoal<>(this, WargEntity.class, true));
        //    }
        //}
    }

    //region [DATA TRANSFER]
    // GETTERS
    public Identifier getNpcTypeIdentifier(){
        return retrieveNpcData().getNpcTypeId();
    }
    public Identifier getFactionIdentifier(){
        Faction faction = getFaction();
        if(faction == null) return null;
        return faction.getId();
    }
    public BlockPos getStructureManagerHostPos() {
        return this.retrieveNpcData().getStructureManagerPos();
    }
    //endregion

    public void tryToInitializeData(){
        if(Objects.equals(blockPosition(), new BlockPos(0, 0, 0))) // 0,0,0 is what's used for commands, needs to be delayed
            return;

        this.prepare();

        Level world = level();
        if(world.isClientSide())
            return;

        if(world instanceof ServerLevel serverWorld){
            if(NpcEntityInitializer.shouldInitialize(serverWorld, this)){
                NpcEntityInitializer.initializeNpcEntity(serverWorld, this);
            }
        }
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel world, DamageSource source) {
        if (source.is(DamageTypes.IN_WALL) && this.isPassenger()) {
            return true;
        }
        return super.isInvulnerableTo(world, source);
    }

    @Override
    protected void customServerAiStep(ServerLevel world) {
        tryToInitializeData();
        //CombatArchetypeRuntimeData runtimeData = getCombatRuntimeData();
       // if(runtimeData != null)
           // runtimeData.tick(this, world);

        /*Profiler profiler = Profilers.get();
        profiler.push("npcBrain");
        this.getBrain().tick(world, this);
        profiler.pop();
        profiler.push("npcActivityUpdate");
        NpcBrain.updateActivities(this);
        profiler.pop();*/
        super.customServerAiStep(world);

        if(isPassenger()){
            forceSetRotation(getVehicle().getYRot(), false, getVehicle().getXRot(), false);
        }
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        Vec3 pos = super.getPassengerAttachmentPoint(passenger, dimensions, scaleFactor);

        Vec3 offset = new Vec3(
                0.0,
                -0.3 * scaleFactor,
                -0.25 * scaleFactor
        );

        return pos.add(rotatePoint(offset, this.getYRot()));
    }

    private static Vec3 rotatePoint(Vec3 point, float yaw) {
        return point.yRot(-yaw * ((float)Math.PI / 180F));
    }

    @Override
    public boolean isUsingItem() {
        boolean value = super.isUsingItem();
        if(!value) {
            return this.entityData.get(USING_ITEM) > 0;
        }
        return value;
    }

    public void setNpcFlag(int mask, boolean value) {
        setLivingEntityFlag(mask, value);

        if(value) {
            int i = this.entityData.get(USING_ITEM) + 1;
            this.entityData.set(USING_ITEM, i);
        } else {
            this.entityData.set(USING_ITEM, 0);
        }
    }

    /*protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return NpcBrain.create(dynamic);
    }

    public Brain<NpcEntity> getBrain() {
        return (Brain<NpcEntity>) super.getBrain();
    }*/

    public float getFightingMovementSpeed(){
        double currentSpeed = this.getAttributeValue(Attributes.MOVEMENT_SPEED);
        return (float) (currentSpeed);
    }

    public boolean isFighting() {
        boolean isFighting = getFighting();

        this.setSprinting(isFighting);
        if(this.isPassenger() && getVehicle() instanceof AbstractHorse abstractHorseEntity){
            abstractHorseEntity.setSprinting(isFighting);
        }
        return isFighting;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if(!this.level().isClientSide()) {

        } else {
            setupAnimationStates();
        }

    }

    private void setupAnimationStates() {
        if(this.zza > 0) {
            this.walkingState.startIfStopped(this.tickCount);
        } else {
            this.idleState.startIfStopped(this.tickCount);
        }

        int bowPullProgress = this.getTicksUsingItem();
        if(bowPullProgress > 0) {
            this.aimingState.startIfStopped(this.tickCount);
        }

    }

    @Override
    public boolean canControlVehicle() {
        return true;
    }

    @Override
    protected boolean couldAcceptPassenger() {
        return !this.isPassenger();
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance localDifficulty) {
        // Overrides vanilla init equipment (gold sets???)
    }

    @Override
    public void die(DamageSource source) {
        if(getVehicle() != null && getVehicle() instanceof LivingEntity vehicleEntity && vehicleEntity.getControllingPassenger() == this){
            vehicleEntity.setItemSlot(EquipmentSlot.SADDLE, Items.AIR.getDefaultInstance());
            vehicleEntity.setItemSlot(EquipmentSlot.BODY, Items.AIR.getDefaultInstance());
            vehicleEntity.ejectPassengers();
            if(vehicleEntity instanceof AbstractHorse abstractHorseEntity){
                abstractHorseEntity.setTamed(false);
                abstractHorseEntity.resetLove();
                abstractHorseEntity.setSprinting(false);
                abstractHorseEntity.setOwner(null);
            }
            if(vehicleEntity instanceof AbstractBeastEntity abstractBeastEntity){
                abstractBeastEntity.resetTameness();
            }
        }
        BlockPos structureManagerPos = getStructureManagerHostPos();
        if(structureManagerPos != null){
            StructureManagerBlockEntity.triggerDeathSignal(structureManagerPos, this);
        }
        super.die(source);
    }

    private boolean canDropLoot(DamageSource damageSource, boolean causedByPlayer){
        boolean canDropLoot = false;
        if(!causedByPlayer)
            return canDropLoot;
        if(damageSource == null)
            return canDropLoot;

        if(damageSource.getEntity() instanceof Player player){
            PlayerData data = StateSaverAndLoader.getPlayerState(player);
            if(data == null)
                canDropLoot = true;
            else if(data.getFaction() == null)
                canDropLoot = true;
            else{
                try{
                    Faction faction = FactionLookup.getFactionById(level(), data.getFaction());
                    if(faction.isHostileToward(this.getFactionIdentifier()))
                        canDropLoot = true;
                } catch (FactionIdentifierException e){
                    canDropLoot = true;
                }
            }
        }

        return canDropLoot;
    }

    protected void dropAllDeathLoot(ServerLevel world, DamageSource damageSource) {
        if(!canDropLoot(damageSource, damageSource.getEntity() != null && damageSource.getEntity().isAlwaysTicking()))
            return;
        super.dropAllDeathLoot(world, damageSource);
    }

    protected int getBaseExperienceReward(ServerLevel world) {
        LootData lootData = retrieveLootData();
        if(lootData == null)
            return 1;
        return lootData.getExperience(world);
    }

    @Override
    protected void dropFromLootTable(ServerLevel world, DamageSource damageSource, boolean causedByPlayer) {
        ResourceKey<LootTable> lootTableRegistryKey = ResourceKey.create(Registries.LOOT_TABLE, getNpcType().getId().withPrefix("entities/"));
        LootTable lootTable = world.getServer().reloadableRegistries().getLootTable(lootTableRegistryKey);

        if (lootTable != null) {
            LootParams.Builder builder = (new LootParams.Builder(world)).withParameter(LootContextParams.THIS_ENTITY, this)
                    .withParameter(LootContextParams.ORIGIN, this.position())
                    .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                    .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, damageSource.getEntity())
                    .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, damageSource.getDirectEntity());
            Player playerEntity = this.getLastHurtByPlayer();
            if (playerEntity != null) {
                builder = builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, playerEntity).withLuck(playerEntity.getLuck());
            }

            LootParams lootWorldContext = builder.create(LootContextParamSets.ENTITY);
            lootTable.getRandomItems(lootWorldContext, this.getLootTableSeed(), (stack) -> this.spawnAtLocation(world, stack));
        }
    }

    @Override
    public boolean isPersistenceRequired() {
        BlockPos structureManagerPos = getStructureManagerHostPos();
        if(structureManagerPos == null)
            return super.isPersistenceRequired();
        if(level().getBlockEntity(structureManagerPos) instanceof StructureManagerBlockEntity structureManagerBlockEntity)
            return true;
        return super.isPersistenceRequired();
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
    }

    protected Faction getFaction(){
        NpcData data = retrieveNpcData();
        if(data == null)
            return null;
        Identifier factionId = data.getFaction();
        if(factionId == null)
            return null;
        try {
            return FactionLookup.getFactionById(level(), factionId);
        } catch (FactionIdentifierException e) {
            return null;
        }
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity entity) {
        float reach = 1.75f;
        try{
            Optional<Double> damageOpt = Optional.of(this.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE));
            reach = damageOpt.get().floatValue();
        } catch (Exception ignored){}

        if(this.getVehicle() != null || entity.getVehicle() != null) {
            reach += 0.5f;
        }

        return this.distanceTo(entity) <= reach;
    }

    @Override
    public boolean doHurtTarget(ServerLevel world, Entity target) {
        this.level().broadcastEntityEvent(this, EntityEvent.START_ATTACKING);
        boolean targetDamaged;
        float damage = 1.0f;
        try{
            Optional<Double> damageOpt = Optional.of(this.getAttributeValue(Attributes.ATTACK_DAMAGE));
            damage = damageOpt.get().floatValue();
        } catch (Exception ignored){}
        ItemStack itemStack = this.getWeaponItem();
        DamageSource damageSource = Optional.ofNullable(itemStack.getDamageSource(this)).orElse(this.damageSources().mobAttack(this));

        var enchantmentDamage = EnchantmentHelper.modifyDamage(world, itemStack, target, damageSource, damage);
        var bonusDamage =  itemStack.getItem().getAttackDamageBonus(target, enchantmentDamage, damageSource);
        var finalDamage = damage + bonusDamage;

        if(isPassenger() && getVehicle() instanceof AbstractBeastEntity mountEntity){
            mountEntity.doHurtTarget((ServerLevel) target.level(), target);
        }

        targetDamaged = target.hurtServer(world, damageSource, finalDamage);
        if (targetDamaged) {
            LivingEntity livingEntity;
            float g = this.getKnockback(target, damageSource);
            if (g > 0.0f && target instanceof LivingEntity) {
                livingEntity = (LivingEntity)target;
                livingEntity.knockback(g * 0.5f, Mth.sin(this.getYRot() * ((float)Math.PI / 180)), -Mth.cos(this.getYRot() * ((float)Math.PI / 180)), damageSource, finalDamage);
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            if (target instanceof LivingEntity) {
                livingEntity = (LivingEntity)target;
                itemStack.hurtEnemy(livingEntity, this);
            }
            EnchantmentHelper.doPostAttackEffects(world, target, damageSource);
            this.setLastHurtMob(target);
            this.playAttackSound();

        }
        return targetDamaged;
    }

    @Override
    public void rideTick() {
        super.rideTick();
        Entity entity = this.getControlledVehicle();
        if (entity instanceof PathfinderMob pathAwareEntity) {
            this.yBodyRot = pathAwareEntity.yBodyRot;
        }
    }

    @Override
    protected Component getTypeName() {
        if(this.getNpcTypeIdentifier() == null) {
            return Component.translatable("npc_type."+ MiddleEarth.MOD_ID +".npc");
        }
        return Component.translatable(this.getNpcTypeIdentifier().toLanguageKey("npc_type"));
    }

    @Override
    protected void blockUsingItem(ServerLevel world, LivingEntity attacker, DamageSource damageSource, float damage) {
        super.blockUsingItem(world, attacker, damageSource, damage);
        ItemStack itemStack = this.getItemBlockingWith();
        BlocksAttacks blocksAttacksComponent = itemStack != null ? itemStack.get(DataComponents.BLOCKS_ATTACKS) : null;
        float f = attacker.getSecondsToDisableBlocking();
        if (f > 0.0f && blocksAttacksComponent != null) {
            blocksAttacksComponent.disable(world, this, f, itemStack);
        }
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return shouldTarget(this, target) && super.canAttack(target);
    }

    public static boolean shouldTarget(NpcEntity npcEntity, LivingEntity target){
        // TODO : datadriven
        if(target instanceof SnailEntity || target instanceof Monster || target instanceof SnowTrollEntity || target instanceof Pouncer)
            return true;
        if(!npcEntity.considersEntityAsAlly(target)){
            return true;
        }
        return false;
    }

    public int getTickAttackSpeedCooldown(){
        if(!this.getAttributes().hasAttribute(Attributes.ATTACK_SPEED))
            return 1;
        return (int)this.getAttributes().getValue(Attributes.ATTACK_SPEED);
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel world, DamageSource source, boolean causedByPlayer) {
        // No drop allowed
    }

    //public void releaseTicketFor(MemoryModuleType<GlobalPos> destination) {
    //    this.releaseTicketFor(MemoryModuleType.HOME);
    //}

    public float getWidthScale() {
        try{
            return (float) this.getAttributeValue(EntityAttributesME.WIDTH_SCALE);
        }
        catch (Exception ignored){
            return 1.0f;
        }
    }

    /*@Override
    public boolean isInAttackRange(LivingEntity entity) {
        CombatArchetypeRuntimeData runtimeData = getCombatRuntimeData();
        if(runtimeData == null)
            return false;
        return runtimeData.getCombatArchetypeData().isInOptimalRange(this, entity.getBlockPos());
    }*/

    protected AbstractArrow createArrowProjectile(ItemStack arrow, float damageModifier, @Nullable ItemStack shotFrom) {
        return ProjectileUtil.getMobArrow(this, arrow, damageModifier, shotFrom);
    }

    public boolean isAiming() {
        int i = this.getTicksUsingItem();
        return i > 0;
    }

    public void aim() {
        var currentItem = getUseItem();
        if(currentItem.isEmpty()){
            stopUsingItem();
            startUsingItem(InteractionHand.MAIN_HAND);
        }
    }

    public void stopAiming() {
        var currentItem = getUseItem();
        if(currentItem.isEmpty()){
            this.stopUsingItem();
            return;
        }
        this.stopUsingItem();
    }

    public boolean isReadyToShoot() {
        return getMainHandItem() != null;
        //int i = this.getItemUseTime();
        //if (i >= 20) {
        //    return true;
        //}
        //return false;
    }

    private void shootAt(LivingEntity target, float pullProgress, float powerModifier) {
        if (!isReadyToShoot())
            return;

        ItemStack weapon = this.getMainHandItem();
        ItemStack projectileStack = this.getProjectile(weapon);
        if(projectileStack.isEmpty()) projectileStack = new ItemStack(Items.ARROW, 1);

        AbstractArrow projectile = this.createArrowProjectile(projectileStack, pullProgress, weapon);

        double distanceX = target.getX() - this.getX();
        double distanceZ = target.getZ() - this.getZ();
        double horizontalDistance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);

        double distanceY = target.getY(0.3F) - projectile.getY();

        float scale = this.getScale();
        float velocity = 1.6F * powerModifier;

        // Reduce the vanilla arc compensation for larger entities and faster arrows.
        double arcCompensation = horizontalDistance * (0.2F / (scale * powerModifier));

        if (this.level() instanceof ServerLevel serverWorld) {
            Projectile.spawnProjectileUsingShoot(
                    projectile,
                    serverWorld,
                    projectileStack,
                    distanceX,
                    distanceY + arcCompensation,
                    distanceZ,
                    velocity,
                    (float) (14 - serverWorld.getDifficulty().getId() * 4)
            );
        }

        this.playSound(
                SoundEvents.ARROW_SHOOT,
                1.0F,
                1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F)
        );

        stopAiming();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        this.shootAt(target, 1, pullProgress);
    }

    public void shootAt(LivingEntity livingEntity) {
        try{
            this.shootAt(livingEntity, BowItem.getPowerForTime(getTicksUsingItem()), 2f);
        } catch (IllegalArgumentException e){
            this.shootAt(livingEntity, CustomLongbowWeaponItem.getPullProgressLongbow(getTicksUsingItem()), 3f);
        }
    }
    public void shootCrossbowAt(LivingEntity target) {
        this.shootAt(target, 1, 1.25f);
    }

    public boolean isCharging() {
        return this.entityData.get(CROSSBOW_CHARGING);
    }

    @Override
    public void setChargingCrossbow(boolean charging) {
        this.entityData.set(CROSSBOW_CHARGING, charging);
    }

    @Override
    public void onCrossbowAttackPerformed() {
        this.entityData.set(CROSSBOW_CHARGING, false);
    }

    @Override
    protected boolean considersEntityAsAlly(Entity other) {
        if(other instanceof NpcEntity npc){
            return !isHostileToward(npc);
        }
        else if(other instanceof Player player){
            return !isHostileTowardPlayer(player);
        }
        return !isHostileToward(other);
    }

    private boolean isHostileTowardPlayer(Player player) {
        if(player.level().isClientSide() || !player.canBeSeenAsEnemy())
            return false;
        PlayerData playerData = StateSaverAndLoader.getPlayerState(player);
        if(playerData == null || playerData.getFaction() == null)
            return true;
        Faction ownFaction = getFaction();
        if(ownFaction == null)
            return true;
        if(ownFaction.isHostileToward(playerData.getFaction()))
            return true;
        return false;
    }

    private boolean isHostileToward(Entity other) {
        if(other instanceof SnailEntity || other instanceof Monster || other instanceof TrollEntity || other instanceof Pouncer)
            return true;

        if(!other.isVehicle())
            return false;

        if(other.getControllingPassenger() instanceof NpcEntity npc && isHostileToward(npc)) {
            return true;
        }
        return false;
    }

    private boolean isHostileToward(NpcEntity npc) {
        Faction ownFaction = getFaction();
        if(ownFaction == null)
            return true;
        Identifier otherNpcFaction = npc.getFactionIdentifier();
        if(otherNpcFaction == null)
            return true;
        if(ownFaction.isHostileToward(otherNpcFaction))
            return true;
        return false;
    }

    public static boolean canSpawn(EntityType<NpcEntity> type, ServerLevelAccessor serverWorldAccess, EntitySpawnReason spawnReason, BlockPos blockPos, RandomSource random) {
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    static {
        NPC_DATA = SynchedEntityData.defineId(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_DATA);
        NPC_INITIALIZATION_DATA = SynchedEntityData.defineId(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_INITIALIZATION_DATA);
        NPC_TEXTURE_DATA = SynchedEntityData.defineId(NpcEntity.class, TrackedDataHandlerRegistryME.NPC_TEXTURE_DATA);
        IS_FIGHTING = SynchedEntityData.defineId(NpcEntity.class, EntityDataSerializers.BOOLEAN);
    }
}
