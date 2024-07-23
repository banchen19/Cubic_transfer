package cn.nukkitmot.exampleplugin.form;

import cn.nukkit.Player;
import cn.nukkit.form.handler.FormResponseHandler;
import cn.nukkit.form.window.FormWindowModal;

public class DemoModalForm {
    public static void open(Player player) {
        FormWindowModal form = new FormWindowModal("Title", "Description", "true", "false");
        // Set actions for confirm and cancel
        form.addHandler(FormResponseHandler.withoutPlayer(ignored -> {
            if (form.wasClosed()) return;
            if (form.getResponse().getClickedButtonId() == 0) {
                // User clicked the true button
            } else {
                // User clicked the false button
            }
        }));
        // Show the form to the player
        player.showFormWindow(form);
    }
}