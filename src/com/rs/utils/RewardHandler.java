// package com.rs.utils;

// import com.rs.Settings;
// import com.rs.game.World;
// import com.rs.game.item.Item;
// import com.rs.game.player.Player;
// import com.rspserver.motivote.*;

// public class RewardHandler extends MotivoteHandler<Reward>
// {
// 	@Override
// 	public void onCompletion(Reward reward)
// 	{
// 		Player p = World.getPlayer(reward.username().toLowerCase());
		
	
		
// 		if (p != null && reward.rewardName().equalsIgnoreCase("gold"))
// 		{
// 			//p.getInventory().addItem(995, reward.amount());
// 			//p.getInventory().addCoins(reward.amount());
// 			p.getBank().addItem(995, reward.amount(), true);
// 			p.sendMessage("<img=1> Thank You for voting 1m Gold has been added to your bank.");
// 			World.sendWorldMessage("<img=7> <col=FF6600>News: "+p.getDisplayName()+" has voted for "+Settings.SERVER_NAME+"!", false);
// 			reward.complete();
// 		}
// 		if (p != null && reward.rewardName().equalsIgnoreCase("skilling mystery"))
// 		{
// 			//p.getInventory().addItem(995, reward.amount());
// 			//p.getInventory().addCoins(reward.amount());
// 			p.getBank().addItem(18768, reward.amount(), true);
// 			p.sendMessage("<img=1> Thank You for voting a mystery box has been added to your bank.");
// 		World.sendWorldMessage("<img=7> <col=FF6600>News: "+p.getDisplayName()+" has voted for "+Settings.SERVER_NAME+"!", false);
// 			reward.complete();
// 			p.setskillbox = 10;
// 		}
// 		if (p != null && reward.rewardName().equalsIgnoreCase("gear mystery"))
// 		{
// 			//p.getInventory().addItem(995, reward.amount());
// 			//p.getInventory().addCoins(reward.amount());
// 			p.getBank().addItem(18768, reward.amount(), true);
// 			p.sendMessage("<img=1> Thank You for voting a mystery box has been added to your bank.");
// 			World.sendWorldMessage("<img=7> <col=FF6600>News: "+p.getDisplayName()+" has voted for "+Settings.SERVER_NAME+"!", false);
// 			reward.complete();
// 			p.setskillbox = 100;
// 		}
// 	}
// }

// /*
//    _____          __    __    .__                              ._.
//   /     \ _____ _/  |__/  |_  |__| ______    _________  ___.__.| |
//  /  \ /  \\__  \\   __\   __\ |  |/  ___/   / ___\__  \<   |  || |
// /    Y    \/ __ \|  |  |  |   |  |\___ \   / /_/  > __ \\___  | \|
// \____|__  (____  /__|  |__|   |__/____  >  \___  (____  / ____| __
//         \/     \/                     \/  /_____/     \/\/      \/.
// */