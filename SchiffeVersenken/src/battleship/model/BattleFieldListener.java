package battleship.model;

import java.util.EventListener;

public interface BattleFieldListener extends EventListener{

    void update(BattleFieldEvent event);
}