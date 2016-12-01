package flappybird3.game;
/**
 * Rozhran� pro objekty,kter� pot�ebuj� v�det, kolik �asu (tiku) ub�hlo od za��tku hry.
 * @author lavican1
 *
 */
public interface TickAware {
	/**
	 * zmeni stav herni entity s ohledem na zmeni herniho casu
	 * @param ticksSinceStart cas od zahajeni hry
	 */
	void tick(long ticksSinceStart);
	
}
