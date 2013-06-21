package fhv.eclipse2013.wwe.impl.scope;

import java.util.Timer;
import java.util.TimerTask;

public class ScopeTimer {

	Timer t;
	private int intervall;

	public ScopeTimer(int intervall) {
		this.intervall = intervall;
	}

	public boolean startTimer(TimerTask t) {
		if (this.t == null) {
			this.t = new Timer();
			this.t.schedule(t, this.intervall, this.intervall);
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

	public void setIntervall(int intervall, TimerTask t) {
		this.intervall = intervall;
		if (t != null) {
			startTimer(t);
			startTimer(t);
		}
	}
}
