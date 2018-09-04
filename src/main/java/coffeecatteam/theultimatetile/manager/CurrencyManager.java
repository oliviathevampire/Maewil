package coffeecatteam.theultimatetile.manager;

import coffeecatteam.theultimatetile.entities.player.EntityPlayerMP;
import coffeecatteam.theultimatetile.manager.iinterface.ITickableManager;

import java.util.ArrayList;
import java.util.List;

public class CurrencyManager implements ITickableManager {

    private List<CurrencyHolder> currencyHolders;

    public CurrencyManager() {
        currencyHolders = new ArrayList<>();
    }

    @Override
    public void tick() {

    }

    public CurrencyHolder getCurrencyHolder(String username) {
        for (int i = 0; i < currencyHolders.size(); i++)
            if (currencyHolders.get(i).getPlayer().getUsername().equals(username))
                return currencyHolders.get(i);
        return null;
    }

    private class CurrencyHolder {

        private EntityPlayerMP player;
        private int currency;

        public CurrencyHolder(EntityPlayerMP player, int currency) {
            this.player = player;
            this.currency = currency;
        }

        public EntityPlayerMP getPlayer() {
            return player;
        }

        public void setPlayer(EntityPlayerMP player) {
            this.player = player;
        }

        public int getCurrency() {
            return currency;
        }

        public void setCurrency(int currency) {
            this.currency = currency;
        }
    }
}
