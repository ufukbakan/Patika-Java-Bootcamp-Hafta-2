package dev.ufuk.bakan;
public class River extends BattleLoc {

	River(Player player) {
		super(player, "Nehir", new Bear(), "Water");
	}

	@Override
	protected void markLocationAsClean(){
		Game.cleanBattleLocs.add(River.class);
	}

}
