package fun.wich.mixin;

import fun.wich.MossyLlamasMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.UnaryOperator;

@Mixin(Items.class)
public abstract class ItemsMixin {
	@Shadow public static Item register(Block block, UnaryOperator<Item.Settings> settingsOperator) { return null; }

	@Inject(method= "register(Lnet/minecraft/block/Block;)Lnet/minecraft/item/Item;", at=@At("HEAD"), cancellable=true)
	private static void MossyLlamas_Register(Block block, CallbackInfoReturnable<Item> cir) {
		EquippableComponent component;
		if (block == Blocks.MOSS_CARPET) component = EquippableComponent.builder(EquipmentSlot.BODY)
				.equipSound(SoundEvents.ENTITY_LLAMA_SWAG)
				.model(RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MossyLlamasMod.MOD_ID, "moss_carpet")))
				.allowedEntities(EntityType.LLAMA, EntityType.TRADER_LLAMA)
				.canBeSheared(true).shearingSound(SoundEvents.ITEM_LLAMA_CARPET_UNEQUIP).build();
		else if (block == Blocks.PALE_MOSS_CARPET) component = EquippableComponent.builder(EquipmentSlot.BODY)
				.equipSound(SoundEvents.ENTITY_LLAMA_SWAG)
				.model(RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(MossyLlamasMod.MOD_ID, "pale_moss_carpet")))
				.allowedEntities(EntityType.LLAMA, EntityType.TRADER_LLAMA)
				.canBeSheared(true).shearingSound(SoundEvents.ITEM_LLAMA_CARPET_UNEQUIP).build();
		else return;
		cir.setReturnValue(register(block, settings -> settings.component(DataComponentTypes.EQUIPPABLE, component)));
	}
}
