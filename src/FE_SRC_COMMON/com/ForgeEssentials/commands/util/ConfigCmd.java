package com.ForgeEssentials.commands.util;

import com.ForgeEssentials.api.modules.ModuleConfigBase;
import com.ForgeEssentials.commands.CommandRules;
import com.ForgeEssentials.commands.ModuleCommands;

import net.minecraft.command.ICommandSender;

import net.minecraftforge.common.Configuration;

import java.io.File;

public class ConfigCmd extends ModuleConfigBase
{
	public Configuration config;
	
	public ConfigCmd(File file)
	{
		super(file);
	}

	@Override
	public void init()
	{
		config = new Configuration(file, true);

		config.addCustomCategoryComment("general", "General Commands configuration.");
		ModuleCommands.removeDuplicateCommands = config.get("general", "removeDuplicateCommands", true,
				"Remove commands from the list if they already exist outside of FE.").getBoolean(true);
		config.save();
	}

	@Override
	public void forceSave()
	{
		// TODO: may have problems..
		String path = CommandRules.rulesFile.getPath();
		path = path.replace(ModuleCommands.cmddir.getPath(), "");
		
		config.addCustomCategoryComment("general", "General Commands configuration.");
		config.get("general", "removeDuplicateCommands", true, "Remove commands from the list if they already exist outside of FE.").value = ""
				+ ModuleCommands.removeDuplicateCommands;
		config.save();
	}

	@Override
	public void forceLoad(ICommandSender sender)
	{
		config.load();
		ModuleCommands.removeDuplicateCommands = config.get("general", "removeDuplicateCommands", true).getBoolean(true);
		
		config.save();
		
		CommandRegistrar.commandConfigs(config);
	}
}
