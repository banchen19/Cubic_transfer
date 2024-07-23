package cn.nukkitmot.exampleplugin.form;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.handler.FormResponseHandler;
import cn.nukkit.form.window.FormWindowSimple;

public class DemoSimpleForm {
    public static void open(Player player) {
        FormWindowSimple form = new FormWindowSimple("Title", "Description");
        // Add buttons
        form.addButton(new ElementButton("Button 1"));
        form.addButton(new ElementButton("Button 2"));
        form.addButton(new ElementButton("Button 3"));
        // Set button click handling
        form.addHandler(FormResponseHandler.withoutPlayer(ignored -> {
            if (form.wasClosed()) return;
            int buttonIndex = form.getResponse().getClickedButtonId(); // Get the clicked button index
            String buttonText = form.getResponse().getClickedButton().getText(); // Get the clicked button text
            switch (buttonIndex) {
                case 0:
                    // Handle button 1 action
                    break;
                case 1:
                    // Handle button 2 action
                    break;
                case 2:
                    // Handle button 3 action
                    break;
                default:
                    // Default handling
                    break;
            }
        }));
        // Show the form to the player
        player.showFormWindow(form);
    }
}