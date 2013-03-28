package battleship.model;

import java.util.EventObject;

public class BattleFieldEvent extends EventObject{

	private static final long serialVersionUID = 1L;

	public BattleFieldEvent(BattleField sender) {
        super(sender);
    }
}