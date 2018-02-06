package com.graction.developer.lumi.Util.Parser;

/**
 * Created by Graction06 on 2018-01-17.
 */

public class MathematicsManager {
    private static final MathematicsManager instance = new MathematicsManager();

    public static MathematicsManager getInstance() {
        return instance;
    }

    public int rounds(double d, double rat, double digit) {
        return (int) Math.round(Math.round((d * rat) * digit) / digit);
    }

    public int rounds(String d, String rat, double digit) {
        return (int) Math.round(Math.round((Double.parseDouble(d) * Double.parseDouble(rat)) * digit) / digit);
    }
}
