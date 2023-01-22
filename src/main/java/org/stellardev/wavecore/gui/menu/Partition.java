package org.stellardev.wavecore.gui.menu;

import com.google.common.collect.Sets;

import java.util.Set;

public class Partition {

    private final Set<Integer> slots = Sets.newHashSet();

    public Partition(int minIndex, int maxIndex) {
        for (int i = minIndex; i <= maxIndex; ++i)
            slots.add(i);
    }

    public boolean contains(int slot) {
        return slots.contains(slot);
    }

}
