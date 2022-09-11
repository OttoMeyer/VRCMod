package net.otto.vrcmod.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.otto.vrcmod.block.custom.FrozeBarrel;
import net.otto.vrcmod.item.ModItems;
import net.otto.vrcmod.recipe.CocktailFrozeRecipe;
import net.otto.vrcmod.screen.CocktailFrozeScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CocktailFrozeBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;


    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(3, ItemStack.EMPTY);

    public CocktailFrozeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COCKTAIL_FROZE_BARREL, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0: return CocktailFrozeBlockEntity.this.progress;
                    case 1: return CocktailFrozeBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: CocktailFrozeBlockEntity.this.progress = value; break;
                    case 1: CocktailFrozeBlockEntity.this.maxProgress = value; break;
                }
            }

            public int size() {
                return 2;
            }
        };
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
        nbt.putInt("coctail_froze_barrel.progress", progress);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("coctail_froze_barrel.progress");
    }


    private void resetProgress() {
        this.progress = 0;
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, CocktailFrozeBlockEntity entity) {
        if(world.isClient()) {
            return;
        }

        if(hasRecipe(entity)){
            entity.progress++;
            markDirty(world, blockPos, state);
            if(entity.progress >= entity.maxProgress){
                craftItem(entity);
            }
        } else {
            entity.resetProgress();
            markDirty(world, blockPos, state);
        }

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
