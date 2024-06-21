package antifarm;

import config.AntiFarmConfigurations;
import config.global.farm.FarmsSettingsConfig;
import config.global.settings.SettingsConfig;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class AntiWaterlessFarm implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onPlayerInteract(PlayerInteractEvent event) {

		if (SettingsConfig.getInstance().getDisabledWorlds().contains(event.getPlayer().getWorld())) return;

		if (event.getClickedBlock() == null || event.getItem() == null) return;
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if (!event.getItem().getType().equals(Material.BONE_MEAL)) return;
		if (!event.getClickedBlock().getRelative(BlockFace.DOWN).getType().equals(Material.FARMLAND)) return;

		BlockData blockData = event.getClickedBlock().getRelative(BlockFace.DOWN).getBlockData();
		Farmland farmland = (Farmland) blockData;

		if (farmland.getMoisture() != 0) return;
		if (!FarmsSettingsConfig.getInstance().isPreventWaterlessFarms()) return;
		if (!AntiFarmConfigurations.GLOBAL.getFarmBlocks().contains(event.getClickedBlock().getType())) return;

		event.setCancelled(true);

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onBlockGrow(BlockGrowEvent event) {

		if (SettingsConfig.getInstance().getDisabledWorlds().contains(event.getBlock().getWorld())) return;

		if (event.isCancelled() || event.getBlock() == null) return;
		if (!event.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.FARMLAND)) return;

		BlockData blockData = event.getBlock().getRelative(BlockFace.DOWN).getBlockData();
		Farmland farmland = (Farmland) blockData;

		if (farmland.getMoisture() != 0) return;
		if (!FarmsSettingsConfig.getInstance().isPreventWaterlessFarms()) return;
		if (!AntiFarmConfigurations.GLOBAL.getFarmBlocks().contains(event.getBlock().getType())) return;

		event.setCancelled(true);
		event.getBlock().breakNaturally();
		event.getBlock().setType(Material.AIR);

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onDispense(BlockDispenseEvent event) {

		if (SettingsConfig.getInstance().getDisabledWorlds().contains(event.getBlock().getWorld())) return;

		if (event.isCancelled()) return;
		if (!event.getBlock().getType().equals(Material.DISPENSER)) return;
		if (!event.getItem().getType().equals(Material.BONE_MEAL)) return;

		Dispenser dispenser = (Dispenser) event.getBlock().getBlockData();
		Block block = event.getBlock().getRelative(dispenser.getFacing());

		if (!block.getRelative(BlockFace.DOWN).getType().equals(Material.FARMLAND)) return;

		Farmland farmland = (Farmland) block.getRelative(BlockFace.DOWN).getBlockData();

		if (farmland.getMoisture() != 0) return;
		if (!FarmsSettingsConfig.getInstance().isPreventWaterlessFarms()) return;
		if (!AntiFarmConfigurations.GLOBAL.getFarmBlocks().contains(block.getType())) return;

		event.setCancelled(true);

	}

}