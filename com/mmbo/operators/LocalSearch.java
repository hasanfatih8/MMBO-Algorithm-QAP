package com.mmbo.operators;

import com.mmbo.Solution;

public class LocalSearch {
    
    static Solution applyLocalSearch(Memeplex.LocalSearch type, int depth, Solution child) {
        switch(type) {
            case SwapBestII:
                //return SwapBestII(depth, child);
            case SwapFirstII:
                //return SwapBestII(depth, child);
            default:
                return child;
        }
    }
}
