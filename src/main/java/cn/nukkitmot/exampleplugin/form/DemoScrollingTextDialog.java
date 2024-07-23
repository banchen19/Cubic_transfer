package cn.nukkitmot.exampleplugin.form;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.form.window.FormWindowDialog;
import cn.nukkit.form.window.ScrollingTextDialog;

public class DemoScrollingTextDialog {
    public static void open(Player player, Entity entity) {
        FormWindowDialog dialog = new FormWindowDialog("Title", "Description", entity);
        // Add options
        dialog.addButton("Option 1");
        dialog.addButton("Option 2");
        // Set submission action
        dialog.addHandler((player_, response) -> {
            String buttonText = response.getClickedButton().getName(); // Get the clicked button text
            // Handle user-submitted data
        });
        ScrollingTextDialog form = new ScrollingTextDialog(player, dialog, 1);
        // Show the dialog to the player
        form.send(player);
    }
}