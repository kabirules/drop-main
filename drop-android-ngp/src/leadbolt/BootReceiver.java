package leadbolt;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.nqiacyviyqlpivoc.AdController;

public class BootReceiver extends BroadcastReceiver {
	
	private static final String MY_LEADBOLT_REENGAGEMENT = "540495798";
	
	public void onReceive(Context ctx, Intent intent) {
		//register the reengagement on reboot
		AdController ad = new AdController(ctx, MY_LEADBOLT_REENGAGEMENT);
		ad.loadReEngagement();
		// Other App specific code here
	}
}