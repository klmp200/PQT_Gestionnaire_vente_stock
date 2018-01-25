package com.pqt.client.gui.frames.main_frame;

import com.pqt.client.gui.ressources.components.generics.IFXComponent;
import com.pqt.client.gui.ressources.components.generics.others.SideBar;
import com.pqt.client.gui.ressources.components.generics.others.listeners.ISideBarListener;
import com.pqt.client.gui.ressources.strings.GUIStringTool;
import com.pqt.core.entities.user_account.Account;
import com.pqt.core.entities.user_account.AccountLevel;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

class MainFrameView implements IFXComponent{

    private final MainFrameController ctrl;

    private BorderPane mainPane;
    private VBox buttonHolder;
    private ObjectProperty<AccountLevel> currentAccountLevel;
    private Label accountNameLabel;

    MainFrameView(MainFrameController ctrl) {
        this.ctrl = ctrl;
        currentAccountLevel = new SimpleObjectProperty<>(AccountLevel.getLowest());
        initGui();
    }

    private void initGui(){
        mainPane = new BorderPane();
        mainPane.getStyleClass().addAll("main-module-pane", "main-frame");

        buttonHolder = new VBox();

        SideBar sidebar = new SideBar();
        sidebar.setFillWidth(true);
        SideBar.setVgrow(buttonHolder, Priority.ALWAYS);
        buttonHolder.prefWidthProperty().bind(sidebar.widthProperty());
        sidebar.getChildren().add(buttonHolder);

        accountNameLabel = new Label();
        Button disconnectionButton = new Button(GUIStringTool.getLogoutButtonLabel());
        disconnectionButton.setOnAction((event -> ctrl.onAccountDisconnectionRequested()));
        sidebar.getChildren().addAll(accountNameLabel, disconnectionButton);

        mainPane.setLeft(sidebar);

        Button sidebarCtrl = new Button();
        if(sidebar.isExpanded())
            sidebarCtrl.setText(GUIStringTool.getSideBarCollapseButtonLabel());
        else
            sidebarCtrl.setText(GUIStringTool.getSideBarExpandButtonLabel());
        sidebarCtrl.setOnMouseClicked(event -> {
            if(sidebar.isExpanded())
                sidebar.collapse();
            else if(sidebar.isCollapsed())
                sidebar.expand();
        });
        sidebar.addListener(new ISideBarListener() {
            @Override
            public void onCollapsedFinished() {
                sidebarCtrl.setText(GUIStringTool.getSideBarExpandButtonLabel());
            }

            @Override
            public void onExpandFinished() {
                sidebarCtrl.setText(GUIStringTool.getSideBarCollapseButtonLabel());
            }
        });
        mainPane.setTop(sidebarCtrl);
    }

    @Override
    public Pane getPane() {
        return mainPane;
    }

    void addGuiModule(String moduleName, Pane moduleContent, AccountLevel requiredLevel, boolean setActive){
        Button button = new Button(moduleName);
        button.getStyleClass().add("menu-button");

        Runnable buttonActivationCode = ()->{
            buttonHolder.getChildren()
                    .stream()
                    .filter(Button.class::isInstance)
                    .map(Button.class::cast)
                    .forEach(b-> b.getStyleClass().remove("menu-button-selected"));
            button.getStyleClass().add("menu-button-selected");
            Platform.runLater(()->{
                buttonHolder.getChildren().forEach(Node::applyCss);
                mainPane.setCenter(moduleContent);
            });
        };
        button.setOnMouseClicked(event-> {
            if(event.getButton().equals(MouseButton.PRIMARY))
                buttonActivationCode.run();
        });
        button.setOnKeyTyped(event->{
            if (event.getCode().equals(KeyCode.ENTER))
                buttonActivationCode.run();
        });
        currentAccountLevel.addListener((obs, oldVal, newVal)->button.setDisable(requiredLevel.compareTo(newVal)>0));
        button.setDisable(requiredLevel.compareTo(currentAccountLevel.get())>0);
        if(setActive)
            buttonActivationCode.run();
        buttonHolder.getChildren().add(button);
    }

    void setCurrentAccount(Account account){
        accountNameLabel.setText(GUIStringTool.getAccountStringConverter().toString(account));
    }

    void updateModuleButtonLock(AccountLevel level) {
        currentAccountLevel.setValue(level);
    }

    public void clearModuleView() {
        mainPane.setCenter(null);
    }
}
