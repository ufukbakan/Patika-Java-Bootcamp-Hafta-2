package dev.ufuk.bakan;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
	Player player;
	Location location;
	Scanner scan = new Scanner(System.in);
	public static List<Class<?>> cleanBattleLocs = new ArrayList<>();

	public void login() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Macera Oyununa Hoşgeldiniz !");
		System.out.println("Oyuna başlamadan önce isminizi giriniz :");
		String playerName = scan.nextLine();
		player = new Player(playerName);
		player.selectCha();
		start();
	}

	public void start() {
		while (true) {
			System.out.println();
			System.out.println("=================================================");
			System.out.println();
			System.out.println("Eylem gerçekleştirmek için bir yer seçiniz : ");
			System.out.println("1. Güvenli Ev --> Size ait güvenli bir mekan, düşman yok !");
			System.out.println("2. Mağara --> Karşınıza belki zombi çıkabilir !");
			System.out.println("3. Orman --> Karşınıza belki vampir çıkabilir !");
			System.out.println("4. Nehir --> Karşınıza belki ayı çıkabilir !");
			System.out.println("5. Maden --> Karşınıza belki yılan çıkabilir !");
			System.out.println("6. Mağaza --> Silah veya Zırh alabilirsiniz!");
			System.out.print("Gitmek istediğiniz yer : ");
			int selLoc = scan.nextInt();
			while (selLoc < 0 || selLoc > 5) {
				System.out.print("Lütfen geçerli bir yer seçiniz : ");
				selLoc = scan.nextInt();
			}

			switch (selLoc) {
			case 1:
				location = new SafeHouse(player);
				break;
			case 2:
				goToBattleLocation(Cave.class);
				break;
			case 3:
				goToBattleLocation(Forest.class);
				break;
			case 4:
				goToBattleLocation(River.class);
				break;
			case 5:
				goToBattleLocation(Mine.class);
				break;
			case 6:
				location = new ToolStore(player);
				break;
			default:
				location = new SafeHouse(player);
			}

			if (location.getClass().getName().equals("SafeHouse")) {
				if (player.getInv().isFirewood() && player.getInv().isFood() && player.getInv().isWater()) {
					System.out.println("Tebrikler Oyunu Kazandınız !");
					break;
				}
			}
			if (!location.getLocation()) {
				System.out.println("Oyun Bitti !");
				break;
			}

		}
	}

	private void goToBattleLocation(Class<?> battleLocationClass){
		if(isBattleLocationEligible(battleLocationClass)){
			if(battleLocationClass == Cave.class){
				location = new Cave(player);
			}
			else if(battleLocationClass == Forest.class){
				location = new Forest(player);
			}
			else if(battleLocationClass == River.class){
				location = new River(player);
			}
			else if(battleLocationClass == Mine.class){
				location = new Mine(player);
			}
		}else{
			System.out.println("Bu bölgeyi zaten temizledin");
			location = new SafeHouse(player);
		}
	}

	private boolean isBattleLocationEligible(Class<?> battleLocationClass){
		if(cleanBattleLocs.contains(battleLocationClass))
			return false; // bölge zaten temizlenmişse false döndürür
		return true; // temiz değilse true
	}
}
