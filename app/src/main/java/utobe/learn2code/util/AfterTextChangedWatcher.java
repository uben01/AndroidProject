package utobe.learn2code.util;

import android.text.TextWatcher;

public abstract class AfterTextChangedWatcher implements TextWatcher {
    // Dummy class, hogy ne kelljen mindig kiírni az alábbi függvényeket
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
