/*
 * Copyright (c) 2018 superblaubeere27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package uwu.smsgamer.pasteclient.notifications;

import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.*;

import java.util.concurrent.LinkedBlockingQueue;

public class NotificationManager {
    @NotNull
    private static final LinkedBlockingQueue<Notification> pendingNotifications = new LinkedBlockingQueue<>();
    @Nullable
    private static Notification lastNotif;
    @Nullable
    private static Notification currentNotification = null;

    public static void show(Notification notification) {
        if (Minecraft.getMinecraft().thePlayer != null && notification != null && !notification.equals(lastNotif)) {
            pendingNotifications.add(notification);
            lastNotif = notification;
        }
    }

    public static void update() {
        if (currentNotification != null && !currentNotification.isShown()) {
            if (currentNotification.equals(lastNotif)) lastNotif = null;
            currentNotification = null;
        }

        if (currentNotification == null && !pendingNotifications.isEmpty()) {
            currentNotification = pendingNotifications.poll();
            currentNotification.show();
        }

    }

    public static void render() {
        update();

        if (currentNotification != null)
            currentNotification.render();
    }
}
