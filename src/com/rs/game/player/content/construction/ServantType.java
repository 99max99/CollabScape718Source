package com.rs.game.player.content.construction;

public enum ServantType {

	RICK(4235, 6, 500, 1, false);
	
	private int id, maxItemsPerTrip, cost, foodId;
	private boolean canDoSawmill;
	
	private ServantType(int id, int maxItemsPerTrip, int cost, int foodId, boolean canDoSawmill) {
		this.id = id;
		this.maxItemsPerTrip = maxItemsPerTrip;
		this.cost = cost;
		this.foodId = foodId;
		this.canDoSawmill = canDoSawmill;
	}
	
	public int getId() {
		return id;
	}
	
	public int getMaxItems() {
		return maxItemsPerTrip;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getFoodId() {
		return foodId;
	}
	
	public boolean canSawmill() {
		return canDoSawmill;
	}
}
