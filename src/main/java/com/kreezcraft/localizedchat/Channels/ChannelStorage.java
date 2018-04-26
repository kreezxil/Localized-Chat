package com.kreezcraft.localizedchat.Channels;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ChannelStorage implements IStorage<IChannel> {

	@Override
	public NBTBase writeNBT(Capability<IChannel> capability, IChannel instance, EnumFacing side) {
		return new NBTTagString(instance.getChannel());
	}

	@Override
	public void readNBT(Capability<IChannel> capability, IChannel instance, EnumFacing side, NBTBase nbt) {
		instance.setChannel(((NBTTagString) nbt).getString());
	}

}
