package com.vero.hilibrary.log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HiLogManager {

    private HiLogConfig config;
    private static HiLogManager instance;
    //打印器
    private List<HiLogPrinter> printers = new ArrayList<>();

    public static HiLogManager getInstance() {
        return instance;
    }


    public HiLogManager(HiLogConfig config, HiLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static void init(@NonNull HiLogConfig config, HiLogPrinter... printers) {
        instance = new HiLogManager(config, printers);
    }

    public HiLogConfig getConfig() {
        return config;
    }

    public List<HiLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(HiLogPrinter printer) {
        this.printers.add(printer);
    }

    public void removePrinters(HiLogPrinter printer) {
        if (printers != null) {
            this.printers.remove(printer);
        }
    }
}
