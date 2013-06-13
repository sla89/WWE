package fhv.eclipse2013.wwe.impl.scope;

import java.util.Timer;
import java.util.TimerTask;

public class ScopeTimer {

	Timer t;

	public boolean startTimer(TimerTask t) {
		if (this.t == null) {
			this.t = new Timer();
			this.t.schedule(t, 0, 100);
		} else {
			this.stopTimer();
			return true;
		}
		return false;
	}

	public void stopTimer() {
		if (this.t != null) {
			this.t.cancel();
			this.t = null;
		}
	}
}
