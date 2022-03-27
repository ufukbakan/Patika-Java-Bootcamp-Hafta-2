package dev.ufuk.bakan;

public class Cave extends BattleLoc {
	Cave(Player player) {
		super(player, "Mağara", new Zombie(), "Food");
	}

	@Override
	protected void markLocationAsClean(){
		Game.cleanBattleLocs.add(Cave.class);
	}

}
