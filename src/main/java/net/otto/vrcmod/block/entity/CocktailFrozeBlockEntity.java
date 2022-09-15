package net.otto.vrcmod.block.entity;

import com.google.common.collect.Maps;
import net.minecraft.SharedConstants;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.World;
import net.otto.vrcmod.block.custom.FrozeBarrel;
import net.otto.vrcmod.item.ModItems;
import net.otto.vrcmod.recipe.CocktailFrozeRecipe;
import net.otto.vrcmod.screen.CocktailFrozeScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class CocktailFrozeBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;
    private int frozeTime = 0;
    private int maxFrozeTime = 0;


    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(3, ItemStack.EMPTY);

    public CocktailFrozeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COCKTAIL_FROZE_BARREL, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return CocktailFrozeBlockEntity.this.progress;
                    case 1: return CocktailFrozeBlockEntity.this.maxProgress;
                    case 2: return CocktailFrozeBlockEntity.this.frozeTime;
                    case 3: return CocktailFrozeBlockEntity.this.maxFrozeTime;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: CocktailFrozeBlockEntity.this.progress = value; break;
                    case 1: CocktailFrozeBlockEntity.this.maxProgress = value; break;
                    case 2: CocktailFrozeBlockEntity.this.frozeTime = value; break;
                    case 3: CocktailFrozeBlockEntity.this.maxFrozeTime = value; break;
                }
            }

            public int size() {
                return 4;
            }
        };
    }

    public static Map<Item, Integer> createFuelTimeMap() {
        LinkedHashMap<Item, Integer> map = Maps.newLinkedHashMap();
        CocktailFrozeBlockEntity.addFuel(map, Items.SNOWBALL, 200);
        return map;
    }

    private static void addFuel(Map<Item, Integer> fuelTimes, ItemConvertible item, int fuelTime) {
        Item item2 = item.asItem();
        fuelTimes.put(item2, fuelTime);
    }

    private boolean isFrozing() {
        return this.frozeTime > 0;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Froze Barrel");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CocktailFrozeScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }



    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("cocktail_froze_barrel.progress", progress);
        nbt.putInt("cocktail_froze_barrel.frozeTime", frozeTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("cocktail_froze_barrel.progress");
        frozeTime = nbt.getInt("cocktail_froze_barrel.frozeTime");
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {

        return (side == Direction.UP && slot == 1) ||
                ((side == Direction.EAST || side == Direction.WEST || side == Direction.NORTH || side == Direction.SOUTH) && slot == 0);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        return side == Direction.DOWN && slot == 2;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, CocktailFrozeBlockEntity entity) {
        ItemStack itemStack = entity.inventory.get(1);

        entity.maxFrozeTime = 20;
        if(world.isClient()) {
            return;
        }
        if(!entity.isFrozing()){
            entity.frozeTime = entity.getFrozeTime(entity.inventory.get(0));
            entity.inventory.get(0).decrement(1);

        }

        if(hasRecipe(entity) && entity.isFrozing()){
            --entity.frozeTime;
            entity.progress++;
            markDirty(world, blockPos, state);
            if(entity.progress >= entity.maxProgress){
                craftItem(entity);
            }
        } else {
            //entity.resetProgress();
            if(entity.progress>0)entity.progress--;
            markDirty(world, blockPos, state);
        }

    }

    protected int getFrozeTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        Item item = fuel.getItem();
        return CocktailFrozeBlockEntity.createFuelTimeMap().getOrDefault(item, 0);
    }

    private static void craftItem(CocktailFrozeBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<CocktailFrozeRecipe> recipe = entity.getWorld().getRecipeManager()
                .getFirstMatch(CocktailFrozeRecipe.Type.INSTANCE, inventory, entity.getWorld());

        if(hasRecipe(entity)) {
            entity.removeStack(1, 1);

            entity.setStack(2,new ItemStack(recipe.get().getOutput().getItem(),
                    entity.getStack(2).getCount() +  1));

            entity.resetProgress();
        }
    }

    private static boolean hasRecipe(CocktailFrozeBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++){
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<CocktailFrozeRecipe> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(CocktailFrozeRecipe.Type.INSTANCE, inventory, entity.getWorld());

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory)
                && canInsertItemIntoOutputSlot(inventory, match.get().getOutput().getItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }
}
