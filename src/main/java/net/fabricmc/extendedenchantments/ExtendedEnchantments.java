package net.fabricmc.extendedenchantments;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.extendedenchantments.enchantment.DoubleJumpEnchantment;
import net.fabricmc.extendedenchantments.enchantment.FrostEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExtendedEnchantments implements ModInitializer {


	public static Enchantment FROST = Registry.register(
            Registry.ENCHANTMENT,
            new Identifier("extendedenchantments", "frost"),
            new FrostEnchantment()
    );

	public static Enchantment DOUBLE_JUMP = Registry.register(
		Registry.ENCHANTMENT,
		new Identifier("extendedenchantments", "double_jump"),
		new DoubleJumpEnchantment()
	);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
	}
}
