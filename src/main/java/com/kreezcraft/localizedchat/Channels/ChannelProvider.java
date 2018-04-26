package com.kreezcraft.localizedchat.Channels;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ChannelProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(IChannel.class)
	public static final Capability<IChannel> CHANNEL = null;
	
	private IChannel instance = CHANNEL.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CHANNEL;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CHANNEL ? CHANNEL.<T> cast(this.instance) : null;
	}
	
	@Override
	public NBTBase serializeNBT() {
		return CHANNEL.getStorage().writeNBT(CHANNEL, this.instance, null);
	}
	
	@Override
	public void deserializeNBT(NBTBase nbt) {
		CHANNEL.getStorage().readNBT(CHANNEL, this.instance, null, nbt);
	}
}
