package com.royaltitans;

import com.royaltitans.RoyalTitansPlugin;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class RoyalTitansPluginTest
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(RoyalTitansPlugin.class);
		RuneLite.main(args);
	}
}