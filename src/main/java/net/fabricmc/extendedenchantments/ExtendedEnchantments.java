package net.fabricmc.extendedenchantments;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.extendedenchantments.enchantment.AcrobaticsEnchantment;
import net.fabricmc.extendedenchantments.enchantment.CharmEnchantment;
import net.fabricmc.extendedenchantments.enchantment.FrostEnchantment;
import net.fabricmc.extendedenchantments.status_effects.CharmStatusEffect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExtendedEnchantments implements ModInitializer {

	public static Enchantment FROST = Registry.register(
			Registry.ENCHANTMENT,
			new Identifier("extendedenchantments", "frost"),
			new FrostEnchantment());

	public static Enchantment ACROBATICS = Registry.register(
			Registry.ENCHANTMENT,
			new Identifier("extendedenchantments", "acrobatics"),
			new AcrobaticsEnchantment());

	public static Enchantment CHARM = Registry.register(
			Registry.ENCHANTMENT,
			new Identifier("extendedenchantments", "charm"),
			new CharmEnchantment());

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(
				Registry.STATUS_EFFECT,
				new Identifier("extendedenchantments", "charm"),
				new CharmStatusEffect());
	}
}
