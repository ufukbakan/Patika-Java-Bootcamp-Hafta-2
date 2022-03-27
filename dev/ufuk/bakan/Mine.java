package dev.ufuk.bakan;
public class Mine extends BattleLoc {

    Mine(Player player) {
        super(player, "Maden", new Snake(), null);
    }

    @Override
	protected void markLocationAsClean(){
		Game.cleanBattleLocs.add(Mine.class);
	}

    // Düşman temizliğinden sonra bölge ödülü olmayan override edilmiş metot:
    @Override
    public boolean getLocation() {
        int obsCount = obstacle.count();
        System.out.println("Şuan buradasınız : " + this.getName());
        System.out.println("Dikkatli ol! Burada " + obsCount + " tane " + obstacle.getName() + " yaşıyor !");
        System.out.print("<S>avaş veya <K>aç :");
        String selCase = scan.nextLine();
        selCase = selCase.toUpperCase();
        if (selCase.equals("S")) {
            if (combat(obsCount)) {
                System.out.println(this.getName() + " bölgesindeki tüm düşmanları temizlediniz !");
                return true;
            }
            if (player.getHealthy() <= 0) {
                System.out.println("Öldünüz !");
                return false;
            }

        }
        return true;
    }

    // Her düşman öldüğünde rastgele ödül düşen combat metodu :
    @Override
    public boolean combat(int obsCount) {
        for (int i = 0; i < obsCount; i++) {
            int defObsHealth = obstacle.getHealth();
            playerStats();
            enemyStats();
            while (player.getHealthy() > 0 && obstacle.getHealth() > 0) {
                System.out.print("<V>ur veya <K>aç :");
                String selCase = scan.nextLine();
                selCase = selCase.toUpperCase();
                if (selCase.equals("V")) {
                    if (Math.random() < 0.5) {
                        if (obstacle.getHealth() > 0) {
                            System.out.println("Canavar size vurdu !");
                            player.setHealthy(
                                    player.getHealthy() - (obstacle.getDamage() - player.getInv().getArmor()));
                            afterHit();
                        }
                        System.out.println("Siz vurdunuz !");
                        obstacle.setHealth(obstacle.getHealth() - player.getTotalDamage());
                        afterHit();
                    } else {
                        System.out.println("Siz vurdunuz !");
                        obstacle.setHealth(obstacle.getHealth() - player.getTotalDamage());
                        afterHit();
                        if (obstacle.getHealth() > 0) {
                            System.out.println("Canavar size vurdu !");
                            player.setHealthy(
                                    player.getHealthy() - (obstacle.getDamage() - player.getInv().getArmor()));
                            afterHit();
                        }
                    }
                } else {
                    return false;
                }
            }

            if (obstacle.getHealth() < player.getHealthy()) {
                System.out.println("Düşmanı yendiniz !");
                pickRandomAward();
                obstacle.setHealth(defObsHealth);
            } else {
                return false;
            }
            System.out.println("-------------------");
        }
        return true;
    }

    // Her bir yılan öldüğünde çağırılacak :
    private void pickRandomAward() {
        double yourLuck = Math.round(Math.random() * 100); // [0-100] arası random yourLuck
        if (yourLuck <= 45) { // yourLuck ilk 45 lik dilimdeyse ödül yok
            System.out.println("Hiç bir ödül kazanamadınız.");
        } else if (yourLuck <= 70) { // yourLuck sonraki [45-70] dilimindeyse ödül para
            double yourCoinLuck = Math.round(Math.random() * 100); // [0-100 arası] coinLuck
            int yourCoin = 1;
            if (yourCoinLuck <= 50) { // coinLuck 0-50 arasında çıkarsa 1 coin
                yourCoin = 1;
            } else if (yourCoinLuck <= 80) { // coinLuck 50-80 arasındaysa 5 coin
                yourCoin = 5;
            } else { // coinLuck 80-100 arasındaysa 10 coin
                yourCoin = 10;
            }
            player.setMoney(player.getMoney() + yourCoin);
            System.out.println(yourCoin + " para kazandınız.");
        } else if (yourLuck <= 85) { // yourLuck [70-85] arasındaysa ödül silah
            double yourWeaponLuck = Math.round(Math.random() * 100);
            int yourWeaponId = 1;
            String wName = "Tabanca";
            if (yourWeaponLuck <= 50) {
                yourWeaponId = 1;
                wName = "Tabanca";
            } else if (yourWeaponLuck <= 80) {
                yourWeaponId = 2;
                wName = "Kılıç";
            } else {
                yourWeaponId = 3;
                wName = "Tüfek";
            }
            String choice = "";
            while (!(choice.equalsIgnoreCase("e") || choice.equalsIgnoreCase("h"))) {
                System.out.println(String.format(
                        "%s buldunuz, kendi silahınızın yerine kuşanmak ister misiniz?\n<E>vet / <H>ayır", wName));
                choice = scan.nextLine();
            }
            if (choice.equalsIgnoreCase("e")) {
                ToolStore.buyWeaponForFree(player, yourWeaponId);
            }
        } else { // yourLuck [85-100] arasındaysa ödül zırh
            double yourArmorLuck = Math.round(Math.random() * 100);
            int yourArmorId = 1;
            String aName = "Hafif Zırh";
            if (yourArmorLuck <= 50) {
                yourArmorId = 1;
                aName = "Hafif Zırh";
            } else if (yourArmorLuck <= 80) {
                yourArmorId = 2;
                aName = "Orta Zırh";
            } else {
                yourArmorId = 3;
                aName = "Ağır Zırh";
            }
            String choice = "";
            while (!(choice.equalsIgnoreCase("e") || choice.equalsIgnoreCase("h"))) {
                System.out.println(String.format(
                        "%s buldunuz, kendi zırhınızın yerine kuşanmak ister misiniz?\n<E>vet / <H>ayır", aName));
                choice = scan.nextLine();
            }
            if (choice.equalsIgnoreCase("e")) {
                ToolStore.buyArmorForFree(player, yourArmorId);
            }
        }
    }

}
