package com.pqt.client.gui.modules.stock_screen;

import com.pqt.client.gui.modules.IGuiModule;
import com.pqt.client.gui.modules.stock_screen.product_manager_screen.ProductManagerScreen;
import com.pqt.client.gui.modules.stock_screen.product_manager_screen.ProductManagerScreenFactory;
import com.pqt.client.module.stock.StockService;
import javafx.scene.layout.Pane;

public class StockScreen implements IGuiModule {

    private StockScreenView view;

    public StockScreen(StockService stockService) {
        StockScreenModel model = new StockScreenModel(stockService);
        StockScreenController ctrl = new StockScreenController(model);
        view = new StockScreenView(ctrl, new ProductManagerScreenFactory(stockService));

        ctrl.setView(view);
        ctrl.refreshView();
    }

    @Override
    public String getModuleName() {
        return "Stock";
    }

    @Override
    public Pane getPane() {
        return view.getPane();
    }
}
