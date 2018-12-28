package com.logiccombine.artmate.logiccombine;

import android.app.Activity;

public class AdditionalFinding {
    private Activity InGameActivity;
    AdditionalFinding (Activity activityInGame){
        InGameActivity = activityInGame;
    }

    public String findImgByArithmeticOperation(arithmeticOperations arithmeticOperation){
        if (arithmeticOperation == arithmeticOperations.PLUS) return InGameActivity.getResources().getResourceEntryName(R.drawable.explus);
        if (arithmeticOperation == arithmeticOperations.MINUS) return InGameActivity.getResources().getResourceEntryName(R.drawable.exminus);
        if (arithmeticOperation == arithmeticOperations.MULTIPLY) return InGameActivity.getResources().getResourceEntryName(R.drawable.exmultiply);
        if (arithmeticOperation == arithmeticOperations.DIVIDE) return InGameActivity.getResources().getResourceEntryName(R.drawable.exshare);
        if (arithmeticOperation == arithmeticOperations.POWER) return InGameActivity.getResources().getResourceEntryName(R.drawable.expower);
        if (arithmeticOperation == arithmeticOperations.CONNECT) return InGameActivity.getResources().getResourceEntryName(R.drawable.excombine);
        return InGameActivity.getResources().getResourceEntryName(R.drawable.exemptycell);
    }

    public int findSignIdByPosition(int position){
        if (position == 0) return InGameActivity.findViewById(R.id.forSign1).getId();
        if (position == 1) return InGameActivity.findViewById(R.id.forSign2).getId();
        if (position == 2) return InGameActivity.findViewById(R.id.forSign3).getId();
        if (position == 3) return InGameActivity.findViewById(R.id.forSign4).getId();
        if (position == 4) return InGameActivity.findViewById(R.id.forSign5).getId();
        if (position == 5) return InGameActivity.findViewById(R.id.forSign6).getId();
        if (position == 6) return InGameActivity.findViewById(R.id.forSign7).getId();
        else return InGameActivity.findViewById(R.id.forSign8).getId();
    }

    public int findArithmeticButtonIdByOperation(arithmeticOperations arithmeticOperation){
        if (arithmeticOperation == arithmeticOperations.PLUS) return InGameActivity.findViewById(R.id.plusButton).getId();
        if (arithmeticOperation == arithmeticOperations.MINUS) return InGameActivity.findViewById(R.id.minusButton).getId();
        if (arithmeticOperation == arithmeticOperations.MULTIPLY) return InGameActivity.findViewById(R.id.multiplyButton).getId();
        if (arithmeticOperation == arithmeticOperations.DIVIDE) return InGameActivity.findViewById(R.id.shareButton).getId();
        if (arithmeticOperation == arithmeticOperations.POWER) return InGameActivity.findViewById(R.id.powerButton).getId();
        else return InGameActivity.findViewById(R.id.connectButton).getId();
    }
}
