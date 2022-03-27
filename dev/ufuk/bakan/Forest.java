package dev.ufuk.bakan;

public class Forest extends BattleLoc {

	Forest(Player player) {
		super(player, "Orman", new Vampire(), "Firewood");
	}
	@Override
	protected void markLocationAsClean(){
		Game.cleanBattleLocs.add(Forest.class);
	}

}
